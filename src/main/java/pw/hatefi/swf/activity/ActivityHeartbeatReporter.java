package pw.hatefi.swf.activity;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.model.RecordActivityTaskHeartbeatRequest;
import pw.hatefi.swf.factory.RecordActivityTaskHeartbeatRequestFactory;

import javax.inject.Inject;

public class ActivityHeartbeatReporter {
  @Inject
  public ActivityHeartbeatReporter(final AmazonSimpleWorkflow swfClient,
                                   final RecordActivityTaskHeartbeatRequestFactory heartbeatRequestFactory) {
    this.swfClient = swfClient;
    this.heartbeatRequestFactory = heartbeatRequestFactory;
  }

  public void report(final String taskToken) {
    final RecordActivityTaskHeartbeatRequest request = heartbeatRequestFactory.create(taskToken);
    swfClient.recordActivityTaskHeartbeat(request);
  }

  private final AmazonSimpleWorkflow swfClient;
  private final RecordActivityTaskHeartbeatRequestFactory heartbeatRequestFactory;
}
