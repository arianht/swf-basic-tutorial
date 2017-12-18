package pw.hatefi.swf.activity;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.model.ActivityTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import pw.hatefi.swf.sns.SnsTopicCreator;
import pw.hatefi.swf.sns.SnsTopicSubscriber;
import pw.hatefi.swf.exception.InvalidInputException;
import pw.hatefi.swf.factory.RespondActivityTaskCompletedRequestFactory;
import pw.hatefi.swf.factory.RespondActivityTaskFailedRequestFactory;
import pw.hatefi.swf.factory.SubscriptionDataFactory;
import pw.hatefi.swf.type.SubscriptionData;
import pw.hatefi.swf.type.UserContact;

import javax.inject.Inject;
import java.io.IOException;

import static com.google.common.base.Strings.isNullOrEmpty;

public class SubscribeTopicActivity extends AbstractActivity {
  @Inject
  public SubscribeTopicActivity(
      final AmazonSimpleWorkflow swfClient,
      final RespondActivityTaskCompletedRequestFactory taskCompletedRequestFactory,
      final RespondActivityTaskFailedRequestFactory taskFailedRequestFactory,
      final ObjectMapper objectMapper,
      final SnsTopicCreator snsTopicCreator,
      final SnsTopicSubscriber snsTopicSubscriber,
      final SubscriptionDataFactory subscriptionDataFactory) {
    super(swfClient, taskCompletedRequestFactory, taskFailedRequestFactory);

    this.objectMapper = objectMapper;
    this.snsTopicCreator = snsTopicCreator;
    this.snsTopicSubscriber = snsTopicSubscriber;
    this.subscriptionDataFactory = subscriptionDataFactory;
  }

  @Override
  protected String internalPerform(final ActivityTask activityTask) throws IOException {
    final String input = activityTask.getInput();
    if (isNullOrEmpty(input)) {
      throw new InvalidInputException();
    }
    final UserContact userContact = objectMapper.readValue(input, UserContact.class);

    final String topicArn = snsTopicCreator.createTopic();
    snsTopicSubscriber.subscribeEmail(topicArn, userContact.getEmail());
    snsTopicSubscriber.subscribeSms(topicArn, userContact.getPhone());
    final SubscriptionData result = subscriptionDataFactory.create(topicArn, userContact);

    return objectMapper.writeValueAsString(result);
  }

  private final ObjectMapper objectMapper;
  private final SnsTopicCreator snsTopicCreator;
  private final SnsTopicSubscriber snsTopicSubscriber;
  private final SubscriptionDataFactory subscriptionDataFactory;
}
