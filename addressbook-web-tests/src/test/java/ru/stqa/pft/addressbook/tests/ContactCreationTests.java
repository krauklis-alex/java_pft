package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test (enabled = true)
  public void testContactCreation() {
    Contacts before = app.contact().all();
    ContactData contact = new ContactData().
            withFirstname("first-name-test").withLastname("last-name-test").withHomePhone("2222222").
            withEmail("test@test.te").withAddress("NewYork Central park").withHomepage("homepage.com").withGroup("test1");
    app.contact().create(contact, true);
    app.goTo().returnToHomePage();
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }
}
