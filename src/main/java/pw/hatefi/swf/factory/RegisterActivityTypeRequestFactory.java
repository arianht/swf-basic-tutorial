package pw.hatefi.swf.factory;

import com.amazonaws.services.simpleworkflow.model.ActivityType;
import com.amazonaws.services.simpleworkflow.model.RegisterActivityTypeRequest;
import com.amazonaws.services.simpleworkflow.model.TaskList;
import pw.hatefi.swf.supplier.DomainNameSupplier;

import javax.inject.Inject;

public class RegisterActivityTypeRequestFactory {
  @Inject
  public RegisterActivityTypeRequestFactory(final DomainNameSupplier domainNameSupplier,
                                            final TaskListFactory taskListFactory) {
    this.domainName = domainNameSupplier.get();
    this.taskListFactory = taskListFactory;
  }

  public RegisterActivityTypeRequest create(final ActivityType activityType) {
    final TaskList taskList = taskListFactory.createForActivities();

    return new RegisterActivityTypeRequest()
        .withDomain(domainName)
        .withName(activityType.getName())
        .withVersion(activityType.getVersion())
        .withDefaultTaskList(taskList)
        .withDefaultTaskScheduleToStartTimeout(DEFAULT_TASK_SCHEDULE_TO_START_TIMEOUT)
        .withDefaultTaskScheduleToCloseTimeout(DEFAULT_TASK_SCHEDULE_TO_CLOSE_TIMEOUT)
        .withDefaultTaskStartToCloseTimeout(DEFAULT_TASK_START_TO_CLOSE_TIMEOUT)
        .withDefaultTaskHeartbeatTimeout(DEFAULT_TASK_HEARTBEAT_TIMEOUT);
  }

  private final String domainName;
  private final TaskListFactory taskListFactory;

  private static final String DEFAULT_TASK_SCHEDULE_TO_START_TIMEOUT = "120";
  private static final String DEFAULT_TASK_SCHEDULE_TO_CLOSE_TIMEOUT = "3800";
  private static final String DEFAULT_TASK_START_TO_CLOSE_TIMEOUT = "3600";
  private static final String DEFAULT_TASK_HEARTBEAT_TIMEOUT = "900";
}
