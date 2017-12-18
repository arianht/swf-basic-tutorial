package pw.hatefi.swf.supplier;

import javax.inject.Inject;
import javax.inject.Named;

public class WorkflowIdSupplier {
  @Inject
  public WorkflowIdSupplier(@Named("workflowId") final String workflowId) {
    this.workflowId = workflowId;
  }

  public String get() {
    return workflowId;
  }

  private final String workflowId;
}
