package pw.hatefi.swf.activity;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.model.ActivityTask;
import com.amazonaws.services.sns.model.Subscription;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.hatefi.swf.exception.InvalidInputException;
import pw.hatefi.swf.exception.NoSubscriptionsConfirmedException;
import pw.hatefi.swf.factory.RespondActivityTaskCompletedRequestFactory;
import pw.hatefi.swf.factory.RespondActivityTaskFailedRequestFactory;
import pw.hatefi.swf.supplier.SnsTopicSubscriptionsSupplier;
import pw.hatefi.swf.type.SubscriptionData;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;
import static pw.hatefi.swf.sns.SnsTopicSubscriber.EMAIL_PROTOCOL;
import static pw.hatefi.swf.sns.SnsTopicSubscriber.SMS_PROTOCOL;

public class WaitForConfirmationActivity extends AbstractActivity {
  @Inject
  public WaitForConfirmationActivity(
      final AmazonSimpleWorkflow swfClient,
      final RespondActivityTaskCompletedRequestFactory taskCompletedRequestFactory,
      final RespondActivityTaskFailedRequestFactory taskFailedRequestFactory,
      final SnsTopicSubscriptionsSupplier snsTopicSubscriptionsSupplier,
      final ObjectMapper objectMapper,
      final ActivityHeartbeatReporter activityHeartbeatReporter) {
    super(swfClient, taskCompletedRequestFactory, taskFailedRequestFactory);

    this.snsTopicSubscriptionsSupplier = snsTopicSubscriptionsSupplier;
    this.objectMapper = objectMapper;
    this.activityHeartbeatReporter = activityHeartbeatReporter;
  }

  @Override
  protected String internalPerform(final ActivityTask activityTask) throws IOException, InterruptedException {
    final String input = activityTask.getInput();
    if (isNullOrEmpty(input)) {
      throw new InvalidInputException();
    }
    final SubscriptionData subscriptionData = objectMapper.readValue(input, SubscriptionData.class);

    boolean subscriptionConfirmed = false;
    while (!subscriptionConfirmed) {
      final List<Subscription> subscriptions = snsTopicSubscriptionsSupplier.get(subscriptionData.getTopicArn());
      for (final Subscription subscription : subscriptions) {
        if (isEmailSubscription(subscriptionData, subscription)
            || isSmsSubscription(subscriptionData, subscription)) {
          // This is one of the endpoints we're interested in. Is it subscribed?
          if (!"PendingConfirmation".equals(subscription.getSubscriptionArn())) {
            LOGGER.info("Topic subscription confirmed for ({}: {})", subscription.getProtocol(),
                subscription.getEndpoint());
            return input;
          }
          LOGGER.info("Topic subscription still pending for ({}: {})", subscription.getProtocol(),
              subscription.getEndpoint());
        }
      }

      activityHeartbeatReporter.report(activityTask.getTaskToken());
      Thread.sleep(4000L);
    }

    if (!subscriptionConfirmed) {
      throw new NoSubscriptionsConfirmedException();
    }

    return input;
  }

  private boolean isEmailSubscription(final SubscriptionData subscriptionData,
                                      final Subscription subscription) {
    return EMAIL_PROTOCOL.equals(subscription.getProtocol())
        && subscriptionData.getUserContact().getEmail().equals(subscription.getEndpoint());
  }

  private boolean isSmsSubscription(final SubscriptionData subscriptionData,
                                    final Subscription subscription) {
    return SMS_PROTOCOL.equals(subscription.getProtocol())
        && subscriptionData.getUserContact().getPhone().equals(subscription.getEndpoint());
  }

  private final SnsTopicSubscriptionsSupplier snsTopicSubscriptionsSupplier;
  private final ObjectMapper objectMapper;
  private final ActivityHeartbeatReporter activityHeartbeatReporter;

  private static final Logger LOGGER = LoggerFactory.getLogger(WaitForConfirmationActivity.class);
}
