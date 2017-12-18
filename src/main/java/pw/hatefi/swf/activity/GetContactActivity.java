package pw.hatefi.swf.activity;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.model.ActivityTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import pw.hatefi.swf.exception.NoUserContactGivenException;
import pw.hatefi.swf.factory.RespondActivityTaskCompletedRequestFactory;
import pw.hatefi.swf.factory.RespondActivityTaskFailedRequestFactory;
import pw.hatefi.swf.input.ContactInfoInputReader;
import pw.hatefi.swf.type.UserContact;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Optional;

public class GetContactActivity extends AbstractActivity {
  @Inject
  public GetContactActivity(
      final AmazonSimpleWorkflow swfClient,
      final RespondActivityTaskCompletedRequestFactory taskCompletedRequestFactory,
      final RespondActivityTaskFailedRequestFactory taskFailedRequestFactory,
      final ContactInfoInputReader contactInfoInputReader,
      final ObjectMapper objectMapper) {
    super(swfClient, taskCompletedRequestFactory, taskFailedRequestFactory);

    this.contactInfoInputReader = contactInfoInputReader;
    this.objectMapper = objectMapper;
  }

  @Override
  protected String internalPerform(final ActivityTask activityTask) throws IOException {
    final Optional<UserContact> userContact = contactInfoInputReader.getUserContact();
    if (!userContact.isPresent()) {
      throw new NoUserContactGivenException();
    }
    return objectMapper.writeValueAsString(userContact.get());
  }

  private final ContactInfoInputReader contactInfoInputReader;
  private final ObjectMapper objectMapper;
}
