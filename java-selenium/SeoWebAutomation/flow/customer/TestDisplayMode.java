package com.seo.selenium.ui.flow.customer;

import java.util.List;

import org.junit.After;
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

public class TestDisplayMode {
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
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlProjectList);
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestCompactDisplayMode() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div/table/thead/tr/td/div/div/div/button")));
		mDriver.findElement(By.xpath("//div/table/thead/tr/td/div/div/div/button")).click();

		Thread.sleep(5000);
		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextCompact)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextCompact)).click();

		Thread.sleep(5000);
		mWait.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//div[contains(@class, 'table-responsive')]/table/thead/tr[2]/th[1]")));
		List<WebElement> elements = mDriver.findElements(By
				.xpath("//div[contains(@class, 'table-responsive')]/table/thead/tr[2]/th"));
		Assert.assertEquals(10, elements.size());
	}

	@Test
	public void TestComfortableDisplayMode() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div/table/thead/tr/td/div/div/div/button")));
		mDriver.findElement(By.xpath("//div/table/thead/tr/td/div/div/div/button")).click();

		Thread.sleep(5000);
		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextComfortable)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextComfortable)).click();

		Thread.sleep(5000);
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div/table/thead/tr[2]/th[1]")));
		List<WebElement> elements = mDriver.findElements(By.xpath("//div/table/thead/tr[2]/th"));
		Assert.assertEquals(8, elements.size());
	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		mDriver.quit();
	}
}
