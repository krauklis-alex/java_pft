package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    File photo = new File("src/test/resources/kim.jpg");
    List list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader (new FileReader(new File("src/test/resources/contacts.csv")));
    String line = reader.readLine();
    while (line != null) {
      String[] split = line.split(";");
      list.add(new Object[] {new ContactData().withFirstname(split[0]).withLastname(split[1]).withAddress(split[2])
              .withHomePhone(split[3]).withEmail(split[4]).withHomepage(split[5]).withGroup(split[6])});
//              .withPhoto(split[7])});
      line = reader.readLine();
    }

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
