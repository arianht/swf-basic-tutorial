package pw.hatefi.swf.factory.decision;

import com.amazonaws.services.simpleworkflow.model.ActivityType;
import com.amazonaws.services.simpleworkflow.model.Decision;
import com.amazonaws.services.simpleworkflow.model.DecisionType;
import com.amazonaws.services.simpleworkflow.model.ScheduleActivityTaskDecisionAttributes;

import javax.inject.Inject;

public class ScheduleActivityTaskDecisionFactory {
  @Inject
  public ScheduleActivityTaskDecisionFactory(
      final ScheduleActivityTaskDecisionAttributesFactory scheduleActivityTaskDecisionAttributesFactory) {
    this.scheduleActivityTaskDecisionAttributesFactory =
        scheduleActivityTaskDecisionAttributesFactory;
  }

  public Decision create(final ActivityType activityType) {
    final ScheduleActivityTaskDecisionAttributes attributes =
        scheduleActivityTaskDecisionAttributesFactory.create(activityType);

    return create(attributes);
  }

  public Decision create(final ActivityType activityType, final String input) {
    final ScheduleActivityTaskDecisionAttributes attributes =
    scheduleActivityTaskDecisionAttributesFactory.create(activityType, input);

    return create(attributes);
  }

  private Decision create(final ScheduleActivityTaskDecisionAttributes attributes)
  {
    return new Decision().withDecisionType(DECISION_TYPE)
                         .withScheduleActivityTaskDecisionAttributes(attributes);
  }

  private final ScheduleActivityTaskDecisionAttributesFactory scheduleActivityTaskDecisionAttributesFactory;

  private static final DecisionType DECISION_TYPE = DecisionType.ScheduleActivityTask;
}
