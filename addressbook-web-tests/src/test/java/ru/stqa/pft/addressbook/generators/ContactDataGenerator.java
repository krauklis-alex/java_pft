package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Group count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);

    if (format.equals("csv")) {
      saveAsCsv(contacts, new File(file));
      ;
    } else if (format.equals("xml")) {
      saveAsXml(contacts, new File(file));
    } else {
      System.out.println("Unrecognized format " + format);
    }
  }

  private void saveAsXml(List<ContactData> groups, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(groups);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }


  private static void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
    try (Writer writer = new FileWriter(file)) {
      for (ContactData contact : contacts) {
        writer.write(String.format("%s;%s;%s;%s;%s;%s;%s\n",
                contact.getFirstname(), contact.getLastname(), contact.getAddress(), contact.getHomePhone(),
                contact.getEmail(), contact.getHomepage(), contact.getGroup()));
      }
    }
  }

  private static List<ContactData> generateContacts(int count) {
    File photo = new File("src/test/resources/kim.jpg");
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withFirstname(String.format("firstname %s", i))
              .withLastname(String.format("lastname %s", i)).withAddress(String.format("address %s", i))
              .withHomePhone(String.format("123 %s", i)).withEmail(String.format("11@11.11 %s", i))
              .withHomepage(String.format("111homepage %s.com", i)).withGroup(String.format("test1")));
//              .withPhoto(String.format(photo));
    }
    return contacts;
  }

}
