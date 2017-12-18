package pw.hatefi.swf.factory;

import com.amazonaws.services.simpleworkflow.model.RegisterDomainRequest;
import pw.hatefi.swf.supplier.DomainNameSupplier;

import javax.inject.Inject;

public class RegisterDomainRequestFactory {
  @Inject
  public RegisterDomainRequestFactory(final DomainNameSupplier domainNameSupplier) {
    this.domainName = domainNameSupplier.get();
  }

  public RegisterDomainRequest create() {
    return new RegisterDomainRequest().withName(domainName)
                                      .withWorkflowExecutionRetentionPeriodInDays(
                                          WORKFLOW_EXECUTION_RETENTION_PERIOD_DAYS);
  }

  private final String domainName;

  private static final String WORKFLOW_EXECUTION_RETENTION_PERIOD_DAYS = "10";

}
