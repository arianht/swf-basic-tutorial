package pw.hatefi.swf.factory;

import com.amazonaws.services.simpleworkflow.model.ChildPolicy;
import com.amazonaws.services.simpleworkflow.model.RegisterWorkflowTypeRequest;
import com.amazonaws.services.simpleworkflow.model.TaskList;
import pw.hatefi.swf.supplier.DomainNameSupplier;

import javax.inject.Inject;

public class RegisterWorkflowTypeRequestFactory {
  @Inject
  public RegisterWorkflowTypeRequestFactory(final DomainNameSupplier domainNameSupplier,
                                            final TaskListFactory taskListFactory) {
    this.domainName = domainNameSupplier.get();
    this.taskListFactory = taskListFactory;
  }

  public RegisterWorkflowTypeRequest create(final String workflowName,
                                            final String workflowVersion) {
    final TaskList taskList = taskListFactory.create();
    return new RegisterWorkflowTypeRequest().withDomain(domainName)
                                            .withName(workflowName)
                                            .withVersion(workflowVersion)
                                            .withDefaultTaskList(taskList)
                                            .withDefaultChildPolicy(DEFAULT_CHILD_POLICY)
                                            .withDefaultTaskStartToCloseTimeout(DEFAULT_TASK_START_TO_CLOSE_TIMEOUT)
                                            .withDefaultExecutionStartToCloseTimeout(DEFAULT_EXECUTION_START_TO_CLOSE_TIMEOUT);
  }

  private final String domainName;
  private final TaskListFactory taskListFactory;

  private final String DEFAULT_CHILD_POLICY = ChildPolicy.TERMINATE.name();
  private final String DEFAULT_TASK_START_TO_CLOSE_TIMEOUT = "3600";
  private final String DEFAULT_EXECUTION_START_TO_CLOSE_TIMEOUT = String.valueOf(24 * 3600);
}
