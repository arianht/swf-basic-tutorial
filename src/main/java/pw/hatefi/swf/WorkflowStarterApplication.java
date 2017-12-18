package pw.hatefi.swf;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.hatefi.swf.registerer.DomainRegisterer;
import pw.hatefi.swf.supplier.WorkflowStarterMessageSupplier;
import pw.hatefi.swf.workflow.WorkflowStarter;

import java.io.IOException;

public class WorkflowStarterApplication {
  public static void main(String[] args) throws IOException, InterruptedException {
    final WorkflowStarterMessageSupplier workflowStarterMessageSupplier =
        INJECTOR.getInstance(WorkflowStarterMessageSupplier.class);

    final String prompt = workflowStarterMessageSupplier.get();
    System.out.println(prompt);

    System.in.read();

    INJECTOR.getInstance(DomainRegisterer.class).registerDomain();

    LOGGER.info("Starting workflow execution...");
    INJECTOR.getInstance(WorkflowStarter.class).startWorkflow();
  }

  private static final Injector INJECTOR = Guice.createInjector(new MainModule());

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowStarterApplication.class);
}
