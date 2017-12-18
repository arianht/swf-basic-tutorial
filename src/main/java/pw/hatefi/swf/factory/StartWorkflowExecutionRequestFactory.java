package pw.hatefi.swf.factory;

import com.amazonaws.services.simpleworkflow.model.StartWorkflowExecutionRequest;
import com.amazonaws.services.simpleworkflow.model.TaskList;
import com.amazonaws.services.simpleworkflow.model.WorkflowType;
import pw.hatefi.swf.supplier.DomainNameSupplier;
import pw.hatefi.swf.supplier.WorkflowIdSupplier;

import javax.inject.Inject;

public class StartWorkflowExecutionRequestFactory {
  @Inject
  public StartWorkflowExecutionRequestFactory(
      final DomainNameSupplier domainNameSupplier,
      final WorkflowIdSupplier workflowIdSupplier,
      final TaskListFactory taskListFactory) {
    this.domainName = domainNameSupplier.get();
    this.workflowId = workflowIdSupplier.get();
    this.taskListFactory = taskListFactory;
  }

  public StartWorkflowExecutionRequest create(final WorkflowType workflowType) {
    final TaskList taskList = taskListFactory.create();

    return new StartWorkflowExecutionRequest()
        .withDomain(domainName)
        .withWorkflowType(workflowType)
        .withWorkflowId(workflowId)
        .withTaskList(taskList)
        .withExecutionStartToCloseTimeout(EXECUTION_START_TO_CLOSE_TIMEOUT);
  }

  private final String domainName;
  private final String workflowId;
  private final TaskListFactory taskListFactory;

  private static final String EXECUTION_START_TO_CLOSE_TIMEOUT = "90";
}
