package com.seo.selenium.ui.rankings.overview;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.selenium.ui.SeleniumConstants;
import com.seo.selenium.ui.UserActions;
import com.seo.utils.date.JulianDay;

public class TestOverviewFullProject {
	private static WebDriver mDriver;
	private static UserActions mTest;
	private static WebDriverWait mWait;

	@Before
	public void Login() {
		mDriver = com.seo.selenium.ui.SeleniumUtils.BuildDriver();
		mTest = new UserActions();
		mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmail2, SeleniumConstants.kUserPassword2);
		mWait = new WebDriverWait(mDriver, 30);
	}

	@Test
	public void TestOverview() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlOverview + SeleniumConstants.kProjectId115);
		Thread.sleep(3000);

		// Click 'All data'
		mWait.until(ExpectedConditions.elementToBeClickable(mDriver.findElement(By
				.linkText(SeleniumConstants.kLinkTextAll))));
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextAll)).click();
		Thread.sleep(3000);

		// Data found for updates
		mWait.until(ExpectedConditions.elementToBeClickable(mDriver.findElement(By.xpath("//div[@id='chartDiv']"))));
		if (mDriver.findElement(By.xpath("//div[@id='chartDiv']")).getText()
				.contains(SeleniumConstants.kTextNoDataToDisplay)) {
			Assert.fail("No data found in chart for project that should have data.");
		}

		// for all
		JavascriptExecutor js = (JavascriptExecutor) mDriver;
		String dateAll = (String) js.executeScript("return chartStartDate");
		String yearAll = dateAll.split("-")[0];
		String monthAll = dateAll.split("-")[1];
		String dayAll = dateAll.split("-")[2].split("T")[0];
		JulianDay julianAll = new JulianDay(Integer.parseInt(yearAll), Integer.parseInt(monthAll),
				Integer.parseInt(dayAll));

		// Click 'Last 6 months'
		mWait.until(ExpectedConditions.elementToBeClickable(mDriver.findElement(By
				.linkText(SeleniumConstants.kLinkText6m))));
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkText6m)).click();
		Thread.sleep(3000);

		if (!mDriver.findElement(By.xpath("//div[@id='chartDiv']")).getText()
				.equals("No data to display. There are no updates available in the selected chart range")) {
			// for last 6 months
			String date6M = (String) js.executeScript("return chartStartDate");
			String year6M = date6M.split("-")[0];
			String month6M = date6M.split("-")[1];
			String day6M = date6M.split("-")[2].split("T")[0];
			JulianDay julian6M = new JulianDay(Integer.parseInt(year6M), Integer.parseInt(month6M),
					Integer.parseInt(day6M));

			// Compare all and last 6 months
			// 0 because project is not older than 6 months
			Assert.assertEquals(true, julianAll.GetJulianDay() <= julian6M.GetJulianDay());

			// Click 'Last 30 days'
			mWait.until(ExpectedConditions.elementToBeClickable(mDriver.findElement(By
					.linkText(SeleniumConstants.kLinkText1M))));
			mDriver.findElement(By.linkText(SeleniumConstants.kLinkText1M)).click();
			Thread.sleep(3000);

			if (!mDriver.findElement(By.xpath("//div[@id='chartDiv']")).getText()
					.equals("No data to display. There are no updates available in the selected chart range")) {

				// for last month
				String date1M = (String) js.executeScript(SeleniumConstants.kJSCommand);
				String year1M = date1M.split("-")[0];
				String month1M = date1M.split("-")[1];
				String day1M = date1M.split("-")[2].split("T")[0];
				JulianDay julian1M = new JulianDay(Integer.parseInt(year1M), Integer.parseInt(month1M),
						Integer.parseInt(day1M));

				// Compare last 6 months and last month
				Assert.assertEquals(true, julian6M.GetJulianDay() <= julian1M.GetJulianDay());

				// Click 'Last 7 days'
				mWait.until(ExpectedConditions.elementToBeClickable(mDriver.findElement(By
						.linkText(SeleniumConstants.kLinkText7D))));
				mDriver.findElement(By.linkText(SeleniumConstants.kLinkText7D)).click();
				Thread.sleep(3000);
				if (!mDriver.findElement(By.xpath("//div[@id='chartDiv']")).getText()
						.equals("No data to display. There are no updates available in the selected chart range")) {

					// for last 7 days
					String date7D = (String) js.executeScript(SeleniumConstants.kJSCommand);
					String year7D = date7D.split("-")[0];
					String month7D = date7D.split("-")[1];
					String day7D = date7D.split("-")[2].split("T")[0];
					JulianDay julian7D = new JulianDay(Integer.parseInt(year7D), Integer.parseInt(month7D),
							Integer.parseInt(day7D));

					// Compare last month and last 7 days
					Assert.assertEquals(true, julian1M.GetJulianDay() <= julian7D.GetJulianDay());
				}
			}
		}

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlOverviewAnalytics + SeleniumConstants.kProjectId115);
		// Data found for Analytics
		boolean hasAnalytics = false;
		try {
			mWait.until(ExpectedConditions.visibilityOf(mDriver.findElement(By
					.xpath("//div[contains(@class, 'dummy-data__action')]/h3"))));
			Assert.assertEquals(SeleniumConstants.kTextGoogleAnalyticsData,
					mDriver.findElement(By.xpath("//div[contains(@class, 'dummy-data__action')]/h3")).getText());
			Assert.assertEquals(SeleniumConstants.kTextConnectGoogleAnalytics,
					mDriver.findElement(By.xpath("//div[contains(@class, 'dummy-data__action')]/a")).getText());
			Assert.assertEquals(
					SeleniumConstants.kTextVideoWrapperClass,
					mDriver.findElement(By.xpath("//div[contains(@class, 'dummy-data__action')]/div")).getAttribute(
							"class"));
		} catch (Exception e) {
			hasAnalytics = true;
		}

		if (hasAnalytics == false) {
			Assert.fail("No analytics data found for project that should have analyitics data");
		}

	}

	@After
	public void CloseWindow() throws Exception {
		mDriver.quit();
	}
}