package pw.hatefi.swf.factory;

import com.amazonaws.services.simpleworkflow.model.TaskList;
import pw.hatefi.swf.supplier.ActivityTaskListNameSupplier;
import pw.hatefi.swf.supplier.WorkflowTaskListNameSupplier;

import javax.inject.Inject;

public class TaskListFactory {
  @Inject
  public TaskListFactory(final WorkflowTaskListNameSupplier workflowTaskListNameSupplier,
                         final ActivityTaskListNameSupplier activityTaskListNameSupplier) {
    this.workflowTaskListName = workflowTaskListNameSupplier.get();
    this.activityTaskListNameSupplier = activityTaskListNameSupplier;
  }

  public TaskList create() {
    return new TaskList().withName(workflowTaskListName);
  }

  public TaskList create(final String taskListName) {
    return new TaskList().withName(taskListName);
  }

  public TaskList createForActivities() {
    final String name = activityTaskListNameSupplier.get();
    return new TaskList().withName(name);
  }

  private final String workflowTaskListName;
  private final ActivityTaskListNameSupplier activityTaskListNameSupplier;
}
