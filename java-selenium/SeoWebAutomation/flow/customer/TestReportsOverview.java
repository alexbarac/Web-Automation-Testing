package com.seo.selenium.ui.flow.customer;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.selenium.ui.SeleniumConstants;
import com.seo.selenium.ui.UserActions;

public class TestReportsOverview {
	private static WebDriver mDriver;
	private static UserActions mTest;
	private static WebDriverWait mWait;

	@BeforeClass
	public static void Login() {
		mDriver = com.seo.selenium.ui.SeleniumUtils.BuildDriver();
		mTest = new UserActions();
		mWait = new WebDriverWait(mDriver, 30);
		mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmailCustomer, SeleniumConstants.kUserPasswordCustomer);
	}

	@Before
	public void GoToProjectList() {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlReports);
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestDeleteSelectReports() throws Exception {
		try {
			mDriver.findElement(By.id(SeleniumConstants.kIdSelAllHeader)).click();
			Assert.fail("Customer should not be able to select reports.");
		} catch (Exception e) {
		}
	}

	@Test
	public void TestSearch() throws Exception {
		mDriver.findElement(By.name(SeleniumConstants.kNameSearchQuery)).clear();
		mDriver.findElement(By.name(SeleniumConstants.kNameSearchQuery)).sendKeys(SeleniumConstants.kTextG);
		mDriver.findElement(By.name(SeleniumConstants.kNameSearchQuery)).sendKeys(Keys.ENTER);
		Thread.sleep(5000);

		int size = mDriver.findElements(By.xpath("//table[contains(@class,'table-striped')]/tbody/tr")).size();
		for (int i = 1; i <= size; i++) {
			String text = mDriver.findElement(
					By.xpath("//table[contains(@class,'table-striped')]/tbody/tr[" + i + "]/td[1]/a")).getText();
			if (!text.contains("g") && !text.contains("G")) {
				Assert.fail("Search in Reports Overview for Customer did not perform as expected.");
			}
		}
	}

	@Test
	public void TestUnreadReports() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewReportsBtn)));
		mDriver.findElement(By.id(SeleniumConstants.kIdNewReportsBtn)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextUnread)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextUnread)).click();
		Thread.sleep(5000);

		int size = mDriver.findElements(By.xpath("//table[contains(@class,'table-striped')]/tbody/tr")).size();
		for (int i = 1; i <= size; i++) {
			if (!mDriver.findElement(By.xpath("//table[contains(@class,'table-striped')]/tbody/tr[" + i + "]/td[1]"))
					.getAttribute(SeleniumConstants.kAttributeStyle).contains(SeleniumConstants.kClassBold))
				Assert.fail("Read report appeared in 'Unread' filter.");
		}
	}

	@Test
	public void TestCurrentWebsite() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdPrjName)));
		List<WebElement> elements = mDriver.findElements(By.id(SeleniumConstants.kIdPrjName));
		for (WebElement el : elements) {
			if (el.getText().contains(SeleniumConstants.kTextAllWebsites)) {
				el.click();
				break;
			}
		}

		mWait.until(ExpectedConditions.elementToBeClickable(By
				.partialLinkText(SeleniumConstants.kLinkTextCurrentWebsite)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextCurrentWebsite)).click();
		Thread.sleep(5000);

		String project = mDriver.findElement(By.xpath("//ul[contains(@class, 'pull-left')]/li/a")).getText();

		int size = mDriver.findElements(By.xpath("//table[contains(@class,'table-striped')]/tbody/tr")).size();
		for (int i = 1; i <= size; i++) {
			if (!mDriver.findElement(By.xpath("//table[contains(@class,'table-striped')]/tbody/tr[" + i + "]/td[2]"))
					.getText().equals(project))
				Assert.fail("Report coresponding to other project present after 'Current website' filter");
		}
	}

	@Test
	public void TestSocial() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSocial + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);

	    mWait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassIconTwitterSign)));
		mDriver.findElement(By.className(SeleniumConstants.kClassIconTwitterSign)).click();
		Thread.sleep(5000);

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_W);
		
		mWait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassIconFacebookSign)));
		mDriver.findElement(By.className(SeleniumConstants.kClassIconFacebookSign)).click();
		Thread.sleep(5000);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_W);
		
		try {
        Integer.parseInt(mDriver.findElement(By.id(SeleniumConstants.kIdTwFollowers)).getText().replace(",", "").replace(".", "").replace(" ", ""));
		Integer.parseInt(mDriver.findElement(By.id(SeleniumConstants.kIdTwLists)).getText().replace(",", "").replace(".", "").replace(" ", ""));
		Integer.parseInt(mDriver.findElement(By.id(SeleniumConstants.kIdFbLike)).getText().replace(",", "").replace(".", "").replace(" ", ""));
		Integer.parseInt(mDriver.findElement(By.id(SeleniumConstants.kIdFbNewLikes)).getText().replace(",", "").replace(".", "").replace(" ", ""));
		} catch(Exception e) {
			Assert.fail("No numbers for the Twitter and/or Facebook sections");
		}
		
	}

	@Test
	public void TestTwitterConnected() throws Exception{
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlTwitter + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);
		
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath
				("//table[contains(@class, 'table-sticky-header')]/thead/tr[1]/th[2]")));
		Assert.assertEquals(SeleniumConstants.kTextFollowers, 
				mDriver.findElement(By.xpath("//div [contains(@class, 'row')]/div/button/span[2]")).getText());		
		Assert.assertEquals(SeleniumConstants.kTextName, 
				mDriver.findElement(By.xpath("//table[contains(@class, 'table-sticky-header')]/thead/tr[1]/th[2]")).getText());
		
	}
	
	@Test
	public void TestFacebookConnected() throws Exception{
	  mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlFacebook + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);
		
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath
				("//table[contains(@class, 'table-sticky-header')]/thead/tr[1]/th[4]")));
		Assert.assertEquals(SeleniumConstants.kTextLikes, 
				mDriver.findElement(By.xpath("//div [contains(@class, 'row')]/div/button/span[2]")).getText());
		Assert.assertEquals(SeleniumConstants.kTextComments, 
				mDriver.findElement(By.xpath("//table[contains(@class, 'table-sticky-header')]/thead/tr[1]/th[4]")).getText());
	}
	
	@Test
	public void TestTwitterNotConnected() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlTwitter + SeleniumConstants.kProjectId114);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'dummy-data__action')]/h3")));
		Assert.assertEquals("Get data from Twitter",
				mDriver.findElement(By.xpath("//div[contains(@class,'dummy-data__action')]/h3")).getText());
		Assert.assertEquals("Authorize Atomic Tangerine to access data from Twitter.",
				mDriver.findElement(By.xpath("//div[contains(@class,'dummy-data__action')]/p")).getText());
		Assert.assertEquals("Sorry! No social profiles connected. Please contact the Atomic Tangerine account owner.", mDriver
				.findElement(By.xpath("//div[contains(@class,'dummy-data__action')]/div")).getText());
	}

	@Test
	public void TestFacebookNotConnected() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlFacebook + SeleniumConstants.kProjectId114);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'dummy-data__action')]/h3")));
		Assert.assertEquals("Get data from Facebook",
				mDriver.findElement(By.xpath("//div[contains(@class,'dummy-data__action')]/h3")).getText());
		Assert.assertEquals("Authorize Atomic Tangerine to access data from Facebook.",
				mDriver.findElement(By.xpath("//div[contains(@class,'dummy-data__action')]/p")).getText());
		Assert.assertEquals("Sorry! No social profiles connected. Please contact the Atomic Tangerine account owner.", mDriver
				.findElement(By.xpath("//div[contains(@class,'dummy-data__action')]/div")).getText());
	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		mDriver.quit();
	}
}
