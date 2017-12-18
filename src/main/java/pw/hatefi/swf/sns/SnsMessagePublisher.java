package pw.hatefi.swf.sns;

import com.amazonaws.services.sns.AmazonSNS;

import javax.inject.Inject;

public class SnsMessagePublisher {
  @Inject
  public SnsMessagePublisher(final AmazonSNS snsClient) {
    this.snsClient = snsClient;
  }

  public void publishMessage(final String topicArn, final String message) {
    snsClient.publish(topicArn, message);
  }

  private final AmazonSNS snsClient;
}
