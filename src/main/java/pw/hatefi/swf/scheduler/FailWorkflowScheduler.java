package pw.hatefi.swf.scheduler;

import com.amazonaws.services.simpleworkflow.model.Decision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.hatefi.swf.factory.decision.FailWorkflowExecutionDecisionFactory;

import javax.inject.Inject;
import java.util.List;

public class FailWorkflowScheduler {
  @Inject
  public FailWorkflowScheduler(
      final FailWorkflowExecutionDecisionFactory failWorkflowExecutionDecisionFactory) {
    this.failWorkflowExecutionDecisionFactory = failWorkflowExecutionDecisionFactory;
  }

  public void scheduleFailure(final List<Decision> decisions)
  {
    LOGGER.info("Failing workflow execution!");
    decisions.add(failWorkflowExecutionDecisionFactory.create());
  }

  private final FailWorkflowExecutionDecisionFactory failWorkflowExecutionDecisionFactory;

  private static final Logger LOGGER = LoggerFactory.getLogger(FailWorkflowScheduler.class);
}
