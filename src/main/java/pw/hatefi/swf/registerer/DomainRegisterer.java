package pw.hatefi.swf.registerer;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.model.DomainAlreadyExistsException;
import com.amazonaws.services.simpleworkflow.model.RegisterDomainRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.hatefi.swf.factory.RegisterDomainRequestFactory;
import pw.hatefi.swf.supplier.DomainNameSupplier;

import javax.inject.Inject;

public class DomainRegisterer {
  @Inject
  public DomainRegisterer(final AmazonSimpleWorkflow swfClient,
                          final DomainNameSupplier domainNameSupplier,
                          final RegisterDomainRequestFactory registerDomainRequestFactory) {
    this.swfClient = swfClient;
    this.domainName = domainNameSupplier.get();
    this.registerDomainRequestFactory = registerDomainRequestFactory;
  }

  public String registerDomain() {
    LOGGER.info("Registering domain: {}", domainName);
    final RegisterDomainRequest request = registerDomainRequestFactory.create();

    try {
      swfClient.registerDomain(request);
    } catch (final DomainAlreadyExistsException e) {
      LOGGER.info("Tried to register domain {}, but it already exists!", domainName);
    }

    return domainName;
  }

  private final AmazonSimpleWorkflow swfClient;
  private final String domainName;
  private final RegisterDomainRequestFactory registerDomainRequestFactory;

  private static final Logger LOGGER = LoggerFactory.getLogger(DomainRegisterer.class);
}
