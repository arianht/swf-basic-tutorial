package pw.hatefi.swf.factory;

import pw.hatefi.swf.type.SubscriptionData;
import pw.hatefi.swf.type.UserContact;

public class SubscriptionDataFactory {
  public SubscriptionData create(final String topicArn, final UserContact userContact) {
    return SubscriptionData.builder()
                           .setTopicArn(topicArn)
                           .setUserContact(userContact)
                           .build();
  }
}
