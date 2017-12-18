package pw.hatefi.swf.input;

import pw.hatefi.swf.factory.UserContactFactory;
import pw.hatefi.swf.supplier.GetContactActivityMessageSupplier;
import pw.hatefi.swf.type.UserContact;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Scanner;

import static com.google.common.base.Strings.isNullOrEmpty;

public class ContactInfoInputReader {
  @Inject
  public ContactInfoInputReader(final GetContactActivityMessageSupplier getContactActivityMessageSupplier,
                                final UserContactFactory userContactFactory) {
    this.getContactActivityMessageSupplier = getContactActivityMessageSupplier;
    this.userContactFactory = userContactFactory;
  }

  public Optional<UserContact> getUserContact() {
    final String intro = getContactActivityMessageSupplier.get();
    System.out.println(intro);

    boolean inputConfirmed = false;
    String email = "";
    String phone = "";
    while (!inputConfirmed) {
      System.out.println();
      System.out.println("Email: ");
      email = INPUT.nextLine();

      System.out.println("Phone: ");
      phone = INPUT.nextLine();

      System.out.println();
      if (isNullOrEmpty(email) && isNullOrEmpty(phone)) {
        System.out.println("You provided no subscription information. Quit? (y/n): ");
        if (didUserConfirm()) {
          return Optional.empty();
        }
      } else {
        System.out.println("You entered:");
        System.out.println("  email: " + email);
        System.out.println("  phone: " + phone);
        System.out.println();
        System.out.println("Is this correct? (y/n): ");
        if (didUserConfirm()) {
          inputConfirmed = true;
        }
      }
    }


    return Optional.of(userContactFactory.create(email, phone));
  }

  private static boolean didUserConfirm() {
    return "y".equals(INPUT.nextLine());
  }

  private final GetContactActivityMessageSupplier getContactActivityMessageSupplier;
  private final UserContactFactory userContactFactory;

  private static final Scanner INPUT = new Scanner(System.in);
}
