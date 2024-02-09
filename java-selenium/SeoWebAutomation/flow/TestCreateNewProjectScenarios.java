package com.seo.selenium.ui.flow;

import java.util.List;

import org.h2.engine.SysProperties;
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

public class TestCreateNewProjectScenarios {
	private static WebDriver mDriver;
	private static UserActions mTest;
	private static WebDriverWait mWait;

	@Before
	public void Login() {
		mDriver = com.seo.selenium.ui.SeleniumUtils.BuildDriver();
		mTest = new UserActions();
		mWait = new WebDriverWait(mDriver, 30);
		mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmail0, SeleniumConstants.kUserPassword0);
	}

	@Test
	public void TestGettingStarted() throws Exception {
		WebElement element = mWait.until(ExpectedConditions.elementToBeClickable(By
				.id(SeleniumConstants.kIdSelAddNewProject)));
		element.click();
		Thread.sleep(5000);

		element = mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewDomain)));
		element.clear();
		element.sendKeys(SeleniumConstants.kUserDomainName2);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelocationDropdown)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelocationDropdown)).click();
		Thread.sleep(5000);
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextRomania)).click();

		Select dropdown = new Select(mDriver.findElement(By.id(SeleniumConstants.kIdDomainFrequency)));
		dropdown.selectByVisibleText(SeleniumConstants.kFrequencyDaily);

		Thread.sleep(5000);
		element = mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewKeywords)));
		element.clear();
		for (int i = 0; i < SeleniumConstants.kNumberOfKeywords1; i++) {
			element.sendKeys(SeleniumConstants.kUserKeywordsArray1[i] + Keys.RETURN);
		}

		element = mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewCompetitors)));
		element.clear();
		element.sendKeys(SeleniumConstants.kUserCompetitorAntena3 + Keys.RETURN);
		element.sendKeys(SeleniumConstants.kUserCompetitorProTv);

		element = mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelAddWebsiteBtn)));
		element.click();

		UserActions userActions = new UserActions();
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlProjectsPhp);
		
		Thread.sleep(5000);

		mDriver.findElement(By.id(SeleniumConstants.kIdProjectFilter)).sendKeys(
				SeleniumConstants.kUserCompetitorRealitatea);
		mDriver.findElement(By.id(SeleniumConstants.kIdProjectFilter)).sendKeys(Keys.ENTER);
		Thread.sleep(5000);

		WebElement table = mDriver.findElement(By.tagName(SeleniumConstants.kTagTable));
		List<WebElement> lists = table.findElements(By.xpath("//tbody/tr/td[2]/small[3]/a"));
		Assert.assertEquals(SeleniumConstants.kUserWebsitePages, lists.get(0).getText());
		Assert.assertEquals(SeleniumConstants.kUserWebsiteSes, lists.get(1).getText());
		Assert.assertEquals(SeleniumConstants.kUserWebsiteKWs1, lists.get(2).getText());

		mDriver.findElement(By.xpath("//table/tbody/tr/td[2]/a[2]")).click();

		// check website details
		Thread.sleep(5000);
		Select select = new Select(mDriver.findElement(By.id(SeleniumConstants.kIdMainWebsiteSelect)));
		Assert.assertEquals(SeleniumConstants.kUserDomainNameShorter1, select.getFirstSelectedOption().getText());

		Assert.assertEquals(SeleniumConstants.kUserAlias1,
				mDriver.findElement(By.xpath("//table[@id='competitors_1']/tbody/tr/td")).getText());

		select = new Select(mDriver.findElement(By.id("frequency")));
		Assert.assertEquals(SeleniumConstants.kFrequencyDaily, select.getFirstSelectedOption().getText());

		select = new Select(mDriver.findElement(By.id("depth")));
		Assert.assertEquals(SeleniumConstants.kDepth5Pages, select.getFirstSelectedOption().getText());

		select = new Select(mDriver.findElement(By.id("location")));
		Assert.assertEquals(SeleniumConstants.kUserCountryRomania, select.getFirstSelectedOption().getText());

		// check keywords size
		mDriver.get(SeleniumConstants.kUrlKeywordSettings);
		Assert.assertEquals(SeleniumConstants.kNumberOfKeywords1, userActions.GetKeywords(mDriver).size());

		// check searchengine
		mDriver.get(SeleniumConstants.kUrlSearchengineSettings);
		Assert.assertEquals(3, userActions.GetSearchEngines(mDriver).size());

		// check competitors
		mDriver.get(SeleniumConstants.kUrlCompetitorsSettings + SeleniumConstants.kParameterActionManagerActiveCompetitors);
		Assert.assertEquals(2, userActions.GetCompetitors(mDriver).size());

		mDriver.get(SeleniumConstants.kUrlKeywordSettings);
		Thread.sleep(5000);

		// Number of completed task should be one initially
		Assert.assertEquals(1, GetNumberOfTasksCompleted());

		// click on the bar
		mWait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassGettingStarted)));
		mDriver.findElement(By.className(SeleniumConstants.kClassGettingStarted)).click();
		Thread.sleep(5000);

		// number of tasks to complete should be 6
		Assert.assertEquals(6, mDriver.findElements(By.xpath("//table/tbody/tr")).size());

		// Only the first element should be crossed, meaning one crossed element
		Assert.assertEquals(1, mDriver.findElements(By.tagName(SeleniumConstants.kTagS)).size());

		// Click on connect Google Analytics
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextGoogleAnalytics)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdGaAddAccount)));
		mDriver.findElement(By.id(SeleniumConstants.kIdGaAddAccount)).click();
		Thread.sleep(5000);

		// Check if account already added
		boolean accountExists = false;
		List<WebElement> emailAccounts = mDriver.findElements(By.className(SeleniumConstants.kClassAccountEmail));
		for (WebElement el : emailAccounts) {
			if (el.getText().contains(SeleniumConstants.kUserEmail1)) {
				el.click();
				Thread.sleep(5000);
				accountExists = true;
			}
		}
		if (!accountExists) {
			mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdEmail)));
			mDriver.findElement(By.id(SeleniumConstants.kIdEmail)).clear();
			mDriver.findElement(By.id(SeleniumConstants.kIdEmail)).sendKeys(SeleniumConstants.kUserEmail1);
			mDriver.findElement(By.id(SeleniumConstants.kIdNext)).click();
			Thread.sleep(5000);
		}

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdPasswd)));
		mDriver.findElement(By.id(SeleniumConstants.kIdPasswd)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdPasswd)).sendKeys(SeleniumConstants.kUserPassword1);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSignIn)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSignIn)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSubmitApproveAccess)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSubmitApproveAccess)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdGaToggle)));
		mDriver.findElement(By.id(SeleniumConstants.kIdGaToggle)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kUserEmail1)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kUserEmail1)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdGapToggle)));
		mDriver.findElement(By.id(SeleniumConstants.kIdGapToggle)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By
				.partialLinkText(SeleniumConstants.kLinkTextAtomicTangerine2)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAtomicTangerine2)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdGawToggle)));
		mDriver.findElement(By.id(SeleniumConstants.kIdGawToggle)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By
				.partialLinkText(SeleniumConstants.kLinkTextAtomicTangerineWebsite)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAtomicTangerineWebsite)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By
				.partialLinkText(SeleniumConstants.kLinkTextSaveTheNewConfiguration)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextSaveTheNewConfiguration)).click();
		Thread.sleep(10000);

		// Number of completed task should be two
		Assert.assertEquals(2, GetNumberOfTasksCompleted());

		// click on 'getting started' bar
		mWait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassGettingStarted)));
		mDriver.findElement(By.className(SeleniumConstants.kClassGettingStarted)).click();
		Thread.sleep(5000);

		// Two elements should be crossed
		Assert.assertEquals(2, mDriver.findElements(By.tagName(SeleniumConstants.kTagS)).size());

		// Click on connect Google Webmaster Tools
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextGoogleWebmasterTools)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextNewAccount)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextNewAccount)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSubmitApproveAccess)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSubmitApproveAccess)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextListWebsites)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextListWebsites)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.name(SeleniumConstants.kNameSite)));
		mDriver.findElement(By.name(SeleniumConstants.kNameSite)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdCheckLink1)));
		mDriver.findElement(By.id(SeleniumConstants.kIdCheckLink1)).click();
		Thread.sleep(5000);

		// Number of completed task should be one initially
		Assert.assertEquals(3, GetNumberOfTasksCompleted());

		// click on 'getting started' bar
		mWait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassGettingStarted)));
		mDriver.findElement(By.className(SeleniumConstants.kClassGettingStarted)).click();
		Thread.sleep(5000);

		// Three elements should be crossed
		Assert.assertEquals(3, mDriver.findElements(By.tagName(SeleniumConstants.kTagS)).size());

		// Click on connect Google Webmaster Tools
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextTwitterAccount)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassBgTwitter)));
		mDriver.findElement(By.className(SeleniumConstants.kClassBgTwitter)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdUsernameOrEmail)));
		mDriver.findElement(By.id(SeleniumConstants.kIdUsernameOrEmail)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdUsernameOrEmail)).sendKeys(SeleniumConstants.kUserEmail1);
		mDriver.findElement(By.id(SeleniumConstants.kIdPassword)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdPassword)).sendKeys(SeleniumConstants.kUserPassword1);
		mDriver.findElement(By.id(SeleniumConstants.kIdAllow)).click();
		Thread.sleep(5000);

		// Number of completed task should be one initially
		Assert.assertEquals(4, GetNumberOfTasksCompleted());

		// click on 'getting started' bar
		mWait.until(ExpectedConditions.elementToBeClickable(By.className("getting-started")));
		mDriver.findElement(By.className(SeleniumConstants.kClassGettingStarted)).click();
		Thread.sleep(5000);

		// Three elements should be crossed
		Assert.assertEquals(4, mDriver.findElements(By.tagName(SeleniumConstants.kTagS)).size());

		// click on 'getting started' bar
		mWait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassGettingStarted)));
		mDriver.findElement(By.className(SeleniumConstants.kClassGettingStarted)).click();
		Thread.sleep(5000);

		// Three elements should be crossed
		Assert.assertEquals(4, mDriver.findElements(By.tagName(SeleniumConstants.kTagS)).size());

		// Click on connect Facebook account Tools
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextWebsiteAudit)).click();
		Thread.sleep(10000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextStartCrawl)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextStartCrawl)).click();
		Thread.sleep(15000);

		Assert.assertEquals(
				"You'll see the results in a few hours and get an email as well when the crawl has been completed.",
				mDriver.findElement(By.xpath("//div[contains(@class, 'dummy-data__action')]/p")).getText());

		mDriver.get(SeleniumConstants.kUrlWebsiteSettings);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextDelete)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextDelete)).click();
		Thread.sleep(5000);

		mDriver.findElement(By.className(SeleniumConstants.kClassModalFooter)).findElement(By.xpath("//button[2]"))
				.click();

	}

	@After
	public void CloseWindow() throws Exception {
		mDriver.quit();
	}

	private int GetNumberOfTasksCompleted() {
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'getting-started')]/p")));
		String text = mDriver.findElement(By.xpath("//div[contains(@class, 'getting-started')]/p")).getText();
		text = text.split("completed")[1];
		text = text.split("out")[0];

		return Integer.parseInt(text.trim());
	}
}