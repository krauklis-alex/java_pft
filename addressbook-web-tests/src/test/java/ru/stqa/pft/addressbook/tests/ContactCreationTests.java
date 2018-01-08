package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException {
   File photo = new File("src/test/resources/kim.jpg");
   try (BufferedReader reader = new BufferedReader
           (new FileReader(new File("src/test/resources/contacts.xml")))) {
     String xml = "";
     String line = reader.readLine();
     while (line != null) {
       xml += line;
       line = reader.readLine();
     }
     XStream xstream = new XStream();
     xstream.processAnnotations(ContactData.class);
     List<ContactData> groups = (List<ContactData>) xstream.fromXML(xml);
     return groups.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
   }
  }


  @Test (dataProvider = "validContactsFromXml")
  public void testContactCreation(ContactData contact) {
    Contacts before = app.db().contacts();
    app.contact().create(contact, true);
    app.goTo().returnToHomePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }


  @Test (enabled = false)
  public void testBadContactCreation() {
    Contacts before = app.db().contacts();
    ContactData contact = new ContactData().
            withFirstname("f'").withLastname("last-name-test").withHomePhone("2222222").
            withEmail("test@test.te").withAddress("NewYork Central park").withHomepage("homepage.com").withGroup("test1");
    app.contact().create(contact, true);
    app.goTo().returnToHomePage();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before));
  }

}
