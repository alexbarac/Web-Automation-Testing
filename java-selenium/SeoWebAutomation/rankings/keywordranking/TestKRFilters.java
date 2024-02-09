package com.seo.selenium.ui.rankings.keywordranking;

import java.util.List;

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

public class TestKRFilters {
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
	public void TestFistPlace() throws Exception {
		mDriver.get(SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kTextAllResult)) {
			mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
			mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

			mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
			Thread.sleep(5000);
		}

		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextFirstPlace)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextFirstPlace)).click();
		Thread.sleep(5000);

		int posPosition = 0;

		int thSizes = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i = 0;
		for (i = 1; i < thSizes + 1; i++) {
			if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
					.equals(SeleniumConstants.kTextPosition)) {
				break;
			}
		}
		posPosition = i + 1;
		List<String> positions = mTest.GetAllPositionsForKR(mDriver, posPosition);

		for (i = 0; i < positions.size(); i++) {
			try {
				Integer pos = Integer.parseInt(positions.get(i).split("\n")[0]);
				if (pos != 1) {
					Assert.fail("Found keyword not in first place for first place filter");
				}
			} catch (Exception e) {
				Assert.fail("Found keyword not ranked for first place filter");
			}
		}
	}

	@Test
	public void TestTop3() throws Exception {
		mDriver.get(SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kTextAllResult)) {
			mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
			mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

			mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
			Thread.sleep(5000);
		}

		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextTop3)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextTop3)).click();
		Thread.sleep(5000);

		int posPosition = 0;

		int thSizes = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i = 0;
		for (i = 1; i < thSizes + 1; i++) {
			if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
					.equals(SeleniumConstants.kTextPosition)) {
				break;
			}
		}
		posPosition = i + 1;
		List<String> positions = mTest.GetAllPositionsForKR(mDriver, posPosition);

		for (i = 0; i < positions.size(); i++) {
			try {
				Integer pos = Integer.parseInt(positions.get(i).split("\n")[0]);
				if (pos > 3) {
					Assert.fail("Found keyword not in top 3 for Top 3 filter");
				}
			} catch (Exception e) {
				Assert.fail("Found keyword not ranked for Top 3 filter");
			}
		}
	}

	@Test
	public void TestTop5() throws Exception {
		mDriver.get(SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kTextAllResult)) {
			mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
			mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

			mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
			Thread.sleep(5000);
		}

		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextTop5)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextTop5)).click();
		Thread.sleep(5000);

		int posPosition = 0;
		int thSizes = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i = 0;
		for (i = 1; i < thSizes + 1; i++) {
			if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
					.equals(SeleniumConstants.kTextPosition)) {
				break;
			}
		}
		posPosition = i + 1;
		List<String> positions = mTest.GetAllPositionsForKR(mDriver, posPosition);

		for (i = 0; i < positions.size(); i++) {
			try {
				Integer pos = Integer.parseInt(positions.get(i).split("\n")[0]);
				if (pos > 5) {
					Assert.fail("Found keyword not in top 5 for Top 5 filter");
				}
			} catch (Exception e) {
				Assert.fail("Found keyword not ranked for Top 5 filter");
			}
		}
	}

	@Test
	public void TestTop10() throws Exception {
		mDriver.get(SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kTextAllResult)) {
			mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
			mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

			mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
			Thread.sleep(5000);
		}

		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextTop10)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextTop10)).click();

		Thread.sleep(5000);

		int posPosition = 0;
		int thSizes = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i = 0;
		for (i = 1; i < thSizes + 1; i++) {
			if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
					.equals(SeleniumConstants.kTextPosition)) {
				break;
			}
		}
		posPosition = i + 1;
		List<String> positions = mTest.GetAllPositionsForKR(mDriver, posPosition);

		for (i = 0; i < positions.size(); i++) {
			try {
				Integer pos = Integer.parseInt(positions.get(i).split("\n")[0]);
				if (pos > 10) {
					Assert.fail("Found keyword not in top 10 for Top 10 filter");
				}
			} catch (Exception e) {
				Assert.fail("Found keyword not ranked for Top 10 filter");
			}
		}
	}


	@Test
	public void TestNotInTop10() throws Exception {
		mDriver.get(SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kTextAllResult)) {
			mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
			mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

			mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
			Thread.sleep(5000);
		}

		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextNotInTop10)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextNotInTop10)).click();
		Thread.sleep(5000);

		int posPosition = 0;
		int thSizes = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i = 0;
		for (i = 1; i < thSizes + 1; i++) {
			if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
					.equals(SeleniumConstants.kTextPosition)) {
				break;
			}
		}
		posPosition = i + 1;
		List<String> positions = mTest.GetAllPositionsForKR(mDriver, posPosition);

		for (i = 0; i < positions.size(); i++) {
			try {
				Integer pos = Integer.parseInt(positions.get(i).split("\n")[0]);
				if (pos < 10) {
					Assert.fail("Found keyword top 10 for Not in Top 10 filter");
				}
			} catch (Exception e) {
			}
		}
	}

	@Test
	public void TestNotRanked() throws Exception {
		mDriver.get(SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kLinkTextAllResults)) {
			mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
			mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

			mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
			Thread.sleep(5000);
		}

		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextNotRanked)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextNotRanked)).click();
		Thread.sleep(5000);

		int posPosition = 0;
		int thSizes = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i = 0;
		for (i = 1; i < thSizes + 1; i++) {
			if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
					.equals(SeleniumConstants.kTextPosition)) {
				break;
			}
		}
		posPosition = i + 1;
		List<String> positions = mTest.GetAllPositionsForKR(mDriver, posPosition);
		for (i = 0; i < positions.size(); i++) {
			try {
				Integer pos = Integer.parseInt(positions.get(i).split("\n")[0]);

				if (!mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]/span/span"))
						.getAttribute("class").contains("label-danger")) {
					Assert.fail("Found ranked keyword Not ranked filter");
				}
			} catch (Exception e) {
			}
		}
	}

	@Test
	public void TestRanked() throws Exception {
		mDriver.get(SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kLinkTextAllResults)) {
			mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
			mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

			mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
			Thread.sleep(5000);
		}

		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextRanked)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextRanked)).click();
		Thread.sleep(5000);

		int posPosition = 0;
		int thSizes = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i = 0;
		for (i = 1; i < thSizes + 1; i++) {
			if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
					.equals(SeleniumConstants.kTextPosition)) {
				break;
			}
		}
		posPosition = i + 1;
		List<String> positions = mTest.GetAllPositionsForKR(mDriver, posPosition);

		for (i = 0; i < positions.size(); i++) {
			try {
				Integer pos = Integer.parseInt(positions.get(i).split("\n")[0]);
			} catch (Exception e) {
				Assert.fail("Found ranked keyword Not ranked filter");
			}
		}
	}

	@Test
	public void TestMovedUp() throws Exception {
		mDriver.get(SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);

		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kLinkTextAllResults)) {
			try {
			mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
			mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

			mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
			Thread.sleep(5000);
			} catch(Exception e) {}
		}

		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kLinkTextAllResults)) {
			mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
			mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

			mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
			Thread.sleep(5000);
		}

		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextMovedUp)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextMovedUp)).click();
		Thread.sleep(5000);

		int posPosition = 0;
		int thSizes = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i = 0;
		for (i = 1; i < thSizes + 1; i++) {
			if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
					.equals(SeleniumConstants.kTextPosition)) {
				break;
			}
		}
		posPosition = i + 1;
		List<Integer> positions = mTest.GetAllChanged(mDriver, posPosition);

		for (i = 0; i < positions.size(); i++) {
			try {
				Integer pos = positions.get(i);
				if (!mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]/span/span"))
						.getAttribute("class").contains("label-danger")) {
					Assert.fail("Found ranked keyword Not ranked filter");
				}
			} catch (Exception e) {
			}
		}
	}

	@Test
	public void TestMovedDown() throws Exception {
		mDriver.get(SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);

		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kLinkTextAllResults)) {
			mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
			mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

			mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
			Thread.sleep(5000);
		}

		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextMovedDown)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextMovedDown)).click();

		Thread.sleep(5000);

		int posPosition = 0;

		int thSizes = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i = 0;
		for (i = 1; i < thSizes + 1; i++) {
			if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
					.equals(SeleniumConstants.kTextPosition)) {
				break;
			}
		}
		posPosition = i + 1;
		List<Integer> positions = mTest.GetAllChanged(mDriver, posPosition);

		for (i = 0; i < positions.size(); i++) {
			try {
				Integer pos = positions.get(i);

				if (!mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]/span/span"))
						.getAttribute("class").contains("danger")) {
					Assert.fail("Found ranked keyword Not ranked filter");
				}
			} catch (Exception e) {
			}
		}
	}

	@Test
	public void TestChanged() throws Exception {
		mDriver.get(SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);

		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kLinkTextAllResults)) {
			mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
			mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

			mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
			Thread.sleep(5000);
		}

		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextChanged)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextChanged)).click();

		Thread.sleep(5000);

		int posPosition = 0;

		int thSizes = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i = 0;
		for (i = 1; i < thSizes + 1; i++) {
			if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
					.equals(SeleniumConstants.kTextPosition)) {
				break;
			}
		}
		posPosition = i + 1;
		List<Integer> positions = mTest.GetAllChanged(mDriver, posPosition);

		for (i = 0; i < positions.size(); i++) {
			try {
				Integer pos = positions.get(i);

				if (!(mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]/span/span"))
						.getAttribute("class").contains("label-danger") || mDriver
						.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]/span/span"))
						.getAttribute("class").contains("success"))) {
					Assert.fail("Found ranked keyword Not ranked filter");
				}
			} catch (Exception e) {
			}
		}
	}

	@Test
	public void TestAdded() throws Exception {
		mDriver.get(SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);

		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kLinkTextAllResults)) {
			mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
			mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

			mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
			Thread.sleep(5000);
		}

		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAdded)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAdded)).click();

		Thread.sleep(5000);

		int posPosition = 0;

		int thSizes = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i = 0;
		for (i = 1; i < thSizes + 1; i++) {
			if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
					.equals(SeleniumConstants.kTextPosition)) {
				break;
			}
		}
		posPosition = i + 1;
		List<Integer> positions = mTest.GetAllChanged(mDriver, posPosition);

		for (i = 0; i < positions.size(); i++) {
			try {
				Integer pos = positions.get(i);

				if (!mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]/span/span"))
						.getAttribute("class").contains("success")) {
					Assert.fail("Found ranked keyword Not ranked filter");
				}
			} catch (Exception e) {
			}
		}
	}
	
	@Test
	public void TestDropped() throws Exception {
		mDriver.get(SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);

		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kLinkTextAllResults)) {
			mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
			mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

			mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
			Thread.sleep(5000);
		}

		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
		mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextDropped)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextDropped)).click();

		Thread.sleep(5000);

		int posPosition = 0;

		int thSizes = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i = 0;
		for (i = 1; i < thSizes + 1; i++) {
			if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
					.equals(SeleniumConstants.kTextPosition)) {
				break;
			}
		}
		posPosition = i + 1;
		List<Integer> positions = mTest.GetAllChanged(mDriver, posPosition);

		for (i = 0; i < positions.size(); i++) {
			try {
				Integer pos = positions.get(i);

				if (!mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]/span/span"))
						.getAttribute("class").contains("label-danger")) {
					Assert.fail("Found ranked keyword Not ranked filter");
				}
			} catch (Exception e) {
			}
		}
	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kLinkTextAllResults)) {
			mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
			mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

			mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
			mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
			Thread.sleep(5000);
		}
		mDriver.quit();
	}

}
