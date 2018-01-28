package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

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
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    type(By.name("homepage"), contactData.getHomepage());
    type(By.name("notes"), contactData.getNote());
    type(By.name("work"), contactData.getWorkPhone());
//    attach(By.name("photo"),contactData.getPhoto());

    if (creation) {
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group")))
                .selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
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

  public void initContactModificationById(int id) {
    click(By.xpath(String.format("//table[@id='maintable']/tbody//a[@href=\"edit.php?id=%s\"]", id)));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void selectContactsInGroups(int id) {
    wd.findElement(By.xpath("//select[@name='group']/option[@value='" + id + "']")).click();
  }



  public void create(ContactData contact, boolean creation) {
    initContactCreation();
    fillContactForm(contact, creation);
    submitContactCreation();
    contactCache = null;
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    contactCache = null;
    app.goTo().returnToHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    closeAlertByOK();
    contactCache = null;
    app.goTo().homePage();
  }


  public void addToGroup(ContactData contact, GroupData group) {
    selectContactById(contact.getId());
    app.group().selectGroupByIdOnContactsPage(group.getId());
    addContactToGroup();
    contact.inGroup(group);
//    refreshContact();
  }

//  private void refreshContact() {
//    Contacts contact
//  }

  private void addContactToGroup() {
    click(By.cssSelector("input[name='add']"));
  }

  public void deleteFromGroup(ContactData contact, GroupData group) {
    selectContactsInGroups(group.getId());
    selectContactById(contact.getId());
    deleteContactFromGroup();
  }

  private void deleteContactFromGroup() {
    click(By.cssSelector("input[name='remove']"));
  }


  public ContactData refreshAfterCreation() {
    Contacts contacts = app.db().contacts();
    int id = contacts.stream().mapToInt(c -> c.getId()).max().getAsInt();
    for (ContactData contact : contacts) {
      if (contact.getId() == id) {
        return contact.withId(id);
      }
    }
    return null;
  }

//
//    Contacts contacts = app.db().contacts();
//    int id = contact.getId();
//    for (contact:contacts)
//      if (contact.getId() == id) {
//        return contact;
//      }
//      return null;
//    }
//  }
/*
    Contacts contacts;
    contacts = app.db().contacts();
    int id = contact.getId();
    Iterator<ContactData> iteratorContactAfter = contacts.iterator();
    while(iteratorContactAfter.hasNext()) {
      contact = iteratorContactAfter.next();
      if(contact.getId() == id) {
        break;
      }
    }
    return contact;
  }
*/

  public ContactData findContactWithNotFullGroups() {
    Contacts contacts = app.db().contacts();
    Groups allGroups = app.db().groups();
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() < allGroups.size()) {
        return contact;
      }
    }
    return null;
  }

  public ContactData createNewContact() {
    ContactData contact = new ContactData().
            withFirstname("first-name-test").withLastname("last-name-test").withHomePhone("2222222");
    create(contact, true);
    app.goTo().returnToHomePage();
    contact = refreshAfterCreation();
    return contact;
  }

  public Groups findGroupsForContacts(ContactData contact){
    Groups appropriateGroupsForContact = new Groups();
    for(GroupData group : app.db().groups()){
      if(!contact.getGroups().contains(group)){
        appropriateGroupsForContact.add(group);
      }
    }
    return appropriateGroupsForContact;
  }


  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }

    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.cssSelector("tr[name='entry']"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      contactCache.add(new ContactData().
              withId(id).withFirstname(firstname).withLastname(lastname)
              .withAddress(address).withAllPhones(allPhones).withAllEmails(allEmails));
    }
    return contactCache;
  }


  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    wd.navigate().back();
    return new ContactData()
            .withId(contact.getId()).withFirstname(firstname).withLastname(lastname).withAddress(address)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
            .withEmail(email).withEmail2(email2).withEmail3(email3);
  }
}

