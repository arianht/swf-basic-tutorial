package pw.hatefi.swf.supplier;

import javax.inject.Inject;

public class WorkflowTaskListNameSupplier {
  @Inject
  public WorkflowTaskListNameSupplier(final WorkflowIdSupplier workflowIdSupplier) {
    this.workflowId = workflowIdSupplier.get();
  }

  public String get() {
    return workflowId;
  }

  private final String workflowId;
}
