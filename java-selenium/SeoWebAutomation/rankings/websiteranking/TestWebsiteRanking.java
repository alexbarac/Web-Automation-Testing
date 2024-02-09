package com.seo.selenium.ui.rankings.websiteranking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
import com.thoughtworks.selenium.Selenium;

public class TestWebsiteRanking {
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
	public void TestWebsiteRankingCompetitors() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlWebsiteRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		List<String> rankingCompetitors = new ArrayList<String>();
		List<WebElement> elements = mDriver.findElements(By.xpath("//table/tbody/tr/td[2]"));
		int size = elements.size();

		for (int i = 0; i < size; i++) {
			if (!elements.get(i).getText().trim().isEmpty()) {
				rankingCompetitors.add(elements.get(i).getText().split(" ")[0]);
			}
		}

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlCompetitorsSettings + SeleniumConstants.kParameterActionManagerActiveCompetitors);
		Thread.sleep(3000);

		List<String> competitors = new ArrayList<String>();
		elements.clear();
		elements = mDriver.findElements(By.xpath("//table/tbody/tr/td[2]"));
		size = elements.size();
		for (int i = 0; i < size; i++) {
			if (!elements.get(i).getText().trim().isEmpty()) {
				competitors.add(elements.get(i).getText());
			}
		}

		Assert.assertEquals(rankingCompetitors.size(), competitors.size() + 1);
		Assert.assertEquals(rankingCompetitors.containsAll(competitors), true);
	}

	@Test
	public void TestBestPositionView() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlWebsiteRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		int numberOfExpanders = mDriver.findElements(By.className(SeleniumConstants.kClassExpander)).size();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdViewDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdViewDisplay)).click();
		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextBestPosition)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextBestPosition)).click();

		Thread.sleep(5000);
		int numberOfExpandersInBestPosition = mDriver.findElements(By.className(SeleniumConstants.kClassExpander))
				.size();
		List<WebElement> elements = mDriver.findElements(By.className(SeleniumConstants.kClassExpander));
		for (WebElement el : elements) {
			if (el.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kTextHidden)) {
				numberOfExpandersInBestPosition--;
			}
		}
		if (numberOfExpandersInBestPosition > 0 || numberOfExpanders < numberOfExpandersInBestPosition) {
			Assert.fail("There shouldn't be any expanders in best position");
		}
	}

	@Test
	public void TestAddingRemobingSE() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlWebsiteRankings + SeleniumConstants.kProjectId1);
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

		Thread.sleep(5000);
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

	}

	@Test
	public void TestTopSites() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlWebsiteRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSearchKeywordToggler)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSearchKeywordToggler)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By
				.partialLinkText(SeleniumConstants.kLinkTextAtomicTangerinePhotography)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAtomicTangerinePhotography)).click();
		Thread.sleep(5000);

		List<WebElement> elementList = mDriver.findElements(By.className(SeleniumConstants.kClassIconChevronDown));
		for (WebElement el : elementList) {
			if (el.isDisplayed()) {
				el.click();
			}
		}

		Map<Integer, String> sortedMap = GetWebsitesRankings();

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdTopSites)));
		mDriver.findElement(By.id(SeleniumConstants.kIdTopSites)).click();

		Thread.sleep(5000);

		int index = 0;
		for (Map.Entry<Integer, String> entry : sortedMap.entrySet()) {
			index++;
			Integer key = entry.getKey();
			String value = entry.getValue();

			Assert.assertEquals(
					mDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + index + "]/td[2]/a/b"))
							.getText(), value);
		}
	}

	@Test
	public void TestChangingKeyword() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlWebsiteRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSearchKeywordToggler)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSearchKeywordToggler)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By
				.partialLinkText(SeleniumConstants.kLinkTextAtomicTangerinePhotography)));
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextTangerine)).click();
		Thread.sleep(5000);

		Assert.assertEquals(mDriver.findElement(By.id(SeleniumConstants.kIdSelKwDisplay)).getText(),
				SeleniumConstants.kLinkTextTangerine);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSearchKeywordToggler)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSearchKeywordToggler)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By
				.partialLinkText(SeleniumConstants.kLinkTextAtomicTangerinePhotography)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAtomicTangerinePhotography)).click();
		Thread.sleep(5000);

		Assert.assertEquals(mDriver.findElement(By.id(SeleniumConstants.kIdSelKwDisplay)).getText(),
				SeleniumConstants.kLinkTextAtomicTangerinePhotography);
	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		 mDriver.quit();
	}

	private Map<Integer, String> GetWebsitesRankings() {
		Map<Integer, String> sortedMap = new TreeMap<Integer, String>();
		List<WebElement> elementList = mDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr"));
		for (int i = 1; i <= elementList.size(); i++) {
			try {
				String website = mDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[2]"))
						.getText();

				int position = Integer.parseInt(mDriver.findElement(
						By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/div/span")).getText());

				if (website.trim().isEmpty()) {
					return sortedMap;
				}

				if (website.contains(" ")) {
					website = website.split(" ")[0];
				}
				sortedMap.put(position, website);
			} catch (Exception e) {
				return sortedMap;
			}
		}
		return sortedMap;
	}
}
