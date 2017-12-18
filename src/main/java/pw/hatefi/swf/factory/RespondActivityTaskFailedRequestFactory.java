package pw.hatefi.swf.factory;

import com.amazonaws.services.simpleworkflow.model.RespondActivityTaskFailedRequest;

public class RespondActivityTaskFailedRequestFactory {
  public RespondActivityTaskFailedRequest create(final String taskToken, final String reason) {
    return new RespondActivityTaskFailedRequest().withTaskToken(taskToken).withReason(reason);
  }
}
