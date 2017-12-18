package pw.hatefi.swf.factory.decision;

import com.amazonaws.services.simpleworkflow.model.ActivityType;
import com.amazonaws.services.simpleworkflow.model.ScheduleActivityTaskDecisionAttributes;
import pw.hatefi.swf.factory.TaskListFactory;

import javax.inject.Inject;
import java.util.UUID;

public class ScheduleActivityTaskDecisionAttributesFactory {
  @Inject
  public ScheduleActivityTaskDecisionAttributesFactory(final TaskListFactory taskListFactory)
  {
    this.taskListFactory = taskListFactory;
  }

  public ScheduleActivityTaskDecisionAttributes create(final ActivityType activityType,
                                                       final String input) {
    return create(activityType).withInput(input);
  }

  public ScheduleActivityTaskDecisionAttributes create(final ActivityType activityType) {
    return new ScheduleActivityTaskDecisionAttributes()
        .withActivityType(activityType)
        .withTaskList(taskListFactory.createForActivities())
        .withActivityId(UUID.randomUUID().toString());
  }

  private final TaskListFactory taskListFactory;
}
