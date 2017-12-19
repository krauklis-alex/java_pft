package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test (enabled = true)
  public void testContactCreation() {
    List<ContactData> before = app.contact().list();
    ContactData contact = new ContactData().
            withFirstname("first-name-test").withLastname("last-name-test").withHomePhone("2222222").
            withEmail("test@test.te").withAddress("NewYork Central park").withHomepage("homepage.com").withGroup("test1");
    app.contact().create(contact, true);
    app.goTo().returnToHomePage();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() + 1);

//    before.add(contact);
//    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
//    before.sort(byId);
//    after.sort(byId);
//    Assert.assertEquals(before, after);

    contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(before, after);
  }
}
