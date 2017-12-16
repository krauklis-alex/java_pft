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

    if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
    if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
    if (homePhone != null ? !homePhone.equals(that.homePhone) : that.homePhone != null) return false;
    if (email != null ? !email.equals(that.email) : that.email != null) return false;
    return address != null ? address.equals(that.address) : that.address == null;
  }

  @Override
  public int hashCode() {
    int result = firstname != null ? firstname.hashCode() : 0;
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    result = 31 * result + (homePhone != null ? homePhone.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    return result;
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
