package pw.hatefi.swf.scheduler;

import com.amazonaws.services.simpleworkflow.model.ActivityType;
import com.amazonaws.services.simpleworkflow.model.Decision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.hatefi.swf.factory.decision.ScheduleActivityTaskDecisionFactory;

import javax.inject.Inject;
import java.util.List;

public class ActivityTaskScheduler {
  @Inject
  public ActivityTaskScheduler(
      final ScheduleActivityTaskDecisionFactory scheduleActivityTaskDecisionFactory) {
    this.scheduleActivityTaskDecisionFactory = scheduleActivityTaskDecisionFactory;
  }

  public void scheduleActivityWithInput(final List<Decision> decisions,
                                        final ActivityType activityType,
                                        final String input)
  {
    LOGGER.info("Scheduling activity task with input: {}, {}", activityType.getName(), input);
    decisions.add(scheduleActivityTaskDecisionFactory.create(activityType, input));
  }

  public void scheduleActivity(final List<Decision> decisions, final ActivityType activityType)
  {
    LOGGER.info("Scheduling activity task: {}", activityType.getName());
    decisions.add(scheduleActivityTaskDecisionFactory.create(activityType));
  }

  private final ScheduleActivityTaskDecisionFactory scheduleActivityTaskDecisionFactory;

  private static final Logger LOGGER = LoggerFactory.getLogger(ActivityTaskScheduler.class);
}
