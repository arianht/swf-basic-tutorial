package pw.hatefi.swf.registerer;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.model.RegisterWorkflowTypeRequest;
import com.amazonaws.services.simpleworkflow.model.TypeAlreadyExistsException;
import com.amazonaws.services.simpleworkflow.model.WorkflowType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.hatefi.swf.factory.RegisterWorkflowTypeRequestFactory;
import pw.hatefi.swf.factory.WorkflowTypeFactory;

import javax.inject.Inject;

public class WorkflowRegisterer {
  @Inject
  public WorkflowRegisterer(final RegisterWorkflowTypeRequestFactory registerWorkflowTypeRequestFactory,
                            final AmazonSimpleWorkflow swfClient,
                            final WorkflowTypeFactory workflowTypeFactory) {
    this.registerWorkflowTypeRequestFactory = registerWorkflowTypeRequestFactory;
    this.swfClient = swfClient;
    this.workflowTypeFactory = workflowTypeFactory;
  }

  public WorkflowType registerWorkflow() {
    LOGGER.info("Registering workflow: {}, {}", WORKFLOW_NAME,
        WORKFLOW_VERSION);
    final RegisterWorkflowTypeRequest request =
        registerWorkflowTypeRequestFactory.create(WORKFLOW_NAME, WORKFLOW_VERSION);

    try {
      swfClient.registerWorkflowType(request);
    } catch (final TypeAlreadyExistsException e) {
      LOGGER.info("Tried to register workflow {}, but it already exists!", WORKFLOW_NAME);
    }

    return workflowTypeFactory.create(WORKFLOW_NAME, WORKFLOW_VERSION);
  }

  private final RegisterWorkflowTypeRequestFactory registerWorkflowTypeRequestFactory;
  private final AmazonSimpleWorkflow swfClient;
  private final WorkflowTypeFactory workflowTypeFactory;

  private static final String WORKFLOW_NAME = "swf-sns-workflow";
  private static final String WORKFLOW_VERSION = "v1";

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowRegisterer.class);
}
