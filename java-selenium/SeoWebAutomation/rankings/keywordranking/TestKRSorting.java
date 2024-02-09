package com.seo.selenium.ui.rankings.keywordranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

public class TestKRSorting {
	private static WebDriver mDriver;
	private static UserActions mTest;
	private static WebDriverWait mWait;;

	@BeforeClass
	public static void Login() {
		mDriver = com.seo.selenium.ui.SeleniumUtils.BuildDriver();
		mTest = new UserActions();
		mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmail0, SeleniumConstants.kUserPassword0);
		mWait = new WebDriverWait(mDriver, 30);
	}

	@Test
	public void TestSortingByPositionDecending() throws Exception {
		List<KRSortingElement> sortedList = new ArrayList<KRSortingElement>();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId32); // SeleniumConstants.kProjectId1);

		try {
			Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
			select.selectByValue(SeleniumConstants.kValue100);
			Thread.sleep(5000);
		} catch (Exception e) {
		}

		// sorteaza dupa altceva (gen keywords)
		mWait.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//table[@id='rankingtable']/thead/tr[2]/th[2]")));
		mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[2]")).click();
		Thread.sleep(5000);

		// Citeste valorile
		List<Map<String, List<Double>>> keywords = mTest.GetAllKeywordsFromKR(mDriver);

		// stabileste unde e pozitionata coloana pos in var position
		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int position = 0;
		for (position = 1; position <= size; position++) {
			if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + position + "]")).getText()
					.contains(SeleniumConstants.kTextPosition)) {
				break;
			}
		}

		// valoarea in lista e cu 2 unitati mai mica, iar lista incepe de la 0
		position -= 3;

		// pune valorile numai pentru keyword - position
		for (int i = 0; i < keywords.size(); i++) {
			Map<String, List<Double>> map = keywords.get(i);
			for (Map.Entry<String, List<Double>> entry : map.entrySet()) {
				String keyword = entry.getKey();
				List<Double> doubleList = entry.getValue();
				Double value = doubleList.get(position);
				sortedList.add(new KRSortingElement(keyword, value));
			}
		}

		// sorteaza lista
		Collections.sort(sortedList, new Comparator<KRSortingElement>() {
			@Override
			public int compare(KRSortingElement left, KRSortingElement right) {
				if (left.GetValue() > right.GetValue())
					return 1;

				if (left.GetValue() < right.GetValue())
					return -1;

				// if(left.getValue() == right.getValue())
				return 0;
			}
		});

		// sorteaza in ui dupa position
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId32);
		Thread.sleep(5000);
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th["
				+ (position + 3) + "]")));
		mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + (position + 3) + "]")).click();
		Thread.sleep(5000);

		// citeste valorile
		keywords.clear();
		keywords = mTest.GetAllKeywordsFromKR(mDriver);

		// pune valorile numai pentru keyword - position
		List<KRSortingElement> uiSortedList = new ArrayList<KRSortingElement>();
		for (int i = 0; i < keywords.size(); i++) {
			Map<String, List<Double>> map = keywords.get(i);
			for (Map.Entry<String, List<Double>> entry : map.entrySet()) {
				String keyword = entry.getKey();
				List<Double> doubleList = entry.getValue();
				Double value = doubleList.get(position);
				uiSortedList.add(new KRSortingElement(keyword, value));
			}
		}

		// compara cele doua list intre ele
		Assert.assertEquals(uiSortedList.size(), sortedList.size());
		for (int i = 0; i < sortedList.size(); i++) {
			Assert.assertEquals(sortedList.get(i).GetValue(), uiSortedList.get(i).GetValue());
			if (!CheckIfKeywordWithSpecifiedPositionExists(uiSortedList, sortedList.get(i).GetKeyword(), sortedList
					.get(i).GetValue())) {
				Assert.fail("Keyword ranking sorint by position did not sort corectly...");
			}
		}
	}

	@Test
	public void TestSortingByPositionAscending() throws Exception {
		List<KRSortingElement> sortedList = new ArrayList<KRSortingElement>();
		
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId32);
		Thread.sleep(5000);
		
		try {
			Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
			select.selectByValue(SeleniumConstants.kValue100);
			Thread.sleep(5000);
		} catch (Exception e) {
		}

		// sorteaza dupa altceva (gen keywords)
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[2]")));
		mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[2]")).click();
		Thread.sleep(5000);

		// Citeste valorile
		List<Map<String, List<Double>>> keywords = mTest.GetAllKeywordsFromKR(mDriver);

		// stabileste unde e pozitionata coloana pos in var position
		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int position = 0;
		for (position = 1; position <= size; position++) {
			if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + position + "]")).getText()
					.contains("Position")) {
				break;
			}
		}

		// valoarea in lista e cu 2 unitati mai mica, iar lista incepe de la 0
		position -= 3;

		// pune valorile numai pentru keyword - position
		for (int i = 0; i < keywords.size(); i++) {
			Map<String, List<Double>> map = keywords.get(i);
			for (Map.Entry<String, List<Double>> entry : map.entrySet()) {
				String keyword = entry.getKey();
				List<Double> doubleList = entry.getValue();
				Double value = doubleList.get(position);
				sortedList.add(new KRSortingElement(keyword, value));
			}
		}

		// sorteaza lista
		Collections.sort(sortedList, new Comparator<KRSortingElement>() {
			@Override
			public int compare(KRSortingElement left, KRSortingElement right) {
				if (left.GetValue() > right.GetValue())
					return -1;

				if (left.GetValue() < right.GetValue())
					return 1;

				// if(left.getValue() == right.getValue())
				return 0;
			}
		});

		// sorteaza in ui dupa position
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId32);
		Thread.sleep(5000);
		
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th["
				+ (position + 3) + "]")));
		mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + (position + 3) + "]")).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th["
				+ (position + 3) + "]")));
		mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + (position + 3) + "]")).click();
		Thread.sleep(5000);

		// citeste valorile
		keywords.clear();
		keywords = mTest.GetAllKeywordsFromKR(mDriver);

		// pune valorile numai pentru keyword - position
		List<KRSortingElement> uiSortedList = new ArrayList<KRSortingElement>();
		for (int i = 0; i < keywords.size(); i++) {
			Map<String, List<Double>> map = keywords.get(i);
			for (Map.Entry<String, List<Double>> entry : map.entrySet()) {
				String keyword = entry.getKey();
				List<Double> doubleList = entry.getValue();
				Double value = doubleList.get(position);
				uiSortedList.add(new KRSortingElement(keyword, value));
			}
		}

		// compara cele doua list intre ele
		Assert.assertEquals(uiSortedList.size(), sortedList.size());
		for (int i = 0; i < sortedList.size(); i++) {
			Assert.assertEquals(sortedList.get(i).GetValue(), uiSortedList.get(i).GetValue());
			if (!CheckIfKeywordWithSpecifiedPositionExists(uiSortedList, sortedList.get(i).GetKeyword(), sortedList
					.get(i).GetValue())) {
				Assert.fail("Keyword ranking sorint by position did not sort corectly...");
			}
		}
	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		mDriver.quit();
	}

	private boolean CheckIfKeywordWithSpecifiedPositionExists(List<KRSortingElement> uiSortedList, String keyword,
			Double value) {
		for (int i = 0; i < uiSortedList.size(); i++) {
			if (uiSortedList.get(i).GetValue().intValue() == value.intValue()
					&& uiSortedList.get(i).GetKeyword().equals(keyword)) {
				return true;
			}
		}
		return false;
	}
}
