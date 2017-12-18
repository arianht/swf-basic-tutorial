package pw.hatefi.swf.supplier;

public class GetContactActivityMessageSupplier {
  public String get() {
    final String msg = "\n" +
        "Please enter either an email address or SMS message (mobile phone) number to\n" +
        "receive SNS notifications. You can also enter both to use both address types.\n" +
        "\n" +
        "If you enter a phone number, it must be able to receive SMS messages, and must\n" +
        "be 11 digits (such as 12065550101 to represent the number 1-206-555-0101).\n" +
        "\n";

    return msg;
  }
}
