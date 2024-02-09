package com.seo.selenium.ui.rankings.overview;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.selenium.ui.SeleniumConstants;
import com.seo.selenium.ui.UserActions;

public class TestOverviewEmptyProject {
	private static WebDriver mDriver;
	private static UserActions mTest;
	private static WebDriverWait mWait;

	@Before
	public void Login() {
		mDriver = com.seo.selenium.ui.SeleniumUtils.BuildDriver();
		mTest = new UserActions();
		mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmail2, SeleniumConstants.kUserPassword2);
		mWait = new WebDriverWait(mDriver, 30);
	}

	@Test
	public void TestOverview() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlOverview + SeleniumConstants.kProjectId287);
		Thread.sleep(5000);
		// Click 'All data'
		try {
		mWait.until(ExpectedConditions.elementToBeClickable(mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextAll))));
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextAll)).click();
		Thread.sleep(3000);
		} catch(Exception e){}

		// No data for updates
		mWait.until(ExpectedConditions.elementToBeClickable(mDriver.findElement(By.className(SeleniumConstants.kClassDummyDataAction))));
		Assert.assertEquals(SeleniumConstants.kTextNoDataAvailableForWebsite, mDriver.findElement(By.className(SeleniumConstants.kClassDummyDataAction)).getText());

		// No data for Analytics
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlOverviewAnalytics + SeleniumConstants.kProjectId139);
		mWait.until(ExpectedConditions.visibilityOf(mDriver.findElement(By
				.xpath("//div[contains(@class, 'dummy-data__action')]/h3"))));
		Assert.assertEquals(SeleniumConstants.kTextGoogleAnalyticsData,
				mDriver.findElement(By.xpath("//div[contains(@class, 'dummy-data__action')]/h3")).getText());
		Assert.assertEquals(SeleniumConstants.kTextConnectGoogleAnalytics,
				mDriver.findElement(By.xpath("//div[contains(@class, 'dummy-data__action')]/a")).getText());
		Assert.assertEquals(SeleniumConstants.kTextVideoWrapperClass,
				mDriver.findElement(By.xpath("//div[contains(@class, 'dummy-data__action')]/div")).getAttribute("class"));

	}

	@After
	public void CloseWindow() throws Exception {
		mDriver.quit();
	}
}