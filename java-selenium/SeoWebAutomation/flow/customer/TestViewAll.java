package com.seo.selenium.ui.flow.customer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.selenium.ui.SeleniumConstants;
import com.seo.selenium.ui.UserActions;

public class TestViewAll {
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
		mDriver.get(SeleniumConstants.kUrlProjectList);
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestActive() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdFilterTags)));
		mDriver.findElement(By.id(SeleniumConstants.kIdFilterTags)).click();
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextActive)).click();
		Thread.sleep(5000);

		Assert.assertEquals(3, mTest.GetWebsitesProjectListCustomer(mDriver).size());
	}

	@Test
	public void TestInactive() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdFilterTags)));
		mDriver.findElement(By.id(SeleniumConstants.kIdFilterTags)).click();
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextInactive)).click();
		Thread.sleep(5000);

		Assert.assertEquals(0, mTest.GetWebsitesProjectListCustomer(mDriver).size());
	}

	@Test
	public void TestSearch() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdProjectFilter)));
		mDriver.findElement(By.id(SeleniumConstants.kIdProjectFilter)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdProjectFilter)).sendKeys("A");
		mDriver.findElement(By.id(SeleniumConstants.kIdProjectFilter)).sendKeys(Keys.ENTER);
		Assert.assertEquals(mTest.GetWebsitesProjectListCustomer(mDriver).size(), 3);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdProjectFilter)));
		mDriver.findElement(By.id(SeleniumConstants.kIdProjectFilter)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdProjectFilter)).sendKeys("Atom");
		mDriver.findElement(By.id(SeleniumConstants.kIdProjectFilter)).sendKeys(Keys.ENTER);
		Assert.assertEquals(mTest.GetWebsitesProjectListCustomer(mDriver).size(), 1);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdProjectFilter)));
		mDriver.findElement(By.id(SeleniumConstants.kIdProjectFilter)).clear();
		mDriver.findElement(By.id(SeleniumConstants.kIdProjectFilter)).sendKeys("Nothing");
		mDriver.findElement(By.id(SeleniumConstants.kIdProjectFilter)).sendKeys(Keys.ENTER);
		Assert.assertEquals(mTest.GetWebsitesProjectListCustomer(mDriver).size(), 0);
	}

	@Test
	public void TestTags() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdFilterTags)));
		mDriver.findElement(By.id(SeleniumConstants.kIdFilterTags)).click();
		Thread.sleep(5000);

		int size = mDriver.findElements(By.xpath("//div[contains(@class,'btn-group open')]/ul/li")).size();
		if (size > 3) {
			for (int i = 3; i <= size; i++) {
				try {
					mDriver.findElement(By.xpath("//div[contains(@class,'btn-group open')]/ul/li[" + i + "]/a/i"));
					Assert.fail("Customer should not have the option to edit tags");
				} catch (Exception e) {

				}
			}
		}

		try {
			mDriver.findElement(By.id(SeleniumConstants.kIdDeleteSelected));
			Assert.fail("Customer should not have the option to add new tags or assign existing ones");
		} catch (Exception e) {
		}

	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		mDriver.quit();
	}
}
