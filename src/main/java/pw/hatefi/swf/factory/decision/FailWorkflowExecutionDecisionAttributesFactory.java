package pw.hatefi.swf.factory.decision;

import com.amazonaws.services.simpleworkflow.model.FailWorkflowExecutionDecisionAttributes;

public class FailWorkflowExecutionDecisionAttributesFactory {
  public FailWorkflowExecutionDecisionAttributes create() {
    return new FailWorkflowExecutionDecisionAttributes();
  }
}
