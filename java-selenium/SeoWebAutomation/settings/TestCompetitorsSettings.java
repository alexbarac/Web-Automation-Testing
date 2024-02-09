package com.seo.selenium.ui.settings;

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

public class TestCompetitorsSettings {
	private static WebDriver mDriver;
	private static UserActions mTtest;

	@BeforeClass
	public static void Login() {
		mDriver = com.seo.selenium.ui.SeleniumUtils.BuildDriver();
		mTtest = new UserActions();
		mTtest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmail1, SeleniumConstants.kUserPassword1);
	}

	@Before
	public void SetUp() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlCompetitorsSettings + SeleniumConstants.kParameterActionManagerActiveCompetitors);
	}

	@Test
	public void TestAddingCompetitors() throws Exception {
		if (mTtest.GetCompetitors(mDriver).size() > 0) {
			mTtest.EmptyCompetitors(mDriver, SeleniumConstants.kHostDev);
		}
		
		Thread.sleep(5000);
		WebDriverWait wait = new WebDriverWait(mDriver, 40);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By
				.id(SeleniumConstants.kIdAddNewCompetitors)));
		element.click();

		element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdAddCompetitorsForm)));
		element.clear();
		for (int i = 0; i < SeleniumConstants.kUserCompetitorsArray.length - 5; i++) {
			element.sendKeys(SeleniumConstants.kUserCompetitorsArray[i] + Keys.ENTER);
		}
		
		Thread.sleep(3000);
		mDriver.findElement(By.id(SeleniumConstants.kIdAddYourCompetitors)).click();
		Thread.sleep(3000);
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlCompetitorsSettings + SeleniumConstants.kParameterActionManagerActiveCompetitors);
		Thread.sleep(3000);
		Assert.assertEquals(SeleniumConstants.kUserCompetitorsArray.length - 5, mTtest.GetCompetitors(mDriver).size());

		mTtest.EmptyCompetitors(mDriver, SeleniumConstants.kHostDev);
		Thread.sleep(3000);
		Assert.assertEquals(0, mTtest.GetCompetitors(mDriver).size());
	}

	@Test
	public void TestCompetitorsPagination() throws Exception {
		if (mTtest.GetCompetitors(mDriver).size() > 0) {
			mTtest.EmptyCompetitors(mDriver, SeleniumConstants.kHostDev);
		}

		WebDriverWait wait = new WebDriverWait(mDriver, 40);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By
				.id(SeleniumConstants.kIdAddNewCompetitors)));
		element.click();

		element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdAddCompetitorsForm)));
		element.clear();
		for (int i = 0; i < SeleniumConstants.kUserCompetitorsArray.length; i++) {
			element.sendKeys(SeleniumConstants.kUserCompetitorsArray[i] + Keys.ENTER);
		}
		
		element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdAddYourCompetitors)));
		element.click();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlCompetitorsSettings + SeleniumConstants.kParameterActionManagerActiveCompetitors);
		Assert.assertEquals(SeleniumConstants.kUserCompetitorsArray.length, mTtest.GetCompetitors(mDriver).size());

		mTtest.EmptyCompetitors(mDriver, SeleniumConstants.kHostDev);
		Assert.assertEquals(0, mTtest.GetCompetitors(mDriver).size());
	}

	@Test
	public void TestAddingSameCompetitors() throws Exception {
		if (mTtest.GetCompetitors(mDriver).size() > 0) {
			mTtest.EmptyCompetitors(mDriver, SeleniumConstants.kHostDev);
		}
		WebDriverWait wait = new WebDriverWait(mDriver, 40);

		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By
				.id(SeleniumConstants.kIdAddNewCompetitors)));
		element.click();

		element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdAddCompetitorsForm)));
		element.clear();
		
		for (int i = 0; i < SeleniumConstants.kUserCompetitorsArray.length - 5; i++) {
			element.sendKeys(SeleniumConstants.kUserCompetitorsArray[i] + Keys.ENTER);
		}
		mDriver.findElement(By.id(SeleniumConstants.kIdAddYourCompetitors)).click();
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlCompetitorsSettings + SeleniumConstants.kParameterActionManagerActiveCompetitors);

		element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdAddNewCompetitors)));
		element.click();

		element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdAddCompetitorsForm)));
		element.clear();
		
		for (int i = 0; i < SeleniumConstants.kUserCompetitorsArray.length - 5; i++) {
			element.sendKeys(SeleniumConstants.kUserCompetitorsArray[i] + Keys.ENTER);
		}
		mDriver.findElement(By.id(SeleniumConstants.kIdAddYourCompetitors)).click();
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlCompetitorsSettings);

		Thread.sleep(5000);
		Assert.assertEquals(SeleniumConstants.kUserCompetitorsArray.length - 5, mTtest.GetCompetitors(mDriver).size());
		mTtest.EmptyCompetitors(mDriver, SeleniumConstants.kHostDev);
		Thread.sleep(5000);
		Assert.assertEquals(0, mTtest.GetCompetitors(mDriver).size());
	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		mDriver.quit();
	}

}
