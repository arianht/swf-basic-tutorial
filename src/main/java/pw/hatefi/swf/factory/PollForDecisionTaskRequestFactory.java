package pw.hatefi.swf.factory;

import com.amazonaws.services.simpleworkflow.model.PollForDecisionTaskRequest;
import com.amazonaws.services.simpleworkflow.model.TaskList;
import pw.hatefi.swf.supplier.DomainNameSupplier;

import javax.inject.Inject;

public class PollForDecisionTaskRequestFactory {
  @Inject
  public PollForDecisionTaskRequestFactory(final DomainNameSupplier domainNameSupplier,
                                           final TaskListFactory taskListFactory) {
    this.domainName = domainNameSupplier.get();
    this.taskListFactory = taskListFactory;
  }

  public PollForDecisionTaskRequest create() {
    final TaskList taskList = taskListFactory.create();
    return new PollForDecisionTaskRequest().withDomain(domainName)
                                           .withTaskList(taskList);
  }

  private final String domainName;
  private final TaskListFactory taskListFactory;
}
