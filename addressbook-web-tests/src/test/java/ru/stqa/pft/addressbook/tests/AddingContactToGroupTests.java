package ru.stqa.pft.addressbook.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

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

      if (app.db().groups().size() == 0) {
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("test1"));
      }
    }
  }

  @Test
  public void testAddingContactToGroup() {

    Contacts contacts = app.db().contacts();

    Groups groups = app.db().groups();
    ContactData addingContact = contacts.iterator().next();
    Groups contactGroupsBefore = addingContact.getGroups();
    GroupData toGroup = groups.iterator().next();
    //find non-existent group for that contact!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    app.contact().addToGroup(addingContact, toGroup);
//    Contacts after = app.db().contacts();
    Groups contactGroupsAfter = addingContact.getGroups();
    assertThat(contactGroupsAfter, equalTo(contactGroupsBefore.withAdded(toGroup)));
  }
}
