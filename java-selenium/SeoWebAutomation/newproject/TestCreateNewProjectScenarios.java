package com.seo.selenium.ui.newproject;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.selenium.ui.SeleniumConstants;
import com.seo.selenium.ui.UserActions;

public class TestCreateNewProjectScenarios
{
  private static WebDriver driver;
  private static UserActions test;

  @Before
  public void login()
  {
    driver = com.seo.selenium.ui.SeleniumUtils.BuildDriver();
    test = new UserActions();
    test.Login(driver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmail2, SeleniumConstants.kUserPassword2);
  }

  /**
   * @throws Exception
   */
  @Test
  public void testCreateNewProject1() throws Exception
  {
    WebDriverWait wait = new WebDriverWait(driver, 40);
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("sel_add_new_project")));
    element.click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewDomain)));
    element.clear();
    // element.sendKeys(SeleniumConstants.USER_DOMAIN_NAME);
    element.sendKeys("www.cinemagia.ro");

    wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelocationDropdown)));
    driver.findElement(By.id(SeleniumConstants.kIdSelocationDropdown)).click();
    // driver.findElement(By.linkText(SeleniumConstants.USER_COUNTRY)).click();
    driver.findElement(By.linkText("Romania")).click();

    Select dropdown = new Select(driver.findElement(By.id(SeleniumConstants.kIdDomainFrequency)));
    // dropdown.selectByVisibleText(SeleniumConstants.USER_FREQUENCY);
    dropdown.selectByVisibleText("daily");

    Thread.sleep(5000);
    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewKeywords)));
    element.clear();
    for (int i = 0; i < SeleniumConstants.kNumberOfKeywords1; i++)
    {
      element.sendKeys(SeleniumConstants.kUserKeywordsArray1[i] + Keys.RETURN);
    }

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewCompetitors)));
    element.clear();
    // element.sendKeys(SeleniumConstants.USER_COMPETITOR_1 + Keys.RETURN);
    // element.sendKeys(SeleniumConstants.USER_COMPETITOR_2 + Keys.RETURN);
    element.sendKeys("imdb.com" + Keys.RETURN);
    element.sendKeys("axn.ro");

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelAddWebsiteBtn)));
    element.click();

    UserActions userActions = new UserActions();
    // check project info
    driver.get("http://dev2015141.awrcloud.com/projects.php");

    driver.findElement(By.id("project_filter")).sendKeys("cinemagia.ro");
    driver.findElement(By.id("project_filter")).sendKeys(Keys.ENTER);
    WebElement table = driver.findElement(By.tagName("table"));
    List<WebElement> lists = table.findElements(By.xpath("//tbody/tr/td[2]/small[3]/a"));
    Assert.assertEquals(SeleniumConstants.kUserWebsitePages, lists.get(0).getText());
    Assert.assertEquals(SeleniumConstants.kUserWebsiteSes, lists.get(1).getText());
    Assert.assertEquals(SeleniumConstants.kUserWebsiteKWs1, lists.get(2).getText());

    driver.findElement(By.xpath("//table/tbody/tr/td[2]/a[2]")).click();

    // check website details
    // driver.get(SeleniumConstants.WEBSITE_SETTINGS_URL);
    Select select = new Select(driver.findElement(By.id("main_website_select")));
    Assert.assertEquals(SeleniumConstants.kUserDomainNameShorter1, select.getFirstSelectedOption().getText());

    Assert.assertEquals(SeleniumConstants.kUserAlias1,
        driver.findElement(By.xpath("//table[@id='competitors_1']/tbody/tr/td")).getText());

    select = new Select(driver.findElement(By.id("frequency")));
    Assert.assertEquals(SeleniumConstants.kFrequencyDaily, select.getFirstSelectedOption().getText());

    select = new Select(driver.findElement(By.id("depth")));
    Assert.assertEquals(SeleniumConstants.kDepth5Pages, select.getFirstSelectedOption().getText());

    select = new Select(driver.findElement(By.id("location")));
    Assert.assertEquals(SeleniumConstants.kUserCountryRomania, select.getFirstSelectedOption().getText());

    // check keywords size
    // driver.get(SeleniumConstants.kUrlKeywordSettings);
    driver.findElement(By.linkText("Keywords")).click();
    Assert.assertEquals(SeleniumConstants.kNumberOfKeywords1, userActions.GetKeywords(driver).size());

    // check searchengine
    // driver.get(SeleniumConstants.kHost +
    // SeleniumConstants.kUrlSearchengineSettings);
    driver.findElement(By.linkText("Search Engines")).click();
    Assert.assertEquals(4, userActions.GetSearchEngines(driver).size());

    // check competitors
    // driver.get(SeleniumConstants.kUrlCompetitorsSettings);
    driver.findElement(By.linkText("Competitors")).click();
    Assert.assertEquals(2, userActions.GetCompetitors(driver).size());
  }

  @Test
  public void testCreateNewProject2() throws Exception
  {
    WebDriverWait wait = new WebDriverWait(driver, 40);
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("sel_add_new_project")));
    element.click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewDomain)));
    element.clear();
    // element.sendKeys(SeleniumConstants.USER_DOMAIN_NAME);
    element.sendKeys("sweden.se");

    wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelocationDropdown)));
    driver.findElement(By.id(SeleniumConstants.kIdSelocationDropdown)).click();
    // driver.findElement(By.linkText(SeleniumConstants.USER_COUNTRY)).click();
    driver.findElement(By.linkText("Sweden")).click();

    Select dropdownDepth = new Select(driver.findElement(By.id(SeleniumConstants.kIdNewdepth)));
    dropdownDepth.selectByVisibleText("10 pages");

    Select dropdownFrequency = new Select(driver.findElement(By.id(SeleniumConstants.kIdDomainFrequency)));
    dropdownFrequency.selectByVisibleText("weekly");

    Thread.sleep(5000);
    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewKeywords)));
    element.clear();
    for (int i = 0; i < SeleniumConstants.kNumberOfKeywords2; i++)
    {
      element.sendKeys(SeleniumConstants.kUserKeywordsArray2[i] + Keys.RETURN);
    }

    driver.findElement(By.id("choice2")).click();
    driver.findElement(By.xpath("//span[@id='ses_mobile']/span[2]/span[2]")).click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewCompetitors)));
    element.clear();
    element.sendKeys("wikipedia.org" + Keys.RETURN);
    element.sendKeys("visitsweden.com" + Keys.RETURN);
    element.sendKeys("www.government.se");

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelAddWebsiteBtn)));
    element.click();

    UserActions userActions = new UserActions();
    // check project info
    driver.get("http://dev2015141.awrcloud.com/projects.php");

    driver.findElement(By.id("project_filter")).sendKeys("sweden.se");
    driver.findElement(By.id("project_filter")).sendKeys(Keys.ENTER);
    WebElement table = driver.findElement(By.tagName("table"));
    List<WebElement> lists = table.findElements(By.xpath("//tbody/tr/td[2]/small[3]/a"));
    Assert.assertEquals(SeleniumConstants.kUserWebsitePages1, lists.get(0).getText());
    Assert.assertEquals(SeleniumConstants.kUserWebsiteSes1, lists.get(1).getText());
    Assert.assertEquals(SeleniumConstants.kUserWebsiteKWs2, lists.get(2).getText());

    driver.findElement(By.xpath("//table/tbody/tr/td[2]/a[2]")).click();

    // check website details
    // driver.get(SeleniumConstants.WEBSITE_SETTINGS_URL);
    Select select = new Select(driver.findElement(By.id("main_website_select")));
    Assert.assertEquals(SeleniumConstants.kUserDomainNameShorter2, select.getFirstSelectedOption().getText());

    Assert.assertEquals(SeleniumConstants.kUserAlias2,
        driver.findElement(By.xpath("//table[@id='competitors_1']/tbody/tr/td")).getText());

    select = new Select(driver.findElement(By.id("frequency")));
    Assert.assertEquals(SeleniumConstants.kFrequencyWeekly, select.getFirstSelectedOption().getText());

    select = new Select(driver.findElement(By.id("depth")));
    Assert.assertEquals(SeleniumConstants.kDepth10Pages, select.getFirstSelectedOption().getText());

    select = new Select(driver.findElement(By.id("location")));
    Assert.assertEquals(SeleniumConstants.kUserContrySweden, select.getFirstSelectedOption().getText());

    // check keywords size
    driver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordSettings);
    Assert.assertEquals(SeleniumConstants.kNumberOfKeywords2, userActions.GetKeywords(driver).size());

    // check searchengine
    driver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchengineSettings);
    Assert.assertEquals(6, userActions.GetSearchEngines(driver).size());

    // check competitors
    driver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlCompetitorsSettings
        + SeleniumConstants.kParameterActionManagerActiveCompetitors);
    Assert.assertEquals(3, userActions.GetCompetitors(driver).size());
  }

  @Test
  public void testCreateNewProject3() throws Exception
  {

    driver.findElement(By.id("home-logo")).click();
    WebDriverWait wait = new WebDriverWait(driver, 40);

    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("sel_add_new_project")));
    element.click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewDomain)));
    element.clear();

    // element.sendKeys(SeleniumConstants.USER_DOMAIN_NAME);
    element.sendKeys("http://www.telegraph.co.uk/");
    driver.findElement(By.id("newtitle")).sendKeys("Telegraph UK");

    wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelocationDropdown)));
    driver.findElement(By.id(SeleniumConstants.kIdSelocationDropdown)).click();

    // driver.findElement(By.linkText(SeleniumConstants.USER_COUNTRY)).click();
    driver.findElement(By.linkText("UK")).click();
    Select dropdownDepth = new Select(driver.findElement(By.id(SeleniumConstants.kIdNewdepth)));
    dropdownDepth.selectByVisibleText("5 pages");

    Select dropdownFrequency = new Select(driver.findElement(By.id(SeleniumConstants.kIdDomainFrequency)));
    dropdownFrequency.selectByVisibleText("biweekly");
    Thread.sleep(5000);
    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewKeywords)));
    element.clear();
    for (int i = 0; i < 7; i++)
    {
      element.sendKeys(SeleniumConstants.kUserKeywordsArray3[i] + Keys.RETURN);
    }
    // adding search in location search engine
    // remove NY custom search engine
    driver.findElement(By.id("choice4")).click();
    driver.findElement(By.xpath("//span[@id='ses_local']/span[1]/span[2]")).click();

    // add location for London
    wait.until(ExpectedConditions.elementToBeClickable(By.className("selenium_add_location")));
    driver.findElement(By.className("selenium_add_location")).click();

    // selecting UK as the country for the SE
    wait.until(ExpectedConditions.elementToBeClickable(By.id("country_dropdown")));
    driver.findElement(By.id("country_dropdown")).click();
    wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("UK")));
    driver.findElement(By.partialLinkText("UK")).click();

    // Choosing the new SE - this will be removed
    Thread.sleep(5000);
    wait.until(ExpectedConditions.elementToBeClickable(By.id("selected_segroup_name")));
    driver.findElement(By.id("selected_segroup_name")).click();
    Thread.sleep(5000);

    wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Google Custom Location")));
    driver.findElement(By.partialLinkText("Google Custom Location")).click();
    Thread.sleep(3000);
    // selecting the new SE

    driver.findElement(By.id("custom_locations")).clear();
    driver.findElement(By.id("custom_locations")).sendKeys(
        "oxford street" + Keys.ENTER + "Liverpool" + Keys.ENTER + "Buckingham" + Keys.ENTER);

    // validate location
    Thread.sleep(3000);
    wait.until(ExpectedConditions.elementToBeClickable(By.id("validateButton")));
    driver.findElement(By.id("validateButton")).click();
    Thread.sleep(4000);

    // adding the SEs
    wait.until(ExpectedConditions.elementToBeClickable(By.id("ses_add_button")));
    driver.findElement(By.id("ses_add_button")).click();
    Thread.sleep(5000);

    // adding competitors
    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewCompetitors)));
    element.clear();
    element.sendKeys("theguardian.com" + Keys.RETURN);
    element.sendKeys("thesun.co.uk" + Keys.RETURN);
    element.sendKeys("wikipedia.org");

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelAddWebsiteBtn)));
    element.click();

    UserActions userActions = new UserActions();
    // check project info
    driver.get("http://dev2015141.awrcloud.com/projects.php");
    driver.findElement(By.id("project_filter")).sendKeys("Telegraph");
    driver.findElement(By.id("project_filter")).sendKeys(Keys.ENTER);

    WebElement table = driver.findElement(By.tagName("table"));
    List<WebElement> lists = table.findElements(By.xpath("//tbody/tr/td[2]/small[3]/a"));
    Assert.assertEquals(SeleniumConstants.kUserWebsitePages, lists.get(0).getText());
    Assert.assertEquals("6 SEs", lists.get(1).getText());
    Assert.assertEquals("7 KWs", lists.get(2).getText());

    driver.findElement(By.xpath("//table/tbody/tr/td[2]/a[2]")).click();
    // check website details
    // driver.get(SeleniumConstants.WEBSITE_SETTINGS_URL);
    Select select = new Select(driver.findElement(By.id("main_website_select")));
    Assert.assertEquals("telegraph.co.uk", select.getFirstSelectedOption().getText());

    String text = driver.findElement(By.id("new_project_title")).getAttribute("value");
    Assert.assertEquals("Telegraph UK", text);
    Assert.assertEquals("*.telegraph.co.uk", driver.findElement(By.xpath("//table[@id='competitors_1']/tbody/tr/td"))
        .getText());

    select = new Select(driver.findElement(By.id("frequency")));
    Assert.assertEquals("biweekly", select.getFirstSelectedOption().getText());

    select = new Select(driver.findElement(By.id("depth")));
    Assert.assertEquals(SeleniumConstants.kDepth5Pages, select.getFirstSelectedOption().getText());

    select = new Select(driver.findElement(By.id("location")));
    Assert.assertEquals("UK", select.getFirstSelectedOption().getText());

    // check keywords size
    driver.findElement(By.linkText("Keywords")).click();
    Assert.assertEquals(7, userActions.GetKeywords(driver).size());

    // check searchengine
    driver.findElement(By.linkText("Search Engines")).click();
    Assert.assertEquals(7, userActions.GetSearchEngines(driver).size());

    // check competitors
    driver.findElement(By.linkText("Competitors")).click();
    Assert.assertEquals(3, userActions.GetCompetitors(driver).size());
  }

  @Test
  public void testRemoveExistingProjects() throws Exception
  {
    driver.findElement(By.id("home-logo")).click();
    WebDriverWait wait = new WebDriverWait(driver, 40);
    Select select = new Select(driver.findElement(By.name("perpage")));
    select.selectByVisibleText("100");
    Thread.sleep(5000);
    int size = driver.findElements(By.xpath("//table[contains(@class,'table-striped')]/tbody/tr")).size();
    for (int i = 1; i <= size; i++)
    {
      if (driver.findElement(By.xpath("//table[contains(@class,'table-striped')]/tbody/tr[" + i + "]/td[2]/a"))
          .getText().equals("cinemagia.ro")
          || driver.findElement(By.xpath("//table[contains(@class,'table-striped')]/tbody/tr[" + i + "]/td[2]/a"))
              .getText().equals("Telegraph UK")
          || driver.findElement(By.xpath("//table[contains(@class,'table-striped')]/tbody/tr[" + i + "]/td[2]/a"))
              .getText().equals("sweden.se"))
      {
        driver.findElement(By.xpath("//table[contains(@class,'table-striped')]/tbody/tr[" + i + "]/td[1]/span/i"))
            .click();
      }
    }

    Thread.sleep(5000);
    wait.until(ExpectedConditions.elementToBeClickable(By.id("tag_actions")));
    driver.findElement(By.id("tag_actions")).click();

    Thread.sleep(5000);
    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Delete Websites")));
    driver.findElement(By.linkText("Delete Websites")).click();
    Thread.sleep(5000);

    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'modal-footer')]/button[2]")));
    driver.findElement(By.xpath("//div[contains(@class, 'modal-footer')]/button[2]")).click();

    Thread.sleep(5000);
    Assert.assertEquals(size - 3, driver.findElements(By.xpath("//table[contains(@class,'table-striped')]/tbody/tr"))
        .size());
  }

  @After
  public void closeWindow() throws Exception
  {
    driver.quit();
  }

  private void StaleElementHandleByID(String elementID)
  {
    int count = 0;
    boolean clicked = false;
    while (count < 4 && !clicked)
    {
      try
      {
        WebElement yourSlipperyElement = driver.findElement(By.id(elementID));
        yourSlipperyElement.click();
        clicked = true;
      } catch (org.openqa.selenium.StaleElementReferenceException e)
      {
        e.toString();
        count = count + 1;
      }
    }
  }
}