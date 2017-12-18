package pw.hatefi.swf.factory.decision;

import com.amazonaws.services.simpleworkflow.model.Decision;
import com.amazonaws.services.simpleworkflow.model.RespondDecisionTaskCompletedRequest;

import java.util.List;

public class RespondDecisionTaskCompletedRequestFactory {
  public RespondDecisionTaskCompletedRequest create(final String taskToken,
                                                    final List<Decision> decisions) {
    return new RespondDecisionTaskCompletedRequest()
        .withTaskToken(taskToken)
        .withDecisions(decisions);
  }
}
