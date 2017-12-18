package pw.hatefi.swf.workflow;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.model.Run;
import com.amazonaws.services.simpleworkflow.model.StartWorkflowExecutionRequest;
import com.amazonaws.services.simpleworkflow.model.WorkflowType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.hatefi.swf.factory.StartWorkflowExecutionRequestFactory;
import pw.hatefi.swf.registerer.WorkflowRegisterer;

import javax.inject.Inject;

public class WorkflowStarter {
  @Inject
  public WorkflowStarter(final AmazonSimpleWorkflow swfClient,
                         final WorkflowRegisterer workflowRegisterer,
                         final StartWorkflowExecutionRequestFactory startWorkflowExecutionRequestFactory,
                         final Workflow workflow) {
    this.swfClient = swfClient;
    this.workflowRegisterer = workflowRegisterer;
    this.startWorkflowExecutionRequestFactory = startWorkflowExecutionRequestFactory;
    this.workflow = workflow;
  }

  public void startWorkflow() throws InterruptedException {
    final WorkflowType workflowType = workflowRegisterer.registerWorkflow();
    final StartWorkflowExecutionRequest startRequest =
        startWorkflowExecutionRequestFactory.create(workflowType);
    final Run run = swfClient.startWorkflowExecution(startRequest);
    LOGGER.info("Workflow execution started with run id {}", run.getRunId());

    workflow.pollForDecisions();
  }

  private final AmazonSimpleWorkflow swfClient;
  private final WorkflowRegisterer workflowRegisterer;
  private final StartWorkflowExecutionRequestFactory startWorkflowExecutionRequestFactory;
  private final Workflow workflow;

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowStarter.class);
}
