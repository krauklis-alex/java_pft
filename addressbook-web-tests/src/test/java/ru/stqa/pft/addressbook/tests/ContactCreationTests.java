package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() {
    File photo = new File("src/test/resources/kim.jpg");
    List list = new ArrayList<Object[]>();
    list.add(new Object[]{new ContactData()
            .withFirstname("first-name-test 1").withLastname("last-name-test 1").withHomePhone("111")
            .withEmail("111test@test.te").withAddress("111NewYork Central park").withHomepage("111homepage.com")
            .withGroup("test1").withPhoto(photo)});
    list.add(new Object[]{new ContactData()
            .withFirstname("first-name-test 2").withLastname("last-name-test 2").withHomePhone("222")
            .withEmail("222test@test.te").withAddress("222NewYork Central park").withHomepage("222homepage.com")
            .withGroup("test1").withPhoto(photo)});
    list.add(new Object[]{new ContactData()
            .withFirstname("first-name-test 3").withLastname("last-name-test 3").withHomePhone("333")
            .withEmail("333test@test.te").withAddress("333NewYork Central park").withHomepage("333homepage.com")
            .withGroup("test1").withPhoto(photo)});
    return list.iterator();
  }


  @Test (dataProvider = "validContacts")
  public void testContactCreation(ContactData contact) {
    Contacts before = app.contact().all();
    app.contact().create(contact, true);
    app.goTo().returnToHomePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }


  @Test (enabled = false)
  public void testBadContactCreation() {
    Contacts before = app.contact().all();
    ContactData contact = new ContactData().
            withFirstname("f'").withLastname("last-name-test").withHomePhone("2222222").
            withEmail("test@test.te").withAddress("NewYork Central park").withHomepage("homepage.com").withGroup("test1");
    app.contact().create(contact, true);
    app.goTo().returnToHomePage();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }

}
