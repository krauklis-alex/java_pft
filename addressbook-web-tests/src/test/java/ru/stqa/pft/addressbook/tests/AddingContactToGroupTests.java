package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.security.acl.Group;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddingContactToGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      ContactData contact = new ContactData().
              withFirstname("first-name-test").withLastname("last-name-test").withHomePhone("2222222").
              withEmail("test@test.te").withAddress("NewYork Central park").withHomepage("homepage.com");
      app.contact().create(contact, true);
      app.goTo().returnToHomePage();
    }

    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }


  @Test
  public void testAddingContactToGroup() {

    Contacts contacts = app.db().contacts();
    Iterator<ContactData> iteratorContact = contacts.iterator();

    ContactData contact = iteratorContact.next();
    Groups avalGroups = app.db().groups();
    avalGroups.removeAll(contact.getGroups());

    while (iteratorContact.hasNext() && avalGroups.size() == 0) {
      avalGroups = app.db().groups();
      contact = iteratorContact.next();
      avalGroups.removeAll(contact.getGroups());
    }

    if (avalGroups.size() == 0) {
      contact = new ContactData().withFirstname("first name");
      app.contact().create(contact, true);
      app.goTo().returnToHomePage();
      avalGroups = app.db().groups();
      contacts = app.db().contacts();
      int id = contacts.stream().mapToInt(c -> c.getId()).max().getAsInt();
      contact.withId(id);
    }

    Groups before = contact.getGroups();
    GroupData group = avalGroups.iterator().next();
    app.contact().addToGroup(contact, group);

    contacts = app.db().contacts();
    int id = contact.getId();
    Iterator<ContactData> iteratorContactAfter = contacts.iterator();
    while(iteratorContactAfter.hasNext()) {
      contact = iteratorContactAfter.next();
      if(contact.getId() == id) {
        break;
      }
    }

    Groups after = contact.getGroups();
    assertThat(after, equalTo(before.withAdded(group)));
  }
}