package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePrecondions() {
    if (app.contact().all().size() == 0) {
      ContactData contact = new ContactData().
              withFirstname("first-name-test").withLastname("last-name-test").withHomePhone("2222222").
              withEmail("test@test.te").withAddress("NewYork Central park").withHomepage("homepage.com").withGroup("test1");
      app.contact().create(contact, true);
      app.goTo().returnToHomePage();
    }
  }

  @Test
  public void testContactDeletion() {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    assertThat(app.contact().count(), equalTo(before.size() - 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.without(deletedContact)));
  }
}
