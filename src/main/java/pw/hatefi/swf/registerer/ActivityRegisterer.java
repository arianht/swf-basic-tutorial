package pw.hatefi.swf.registerer;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.model.ActivityType;
import com.amazonaws.services.simpleworkflow.model.RegisterActivityTypeRequest;
import com.amazonaws.services.simpleworkflow.model.TypeAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.hatefi.swf.factory.RegisterActivityTypeRequestFactory;

import javax.inject.Inject;

public class ActivityRegisterer {
  @Inject
  public ActivityRegisterer(final RegisterActivityTypeRequestFactory registerActivityTypeRequestFactory,
                            final AmazonSimpleWorkflow swfClient) {
    this.registerActivityTypeRequestFactory = registerActivityTypeRequestFactory;
    this.swfClient = swfClient;
  }

  public void registerActivity(final ActivityType activityType) {
    LOGGER.info("Registering activity: {}, {}", activityType.getName(),
        activityType.getVersion());
    final RegisterActivityTypeRequest request = registerActivityTypeRequestFactory.create(activityType);


    try {
      swfClient.registerActivityType(request);
    } catch (final TypeAlreadyExistsException e) {
      LOGGER.info("Tried to register activity {}, but it already exists!", activityType.getName());
    }
  }

  private final RegisterActivityTypeRequestFactory registerActivityTypeRequestFactory;
  private final AmazonSimpleWorkflow swfClient;

  private static final Logger LOGGER = LoggerFactory.getLogger(ActivityRegisterer.class);
}
