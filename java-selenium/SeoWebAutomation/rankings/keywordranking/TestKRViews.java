package com.seo.selenium.ui.rankings.keywordranking;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.seo.selenium.ui.SeleniumConstants;
import com.seo.selenium.ui.UserActions;

public class TestKRViews {
	private static WebDriver mDriver;
	private static UserActions mTest;
	private static WebDriverWait mWait;

	@BeforeClass
	public static void Login() {
		mDriver = com.seo.selenium.ui.SeleniumUtils.BuildDriver();
		mTest = new UserActions();
		mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmail2, SeleniumConstants.kUserPassword2);
		mWait = new WebDriverWait(mDriver, 30);
	}

	@Test
	public void TestDefaultView() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdViewDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdViewDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextDefault)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextDefault)).click();
		Thread.sleep(5000);

		Assert.assertEquals(mTest.GetDefaultUrls(mDriver, 5), true);
	}

	@Test
	public void TestBestView() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdViewDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdViewDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextBestPosition)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextBestPosition)).click();
		Thread.sleep(5000);

		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);

		Map<Integer, List<String>> map = mTest.GetBestPositionKR(mDriver);

		for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
			if (entry.getKey() != entry.getValue().size()) {
				Assert.fail("The numbers of urls hidden by expander and marked as hidden is not equal with the one the UI displays");
			}
		}
	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		mDriver.quit();
	}
}
