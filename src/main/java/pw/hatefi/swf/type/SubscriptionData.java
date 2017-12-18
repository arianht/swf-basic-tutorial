package pw.hatefi.swf.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_SubscriptionData.Builder.class)
public abstract class SubscriptionData {
  public abstract String getTopicArn();
  public abstract UserContact getUserContact();

  public static Builder builder() {
    return new AutoValue_SubscriptionData.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("topicArn")
    public abstract Builder setTopicArn(String topicArn);
    @JsonProperty("userContact")
    public abstract Builder setUserContact(UserContact userContact);

    public abstract SubscriptionData build();
  }
}
