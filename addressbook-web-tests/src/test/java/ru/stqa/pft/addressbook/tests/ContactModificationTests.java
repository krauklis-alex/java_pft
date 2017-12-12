package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification()  {
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("first-name-test", "last-name-test",
              "2222222", "test@test.te", "NewYork Central park", "1234567890",
              "homepage.com", "note text", "test1"), true);
      app.getNavigationHelper().returnToHomePage();
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(before.size() - 1);
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("1first-name-test", "1last-name-test",
            "12222222", "1test@test.te", "NewYork Central park",
            "1234567890", "homepage.com", null, null), false);
    app.getContactHelper().submitContactModification();
        app.getNavigationHelper().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());
  }
}
