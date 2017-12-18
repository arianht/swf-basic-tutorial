package pw.hatefi.swf.factory;

import com.amazonaws.services.simpleworkflow.model.PollForActivityTaskRequest;
import com.amazonaws.services.simpleworkflow.model.TaskList;
import pw.hatefi.swf.supplier.DomainNameSupplier;

import javax.inject.Inject;

public class PollForActivityTaskRequestFactory {
  @Inject
  public PollForActivityTaskRequestFactory(final DomainNameSupplier domainNameSupplier,
                                           final TaskListFactory taskListFactory) {
    this.domainName = domainNameSupplier.get();
    this.taskListFactory = taskListFactory;
  }

  public PollForActivityTaskRequest create(final String taskListName) {
    final TaskList taskList = taskListFactory.create(taskListName);
    return new PollForActivityTaskRequest().withDomain(domainName)
                                           .withTaskList(taskList);
  }

  private final String domainName;
  private final TaskListFactory taskListFactory;
}
