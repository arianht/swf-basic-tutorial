package pw.hatefi.swf.factory;

import com.amazonaws.services.simpleworkflow.model.RecordActivityTaskHeartbeatRequest;

public class RecordActivityTaskHeartbeatRequestFactory {
  public RecordActivityTaskHeartbeatRequest create(final String taskToken) {
    return new RecordActivityTaskHeartbeatRequest()
        .withDetails("Still need more subscriptions to confirm.")
        .withTaskToken(taskToken);
  }
}
