package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;


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

   public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
  }

  public void initContactModification() {
    click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public void createContact(ContactData contact, boolean creation) {
   initContactCreation();
   fillContactForm(contact, creation);
   submitContactCreation();
  }

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.cssSelector("tr[name='entry']"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      String lastname = cells.get(1).getText();//1
      String firstname = cells.get(2).getText(); //2
      String address = cells.get(3).getText();//3
      String email = cells.get(4).getText();//4
      String homepage = element.findElement(By.xpath("//td[10]/a")).getText();
      String homephone = cells.get(5).getText();//5
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));

      ContactData contact = new ContactData (id, firstname, lastname, homephone, email,
              address, null, homepage, null, null);
      contacts.add(contact);
    }
    return contacts;
  }

  //      int id = Integer.parseInt(cells.get(1).getAttribute("value"));//      String firstname = element.findElement(By.xpath("//td[3]")).getText();
//      String lastname = element.findElement(By.xpath("//td[2]")).getText();
//      String address = element.findElement(By.xpath("//td[4]")).getText();
//      String email = element.findElement(By.xpath("//td[5]")).getText();
//      String homepage = element.findElement(By.xpath("//td[10]/a")).getText();
//      String homephone = element.findElement(By.xpath("//td[6]")).getText();
//      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }
}

