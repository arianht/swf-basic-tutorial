package pw.hatefi.swf.supplier;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Subscription;

import javax.inject.Inject;
import java.util.List;

public class SnsTopicSubscriptionsSupplier {
  @Inject
  public SnsTopicSubscriptionsSupplier(final AmazonSNS snsClient) {
    this.snsClient = snsClient;
  }

  public List<Subscription> get(final String topicArn) {
    return snsClient.listSubscriptionsByTopic(topicArn)
                    .getSubscriptions();
  }

  private final AmazonSNS snsClient;
}
