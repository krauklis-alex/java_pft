package ru.stqa.pft.addressbook.model;

public class ContactData {
  private final String firstname;
  private final String lastname;
  private final String homePhone;
  private final String email;
  private final String address;
  private final String mobilePhone;
  private final String homepage;
  private final String note;
  private int id;
  private String group;

  public ContactData(int id, String firstname, String lastname, String homePhone,
                     String email, String address, String mobilePhone,
                     String homepage, String note, String group) {
    this.id = id;

    this.firstname = firstname;
    this.lastname = lastname;
    this.homePhone = homePhone;
    this.email = email;
    this.address = address;
    this.mobilePhone = mobilePhone;
    this.homepage = homepage;
    this.note = note;
    this.group = group;
  }



  public void setId(int id) {
    this.id = id;
  }

  public ContactData(String firstname, String lastname, String homePhone,
                     String email, String address, String mobilePhone,
                     String homepage, String note, String group) {
    this.id = Integer.MAX_VALUE;
    this.firstname = firstname;
    this.lastname = lastname;
    this.homePhone = homePhone;
    this.email = email;
    this.address = address;
    this.mobilePhone = mobilePhone;
    this.homepage = homepage;
    this.note = note;
    this.group = group;
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

  public String getGroup() {
    return group;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    return id == that.id;
  }

  @Override
  public int hashCode() {
    return id;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", homePhone='" + homePhone + '\'' +
            ", email='" + email + '\'' +
            ", address='" + address + '\'' +
            ", mobilePhone='" + mobilePhone + '\'' +
            ", homepage='" + homepage + '\'' +
            ", note='" + note + '\'' +
            ", id=" + id +
            ", group='" + group + '\'' +
            '}';
  }
}
