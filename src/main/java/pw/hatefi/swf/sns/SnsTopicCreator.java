package pw.hatefi.swf.sns;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreateTopicResult;

import javax.inject.Inject;

public class SnsTopicCreator {
  @Inject
  public SnsTopicCreator(final AmazonSNS snsClient) {
    this.snsClient = snsClient;
  }

  public String createTopic() {
    final CreateTopicResult result = snsClient.createTopic(TOPIC_NAME);
    final String topicArn = result.getTopicArn();

    // For an SMS notification, setting `DisplayName` is *required*. Note that
    // only the *first 10 characters* of the DisplayName will be shown on the
    // SMS message sent to the user, so choose your DisplayName wisely!
    snsClient.setTopicAttributes(topicArn, ATTRIBUTE_NAME, ATTRIBUTE_VALUE);

    return topicArn;
  }

  private final AmazonSNS snsClient;

  private static final String TOPIC_NAME = "SWF_Sample_Topic";
  private static final String ATTRIBUTE_NAME = "DisplayName";
  private static final String ATTRIBUTE_VALUE = "SWFSample";
}
