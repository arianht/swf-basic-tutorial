package pw.hatefi.swf.factory;

import com.amazonaws.services.simpleworkflow.model.ListWorkflowTypesRequest;
import pw.hatefi.swf.supplier.DomainNameSupplier;

import javax.inject.Inject;

public class ListWorkflowTypesRequestFactory {
  @Inject
  public ListWorkflowTypesRequestFactory(final DomainNameSupplier domainNameSupplier) {
    this.domainName = domainNameSupplier.get();
  }

  public ListWorkflowTypesRequest create() {
    return new ListWorkflowTypesRequest().withDomain(domainName);
  }

  private final String domainName;
}
