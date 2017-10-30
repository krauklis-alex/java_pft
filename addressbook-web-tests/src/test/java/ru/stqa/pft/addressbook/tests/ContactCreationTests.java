package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {

        app.initContactCreation();
        app.fillContactForm(new ContactData("first-name-test", "last-name-test", "2222222", "test@test.te", "NewYork Central park", "1234567890", "homepage.com", "note text"));
        app.submitContactCreation();
        app.returnToHomePage();
    }

}
