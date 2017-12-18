package pw.hatefi.swf.supplier;

import com.amazonaws.services.simpleworkflow.model.ActivityType;
import pw.hatefi.swf.activity.AbstractActivity;
import pw.hatefi.swf.activity.GetContactActivity;
import pw.hatefi.swf.activity.SendResultActivity;
import pw.hatefi.swf.activity.SubscribeTopicActivity;
import pw.hatefi.swf.activity.WaitForConfirmationActivity;

import javax.inject.Inject;

import static pw.hatefi.swf.supplier.ActivityTypesSupplier.GET_CONTACT_ACTIVITY;
import static pw.hatefi.swf.supplier.ActivityTypesSupplier.SEND_RESULT_ACTIVITY;
import static pw.hatefi.swf.supplier.ActivityTypesSupplier.SUBSCRIBE_TOPIC_ACTIVITY;
import static pw.hatefi.swf.supplier.ActivityTypesSupplier.WAIT_FOR_CONFIRMATION_ACTIVITY;

public class ActivitySupplier {
  @Inject
  public ActivitySupplier(
      final GetContactActivity getContactActivity,
      final SubscribeTopicActivity subscribeTopicActivity,
      final WaitForConfirmationActivity waitForConfirmationActivity,
      final SendResultActivity sendResultActivity) {
    this.getContactActivity = getContactActivity;
    this.subscribeTopicActivity = subscribeTopicActivity;
    this.waitForConfirmationActivity = waitForConfirmationActivity;
    this.sendResultActivity = sendResultActivity;
  }

  public AbstractActivity get(final ActivityType activityType) {
    switch (activityType.getName()) {
      case GET_CONTACT_ACTIVITY:
        return getContactActivity;
      case SUBSCRIBE_TOPIC_ACTIVITY:
        return subscribeTopicActivity;
      case WAIT_FOR_CONFIRMATION_ACTIVITY:
        return waitForConfirmationActivity;
      case SEND_RESULT_ACTIVITY:
        return sendResultActivity;
    }
    throw new IllegalStateException(String.format("Received unexpected activity type %s",
        activityType.getName()));
  }

  private final GetContactActivity getContactActivity;
  private final SubscribeTopicActivity subscribeTopicActivity;
  private final WaitForConfirmationActivity waitForConfirmationActivity;
  private final SendResultActivity sendResultActivity;
}
