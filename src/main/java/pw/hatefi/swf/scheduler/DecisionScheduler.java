package pw.hatefi.swf.scheduler;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.model.Decision;
import com.amazonaws.services.simpleworkflow.model.RespondDecisionTaskCompletedRequest;
import pw.hatefi.swf.factory.decision.RespondDecisionTaskCompletedRequestFactory;

import javax.inject.Inject;
import java.util.List;

public class DecisionScheduler {
  @Inject
  public DecisionScheduler(
      final RespondDecisionTaskCompletedRequestFactory respondDecisionTaskCompletedRequestFactory,
      final AmazonSimpleWorkflow swfClient) {
    this.respondDecisionTaskCompletedRequestFactory = respondDecisionTaskCompletedRequestFactory;
    this.swfClient = swfClient;
  }

  public void scheduleDecisions(final String taskToken, final List<Decision> decisions) {
    final RespondDecisionTaskCompletedRequest decisionTaskCompletedRequest =
        respondDecisionTaskCompletedRequestFactory.create(taskToken, decisions);
    swfClient.respondDecisionTaskCompleted(decisionTaskCompletedRequest);
  }

  private final AmazonSimpleWorkflow swfClient;
  private final RespondDecisionTaskCompletedRequestFactory respondDecisionTaskCompletedRequestFactory;
}
