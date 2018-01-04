package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.stqa.pft.addressbook.tests.TestBase.app;


public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("email"), contactData.getEmail());
    type(By.name("homepage"), contactData.getHomepage());
    type(By.name("notes"), contactData.getNote());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  private void selectContactById(int id) {
      wd.findElement(By.cssSelector("input[id='" + id + "']")).click();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
  }

  public void initContactModification(int id) {
    click(By.xpath("//table[@id='maintable']/tbody//a[@href=\"edit.php?id=" + id + "\"]"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public void create(ContactData contact, boolean creation) {
   initContactCreation();
   fillContactForm(contact, creation);
   submitContactCreation();
  }

  public void modify(ContactData contact) {
    initContactModification(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    app.goTo().returnToHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    closeAlertByOK();
    app.goTo().homePage();
  }

  public Contacts all() {
    Contacts contacts = new Contacts();
    List<WebElement> elements = wd.findElements(By.cssSelector("tr[name='entry']"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String address = cells.get(3).getText();
      String email = cells.get(4).getText();
//      String homephone = cells.get(5).getText();
      String[] phone = cells.get(5).getText().split("\n");
      String homephone = phone[0];
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));

      contacts.add(new ContactData().
              withId(id).withFirstname(firstname).withLastname(lastname).withHomePhone(homephone).
              withEmail(email).withAddress(address));
    }
    return contacts;
  }



}

