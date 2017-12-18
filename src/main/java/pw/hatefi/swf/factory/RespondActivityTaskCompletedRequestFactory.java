package pw.hatefi.swf.factory;

import com.amazonaws.services.simpleworkflow.model.RespondActivityTaskCompletedRequest;

public class RespondActivityTaskCompletedRequestFactory {
  public RespondActivityTaskCompletedRequest create(final String taskToken, final String result) {
    return new RespondActivityTaskCompletedRequest().withTaskToken(taskToken).withResult(result);
  }
}
