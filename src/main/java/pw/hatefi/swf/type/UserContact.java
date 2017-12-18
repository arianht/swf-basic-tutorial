package pw.hatefi.swf.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_UserContact.Builder.class)
public abstract class UserContact {
  public abstract String getEmail();
  public abstract String getPhone();

  public static Builder builder() {
    return new AutoValue_UserContact.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("email")
    public abstract Builder setEmail(String email);
    @JsonProperty("phone")
    public abstract Builder setPhone(String phone);

    public abstract UserContact build();
  }
}
