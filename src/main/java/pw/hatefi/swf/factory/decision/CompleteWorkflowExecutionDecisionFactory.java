package pw.hatefi.swf.factory.decision;

import com.amazonaws.services.simpleworkflow.model.CompleteWorkflowExecutionDecisionAttributes;
import com.amazonaws.services.simpleworkflow.model.Decision;
import com.amazonaws.services.simpleworkflow.model.DecisionType;

import javax.inject.Inject;

public class CompleteWorkflowExecutionDecisionFactory {
  @Inject
  public CompleteWorkflowExecutionDecisionFactory(
      final CompleteWorkflowExecutionDecisionAttributesFactory completeWorkflowExecutionDecisionAttributesFactory) {
    this.completeWorkflowExecutionDecisionAttributesFactory =
        completeWorkflowExecutionDecisionAttributesFactory;
  }

  public Decision create() {
    final CompleteWorkflowExecutionDecisionAttributes attributes =
        completeWorkflowExecutionDecisionAttributesFactory.create();

    return new Decision().withDecisionType(DECISION_TYPE)
                         .withCompleteWorkflowExecutionDecisionAttributes(attributes);
  }

  private final CompleteWorkflowExecutionDecisionAttributesFactory completeWorkflowExecutionDecisionAttributesFactory;

  private static final DecisionType DECISION_TYPE = DecisionType.CompleteWorkflowExecution;
}
