package pw.hatefi.swf.factory;

import com.amazonaws.services.simpleworkflow.model.WorkflowType;

public class WorkflowTypeFactory {
  public WorkflowType create(final String name, final String version) {
    return new WorkflowType().withName(name).withVersion(version);
  }
}
