package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test (enabled = true)
  public void testContactModification()  {
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("first-name-test", "last-name-test",
              "2222222", "test@test.te", "NewYork Central park", null,
              "homepage.com", null, "test1"), true);
      app.goTo().returnToHomePage();
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    //app.getContactHelper().selectContact(before.size() - 1);
    app.getContactHelper().initContactModification(before.get(before.size() - 1).getId());
    ContactData contact = new ContactData (before.get(before.size() - 1).getId(),"1first-name-test", "1last-name-test",
            "12222222", "1test@test.te", "1NewYork Central park", null,
            null, null, null);
    app.getContactHelper().fillContactForm(contact, false);
    app.getContactHelper().submitContactModification();
        app.goTo().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() - 1);
    before.add(contact);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
  }
}
