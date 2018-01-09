package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePrecondions() {
    if (app.db().contacts().size() == 0) {
      ContactData contact = new ContactData().
              withFirstname("first-name-test").withLastname("last-name-test").withHomePhone("2222222").
              withEmail("test@test.te").withAddress("NewYork Central park").withHomepage("homepage.com").withGroup("test1");
      app.contact().create(contact, true);
      app.goTo().returnToHomePage();
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().
            withId(modifiedContact.getId()).withFirstname("first-name-test").withLastname("1last-name-test")
            .withAddress("1NewYork Central park").withHomepage("")
            .withHomePhone("12222222").withMobilePhone("").withWorkPhone("")
            .withEmail("1test@test.te").withEmail2("").withEmail3("").withNote("");
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI();
  }



}
