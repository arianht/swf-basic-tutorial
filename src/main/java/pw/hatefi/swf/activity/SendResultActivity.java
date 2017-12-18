package pw.hatefi.swf.activity;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.model.ActivityTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.hatefi.swf.sns.SnsMessagePublisher;
import pw.hatefi.swf.exception.InvalidInputException;
import pw.hatefi.swf.factory.RespondActivityTaskCompletedRequestFactory;
import pw.hatefi.swf.factory.RespondActivityTaskFailedRequestFactory;
import pw.hatefi.swf.type.SubscriptionData;

import javax.inject.Inject;
import java.io.IOException;

import static com.google.common.base.Strings.isNullOrEmpty;

public class SendResultActivity extends AbstractActivity {
  @Inject
  public SendResultActivity(
      final AmazonSimpleWorkflow swfClient,
      final RespondActivityTaskCompletedRequestFactory taskCompletedRequestFactory,
      final RespondActivityTaskFailedRequestFactory taskFailedRequestFactory,
      final ObjectMapper objectMapper,
      final SnsMessagePublisher snsMessagePublisher) {
    super(swfClient, taskCompletedRequestFactory, taskFailedRequestFactory);

    this.objectMapper = objectMapper;
    this.snsMessagePublisher = snsMessagePublisher;
  }

  @Override
  protected String internalPerform(final ActivityTask activityTask) throws IOException {
    final String input = activityTask.getInput();
    if (isNullOrEmpty(input)) {
      throw new InvalidInputException();
    }
    final SubscriptionData subscriptionData = objectMapper.readValue(input, SubscriptionData.class);

    final String message = "Thanks, you've successfully confirmed registration, and your workflow is complete!";
    snsMessagePublisher.publishMessage(subscriptionData.getTopicArn(), message);
    LOGGER.info(message);

    return message;
  }

  private final ObjectMapper objectMapper;
  private final SnsMessagePublisher snsMessagePublisher;

  private static final Logger LOGGER = LoggerFactory.getLogger(SendResultActivity.class);
}
