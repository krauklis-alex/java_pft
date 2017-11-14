package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.getContactHelper().createContact(new ContactData("first-name-test", "last-name-test",
            "2222222", "test@test.te", "NewYork Central park", "1234567890",
            "homepage.com", "note text", "test1"), true);
    app.getNavigationHelper().returnToHomePage();
  }

}
