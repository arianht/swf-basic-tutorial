package pw.hatefi.swf.registerer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.hatefi.swf.supplier.ActivityTypesSupplier;

import javax.inject.Inject;

public class ActivitiesRegisterer {
  @Inject
  public ActivitiesRegisterer(final ActivityTypesSupplier activityTypesSupplier,
                              final ActivityRegisterer activityRegisterer) {
    this.activityTypesSupplier = activityTypesSupplier;
    this.activityRegisterer = activityRegisterer;
  }

  public void registerActivities() {
    LOGGER.info("Registering all activities...");

    activityTypesSupplier.get()
                         .stream()
                         .forEach(activityRegisterer::registerActivity);
  }

  private final ActivityTypesSupplier activityTypesSupplier;
  private final ActivityRegisterer activityRegisterer;

  private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiesRegisterer.class);
}
