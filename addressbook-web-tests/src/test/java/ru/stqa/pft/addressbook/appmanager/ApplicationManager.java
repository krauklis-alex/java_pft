package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {

  FirefoxDriver wd;

  private ContactHelper contactHelper;
  private SessionHelper sessionHelper;
  private NavigationHelper navigationHelper;
  private GroupHelper groupHelper;

  public void init() {
    navigationHelper.wd = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
    navigationHelper.wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    navigationHelper.wd.get("http://localhost/addressbook/");
    groupHelper = new GroupHelper(navigationHelper.wd);
    navigationHelper = new NavigationHelper(navigationHelper.wd);
    sessionHelper = new SessionHelper(navigationHelper.wd);
    contactHelper = new ContactHelper(navigationHelper.wd);
    sessionHelper.login("admin", "secret");
  }

  public void stop() {
    navigationHelper.wd.quit();
  }

  public GroupHelper getGroupHelper() {
    return groupHelper;
  }

  public NavigationHelper getNavigationHelper() {
    return navigationHelper;
  }

  public ContactHelper getContactHelper() {
    return contactHelper;
  }

}
