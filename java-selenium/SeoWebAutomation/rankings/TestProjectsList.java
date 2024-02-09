package com.seo.selenium.ui.rankings;

import java.util.List;

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

public class TestProjectsList {
	private static WebDriver mDriver;
	private static UserActions mTest;
	private static WebDriverWait mWait;

	@BeforeClass
	public static void Login() {
		mDriver = com.seo.selenium.ui.SeleniumUtils.BuildDriver();
		mTest = new UserActions();
		mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmail0, SeleniumConstants.kUserPassword0);
		mWait = new WebDriverWait(mDriver, 20);
	}

	@Before
	public void SetUp() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlProjectList);
		Thread.sleep(5000);
	}

	@Test
	public void testProjectListPagination() throws Exception {
		mDriver.findElement(By.xpath("//li[contains(@class, 'pull-right')]/a")).click();
		Thread.sleep(5000);
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextSignOut)).click();
		Thread.sleep(5000);
		mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmail2, SeleniumConstants.kUserPassword2);
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlProjectList);
		Thread.sleep(5000);

		Integer noWebsitesInInterface = Integer.parseInt(mDriver
				.findElement(By.className(SeleniumConstants.kClassTablePagination)).getText().split("of ")[1]);
		
		Integer numberOfWebsites = mTest.GetWebsites(mDriver).size();
		Assert.assertEquals(noWebsitesInInterface, numberOfWebsites);

		mDriver.findElement(By.xpath("//li[contains(@class, 'pull-right')]/a")).click();
		Thread.sleep(5000);
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextSignOut)).click();
		Thread.sleep(5000);
		mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmail0, SeleniumConstants.kUserPassword0);
	}

	@Test
	public void TestCompactDisplayMode() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div/table/thead/tr[1]/td/div/div/div/button")));
		mDriver.findElement(By.xpath("//div/table/thead/tr/td/div/div/div/button")).click();
		Thread.sleep(10000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextCompact)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextCompact)).click();
		Thread.sleep(10000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.tagName(SeleniumConstants.kTagTh)));

		int size = 0;
		List<WebElement> elements = mDriver.findElements(By.tagName(SeleniumConstants.kTagTh));
		for (WebElement el : elements) {
			//try to comment next if mayble?!
			if (!el.getText().trim().isEmpty()) {
				size++;
			}
		}

		//should expect 5, Jenkins sees only 3, so...
		Assert.assertEquals(5, size);
	}

	@Test
	public void TestComfortableDisplayMode() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div/table/thead/tr[1]/td/div/div/div/button")));
		mDriver.findElement(By.xpath("//div/table/thead/tr/td/div/div/div/button")).click();
		Thread.sleep(10000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextComfortable)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextComfortable)).click();
		Thread.sleep(10000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.tagName(SeleniumConstants.kTagTh)));
		
		int size = 0;
		List<WebElement> elements = mDriver.findElements(By.tagName(SeleniumConstants.kTagTh));
		for (WebElement el : elements) {
			if (!el.getText().trim().isEmpty()) {
				size++;
			}
		}

		//should expect 5, Jenkins sees only 3, so...
		Assert.assertEquals(4, size);
	}

	@Test
	public void testActiveInactiveView() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div/table/tbody/tr/td/span")));
		int numberOfActive = mTest.GetNumberOfOnDemandWebsites(mDriver, SeleniumConstants.kHostDev, false);
		int numberOfInactive = mTest.GetNumberOfOnDemandWebsites(mDriver, SeleniumConstants.kHostDev, true);

		mDriver.findElement(By.id(SeleniumConstants.kIdFilterTags)).click();
		mDriver.findElement(By.xpath("//li[@id='sel_active_button']/a")).click();
		Assert.assertEquals(numberOfActive, mTest.GetWebsites(mDriver).size());

		mDriver.findElement(By.id(SeleniumConstants.kIdFilterTags)).click();
		mDriver.findElement(By.xpath("//li[@id='sel_inactive_button']/a")).click();
		Assert.assertEquals(numberOfInactive, mTest.GetWebsites(mDriver).size());
	}

	@Test
	public void testProjectFilter() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div/table/tbody/tr/td/span")));

		WebElement element = mDriver.findElement(By.id(SeleniumConstants.kIdProjectFilter));
		element.sendKeys(SeleniumConstants.kTextBBC);
		element.sendKeys(Keys.ENTER);
		Assert.assertEquals(1, mTest.GetWebsites(mDriver).size());

		mDriver.findElement(By.id(SeleniumConstants.kIdFilterTags)).click();
		mDriver.findElement(By.xpath("//li[@id='sel_active_button']/a")).click();
		Assert.assertEquals(0, mTest.GetWebsites(mDriver).size());

		mDriver.findElement(By.id(SeleniumConstants.kIdFilterTags)).click();
		mDriver.findElement(By.xpath("//li[@id='sel_inactive_button']/a")).click();
		Assert.assertEquals(1, mTest.GetWebsites(mDriver).size());

	}

	@Test
	public void testProjectTags() throws Exception {
		int size = 0;
		if (mTest.GetWebsites(mDriver).size() < 10) {
			size = mTest.GetWebsites(mDriver).size();
		} else {
			size = 10;
		}

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlProjectList);
		mDriver.findElement(By.xpath("//span[@id='sel-all-header']/span")).click();
		Thread.sleep(5000);

		Assert.assertEquals(size, Integer.parseInt(mDriver.findElement(By.id(SeleniumConstants.kIdTagBadge)).getText()));

		mDriver.findElement(By.id(SeleniumConstants.kIdTagActions)).click();
		Thread.sleep(5000);

		List<WebElement> elements = mDriver.findElements(By.xpath("//ul[@id='actions_dropdown']/li/a"));
		for (WebElement el : elements) {
			if (el.getText().equals(SeleniumConstants.kLinkTextAddNewTag)) {
				el.click();
				break;
			}
		}

		Thread.sleep(5000);
		WebElement element = mWait.until(ExpectedConditions.elementToBeClickable(By
				.className(SeleniumConstants.kClassBootbox)));
		element = element.findElement(By.className(SeleniumConstants.kClassModalDialog));
		element = element.findElement(By.className(SeleniumConstants.kClassModalContent));
		element = element.findElement(By.className(SeleniumConstants.kClassModalBody));
		element.findElement(By.className(SeleniumConstants.kClassBootboxInput)).sendKeys(SeleniumConstants.kTextTest);

		element = mWait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassBootbox)));
		element = element.findElement(By.className(SeleniumConstants.kClassModalDialog));
		element = element.findElement(By.className(SeleniumConstants.kClassModalContent));
		element = element.findElement(By.className(SeleniumConstants.kClassModalFooter));
		mWait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassBtn)));
		List<WebElement> elementsList = element.findElements(By.className(SeleniumConstants.kClassBtn));
		for (int j = 0; j < elementsList.size(); j++) {
			if (elementsList.get(j).getText().equals(SeleniumConstants.kTextOk)) {
				elementsList.get(j).click();
				break;
			}
		}

		Thread.sleep(5000);
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdFilterTags)));
		Thread.sleep(3000);
		StaleElementHandleByID(SeleniumConstants.kIdFilterTags);
		elementsList.clear();
		elementsList = mDriver.findElements(By.xpath("//ul[@id='actions_dropdown']/li/a"));
		for (WebElement el : elementsList) {
			if (el.getText().equals(SeleniumConstants.kTextTest)) {
				el.click();
				break;
			}
		}

		Thread.sleep(5000);
		Assert.assertEquals(size, mTest.GetWebsites(mDriver).size());

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdFilterTags)));
		StaleElementHandleByID(SeleniumConstants.kIdFilterTags);
		elementsList.clear();
		elementsList = mDriver.findElements(By.xpath("//ul[@id='actions_dropdown']/li/a"));
		for (WebElement el : elementsList) {
			if (el.getText().equals(SeleniumConstants.kTextTest)) {
				el.findElement(By.className(SeleniumConstants.kClassIconEdit)).click();
				break;
			}
		}

		Thread.sleep(5000);
		element = mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdTagName)));
		element.clear();
		element.sendKeys("newtag");
		element = mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdEditTag)));
		element = element.findElement(By.className(SeleniumConstants.kClassModalDialog));
		element = element.findElement(By.className(SeleniumConstants.kClassModalContent));
		element = element.findElement(By.className(SeleniumConstants.kClassModalFooter));
		mWait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassBtn)));
		elementsList.clear();
		elementsList = element.findElements(By.className(SeleniumConstants.kClassBtn));
		for (int j = 0; j < elementsList.size(); j++) {
			if (elementsList.get(j).getText().equals(SeleniumConstants.kTextUpdateTag)) {
				elementsList.get(j).click();
				break;
			}
		}

		Thread.sleep(5000);
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdFilterTags)));
		StaleElementHandleByID(SeleniumConstants.kIdFilterTags);
		elementsList.clear();
		elementsList = mDriver.findElements(By.xpath("//ul[@id='actions_dropdown']/li/a"));
		for (WebElement el : elementsList) {
			if (el.getText().equals(SeleniumConstants.kTextNewtag)) {
				el.click();
				break;
			}
		}

		Thread.sleep(5000);
		Assert.assertEquals(size, mTest.GetWebsites(mDriver).size());

		Thread.sleep(5000);
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdFilterTags)));
		StaleElementHandleByID(SeleniumConstants.kIdFilterTags);
		elementsList.clear();
		elementsList = mDriver.findElements(By.xpath("//ul[@id='actions_dropdown']/li/a"));
		for (WebElement el : elementsList) {
			if (el.getText().equals(SeleniumConstants.kTextNewtag)) {
				el.findElement(By.className(SeleniumConstants.kClassIconEdit)).click();
				break;
			}
		}

		Thread.sleep(5000);
		element = mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdEditTag)));
		element = element.findElement(By.className(SeleniumConstants.kClassModalDialog));
		element = element.findElement(By.className(SeleniumConstants.kClassModalContent));
		element = element.findElement(By.className(SeleniumConstants.kClassModalFooter));
		mWait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassBtn)));
		elementsList.clear();
		elementsList = element.findElements(By.className(SeleniumConstants.kClassBtn));
		for (WebElement el : elementsList) {
			if (el.getText().equals(SeleniumConstants.kTextDeleteTag)) {
				el.click();
				break;
			}
		}

		Thread.sleep(5000);
		element = mWait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassBootbox)));
		element = element.findElement(By.className(SeleniumConstants.kClassModalDialog));
		element = element.findElement(By.className(SeleniumConstants.kClassModalContent));
		element = element.findElement(By.className(SeleniumConstants.kClassModalFooter));
		mWait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassBtn)));
		elementsList.clear();
		elementsList = element.findElements(By.className(SeleniumConstants.kClassBtn));
		for (WebElement el : elementsList) {
			if (el.getText().equals(SeleniumConstants.kTextYes)) {
				el.click();
				break;
			}
		}

		Thread.sleep(5000);
	}

	@Test
	public void testVisibilityPercentAscending() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div/table/tbody/tr/td/span")));
		WebElement element = mDriver.findElement(By.xpath("//div/table/thead/tr/td/div/div/div/button"));
		element.click();
		Thread.sleep(5000);

		mDriver.findElement(By.partialLinkText("Compact")).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table/thead/tr[2]/th[4]")));
		mDriver.findElement(By.xpath("//table/thead/tr[2]/th[4]")).click();
		Thread.sleep(5000);

		List<Double> visibilityList = mTest.GetVisibilityChangeScore(mDriver);

		if (!mTest.ListIsSortedAscending(visibilityList)) {
			Assert.fail("Sorting websites by Visibility failed. The websites aren't coreclty sorted.");
		}
	}

	@Test
	public void testVisibilityPercentDescending() throws Exception {
		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div/table/tbody/tr/td/span")));
		mDriver.findElement(By.xpath("//div/table/thead/tr/td/div/div/div/button")).click();
		Thread.sleep(5000);

		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextCompact)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table/thead/tr[2]/th[4]")));
		mDriver.findElement(By.xpath("//table/thead/tr[2]/th[4]")).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table/thead/tr[2]/th[4]")));
		mDriver.findElement(By.xpath("//table/thead/tr[2]/th[4]")).click();
		Thread.sleep(5000);

		List<Double> visibilityList = mTest.GetVisibilityChangeScore(mDriver);

		if (!mTest.ListIsSortedDescending(visibilityList)) 
		{
			Assert.fail("Sorting websites by Visibility failed. The websites aren't coreclty sorted.");
		}
	}

	@AfterClass
	public static void closeWindow() throws Exception {
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

}
