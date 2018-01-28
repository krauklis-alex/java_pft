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
      app.contact().createNewContact();
    }

    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }


  @Test
  public void testAddingContactToGroup() {
    ContactData contact = app.contact().findContactWithNotFullGroups();
    if (contact == null) {
      contact = app.contact().createNewContact();
    }
    Groups groups = app.contact().findGroupsForContacts(contact);
    Groups before = contact.getGroups();
    GroupData group = groups.iterator().next();
    app.contact().addToGroup(contact, group);
    Groups after = contact.getGroups();
    assertThat(after, equalTo(before.withAdded(group)));
  }
}