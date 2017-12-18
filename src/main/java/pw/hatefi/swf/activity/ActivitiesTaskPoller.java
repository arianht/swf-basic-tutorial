package pw.hatefi.swf.activity;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.model.ActivityTask;
import com.amazonaws.services.simpleworkflow.model.PollForActivityTaskRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.hatefi.swf.factory.PollForActivityTaskRequestFactory;
import pw.hatefi.swf.supplier.ActivitySupplier;

import javax.inject.Inject;

public class ActivitiesTaskPoller {
  @Inject
  public ActivitiesTaskPoller(
      final PollForActivityTaskRequestFactory pollForActivityTaskRequestFactory,
      final AmazonSimpleWorkflow swfClient,
      final ActivitySupplier activitySupplier) {
    this.pollForActivityTaskRequestFactory = pollForActivityTaskRequestFactory;
    this.swfClient = swfClient;
    this.activitySupplier = activitySupplier;
  }

  public void pollForActivities(final String taskListName) throws InterruptedException {
    while (true) {
      final PollForActivityTaskRequest request = pollForActivityTaskRequestFactory.create(taskListName);
      LOGGER.info("Polling for an activity task from the task list with request: {}", request);
      final ActivityTask task = swfClient.pollForActivityTask(request);
      LOGGER.info("Processing activity task with type{}", task.getActivityType());
      activitySupplier.get(task.getActivityType()).perform(task);
      Thread.sleep(5000);
    }
  }

  private final PollForActivityTaskRequestFactory pollForActivityTaskRequestFactory;
  private final AmazonSimpleWorkflow swfClient;
  private final ActivitySupplier activitySupplier;

  private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiesTaskPoller.class);
}
