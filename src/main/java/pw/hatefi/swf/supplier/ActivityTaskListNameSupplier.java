package pw.hatefi.swf.supplier;

import javax.inject.Inject;

public class ActivityTaskListNameSupplier {
  @Inject
  public ActivityTaskListNameSupplier(final WorkflowIdSupplier workflowIdSupplier) {
    this.generatedWorkflowId = workflowIdSupplier.get();
  }

  public String get() {
    return format(generatedWorkflowId);
  }

  public String get(final String workflowId) {
    return format(workflowId);
  }

  private String format(final String workflowId) {
    return String.format(TEMPLATE, workflowId);
  }

  private final String generatedWorkflowId;

  private static final String TEMPLATE = "%s-activities";
}
