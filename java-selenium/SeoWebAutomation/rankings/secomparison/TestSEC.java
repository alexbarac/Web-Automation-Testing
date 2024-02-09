package com.seo.selenium.ui.rankings.secomparison;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.seo.selenium.ui.SeleniumConstants;
import com.seo.selenium.ui.UserActions;

public class TestSEC {
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
	public void TestDate() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
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
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//table[@id='rankingtable']/thead/tr[1]/td[1]/div/div/span/i")));
		mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[1]/td/div/div/span/i")).click();
		Thread.sleep(5000);

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

		Thread.sleep(5000);
		mDriver.findElement(By.name(SeleniumConstants.kNameCostPerClick)).submit();
		Thread.sleep(5000);

		int sizeBefore = 0;
		List<WebElement> elements = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th"));
		for (WebElement el : elements) {
			if (!el.getText().trim().isEmpty()) {
				sizeBefore++;
			}
		}

		Thread.sleep(5000);
		mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[1]/td/div/div/span/i")).click();
		Thread.sleep(5000);

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

		Thread.sleep(5000);
		mDriver.findElement(By.name(SeleniumConstants.kNameCostPerClick)).submit();
		Thread.sleep(5000);

		int sizeAfter = 0;
		elements.clear();
		elements = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th"));
		for (WebElement el : elements) {
			if (!el.getText().trim().isEmpty()) {
				sizeAfter++;
			}
		}

