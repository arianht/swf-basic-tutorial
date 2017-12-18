package pw.hatefi.swf.supplier;

import com.amazonaws.services.simpleworkflow.model.ActivityType;
import com.google.common.collect.ImmutableList;

import java.util.ArrayDeque;
import java.util.Deque;

public class ActivityTypesSupplier {
  /**
   *
   * @return the list of workflow activities in reverse order.
   */
  public Deque<ActivityType> get() {
    final ArrayDeque<ActivityType> result = new ArrayDeque<>(ACTIVITIES.size());

    ACTIVITIES.forEach(result::addFirst);

    return result;
  }

  public static final String GET_CONTACT_ACTIVITY = "get_contact_activity";
  public static final String SUBSCRIBE_TOPIC_ACTIVITY = "subscribe_topic_activity";
  public static final String WAIT_FOR_CONFIRMATION_ACTIVITY = "wait_for_confirmation_activity";
  public static final String SEND_RESULT_ACTIVITY = "send_result_activity";
  private static final ImmutableList<ActivityType> ACTIVITIES =
      ImmutableList.of(
          new ActivityType().withName(GET_CONTACT_ACTIVITY).withVersion("v1"),
          new ActivityType().withName(SUBSCRIBE_TOPIC_ACTIVITY).withVersion("v1"),
          new ActivityType().withName(WAIT_FOR_CONFIRMATION_ACTIVITY).withVersion("v1"),
          new ActivityType().withName(SEND_RESULT_ACTIVITY).withVersion("v1")
      );
}
