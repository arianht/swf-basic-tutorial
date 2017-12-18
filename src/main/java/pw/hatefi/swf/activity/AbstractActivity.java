package pw.hatefi.swf.activity;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.model.ActivityTask;
import com.amazonaws.services.simpleworkflow.model.RespondActivityTaskCompletedRequest;
import com.amazonaws.services.simpleworkflow.model.RespondActivityTaskFailedRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.hatefi.swf.factory.RespondActivityTaskCompletedRequestFactory;
import pw.hatefi.swf.factory.RespondActivityTaskFailedRequestFactory;

import java.io.IOException;

public abstract class AbstractActivity {
  public AbstractActivity(final AmazonSimpleWorkflow swfClient,
                          final RespondActivityTaskCompletedRequestFactory taskCompletedRequestFactory,
                          final RespondActivityTaskFailedRequestFactory taskFailedRequestFactory) {
    this.swfClient = swfClient;
    this.taskCompletedRequestFactory = taskCompletedRequestFactory;
    this.taskFailedRequestFactory = taskFailedRequestFactory;
  }

  public void perform(final ActivityTask activityTask) {
    final String activityName = activityTask.getActivityType().getName();
    final String taskToken = activityTask.getTaskToken();
    try {
      final String result = internalPerform(activityTask);
      final RespondActivityTaskCompletedRequest request =
          taskCompletedRequestFactory.create(taskToken, result);
      swfClient.respondActivityTaskCompleted(request);
      LOGGER.warn("--- Activity task completed: {}", activityName);
    } catch (final Exception e) {
      LOGGER.warn("--- Activity task failed: {}", activityName);
      LOGGER.warn("Exception: {}", e);
      final RespondActivityTaskFailedRequest request =
          taskFailedRequestFactory.create(taskToken, activityTask.getActivityType().getName() + " failed.");
      swfClient.respondActivityTaskFailed(request);
    }
  }

  protected abstract String internalPerform(final ActivityTask activityTask) throws IOException, InterruptedException;

  private final AmazonSimpleWorkflow swfClient;
  private final RespondActivityTaskCompletedRequestFactory taskCompletedRequestFactory;
  private final RespondActivityTaskFailedRequestFactory taskFailedRequestFactory;

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractActivity.class);
}
