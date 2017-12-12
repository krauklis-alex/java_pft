package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {
  @Test
  public void testContactDeletion() {

    app.getNavigationHelper().gotoHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("first-name-test", "last-name-test",
              "2222222", "test@test.te", "NewYork Central park", "1234567890",
              "homepage.com", "note text", "test1"), true);
      app.getNavigationHelper().returnToHomePage();
    }
    //List<ContactData> before = app.getContactHelper().getContactList();
    int  before = app.getContactHelper().getContactCount();
    app.getContactHelper().selectContact(before- 1);
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().closeAlertByOK();
    app.getNavigationHelper().gotoHomePage();
    //List<ContactData> after = app.getContactHelper().getContactList();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before - 1);
  }
}
