package com.seo.selenium.ui.flow.customer;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.selenium.ui.SeleniumConstants;
import com.seo.selenium.ui.UserActions;

public class TestAssignProjects
{
  private static WebDriver mDriver;
  private static UserActions mTest;
  private static WebDriverWait mWait;

  // maybe put this in constants
  private static List<String> mProjectsNames;

  @BeforeClass
  public static void Login()
  {
    mDriver = com.seo.selenium.ui.SeleniumUtils.BuildDriver();
    mTest = new UserActions();
    mWait = new WebDriverWait(mDriver, 30);
    mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmail2, SeleniumConstants.kUserPassword2);
    mProjectsNames = new ArrayList<String>();
    mProjectsNames.add(SeleniumConstants.kUserAWRAlias);
    mProjectsNames.add(SeleniumConstants.kTextAtomicTangerine);
    mProjectsNames.add(SeleniumConstants.kTextBlizzard);
  }

  @Test
  public void TestGettingStarted() throws Exception
  {
    mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlUsers);
    Thread.sleep(5000);

    boolean found = false;
    List<WebElement> elements = mDriver.findElements(By.xpath("//td/a"));
    for (WebElement el : elements)
    {
      if (found)
      {
        el.click();
        break;
      }

      if (el.getText().contains(SeleniumConstants.kUserEmailCustomer))
      {
        found = true;
      }
    }
    Thread.sleep(5000);

    mDriver.findElement(By.xpath("//table[contains(@class, 'table-striped')]/thead[2]/tr/th/div/label/input")).click();
    Thread.sleep(2000);
    mDriver.findElement(By.xpath("//table[contains(@class, 'table-striped')]/thead[2]/tr/th/div/label/input")).click();

    mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextUpdate)));
    mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextUpdate)).click();
    Thread.sleep(5000);

    found = false;
    elements.clear();
    elements = mDriver.findElements(By.xpath("//td/a"));
    for (WebElement el : elements)
    {
      if (found)
      {
        el.click();
        break;
      }

      if (el.getText().contains(SeleniumConstants.kUserEmailCustomer))
      {
        found = true;
      }
    }
    Thread.sleep(5000);

    int size = mDriver.findElements(By.xpath("//table/tbody/tr")).size();
    for (int i = 1; i <= size; i++)
    {
      if (mProjectsNames.contains(mDriver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[1]")).getText()))
      {
        mDriver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td/div/label/input")).click();
      }
    }

    mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextUpdate)));
    mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextUpdate)).click();
    Thread.sleep(5000);

    mDriver.findElement(By.xpath("//li[contains(@class, 'pull-right')]/a")).click();
    Thread.sleep(5000);
    mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextSignOut)).click();
    Thread.sleep(5000);

    mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlLogin);
    mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmailCustomer, SeleniumConstants.kUserPasswordCustomer);
    Thread.sleep(5000);

    mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlProjectList);
    Thread.sleep(5000);

    List<String> projects = mTest.GetWebsitesProjectListCustomer(mDriver);
    Assert.assertEquals(mProjectsNames.size(), projects.size());

    for (int i = 0; i < projects.size(); i++)
    {
      if (!mProjectsNames.contains(projects.get(i)))
      {
        Assert.fail("Found project that should not be available to customer");
      }
    }
  }

  @AfterClass
  public static void CloseWindow() throws Exception
  {
     mDriver.quit();
  }
}
