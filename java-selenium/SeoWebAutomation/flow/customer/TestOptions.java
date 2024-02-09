package com.seo.selenium.ui.flow.customer;

import java.util.ArrayList;
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

public class TestOptions {
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
	public void TestMenu() throws Exception {
		Thread.sleep(3000);
		int size = mDriver.findElements(By.xpath("//ul[contains(@class,'main-navbar-menu')]/li")).size();
		Assert.assertEquals(4, size);

		for (int i = 1; i <= size; i++) {
			String text = mDriver.findElement(
					By.xpath("//ul[contains(@class,'main-navbar-menu')]/li[" + i + "]/a/span")).getText();
			if (!text.trim().isEmpty() && !text.equals(SeleniumConstants.kTextRankings)
					&& !text.equals(SeleniumConstants.kTextSocial) && !text.equals(SeleniumConstants.kTextReports)) {
				Assert.fail("Menu should not have " + text + " option for Customer");
			}
		}
	}

	@Test
	public void TestProgressBar() throws Exception {
		Thread.sleep(3000);
		try {
			mDriver.findElement(By.xpath("//span[contains(@class,'progress')]/span[1]"));
			Assert.fail("Customer should not have progress bar.");
		} catch (Exception e) {

		}
	}

	@Test
	public void TestNavigationRight() throws Exception {
		Thread.sleep(3000);
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@class,'pull-right')]/a")));
		mDriver.findElement(By.xpath("//li[contains(@class,'pull-right')]/a")).click();
		Thread.sleep(5000);

		List<String> options = new ArrayList<String>();
		int size = mDriver.findElements(By.xpath("//li[contains(@class,'pull-right')]/ul/li")).size();
		for (int i = 1; i <= size; i++) {
			String classType = mDriver.findElement(By.xpath("//li[contains(@class,'pull-right')]/ul/li[" + i + "]"))
					.getAttribute(SeleniumConstants.kAttributeClass);

			if (!(classType.contains(SeleniumConstants.kClassDivider)
					|| classType.contains(SeleniumConstants.kClassVisible) || classType
						.contains(SeleniumConstants.kClassDisabled))) {
				options.add(mDriver.findElement(By.xpath("//li[contains(@class,'pull-right')]/ul/li[" + i + "]/a"))
						.getText());
			}
		}

		for (String opt : options) {
			if (!(opt.equals(SeleniumConstants.kTextSignOut) || opt.equals(SeleniumConstants.kTextAccountSettings))) {
				Assert.fail("Customer should only have 'Sign out' and 'Settings' option in top right naviation bar");
			}
		}
	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		mDriver.quit();
	}
}
