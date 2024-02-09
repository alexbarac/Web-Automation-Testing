package com.seo.selenium.ui.flow.customer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.seo.selenium.ui.SeleniumConstants;
import com.seo.selenium.ui.UserActions;

public class TestQuickReports {

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

	@Test
	public void TestEditBrandAndReportTemplates() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlReports + SeleniumConstants.kProjectId267);
		Thread.sleep(5000);

		Assert.assertEquals(mDriver.findElements(By.xpath("//li[contains(@class,'active')]/ul/li/a")).size(), 1);

	}
	
	@Test
	public void TestSECWithPermission() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId267);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextQuickReport)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextQuickReport)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdDldLabel)));
		mDriver.findElement(By.id(SeleniumConstants.kIdDldLabel)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdHtmlLabel)));
		mDriver.findElement(By.id(SeleniumConstants.kIdHtmlLabel)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdQuickReportName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdQuickReportName)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdQuickReportName)).sendKeys("Test SEC");

		mWait.until(ExpectedConditions.elementToBeClickable(By.linkText(SeleniumConstants.kLinkTextDownload)));
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextDownload)).click();
	}

	@Test
	public void TestWRWithPermission() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlWebsiteRankings + SeleniumConstants.kProjectId267);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextQuickReport)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextQuickReport)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdDldLabel)));
		mDriver.findElement(By.id(SeleniumConstants.kIdDldLabel)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdHtmlLabel)));
		mDriver.findElement(By.id(SeleniumConstants.kIdHtmlLabel)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdQuickReportName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdQuickReportName)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdQuickReportName)).sendKeys("Test SEC");

		mWait.until(ExpectedConditions.elementToBeClickable(By.linkText(SeleniumConstants.kLinkTextDownload)));
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextDownload)).click();
	}

	@Test
	public void TestKRWithPermission() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId267);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextQuickReport)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextQuickReport)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdDldLabel)));
		mDriver.findElement(By.id(SeleniumConstants.kIdDldLabel)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdHtmlLabel)));
		mDriver.findElement(By.id(SeleniumConstants.kIdHtmlLabel)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdQuickReportName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdQuickReportName)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdQuickReportName)).sendKeys("Test SEC");

		mWait.until(ExpectedConditions.elementToBeClickable(By.linkText(SeleniumConstants.kLinkTextDownload)));
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextDownload)).click();
	}
    
	@Test
	public void TestKGWithPermission() throws Exception {
	  mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordGroups + SeleniumConstants.kProjectId267);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextQuickReport)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextQuickReport)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdDldLabel)));
		mDriver.findElement(By.id(SeleniumConstants.kIdDldLabel)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdHtmlLabel)));
		mDriver.findElement(By.id(SeleniumConstants.kIdHtmlLabel)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdQuickReportName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdQuickReportName)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdQuickReportName)).sendKeys("Test SEC");

		mWait.until(ExpectedConditions.elementToBeClickable(By.linkText(SeleniumConstants.kLinkTextDownload)));
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextDownload)).click();
	}

	@Test
	public void TestSECWithoutPermission() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId114);
		Thread.sleep(5000);

		try {
			mWait.until(ExpectedConditions.elementToBeClickable(By
					.partialLinkText(SeleniumConstants.kLinkTextQuickReport)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextQuickReport)).click();
			Assert.fail("Customer should not have permission to create quick reports");
		} catch (Exception e) {
		}
	}

	@Test
	public void TestWRWithoutPermission() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlWebsiteRankings + SeleniumConstants.kProjectId114);
		Thread.sleep(5000);

		try {
			mWait.until(ExpectedConditions.elementToBeClickable(By
					.partialLinkText(SeleniumConstants.kLinkTextQuickReport)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextQuickReport)).click();
			Assert.fail("Customer should not have permission to create quick reports");
		} catch (Exception e) {
		}
	}
  
	@Test
	public void TestKRWithoutPermission() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId114);
		Thread.sleep(5000);

		try {
			mWait.until(ExpectedConditions.elementToBeClickable(By
					.partialLinkText(SeleniumConstants.kLinkTextQuickReport)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextQuickReport)).click();
			Assert.fail("Customer should not have permission to create quick reports");
		} catch (Exception e) {
		}
	}

	@Test
	public void TestKGWithoutPermission() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordGroups + SeleniumConstants.kProjectId114);
		Thread.sleep(5000);

		try {
			mWait.until(ExpectedConditions.elementToBeClickable(By
					.partialLinkText(SeleniumConstants.kLinkTextQuickReport)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextQuickReport)).click();
			Assert.fail("Customer should not have permission to create quick reports");
		} catch (Exception e) {
		}
	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		mDriver.quit();
	}
}