		Assert.assertEquals(sizeAfter, sizeBefore + 3);
	}

	@Test
	public void TestFistPlace() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);

		try 
		{
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);
		} catch (Exception e) 
		{
		}

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextFirstPlace)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextFirstPlace)).click();
		Thread.sleep(5000);
		
		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th")).size();
		int posMin = 2;
		int posMax = size;
		for (int i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th[" + i + "]"))
						.getText();
				if (!text.trim().isEmpty()) {
					if (text.equals(SeleniumConstants.kTextCompetition) || text.equals(SeleniumConstants.kTextSearches)
							|| text.equals(SeleniumConstants.kTextCPC)) {
						posMin++;
					}
				}
			} catch (Exception e) {
				posMax = i - 1;
				break;
			}
		}

		int numberOfElements = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextFirstPlace)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextFirstPlace)).click();
		Thread.sleep(5000);

		for (int i = 1; i <= numberOfElements; i++) {
			if (!CheckElementForFirstPlace(mDriver, i - (100 * (i / 100)), posMin, posMax)) {
				Assert.fail("None of the SEs are on first place");
			}

			if (i % 100 == 0 && numberOfElements > 100) {
				mDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]")).click();
				Thread.sleep(5000);
			}
		}
	}

	@Test
	public void TestTop3() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		try
		{
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue("100");
		Thread.sleep(5000);
		} catch (Exception e)
		{}

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextTop3)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextTop3)).click();

		Thread.sleep(5000);
		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th")).size();
		int posMin = 2;
		int posMax = size;
		for (int i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th[" + i + "]"))
						.getText();
				if (!text.trim().isEmpty()) {
					if (text.equals(SeleniumConstants.kTextCompetition) || text.equals(SeleniumConstants.kTextSearches)
							|| text.equals(SeleniumConstants.kTextCPC)) {
						posMin++;
					}
				}
			} catch (Exception e) {
				posMax = i - 1;
				break;
			}
		}

		int numberOfElements = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextTop3)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextTop3)).click();
		Thread.sleep(5000);

		for (int i = 1; i <= numberOfElements; i++) {
			if (!CheckElementInTop(mDriver, i - (100 * (i / 100)), posMin, posMax, 3)) {
				Assert.fail("At least one row doesn't have any of the SEs in top 3");
			}

			if (i % 100 == 0 && numberOfElements > 100) {
				mDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]")).click();
				Thread.sleep(5000);
			}
		}
	}

	@Test
	public void TestTop5() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		try 
		{
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);
		} catch(Exception e)
		{
		}

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextTop5)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextTop5)).click();

		Thread.sleep(5000);
		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th")).size();
		int posMin = 2;
		int posMax = size;
		for (int i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th[" + i + "]"))
						.getText();
				if (!text.trim().isEmpty()) {
					if (text.equals(SeleniumConstants.kTextCompetition) || text.equals(SeleniumConstants.kTextSearches)
							|| text.equals(SeleniumConstants.kTextSearches)) {
						posMin++;
					}
				}
			} catch (Exception e) {
				posMax = i - 1;
				break;
			}
		}

		int numberOfElements = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextTop5)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextTop5)).click();
		Thread.sleep(5000);

		for (int i = 1; i <= numberOfElements; i++) {
			if (!CheckElementInTop(mDriver, i - (100 * (i / 100)), posMin, posMax, 5)) {
				Assert.fail("At least one row doesn't have any of the SEs in top 5");
			}

			if (i % 100 == 0 && numberOfElements > 100) {
				mDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]")).click();
				Thread.sleep(5000);
			}
		}
	}

	@Test
	public void TestTop10() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);
	  try 
    {
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);
    } catch(Exception e)
	  {
	  }

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextTop10)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextTop10)).click();

		Thread.sleep(5000);
		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th")).size();
		int posMin = 2;
		int posMax = size;
		for (int i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th[" + i + "]"))
						.getText();
				if (!text.trim().isEmpty()) {
					if (text.equals(SeleniumConstants.kTextCompetition) || text.equals(SeleniumConstants.kTextSearches)
							|| text.equals(SeleniumConstants.kTextCPC)) {
						posMin++;
					}
				}
			} catch (Exception e) {
				posMax = i - 1;
				break;
			}
		}

		int numberOfElements = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextTop10)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextTop10)).click();
		Thread.sleep(5000);

		for (int i = 1; i <= numberOfElements; i++) {
			if (!CheckElementInTop(mDriver, i - (100 * (i / 100)), posMin, posMax, 10)) {
				Assert.fail("At least one row doesn't have any of the SEs in top 10");
			}

			if (i % 100 == 0 && numberOfElements > 100) {
				mDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]")).click();
				Thread.sleep(5000);
			}
		}
	}

	@Test
	public void TestNotInTop10() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		try 
		{
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);
		} catch(Exception e)
		{}

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextNotInTop10)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextNotInTop10)).click();

		Thread.sleep(5000);
		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th")).size();
		int posMin = 2;
		int posMax = size;
		for (int i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th[" + i + "]"))
						.getText();
				if (!text.trim().isEmpty()) {
					if (text.equals(SeleniumConstants.kTextCompetition) || text.equals(SeleniumConstants.kTextSearches)
							|| text.equals(SeleniumConstants.kTextCPC)) {
						posMin++;
					}
				}
			} catch (Exception e) {
				posMax = i - 1;
				break;
			}
		}

		int numberOfElements = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextNotInTop10)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextNotInTop10)).click();
		Thread.sleep(5000);

		for (int i = 1; i <= numberOfElements; i++) {
			if (!CheckElementNotInTop(mDriver, i - (100 * (i / 100)), posMin, posMax, 10)) {
				Assert.fail("At least one row haves a SE in top 10");
			}

			if (i % 100 == 0 && numberOfElements > 100) {
				mDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]")).click();
				Thread.sleep(5000);
			}
		}

	}

	@Test
	public void TestRanked() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		try 
		{
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);
		} catch(Exception e)
		{}

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextRanked)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextRanked)).click();

		Thread.sleep(5000);
		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th")).size();
		int posMin = 2;
		int posMax = size;
		for (int i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th[" + i + "]"))
						.getText();
				if (!text.trim().isEmpty()) {
					if (text.equals(SeleniumConstants.kTextCompetition) || text.equals(SeleniumConstants.kTextSearches)
							|| text.equals(SeleniumConstants.kTextCPC)) {
						posMin++;
					}
				}
			} catch (Exception e) {
				posMax = i - 1;
				break;
			}
		}

		int numberOfElements = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextRanked)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextRanked)).click();
		Thread.sleep(5000);

		for (int i = 1; i <= numberOfElements; i++) {
			if (!CheckElementRanked(mDriver, i - (100 * (i / 100)), posMin, posMax)) {
				Assert.fail("None of the positions are ranked for keyword: " + i);
			}

			if (i % 100 == 0 && numberOfElements > 100) {
				mDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]")).click();
				Thread.sleep(5000);
			}
		}

	}

	@Test
	public void TestNotRanked() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

	  try 
    {
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);
    }catch (Exception e)
	  {}

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextNotRanked)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextNotRanked)).click();

		Thread.sleep(5000);
		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th")).size();
		int posMin = 2;
		int posMax = size;
		for (int i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th[" + i + "]"))
						.getText();
				if (!text.trim().isEmpty()) {
					if (text.equals(SeleniumConstants.kTextCompetition) || text.equals(SeleniumConstants.kTextSearches)
							|| text.equals(SeleniumConstants.kTextCPC)) {
						posMin++;
					}
				}
			} catch (Exception e) {
				posMax = i - 1;
				break;
			}
		}

		int numberOfElements = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextNotRanked)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextNotRanked)).click();
		Thread.sleep(5000);

		for (int i = 1; i <= numberOfElements; i++) {
			if (!CheckElementNotRanked(mDriver, i - (100 * (i / 100)), posMin, posMax)) {
				Assert.fail("None of the positions are ranked for keyword: " + i);
			}

			if (i % 100 == 0 && numberOfElements > 100) {
				mDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]")).click();
				Thread.sleep(5000);
			}
		}

	}

	@Test
	public void TestMovedUp() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);

		try {
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);
		} catch(Exception e)
		{}

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextMovedUp)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextMovedUp)).click();
		Thread.sleep(5000);
		
		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th")).size();
		int posMin = 2;
		int posMax = size;
		for (int i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th[" + i + "]"))
						.getText();
				if (!text.trim().isEmpty()) {
					if (text.equals(SeleniumConstants.kTextCompetition) || text.equals(SeleniumConstants.kTextSearches)
							|| text.equals(SeleniumConstants.kTextCPC)) {
						posMin++;
					}
				}
			} catch (Exception e) {
				posMax = i - 1;
				break;
			}
		}

		int numberOfElements = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextMovedUp)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextMovedUp)).click();
		Thread.sleep(5000);

		for (int i = 1; i <= numberOfElements; i++) {
			if (!CheckElementMovedUp(mDriver, i - (100 * (i / 100)), posMin, posMax)) {
				Assert.fail("None of the positions are ranked for keyword: " + i);
			}

			if (i % 100 == 0 && numberOfElements > 100) {
				mDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]")).click();
				Thread.sleep(5000);
			}
		}
	}

	@Test
	public void TestMovedDown() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		try
		{
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);
		} catch(Exception e)
		{}

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextMovedDown)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextMovedDown)).click();
		Thread.sleep(5000);
		
		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th")).size();
		int posMin = 2;
		int posMax = size;
		for (int i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th[" + i + "]"))
						.getText();
				if (!text.trim().isEmpty()) {
					if (text.equals(SeleniumConstants.kTextCompetition) || text.equals(SeleniumConstants.kTextSearches)
							|| text.equals(SeleniumConstants.kTextCPC)) {
						posMin++;
					}
				}
			} catch (Exception e) {
				posMax = i - 1;
				break;
			}
		}

		int numberOfElements = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextMovedDown)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextMovedDown)).click();
		Thread.sleep(5000);

		for (int i = 1; i <= numberOfElements; i++) {
			if (!CheckElementMovedDown(mDriver, i - (100 * (i / 100)), posMin, posMax)) {
				Assert.fail("None of the positions are ranked for keyword: " + i);
			}

			if (i % 100 == 0 && numberOfElements > 100) {
				mDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]")).click();
				Thread.sleep(5000);
			}
		}
	}

	@Test
	public void TestChanged() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + "?project_id=1");
		Thread.sleep(3000);

		try {
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);
		} catch(Exception e)
		{}

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextChanged)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextChanged)).click();

		Thread.sleep(5000);
		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th")).size();
		int posMin = 2;
		int posMax = size;
		for (int i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th[" + i + "]"))
						.getText();
				if (!text.trim().isEmpty()) {
					if (text.equals(SeleniumConstants.kTextCompetition) || text.equals(SeleniumConstants.kTextSearches)
							|| text.equals(SeleniumConstants.kTextCPC)) {
						posMin++;
					}
				}
			} catch (Exception e) {
				posMax = i - 1;
				break;
			}
		}

		int numberOfElements = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextChanged)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextChanged)).click();
		Thread.sleep(5000);

		for (int i = 1; i <= numberOfElements; i++) {
			if (!CheckElemenChanged(mDriver, i - (100 * (i / 100)), posMin, posMax)) {
				Assert.fail("None of the positions are ranked for keyword: " + i);
			}

			if (i % 100 == 0 && numberOfElements > 100) {
				mDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]")).click();
				Thread.sleep(5000);
			}
		}
	}

	@Test
	public void TestAdded() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		try {
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);
		} catch(Exception e)
		{}

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAdded)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAdded)).click();

		Thread.sleep(5000);
		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th")).size();
		int posMin = 2;
		int posMax = size;
		for (int i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th[" + i + "]"))
						.getText();
				if (!text.trim().isEmpty()) {
					if (text.equals(SeleniumConstants.kTextCompetition) || text.equals(SeleniumConstants.kTextSearches)
							|| text.equals(SeleniumConstants.kTextCPC)) {
						posMin++;
					}
				}
			} catch (Exception e) {
				posMax = i - 1;
				break;
			}
		}

		int numberOfElements = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAdded)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAdded)).click();
		Thread.sleep(5000);

		for (int i = 1; i <= numberOfElements; i++) {
			if (!CheckElemenAdded(mDriver, i - (100 * (i / 100)), posMin, posMax)) {
				Assert.fail("None of the positions are ranked for keyword: " + i);
			}

			if (i % 100 == 0 && numberOfElements > 100) {
				mDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]")).click();
				Thread.sleep(5000);
			}
		}
	}

	@Test
	public void TestDropped() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		try {
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);
		}catch(Exception e)
		{}

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextDropped)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextDropped)).click();

		Thread.sleep(5000);
		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th")).size();
		int posMin = 2;
		int posMax = size;
		for (int i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th[" + i + "]"))
						.getText();
				if (!text.trim().isEmpty()) {
					if (text.equals(SeleniumConstants.kTextCompetition) || text.equals(SeleniumConstants.kTextSearches)
							|| text.equals(SeleniumConstants.kTextCPC)) {
						posMin++;
					}
				}
			} catch (Exception e) {
				posMax = i - 1;
				break;
			}
		}

		int numberOfElements = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextDropped)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextDropped)).click();
		Thread.sleep(5000);

		for (int i = 1; i <= numberOfElements; i++) {
			if (!CheckElemenDropped(mDriver, i - (100 * (i / 100)), posMin, posMax)) {
				Assert.fail("None of the positions are ranked for keyword: " + i);
			}

			if (i % 100 == 0 && numberOfElements > 100) {
				mDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]")).click();
				Thread.sleep(5000);
			}
		}
	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		mDriver.quit();
	}

	private void StaleElementHandleByID(String elementID) {
		int count = 0;
		boolean clicked = false;
		while (count < 4 && !clicked) {
			try {
				WebElement yourSlipperyElement = mDriver.findElement(By.id(elementID));
				yourSlipperyElement.click();
				clicked = true;
			} catch (org.openqa.selenium.StaleElementReferenceException e) {
				e.toString();
				count = count + 1;
			}
		}
	}

	private boolean CheckElementForFirstPlace(WebDriver driver2, int i, int min, int max) {
		for (int j = min; j <= max; j++) {
			try {
				if (Integer.parseInt(mDriver.findElement(
						By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + j + "]/div/a")).getText()) == 1) {
					return true;
				}
			} catch (Exception e) {

			}
		}
		return false;
	}

	private boolean CheckElementInTop(WebDriver driver2, int i, int min, int max, int top) {
		for (int j = min; j <= max; j++) {
			try {
				if (Integer.parseInt(mDriver.findElement(
						By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + j + "]/div/a")).getText()) <= top) {
					return true;
				}
			} catch (Exception e) {

			}
		}
		return false;
	}

	private boolean CheckElementNotInTop(WebDriver driver2, int i, int min, int max, int top) {
		for (int j = min; j <= max; j++) {
			try {
				if (Integer.parseInt(mDriver.findElement(
						By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + j + "]/div/a")).getText()) > top) {
					return true;
				}
			} catch (Exception e) {
				return true;
			}
		}
		return false;
	}

	private boolean CheckElementRanked(WebDriver driver2, int i, int min, int max) {
		for (int j = min; j <= max; j++) {
			try {
				Integer.parseInt(mDriver.findElement(
						By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + j + "]/div/a")).getText());

				return true;
			} catch (Exception e) {
			}
		}
		return false;
	}

	private boolean CheckElementNotRanked(WebDriver driver2, int i, int min, int max) {
		for (int j = min; j <= max; j++) {
			try {
				Integer.parseInt(mDriver.findElement(
						By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + j + "]/div/a")).getText());
			} catch (Exception e) {
				return true;
			}
		}
		return false;
	}

	private boolean CheckElementMovedUp(WebDriver driver2, int i, int min, int max) {
		for (int j = min; j <= max; j++) {
			try {
				WebElement element = mDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td["
						+ j + "]/div/span/span"));

				if (element.getAttribute("class").contains("success")) {
					return true;
				}
			} catch (Exception e) {
			}
		}
		return false;
	}

	private boolean CheckElementMovedDown(WebDriver driver2, int i, int min, int max) {
		for (int j = min; j <= max; j++) {
			try {
				WebElement element = mDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td["
						+ j + "]/div/span/span"));

				if (element.getAttribute("class").contains("danger")) {
					return true;
				}
			} catch (Exception e) {
			}
		}
		return false;
	}

	private boolean CheckElemenChanged(WebDriver driver2, int i, int min, int max) {
		for (int j = min; j <= max; j++) {
			try {
				mDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + j
						+ "]/div/span/span"));
				return true;
			} catch (Exception e) {
			}
		}
		return false;
	}

	private boolean CheckElemenAdded(WebDriver driver2, int i, int min, int max) {
		for (int j = min; j <= max; j++) {
			try {
				WebElement element = mDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td["
						+ j + "]/div/span/span/i"));
				if (element.getAttribute("class").contains("icon-plus")
						|| element.getAttribute("class").contains("icon-minus")) {
					return true;
				}
			} catch (Exception e) {
			}
		}
		return false;
	}

	private boolean CheckElemenDropped(WebDriver driver2, int i, int min, int max) {
		for (int j = min; j <= max; j++) {
			try {
				WebElement element = mDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td["
						+ j + "]/div/span/span/i"));
				if (element.getAttribute("class").contains("icon-remove")) {
					return true;
				}
			} catch (Exception e) {
			}
		}
		return false;
	}
}