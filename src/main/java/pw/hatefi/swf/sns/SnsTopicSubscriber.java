package pw.hatefi.swf.sns;

import com.amazonaws.services.sns.AmazonSNS;

import javax.inject.Inject;

public class SnsTopicSubscriber {
  @Inject
  public SnsTopicSubscriber(final AmazonSNS snsClient) {
    this.snsClient = snsClient;
  }

  public void subscribeEmail(final String topicArn, final String email) {
    snsClient.subscribe(topicArn, EMAIL_PROTOCOL, email);
  }

  public void subscribeSms(final String topicArn, final String phone) {
    snsClient.subscribe(topicArn, SMS_PROTOCOL, phone);
  }

  private final AmazonSNS snsClient;

  public static final String EMAIL_PROTOCOL = "email";
  public static final String SMS_PROTOCOL = "sms";
}
