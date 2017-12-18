package pw.hatefi.swf.scheduler;

import com.amazonaws.services.simpleworkflow.model.Decision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.hatefi.swf.factory.decision.CompleteWorkflowExecutionDecisionFactory;

import javax.inject.Inject;
import java.util.List;

public class CompleteWorkflowScheduler {
  @Inject
  public CompleteWorkflowScheduler(
      final CompleteWorkflowExecutionDecisionFactory completeWorkflowExecutionDecisionFactory) {
    this.completeWorkflowExecutionDecisionFactory = completeWorkflowExecutionDecisionFactory;
  }

  public void scheduleCompletion(final List<Decision> decisions)
  {
    LOGGER.info("All activities have run! Ending workflow execution.");
    decisions.add(completeWorkflowExecutionDecisionFactory.create());
  }

  private final CompleteWorkflowExecutionDecisionFactory completeWorkflowExecutionDecisionFactory;

  private static final Logger LOGGER = LoggerFactory.getLogger(CompleteWorkflowScheduler.class);
}
