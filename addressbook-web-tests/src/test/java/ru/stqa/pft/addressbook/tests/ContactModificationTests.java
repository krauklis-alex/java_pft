package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Set;

public class ContactModificationTests extends TestBase {

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
  public void testContactModification()  {

    Set<ContactData> before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
//    int id = modifiedContact.getId();
    ContactData contact = new ContactData().
            withId(modifiedContact.getId()).withFirstname("first-name-test").withLastname("1last-name-test").
            withHomePhone("12222222").withEmail("1test@test.te").withAddress("1NewYork Central park");
    app.contact().modify(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedContact);
    before.add(contact);
    Assert.assertEquals(before, after);
  }

}
