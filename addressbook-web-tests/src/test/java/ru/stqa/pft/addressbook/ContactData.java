package ru.stqa.pft.addressbook;

public class ContactData {
  private final String firstname;
  private final String lastname;
  private final String homePhone;
  private final String email;
  private final String address;
  private final String mobilePhone;
  private final String homepage;
  private final String note;

  public ContactData(String firstname, String lastname, String homePhone, String email, String address, String mobilePhone, String homepage, String note) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.homePhone = homePhone;
    this.email = email;
    this.address = address;
    this.mobilePhone = mobilePhone;
    this.homepage = homepage;
    this.note = note;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getHomePhone() {
    return homePhone;
  }

  public String getEmail() {
    return email;
  }

  public String getAddress() {
    return address;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public String getHomepage() {
    return homepage;
  }

  public String getNote() {
    return note;
  }
}
