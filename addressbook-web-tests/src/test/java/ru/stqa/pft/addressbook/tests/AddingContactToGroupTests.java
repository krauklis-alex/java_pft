package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

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
  }

  @Test
  public void testAddingContactToGroup() {
    Contacts before = app.db().contacts();
    Groups groups = app.db().groups();
    ContactData addingContact = before.iterator().next();
    GroupData toGroup = groups.iterator().next();
    app.contact().addToGroup(addingContact, toGroup);
    Contacts after = app.db().contacts();
//    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }

}
