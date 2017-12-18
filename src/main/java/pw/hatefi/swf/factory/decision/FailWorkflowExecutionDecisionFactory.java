package pw.hatefi.swf.factory.decision;

import com.amazonaws.services.simpleworkflow.model.Decision;
import com.amazonaws.services.simpleworkflow.model.DecisionType;
import com.amazonaws.services.simpleworkflow.model.FailWorkflowExecutionDecisionAttributes;

import javax.inject.Inject;

public class FailWorkflowExecutionDecisionFactory {
  @Inject
  public FailWorkflowExecutionDecisionFactory(
      final FailWorkflowExecutionDecisionAttributesFactory failWorkflowExecutionDecisionAttributesFactory) {
    this.failWorkflowExecutionDecisionAttributesFactory =
        failWorkflowExecutionDecisionAttributesFactory;
  }

  public Decision create() {
    final FailWorkflowExecutionDecisionAttributes attributes =
        failWorkflowExecutionDecisionAttributesFactory.create();

    return new Decision().withDecisionType(DECISION_TYPE)
                         .withFailWorkflowExecutionDecisionAttributes(attributes);
  }

  private final FailWorkflowExecutionDecisionAttributesFactory failWorkflowExecutionDecisionAttributesFactory;

  private static final DecisionType DECISION_TYPE = DecisionType.FailWorkflowExecution;
}
