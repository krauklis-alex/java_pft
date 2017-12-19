package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePrecondions() {
    if (app.contact().list().size() == 0) {
      ContactData contact = new ContactData().
              withFirstname("first-name-test").withLastname("last-name-test").withHomePhone("2222222").
              withEmail("test@test.te").withAddress("NewYork Central park").withHomepage("homepage.com").withGroup("test1");
      app.contact().create(contact, true);
        app.goTo().returnToHomePage();
    }
  }

  @Test
  public void testContactModification()  {

    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    int id = before.get(index).getId();
    ContactData contact = new ContactData().
            withId(id).withFirstname("first-name-test").withLastname("1last-name-test").withHomePhone("12222222").
            withEmail("1test@test.te").withAddress("1NewYork Central park");
    app.contact().modify(id, contact);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(contact);
    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

}
