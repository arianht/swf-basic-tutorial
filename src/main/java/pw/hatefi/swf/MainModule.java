package pw.hatefi.swf;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

import java.util.UUID;

public class MainModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(String.class)
        .annotatedWith(Names.named("domainName"))
        .toInstance(DOMAIN_NAME);
    bind(String.class)
        .annotatedWith(Names.named("workflowId"))
        .toInstance(WORKFLOW_ID);
    bind(ObjectMapper.class).toInstance(new ObjectMapper());
  }

  @Provides
  AmazonSimpleWorkflow provideAmazonSimpleWorkflow() {
    return AmazonSimpleWorkflowClientBuilder.standard().build();
  }

  @Provides
  AmazonSNS provideAmazonSns() {
    return AmazonSNSClientBuilder.standard().build();
  }

  private static final String DOMAIN_NAME = "SWFTutorialDomain";
  private static final String WORKFLOW_ID = UUID.randomUUID().toString();
}
