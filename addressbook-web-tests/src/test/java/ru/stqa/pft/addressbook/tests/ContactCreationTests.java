package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {

        initContactCreation();
        fillContactForm(new ContactData("first-name-test", "last-name-test", "2222222", "test@test.te", "NewYork Central park", "1234567890", "homepage.com", "note text"));
        submitContactCreation();
        returnToHomePage();
    }

}
