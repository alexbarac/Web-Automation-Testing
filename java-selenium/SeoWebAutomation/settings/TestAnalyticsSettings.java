package com.seo.selenium.ui.settings;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
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

public class TestAnalyticsSettings
{
  private static WebDriver mDriver;
  private static UserActions mTest;

  @BeforeClass
  public static void Login()
  {
    mDriver = com.seo.selenium.ui.SeleniumUtils.BuildDriver();
    mTest = new UserActions();
    mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmail2, SeleniumConstants.kUserPassword2);
  }

  @Before
  public void SetUp() throws Exception
  {
    mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlAnalyticsSettings);
  }

  @Test
  public void TestAnalyticsSettings() throws Exception
  {
    WebDriverWait wait = new WebDriverWait(mDriver, 40);
    WebElement element = null;
    try
    {

      element = wait
          .until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdDeleteAnalyticsAccountId)));
      element.click();

      element = wait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassBootbox)));
      element = element.findElement(By.className(SeleniumConstants.kClassModalDialog));
      element = element.findElement(By.className(SeleniumConstants.kClassModalContent));
      element = element.findElement(By.className(SeleniumConstants.kClassModalFooter));
      wait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassBtn)));
      List<WebElement> elementsList = element.findElements(By.className(SeleniumConstants.kClassBtn));
      for (int j = 0; j < elementsList.size(); j++)
      {
        if (elementsList.get(j).getText().equals(SeleniumConstants.kTextOk))
        {
          elementsList.get(j).click();
        }
      }
    } catch (Exception e)
    {
    }

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdGaAddAccount)));
    element.click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdEmail)));
    element.clear();
    element.sendKeys(SeleniumConstants.kUserAnalyticsAccount);

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNext)));
    element.click();

    Thread.sleep(5000);
    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdPasswd)));
    element.clear();
    element.sendKeys(SeleniumConstants.kUserAnalyticsPassword);

    Thread.sleep(5000);
    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSignIn)));
    element.click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSubmitApproveAccess)));
    element.click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdGaToggle)));
    element.click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By
        .partialLinkText(SeleniumConstants.kUserAnalyticsAccount)));
    element.click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdGapToggle)));
    element.click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdGaps)));

    wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kUserAtomic)));
    element = element.findElement(By.partialLinkText(SeleniumConstants.kUserAtomic));
    element.click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdGawToggle)));
    element.click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kUserAdeWeebly)));
    element.click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextSave)));
    element.click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassWell)));
    List<WebElement> elementList = element.findElements(By.className(SeleniumConstants.kClassFormGroup));

    Assert.assertEquals(SeleniumConstants.kAssertGoogleAccount, elementList.get(0).getText());
    Assert.assertEquals(SeleniumConstants.kAssertAnalyticsAccount, elementList.get(1).getText());
    Assert.assertEquals(SeleniumConstants.kAssertAnalyticsProperty, elementList.get(2).getText());
    Assert.assertEquals(SeleniumConstants.kAssertAnalyticsView, elementList.get(3).getText());
    Assert.assertEquals(5, elementList.size());
  }

  @AfterClass
  public static void CloseWindow() throws Exception
  {
    mDriver.quit();
  }

}
