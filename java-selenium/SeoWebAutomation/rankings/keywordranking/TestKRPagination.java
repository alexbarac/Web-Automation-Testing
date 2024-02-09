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

public class TestKRPagination {
	private static WebDriver mDriver;
	private static UserActions mTest;
	private static WebDriverWait mWait;
	private static List<String> mSettingsKeywords;

	@BeforeClass
	public static void Login() {
	  //TAKES TOO LONG
		mDriver = com.seo.selenium.ui.SeleniumUtils.BuildDriver();
		mTest = new UserActions();
		mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmail2, SeleniumConstants.kUserPassword2);
		mWait = new WebDriverWait(mDriver, 30);

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordSettings);
//		try {
//			Thread.sleep(5000);
//
//			Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
//			select.selectByValue(SeleniumConstants.kValue100);
//			Thread.sleep(5000);
//
//			mSettingsKeywords = mTest.GetKeywords(mDriver);
//			mDriver.get(SeleniumConstants.kHost + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
//			Thread.sleep(5000);
//			if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText()
//					.equals(SeleniumConstants.kTextAllResult)) {
//				mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
//				mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();
//
//				mWait.until(ExpectedConditions.elementToBeClickable(By
//						.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
//				mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
//			}
//		} catch (Exception e) {
//		}
	}

	@Test
	public void TestPagination10() throws Exception {
//		mDriver.get(SeleniumConstants.kHost + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
//
//		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
//		select.selectByValue(SeleniumConstants.kValue10);
//		Thread.sleep(5000);
//		List<Map<String, List<Double>>> keywords = mTest.GetAllKeywordsFromKR(mDriver);
//
//		// compare with results from settings-keywords
//		Assert.assertEquals(keywords.size(), mSettingsKeywords.size());

	}

	@Test
	public void TestPagination25() throws Exception {
//		mDriver.get(SeleniumConstants.kHost + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
//
//		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
//		select.selectByValue(SeleniumConstants.kValue25);
//		Thread.sleep(5000);
//		List<Map<String, List<Double>>> keywords = mTest.GetAllKeywordsFromKR(mDriver);
//
//		// compare with results from settings-keywords
//		Assert.assertEquals(keywords.size(), mSettingsKeywords.size());

	}

	@Test
	public void TestPagination50() throws Exception {
//		mDriver.get(SeleniumConstants.kHost + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
//
//		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
//		select.selectByValue(SeleniumConstants.kValue100);
//		Thread.sleep(5000);
//		List<Map<String, List<Double>>> keywords = mTest.GetAllKeywordsFromKR(mDriver);
//
//		// compare with results from settings-keywords
//		Assert.assertEquals(keywords.size(), mSettingsKeywords.size());
	}

	@Test
	public void TestPagination100() throws Exception {
//		mDriver.get(SeleniumConstants.kHost + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
//
//		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
//		select.selectByValue(SeleniumConstants.kValue100);
//		Thread.sleep(5000);
//		List<Map<String, List<Double>>> keywords = mTest.GetAllKeywordsFromKR(mDriver);
//
//		// compare with results from settings-keywords
//		Assert.assertEquals(keywords.size(), mSettingsKeywords.size());
	}

//	private boolean CheckifKeyExists(String string, List<Map<String, List<Double>>> keywords) {
//		for (int i = 0; i < mSettingsKeywords.size(); i++) {
//			if (keywords.get(i).containsKey(mSettingsKeywords.get(i))) {
//				return true;
//			}
//		}
//		return false;
//	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		mDriver.quit();
	}
}