package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeletionContactFromGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.contact().createNewContact(new ContactData().withFirstname("first-name-test").withLastname("last-name-test")
              .withHomePhone("2222222"));
    }

    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }

    Contacts contactsWithGroups = app.contact().findAllContactsWithGroups();
    if (contactsWithGroups.size() == 0) {
      ContactData contact = app.contact().createNewContact(new ContactData()
              .withFirstname("first-name-test").withLastname("last-name-test").withHomePhone("2222222")
              .inGroup(app.db().groups().iterator().next()));
      contactsWithGroups.add(contact);
    }
  }


  @Test
  public void testDeletionContactToGroup() {

    ContactData contact = app.contact().findContactWithGroups();
    Groups before = contact.getGroups();
    GroupData group = before.iterator().next();
    app.contact().deleteFromGroup(contact, group);
    contact = app.contact().refreshContact(contact);
    Groups after = contact.getGroups();
    assertThat(after, equalTo(before.without(group)));
  }

}