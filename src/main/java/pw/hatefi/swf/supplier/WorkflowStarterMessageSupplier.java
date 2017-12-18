package pw.hatefi.swf.supplier;

import javax.inject.Inject;

public class WorkflowStarterMessageSupplier {
  @Inject
  public WorkflowStarterMessageSupplier(
      final WorkflowIdSupplier workflowIdSupplier,
      final ActivityTaskListNameSupplier activityTaskListNameSupplier) {
    this.workflowId = workflowIdSupplier.get();
    this.activityTaskListNameSupplier = activityTaskListNameSupplier;
  }

  public String get() {
    final String activityTaskListName = activityTaskListNameSupplier.get(workflowId);
    final String msg = "\n" +
        "Amazon SWF Example\n" +
        "------------------\n" +
        "\n" +
        "Start the activity worker, preferably in a separate command-line window, with\n" +
        "the following command line argument:\n" +
        "\n" +
        "... " + activityTaskListName + "\n" +
        "\n" +
        "Press return when you are ready...\n" +
        "\n";

    return msg;
  }

  private final String workflowId;
  private final ActivityTaskListNameSupplier activityTaskListNameSupplier;
}
