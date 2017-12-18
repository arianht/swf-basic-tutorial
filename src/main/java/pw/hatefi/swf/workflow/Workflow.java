package pw.hatefi.swf.workflow;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.model.ActivityType;
import com.amazonaws.services.simpleworkflow.model.Decision;
import com.amazonaws.services.simpleworkflow.model.DecisionTask;
import com.amazonaws.services.simpleworkflow.model.EventType;
import com.amazonaws.services.simpleworkflow.model.HistoryEvent;
import com.amazonaws.services.simpleworkflow.model.PollForDecisionTaskRequest;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.hatefi.swf.factory.PollForDecisionTaskRequestFactory;
import pw.hatefi.swf.scheduler.ActivityTaskScheduler;
import pw.hatefi.swf.scheduler.CompleteWorkflowScheduler;
import pw.hatefi.swf.scheduler.DecisionScheduler;
import pw.hatefi.swf.scheduler.FailWorkflowScheduler;
import pw.hatefi.swf.supplier.ActivityTypesSupplier;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Workflow {
  @Inject
  public Workflow(final PollForDecisionTaskRequestFactory pollForDecisionTaskRequestFactory,
                  final AmazonSimpleWorkflow swfClient,
                  final ActivityTaskScheduler activityTaskScheduler,
                  final CompleteWorkflowScheduler completeWorkflowScheduler,
                  final FailWorkflowScheduler failWorkflowScheduler,
                  final ActivityTypesSupplier activityTypesSupplier,
                  final DecisionScheduler decisionScheduler) {
    this.pollForDecisionTaskRequestFactory = pollForDecisionTaskRequestFactory;
    this.swfClient = swfClient;
    this.activityTaskScheduler = activityTaskScheduler;
    this.completeWorkflowScheduler = completeWorkflowScheduler;
    this.failWorkflowScheduler = failWorkflowScheduler;
    this.activities = activityTypesSupplier.get();
    this.decisionScheduler = decisionScheduler;
  }

  public void pollForDecisions() throws InterruptedException {
    Instant lastDecisionInstant = Instant.MIN;
    while (true) {
      final List<Decision> decisions = Lists.newArrayList();
      final PollForDecisionTaskRequest request =
          pollForDecisionTaskRequestFactory.create();
      LOGGER.info("Polling for a decision task from the task list with request: {}", request);
      final DecisionTask task = swfClient.pollForDecisionTask(request);
      ActivityType activityType;
      final List<HistoryEvent> eventsToProcess = getNewEvents(lastDecisionInstant,
          task.getEvents());
      LOGGER.info("Processing events {}", eventsToProcess.stream()
                                                         .map(HistoryEvent::getEventType)
                                                         .collect(Collectors.toList()));
      for (final HistoryEvent event : eventsToProcess) {
        switch (EventType.fromValue(event.getEventType()))
        {
          case WorkflowExecutionStarted:
            // Schedule the first activity on the list.
            activityType = activities.peekLast();
            activityTaskScheduler.scheduleActivity(decisions, activityType);
            break;
          case ActivityTaskCompleted:
            // We are running the activities in sequential order and using
            // the results of the previous activity as input for the next activity.
            activities.pollLast();
            if (activities.isEmpty()) {
              completeWorkflowScheduler.scheduleCompletion(decisions);
              decisionScheduler.scheduleDecisions(task.getTaskToken(), decisions);
              return;
            }
            // Schedule the next activity, passing it the results from the
            // previous activity.
            activityType = activities.peekLast();
            final String resultFromLastActivity = event.getActivityTaskCompletedEventAttributes()
                                                       .getResult();
            if (resultFromLastActivity != null) {
              activityTaskScheduler.scheduleActivityWithInput(decisions, activityType,
                  resultFromLastActivity);
            } else {
              activityTaskScheduler.scheduleActivity(decisions, activityType);
            }
            break;
          case ActivityTaskTimedOut:
            LOGGER.warn("An activity timed out!");
            failWorkflowScheduler.scheduleFailure(decisions);
            break;
          case ActivityTaskFailed:
            LOGGER.warn("An activity failed!");
            failWorkflowScheduler.scheduleFailure(decisions);
            break;
          case WorkflowExecutionCompleted:
            LOGGER.warn("Yay! Workflow execution completed!");
            return;
        }
      }
      lastDecisionInstant = eventsToProcess.stream()
                                           .map(HistoryEvent::getEventTimestamp)
                                           .map(Date::toInstant)
                                           .max(Comparator.naturalOrder())
                                           .orElseThrow(IllegalStateException::new);

      decisionScheduler.scheduleDecisions(task.getTaskToken(), decisions);

      Thread.sleep(5000);
    }
  }

  private List<HistoryEvent> getNewEvents(final Instant lastDecisionInstant,
                                          final List<HistoryEvent> events)
  {
    return events.stream()
                 .filter(event -> event.getEventTimestamp()
                                       .toInstant()
                                       .isAfter(lastDecisionInstant))
                 .collect(Collectors.toList());
  }

  private final PollForDecisionTaskRequestFactory pollForDecisionTaskRequestFactory;
  private final AmazonSimpleWorkflow swfClient;
  private final ActivityTaskScheduler activityTaskScheduler;
  private final CompleteWorkflowScheduler completeWorkflowScheduler;
  private final FailWorkflowScheduler failWorkflowScheduler;
  private final Deque<ActivityType> activities;
  private final DecisionScheduler decisionScheduler;

  private static final Logger LOGGER = LoggerFactory.getLogger(Workflow.class);
}
