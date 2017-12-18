package pw.hatefi.swf.supplier;

import javax.inject.Inject;
import javax.inject.Named;

public class DomainNameSupplier {
  @Inject
  public DomainNameSupplier(@Named("domainName") final String domainName) {
    this.domainName = domainName;
  }

  public String get() {
    return domainName;
  }

  private final String domainName;
}
