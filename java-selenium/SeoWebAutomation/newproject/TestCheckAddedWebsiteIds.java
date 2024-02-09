package com.seo.selenium.ui.newproject;

import java.util.ArrayList;
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

public class TestCheckAddedWebsiteIds {
	private static WebDriver driver;
	private static UserActions test;

	@Before
	public void login() {
		driver = com.seo.selenium.ui.SeleniumUtils.BuildDriver();
		test = new UserActions();
		test.Login(driver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmail0, SeleniumConstants.kUserPassword0);
	}

	@Test
	public void testCreateNewProject2() throws Exception {

		// Clicking 'Add Website'
		WebDriverWait wait = new WebDriverWait(driver, 40);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("sel_add_new_project")));
		element.click();

		// Inserting the website domain
		element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewDomain)));
		element.clear();
		element.sendKeys("sweden.se");

		// Inserting the website title
		element = wait.until(ExpectedConditions.elementToBeClickable(By.id("newtitle")));
		element.clear();
		element.sendKeys("sweden");

		// Choosing the country
		wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelocationDropdown)));
		driver.findElement(By.id(SeleniumConstants.kIdSelocationDropdown)).click();
		driver.findElement(By.linkText("Sweden")).click();

		// Selecting the depth
		Select dropdownDepth = new Select(driver.findElement(By.id(SeleniumConstants.kIdNewdepth)));
		dropdownDepth.selectByVisibleText("10 pages");

		// Selecting update frequency
		Select dropdownFrequency = new Select(driver.findElement(By.id(SeleniumConstants.kIdDomainFrequency)));
		dropdownFrequency.selectByVisibleText("weekly");

		// Clicking 'Get Suggestion' button for Keywords
		Thread.sleep(5000);
		element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewKeywords)));
		driver.findElement(By.xpath("//div[@id='step4']/div/p/button")).click();
		Thread.sleep(3000);

		// The total number of Keywords displayed in the User Interface
		Integer numberOfKeywords = Integer.parseInt(driver.findElement(By.id("kwCounter")).getText().replace("#", ""));

		List<String> keywords = new ArrayList<String>();
		String keywordsText = driver.findElement(By.id("newkeywords")).getAttribute("value");
		String[] tempK = keywordsText.split("\n");
		for (int i = 0; i < tempK.length; i++) {
			if (!tempK[i].isEmpty()) {
				keywords.add(tempK[i]);
			}
		}

		// Clicking Mobile SEs checkbox
		List<String> searchEngines = new ArrayList<String>();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("choice2")));
		driver.findElement(By.id("choice2")).click();

		// removing second element of Mobile SEs
		driver.findElement(By.xpath("//span[@id='ses_mobile']/span[1]/span[2]")).click();

		// Clicking Location SEs Checkbox
		wait.until(ExpectedConditions.elementToBeClickable(By.id("choice4")));
		driver.findElement(By.id("choice4")).click();

		// Clicking Video SEs Checkbox
		wait.until(ExpectedConditions.elementToBeClickable(By.id("choice3")));
		driver.findElement(By.id("choice3")).click();

		// Clicking 'Add search in location button'
		wait.until(ExpectedConditions.elementToBeClickable(By.className("selenium_add_location")));
		driver.findElement(By.className("selenium_add_location")).click();

		// Choosing Country for new SE
		wait.until(ExpectedConditions.elementToBeClickable(By.id("country_dropdown")));
		driver.findElement(By.id("country_dropdown")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("USA")));
		driver.findElement(By.partialLinkText("USA")).click();

		// Choosing the new SE
		Thread.sleep(5000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("selected_segroup_name")));
		driver.findElement(By.id("selected_segroup_name")).click();
		Thread.sleep(5000);
		wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Google")));
		driver.findElement(By.partialLinkText("Google")).click();

		// Selecting the locations for the new SE
		wait.until(ExpectedConditions.elementToBeClickable(By.id("custom_locations")));
		WebElement webElement = driver.findElement(By.id("custom_locations"));
		webElement.clear();
		webElement.sendKeys("Third Avenue" + Keys.ENTER + "Washington" + Keys.ENTER + "San Francisco" + Keys.ENTER);

		// Validating the locations
		Thread.sleep(3000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("validateButton")));
		driver.findElement(By.id("validateButton")).click();

		// Adding the new SE
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ses_add_button")));
		driver.findElement(By.id("ses_add_button")).click();
		Thread.sleep(5000);

		// For later comparasion, we are going to store all the SEs in
		// searchEngines of type java.util.List
		// storing for most popular
		int size = driver.findElements(By.xpath("//span[@id='ses_most_popular']/span")).size();
		for (int i = 1; i <= size; i++) {
			String attribute2 = driver.findElement(By.xpath("//span[@id='ses_most_popular']/span[" + i + "]/span[2]"))
					.getAttribute("onclick");
			searchEngines.add(attribute2.split("\"")[1]);
		}
		// storing for mobile
		size = driver.findElements(By.xpath("//span[@id='ses_mobile']/span")).size();
		for (int i = 1; i <= size; i++) {
			WebElement el = driver.findElement(By.xpath("//span[@id='ses_mobile']/span[" + i + "]/span[2]"));
			String attribute2 = el.getAttribute("onclick");
			searchEngines.add(attribute2.split("\"")[1]);
		}
		// storing for location
		size = driver.findElements(By.xpath("//span[@id='ses_local']/span")).size();
		for (int i = 1; i <= size; i++) {
			WebElement el = driver.findElement(By.xpath("//span[@id='ses_local']/span[" + i + "]/span[2]"));
			String attribute2 = el.getAttribute("onclick");
			searchEngines.add(attribute2.split("\"")[1]);
		}
		// storing for most video
		size = driver.findElements(By.xpath("//span[@id='ses_video']/span")).size();
		for (int i = 1; i <= size; i++) {
			WebElement el = driver.findElement(By.xpath("//span[@id='ses_video']/span[" + i + "]/span[2]"));
			String attribute2 = el.getAttribute("onclick");
			searchEngines.add(attribute2.split("\"")[1]);
		}

		// The total number of SEs displayed in the User Interface
		Integer numberOfSE = Integer.parseInt(driver.findElement(By.id("seCounter")).getText().replace("#", ""));

		// Clicking the 'Get suggestions' button for Competitors and storing
		// them for later comparation
		element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewCompetitors)));
		element.clear();
		driver.findElement(By.xpath("//div[@id='step3']/div/p/button")).click();
		Thread.sleep(3000);

		// The total number of Competitors displayed in the User Interface
		Integer numberOfCompetitors = Integer.parseInt(driver.findElement(By.id("comCounter")).getText()
				.replace("#", ""));

		List<String> competitors = new ArrayList<String>();
		String competitorsText = driver.findElement(By.id("newcompetitors")).getAttribute("value");
		String[] tempC = competitorsText.split("\n");
		for (int i = 0; i < tempC.length; i++) {
			if (!tempC[i].isEmpty()) {
				competitors.add(tempC[i]);
			}
		}

		// Checking if our numbers and the numbers in the UI are equal
		Assert.assertEquals(numberOfKeywords, new Integer(keywords.size()));
		Assert.assertEquals(numberOfSE, new Integer(searchEngines.size()));
		Assert.assertEquals(numberOfCompetitors, new Integer(competitors.size()));

		// Adding the websitew
		element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelAddWebsiteBtn)));
		element.click();

		UserActions userActions = new UserActions();

		driver.get("http://dev2015141.awrcloud.com/projects.php");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("project_filter")));

		// Selecting the website and checking the information displayed in the
		// Projects list
		driver.findElement(By.id("project_filter")).sendKeys("sweden.se");
		driver.findElement(By.id("project_filter")).sendKeys(Keys.ENTER);
		WebElement table = driver.findElement(By.tagName("table"));
		List<WebElement> lists = table.findElements(By.xpath("//tbody/tr/td[2]/small[3]/a"));
		Assert.assertEquals(SeleniumConstants.kUserWebsitePages1, lists.get(0).getText());
		Assert.assertEquals(numberOfSE + " SEs", lists.get(1).getText());
		Assert.assertEquals(numberOfKeywords + " KWs", lists.get(2).getText());

		driver.findElement(By.xpath("//table/tbody/tr/td[2]/a[2]")).click();

		// Checking the information stored in Website Settings

		// Checking the website URL
		Select select = new Select(driver.findElement(By.id("main_website_select")));
		Assert.assertEquals(SeleniumConstants.kUserDomainNameShorter2, select.getFirstSelectedOption().getText());

		// Checking the website title
		String text = driver.findElement(By.id("new_project_title")).getAttribute("value");
		Assert.assertEquals("sweden", text);

		// Checking the website alias
		Assert.assertEquals(SeleniumConstants.kUserAlias2,
				driver.findElement(By.xpath("//table[@id='competitors_1']/tbody/tr/td")).getText());

		// Checking the website update frequency
		select = new Select(driver.findElement(By.id("frequency")));
		Assert.assertEquals(SeleniumConstants.kFrequencyWeekly, select.getFirstSelectedOption().getText());

		// Checking the website depth selection
		select = new Select(driver.findElement(By.id("depth")));
		Assert.assertEquals(SeleniumConstants.kDepth10Pages, select.getFirstSelectedOption().getText());

		// Checking the website location
		select = new Select(driver.findElement(By.id("location")));
		Assert.assertEquals(SeleniumConstants.kUserContrySweden, select.getFirstSelectedOption().getText());

		// Checking the information stored in Keywords Settings
		driver.get(SeleniumConstants.kUrlKeywordSettings);
		Assert.assertEquals(numberOfKeywords, new Integer(userActions.GetKeywords(driver).size()));

		// Checking information stored in Search Engines Settings
		driver.get(SeleniumConstants.kUrlSearchengineSettings);
		Assert.assertEquals(numberOfSE, new Integer(userActions.GetSearchEngines(driver).size()));
		List<String> searchEngineNames = userActions.GetSearchEnginesNames(driver);
		Assert.assertEquals(searchEngines.size(), searchEngineNames.size());
		for (int i = 0; i < searchEngines.size(); i++) {
			if (!searchEngineNames.contains(searchEngines.get(i))) {
				Assert.fail("The inserted SE and the ones saved for the website are not the same ones");
			}
		}

		// Checking information stored in Competitors Settings
		driver.get(SeleniumConstants.kUrlCompetitorsSettings + SeleniumConstants.kParameterActionManagerActiveCompetitors);
		Assert.assertEquals(numberOfCompetitors, new Integer(userActions.GetCompetitors(driver).size()));
	}

	@After
	public void closeWindow() throws Exception {
		driver.quit();
	}
}
