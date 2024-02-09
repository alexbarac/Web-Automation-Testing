package com.seo.selenium.ui.rankings.keywordranking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.seo.selenium.ui.SeleniumConstants;
import com.seo.selenium.ui.UserActions;
import com.seo.serp.utils.mysql.DatabaseUtils;

public class TestKR {
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
	public void TestAddingRemovingSE() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelSeDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelSeDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By
				.partialLinkText(SeleniumConstants.kLinkTextAddSearchEngine)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAddSearchEngine)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelectedCountryName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelectedCountryName)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextRomania)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextRomania)).click();
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelectedSegroupName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelectedSegroupName)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextBing)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextBing)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassCheckbox)));
		mDriver.findElements(By.className(SeleniumConstants.kClassCheckbox)).get(0)
				.findElement(By.className(SeleniumConstants.kClassSelectedSesClass)).click();

		mDriver.findElement(By.id(SeleniumConstants.kIdSesAddButton)).click();
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelSeDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelSeDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextBing)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextBing)).click();
		Thread.sleep(5000);
		Assert.assertEquals(mDriver.findElement(By.id(SeleniumConstants.kIdSelSeDisplay)).getText(),
				SeleniumConstants.kLinkTextBingRomania);

		Thread.sleep(2000);
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchengineSettings);

		int size = mDriver.findElements(By.xpath("//div[@id='searchengines']/form/div/table/tbody/tr")).size();
		for (int i = 1; i <= size; i++) {
			if (mDriver.findElement(By.xpath("//div[@id='searchengines']/form/div/table/tbody/tr[" + i + "]/td[3]"))
					.getText().equals("Bing Romania")) {
				mDriver.findElement(By.xpath("//div[@id='searchengines']/form/div/table/tbody/tr[" + i + "]/td[2]/input"))
						.click();
			}
		}

		WebElement element = mWait
				.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdDelSel)));
		element.click();

		element = mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'OK')]")));
		element.click();
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestChangingWebsite() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelWsDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelWsDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextTumblrCom)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextTumblrCom)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelWsDisplay)));
		Assert.assertEquals(mDriver.findElement(By.id(SeleniumConstants.kIdSelWsDisplay)).getText(),
				SeleniumConstants.kLinkTextTumblrCom);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelWsDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelWsDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By
				.partialLinkText(SeleniumConstants.kLinkTextAtomicTangerine)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAtomicTangerine)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelWsDisplay)));
		Assert.assertEquals(mDriver.findElement(By.id(SeleniumConstants.kIdSelWsDisplay)).getText(),
				SeleniumConstants.kTextAtomic_Tangerine);
	}

	@Test
	public void TestDate() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdDpstop)));
		mDriver.findElement(By.id(SeleniumConstants.kIdDpstop)).click();
		Thread.sleep(5000);

		while (!mDriver.findElements(By.className(SeleniumConstants.kClassSwitch)).get(3).getText()
				.equals(SeleniumConstants.kDateApril2015)) {
			mDriver.findElements(By.className(SeleniumConstants.kClassPrev)).get(3).click();
		}

		Thread.sleep(10000);
		List<WebElement> elements = mDriver.findElements(By.className(SeleniumConstants.kClassDayBold));
		for (int i = 0; i < elements.size(); i++) {
			try {
				Integer.parseInt(elements.get(i).getText());
				elements.get(i).click();
			} catch (Exception e) {
			}
		}

		Thread.sleep(10000);
		Assert.assertEquals(mDriver.findElement(By.id(SeleniumConstants.kIdDpstart)).getText(),
				SeleniumConstants.kDate26Feb2015);
	}

	@Test
	public void TestColumnChooser() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//table[@id='rankingtable']/thead/tr[1]/td[1]/div/div/span/i")));
		mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[1]/td/div/div/span/i")).click();
		Thread.sleep(5000);

		if (mDriver.findElement(By.name(SeleniumConstants.kNamePosition)).getAttribute(
				SeleniumConstants.kAttributeChecked) != null) {
			mDriver.findElement(By.name(SeleniumConstants.kNamePosition)).click();
		}
		
		if (mDriver.findElement(By.name(SeleniumConstants.kNameBest)).getAttribute(
        SeleniumConstants.kAttributeChecked) != null) {
      mDriver.findElement(By.name(SeleniumConstants.kNameBest)).click();
    }

		if (mDriver.findElement(By.name(SeleniumConstants.kNameAnalytics)).getAttribute(
				SeleniumConstants.kAttributeChecked) != null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameAnalytics)).click();
		}

		if (mDriver.findElement(By.name(SeleniumConstants.kNamePage)).getAttribute(SeleniumConstants.kAttributeChecked) != null) {
			mDriver.findElement(By.name(SeleniumConstants.kNamePage)).click();
		}

		if (mDriver.findElement(By.name(SeleniumConstants.kNameCompetition)).getAttribute(
				SeleniumConstants.kAttributeChecked) != null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameCompetition)).click();
		}

		if (mDriver.findElement(By.name(SeleniumConstants.kNameGlobalMSearches)).getAttribute(
				SeleniumConstants.kAttributeChecked) != null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameGlobalMSearches)).click();
		}

		if (mDriver.findElement(By.name(SeleniumConstants.kNameCostPerClick)).getAttribute(
				SeleniumConstants.kAttributeChecked) != null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameCostPerClick)).click();
		}

		if (mDriver.findElement(By.name(SeleniumConstants.kNameTotalImpressions)).getAttribute(
				SeleniumConstants.kAttributeChecked) != null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameTotalImpressions)).click();
		}

		if (mDriver.findElement(By.name(SeleniumConstants.kNameTotalClicks)).getAttribute(
				SeleniumConstants.kAttributeChecked) != null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameTotalClicks)).click();
		}
		if (mDriver.findElement(By.name(SeleniumConstants.kNameTotalCTR)).getAttribute(SeleniumConstants.kAttributeChecked) != null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameTotalCTR)).click();
		}
		if (mDriver.findElement(By.name(SeleniumConstants.kNameTotalAveragePosition)).getAttribute(
				SeleniumConstants.kAttributeChecked) != null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameTotalAveragePosition)).click();
		}
		if (mDriver.findElement(By.name(SeleniumConstants.kNameType)).getAttribute(SeleniumConstants.kAttributeChecked) != null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameType)).click();
		}

		Thread.sleep(5000);
		mDriver.findElement(By.name(SeleniumConstants.kNameType)).submit();
		Thread.sleep(5000);

		int sizeBefore = 0;
		List<WebElement> elements = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th"));
		for (WebElement el : elements) {
			if (!el.getText().trim().isEmpty()) {
				sizeBefore++;
			}
		}
		
		Assert.assertEquals(sizeBefore, 8);
		Thread.sleep(5000);
		
		mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[1]/td/div/div/span/i")).click();
		Thread.sleep(5000);

		if (mDriver.findElement(By.name(SeleniumConstants.kNamePosition)).getAttribute(
				SeleniumConstants.kAttributeChecked) == null) {
			mDriver.findElement(By.name(SeleniumConstants.kNamePosition)).click();
		}

		if (mDriver.findElement(By.name(SeleniumConstants.kNameBest)).getAttribute(
        SeleniumConstants.kAttributeChecked) == null) {
      mDriver.findElement(By.name(SeleniumConstants.kNameBest)).click();
    }
		
		if (mDriver.findElement(By.name(SeleniumConstants.kNameAnalytics)).getAttribute(
				SeleniumConstants.kAttributeChecked) == null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameAnalytics)).click();
		}

		if (mDriver.findElement(By.name(SeleniumConstants.kNamePage)).getAttribute(SeleniumConstants.kAttributeChecked) == null) {
			mDriver.findElement(By.name(SeleniumConstants.kNamePage)).click();
		}

		if (mDriver.findElement(By.name(SeleniumConstants.kNameCompetition)).getAttribute(
				SeleniumConstants.kAttributeChecked) == null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameCompetition)).click();
		}

		if (mDriver.findElement(By.name(SeleniumConstants.kNameGlobalMSearches)).getAttribute(
				SeleniumConstants.kAttributeChecked) == null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameGlobalMSearches)).click();
		}

		if (mDriver.findElement(By.name(SeleniumConstants.kNameCostPerClick)).getAttribute(
				SeleniumConstants.kAttributeChecked) == null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameCostPerClick)).click();
		}

		if (mDriver.findElement(By.name(SeleniumConstants.kNameTotalImpressions)).getAttribute(
				SeleniumConstants.kAttributeChecked) == null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameTotalImpressions)).click();
		}

		if (mDriver.findElement(By.name(SeleniumConstants.kNameTotalClicks)).getAttribute(
				SeleniumConstants.kAttributeChecked) == null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameTotalClicks)).click();
		}
		
		if (mDriver.findElement(By.name(SeleniumConstants.kNameTotalCTR)).getAttribute(SeleniumConstants.kAttributeChecked) == null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameTotalCTR)).click();
		}
		
		if (mDriver.findElement(By.name(SeleniumConstants.kNameTotalAveragePosition)).getAttribute(
				SeleniumConstants.kAttributeChecked) == null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameTotalAveragePosition)).click();
		}
		if (mDriver.findElement(By.name(SeleniumConstants.kNameType)).getAttribute(SeleniumConstants.kAttributeChecked) == null) {
			mDriver.findElement(By.name(SeleniumConstants.kNameType)).click();
		}

		Thread.sleep(5000);
		mDriver.findElement(By.name(SeleniumConstants.kNameType)).submit();
		Thread.sleep(5000);

		int sizeAfter = 0;
		elements.clear();
		elements = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th"));
		for (WebElement el : elements) {
			if (!el.getText().trim().isEmpty()) {
				sizeAfter++;
			}
		}
		
		Assert.assertEquals(sizeAfter, sizeBefore + 7);
	}

	@Test
	public void TestMultipleDates4() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		List<String> dates = new ArrayList<String>();
		ArrayList<String> unparsedDates = DatabaseUtils.GetMDDates(DatabaseUtils.GetUser(59613348), 1,
				SeleniumConstants.kDate20152206, 4);
		for (int i = 0; i < unparsedDates.size(); i++) {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(unparsedDates.get(i));
			String month = new SimpleDateFormat("MMM").format(date);
			String day = new SimpleDateFormat("d").format(date);
			String year = new SimpleDateFormat("yyy").format(date);
			dates.add(month + " " + day + ", " + year);

		}
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By
				.partialLinkText(SeleniumConstants.kLinkTextMultipleDates)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextMultipleDates)).click();
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelMdDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelMdDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextLast4Updates)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextLast4Updates)).click();
		Thread.sleep(3000);

		for (int i = 0; i < 4; i++) {
			Assert.assertEquals(
					mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + (6 - i) + "]"))
							.getText(), dates.get(i));
		}
		
	}

	@Test
	public void TestMultipleDates7() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		List<String> dates = new ArrayList<String>();
		ArrayList<String> unparsedDates = DatabaseUtils.GetMDDates(DatabaseUtils.GetUser(59613348), 1,
				SeleniumConstants.kDate20152206, 7);
		for (int i = 0; i < unparsedDates.size(); i++) {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(unparsedDates.get(i));
			String month = new SimpleDateFormat("MMM").format(date);
			String day = new SimpleDateFormat("d").format(date);
			String year = new SimpleDateFormat("yyy").format(date);
			dates.add(month + " " + day + ", " + year);

		}

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By
				.partialLinkText(SeleniumConstants.kLinkTextMultipleDates)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextMultipleDates)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelMdDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelMdDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextLast4Updates)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextLast7Updates)).click();
		Thread.sleep(3000);

		for (int i = 0; i < 7; i++) {
			Assert.assertEquals(
					mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + (9 - i) + "]"))
							.getText(), dates.get(i));
		}
	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		mDriver.quit();
	}

}
