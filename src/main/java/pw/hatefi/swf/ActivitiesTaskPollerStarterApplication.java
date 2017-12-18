package pw.hatefi.swf;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.hatefi.swf.activity.ActivitiesTaskPoller;
import pw.hatefi.swf.registerer.ActivitiesRegisterer;
import pw.hatefi.swf.registerer.DomainRegisterer;

import java.io.IOException;

public class ActivitiesTaskPollerStarterApplication {
  public static void main(String[] args) throws IOException, InterruptedException {
    if (args.length < 1) {
      System.out.println("You must supply a task-list name to use!");
      return;
    }

    INJECTOR.getInstance(DomainRegisterer.class).registerDomain();
    INJECTOR.getInstance(ActivitiesRegisterer.class).registerActivities();

    final String taskListName = args[0];
    INJECTOR.getInstance(ActivitiesTaskPoller.class).pollForActivities(taskListName);
    LOGGER.info("All done!");
  }

  private static final Injector INJECTOR = Guice.createInjector(new MainModule());

  private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiesTaskPollerStarterApplication.class);
}
