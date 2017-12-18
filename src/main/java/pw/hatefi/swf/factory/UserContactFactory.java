package pw.hatefi.swf.factory;

import pw.hatefi.swf.type.UserContact;

public class UserContactFactory {
  public UserContact create(final String email, final String phone) {
    return UserContact.builder()
                      .setEmail(email)
                      .setPhone(phone)
                      .build();
  }
}
