package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeletionContactFromGroupTests extends TestBase {

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
  public void testDelitionContactToGroup() {

//    chooseContact();
    Contacts contacts = app.db().contacts();
    Iterator<ContactData> iteratorContact = contacts.iterator();
    ContactData contact = iteratorContact.next();
    Groups groups = app.db().groups();

    while (iteratorContact.hasNext() && contact.getGroups().size() == 0) {
      contact = iteratorContact.next();
    }

    if (contact.getGroups().size() == 0) {
      contact = new ContactData().withFirstname("first name").inGroup(groups.iterator().next());
      app.contact().create(contact, true);
      app.goTo().returnToHomePage();
      contacts = app.db().contacts();
      int id = contacts.stream().mapToInt(c -> c.getId()).max().getAsInt();
      contact.withId(id);
    }

//  оставить как есть
    Groups before = contact.getGroups();

//    chooseGroup()
    GroupData group = contact.getGroups().iterator().next();

    app.contact().deleteFromGroup(contact, group);

//    refresh
    contact = app.contact().refresh(contact);

//      оставить как есть
    Groups after = contact.getGroups();
    assertThat(after, equalTo(before.without(group)));
  }

}