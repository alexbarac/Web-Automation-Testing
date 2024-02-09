package com.seo.selenium.ui.flow.customer;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.selenium.ui.SeleniumConstants;
import com.seo.selenium.ui.SeleniumUtils;
import com.seo.selenium.ui.UserActions;

public class TestAccountSettings {
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
	public void GoToSettings() {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlAccountSettings + SeleniumConstants.kProjectId1);
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestNavPills() throws Exception {
		int size = mDriver.findElements(By.xpath("//ul[contains(@class, 'nav-pills')]/li")).size();
		Assert.assertEquals(2, size);

		for (int i = 1; i <= size; i++) {
			String text = mDriver.findElement(By.xpath("//ul[contains(@class,'nav-pills')]/li/a")).getText();

			if (!text.equals(SeleniumConstants.kTextAccount) && !text.equals(SeleniumConstants.kTextReportNotification)) {
				Assert.fail("Customer should have acces only to 'Account' and 'Report notification' tabs");
			}
		}

	}

	@Test
	public void TestChangeTreshold() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdDeltaChangesValue)));
		mDriver.findElement(By.id(SeleniumConstants.kIdDeltaChangesValue)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdDeltaChangesValue)).sendKeys(SeleniumConstants.kValue1);
		mDriver.findElement(By.id(SeleniumConstants.kIdDeltaChangesValue)).submit();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();
		int sizeBefore = mDriver.findElements(By.xpath("//div[contains(@class,'open')]/div/ul[2]/li")).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlAccountSettings);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdDeltaChangesValue)));
		mDriver.findElement(By.id(SeleniumConstants.kIdDeltaChangesValue)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdDeltaChangesValue)).sendKeys(SeleniumConstants.kValue10);
		mDriver.findElement(By.id(SeleniumConstants.kIdDeltaChangesValue)).submit();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();
		int sizeAfter = mDriver.findElements(By.xpath("//div[contains(@class,'open')]/div/ul[2]/li")).size();

		Assert.assertEquals(sizeBefore + 3, sizeAfter);
		
		//modify the Treshold back to 1
		
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlAccountSettings);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdDeltaChangesValue)));
		mDriver.findElement(By.id(SeleniumConstants.kIdDeltaChangesValue)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdDeltaChangesValue)).sendKeys("1");
		mDriver.findElement(By.id(SeleniumConstants.kIdDeltaChangesValue)).submit();
		
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();
		sizeAfter = mDriver.findElements(By.xpath("//div[contains(@class,'open')]/div/ul[2]/li")).size();

		Assert.assertEquals(sizeBefore, sizeAfter);
	}

	@Test
	public void TestChangePassword() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewPassword)));
		mDriver.findElement(By.id(SeleniumConstants.kIdNewPassword)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdNewPassword)).sendKeys(SeleniumConstants.kTextNewPassword);

		mDriver.findElement(By.id(SeleniumConstants.kIdCNewPassword)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdCNewPassword)).sendKeys(SeleniumConstants.kTextNewPassword);
		mDriver.findElement(By.id(SeleniumConstants.kIdCNewPassword)).submit();
		Thread.sleep(5000);

		mDriver.findElement(By.xpath("//li[contains(@class, 'pull-right')]/a")).click();
		Thread.sleep(5000);
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextSignOut)).click();
		Thread.sleep(5000);

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlLogin);
		mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmailCustomer, SeleniumConstants.kTextNewPassword);
		Thread.sleep(5000);

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlAccountSettings + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);
		
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewPassword)));
		mDriver.findElement(By.id(SeleniumConstants.kIdNewPassword)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdNewPassword)).sendKeys(SeleniumConstants.kUserPasswordCustomer);

		mDriver.findElement(By.id(SeleniumConstants.kIdCNewPassword)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdCNewPassword)).sendKeys(SeleniumConstants.kUserPasswordCustomer);
		mDriver.findElement(By.id(SeleniumConstants.kIdCNewPassword)).submit();
		Thread.sleep(5000);

		mDriver.findElement(By.xpath("//li[contains(@class, 'pull-right')]/a")).click();
		Thread.sleep(5000);
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextSignOut)).click();
		Thread.sleep(5000);

		mDriver.get(SeleniumConstants.kUrlLogin);
		mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmailCustomer, SeleniumConstants.kUserPasswordCustomer);
		Thread.sleep(5000);

	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		mDriver.quit();
	}
}
