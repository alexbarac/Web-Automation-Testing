package com.seo.selenium.ui.rankings.secomparison;

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

public class TestSECGroups {
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
	public void TestGroups() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);

	  if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kTextAllResult)) {
      mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
      mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

      mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
      mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
      Thread.sleep(5000);
    }
	  
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordGroupName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdKeywordGroupName)).click();

		int expectedNumberOfKeywords = Integer.parseInt(mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextOther)).getText()
				.split("  ")[1].replace(")", "").replace("(", ""));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextOther)).click();
		Thread.sleep(5000);

		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th")).size();
		int posMin = 2;
		int posMax = size;
		for (int i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th[" + i + "]"))
						.getText();
				if (!text.trim().isEmpty()) {
					if (text.equals(SeleniumConstants.kTextCompetition) || text.equals(SeleniumConstants.kTextSearches) || text.equals(SeleniumConstants.kTextCPC)) {
						posMin++;
					}
				}
			} catch (Exception e) {
				posMax = i - 1;
				break;
			}
		}
		int actualNumberOfElements = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();
		Assert.assertEquals(actualNumberOfElements, expectedNumberOfKeywords);
	}

	@Test
	public void TestBranded() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);
		
		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kTextAllResult)) {
      mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
      mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

      mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
      mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
      Thread.sleep(5000);
    }
    

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordGroupName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdKeywordGroupName)).click();

		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextBranded)).click();
		Thread.sleep(5000);

		Thread.sleep(5000);
		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th")).size();
		int posMin = 2;
		int posMax = size;
		for (int i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th[" + i + "]"))
						.getText();
				if (!text.trim().isEmpty()) {
					if (text.equals(SeleniumConstants.kTextCompetition) || text.equals(SeleniumConstants.kTextSearches) || text.equals(SeleniumConstants.kTextCPC)) {
						posMin++;
					}
				}
			} catch (Exception e) {
				posMax = i - 1;
				break;
			}
		}
		int SECNoOfKeywords = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordSettings);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextBrandedAndNonBranded)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextBrandedAndNonBranded)).click();
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextBranded)).click();

		int settingNoKeywords = mTest.GetKeywords(mDriver).size();

		Assert.assertEquals(SECNoOfKeywords, settingNoKeywords);
	}

	@Test
	public void TestNonBranded() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);
		
		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kTextAllResult)) {
      mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
      mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

      mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
      mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
      Thread.sleep(5000);
    }
    

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordGroupName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdKeywordGroupName)).click();

		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextNonBranded)).click();
		Thread.sleep(5000);

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
		int SECNoOfKeywords = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordSettings);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By
				.partialLinkText(SeleniumConstants.kLinkTextBrandedAndNonBranded)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextBrandedAndNonBranded)).click();
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextNonBranded)).click();

		int settingNoKeywords = mTest.GetKeywords(mDriver).size();
		Assert.assertEquals(SECNoOfKeywords, settingNoKeywords);
	}

	@Test
	public void TestGroupNonBranded() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);

		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kTextAllResult)) {
      mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
      mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

      mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
      mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
      Thread.sleep(5000);
    }
    
		
		Thread.sleep(5000);
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordGroupName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdKeywordGroupName)).click();
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextNonBranded)).click();
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordGroupName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdKeywordGroupName)).click();
		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextOther)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextOther)).click();
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
		int SECNoOfKeywords = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordSettings);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdFilterActions)));
		mDriver.findElement(By.id(SeleniumConstants.kIdFilterActions)).click();
		Thread.sleep(1000);

		size = mDriver.findElements(By.xpath("//ul[@id='actions_dropdown_groups']/li")).size();

		for (int i = 1; i <= size; i++) {
			WebElement el = mDriver.findElement(By.xpath("//ul[@id='actions_dropdown_groups']/li[" + i + "]"));
			if (el.getText().contains(SeleniumConstants.kLinkTextOther)) {
				mDriver.findElement(By.xpath("//ul[@id='actions_dropdown_groups']/li[" + i + "]/a/span")).click();
				break;
			}
		}

		Thread.sleep(2000);
		mWait.until(ExpectedConditions.elementToBeClickable(By
				.partialLinkText(SeleniumConstants.kLinkTextBrandedAndNonBranded)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextBrandedAndNonBranded)).click();
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextNonBranded)).click();

		int settingNoKeywords = mTest.GetKeywords(mDriver).size();
		Assert.assertEquals(SECNoOfKeywords, settingNoKeywords);
	}

	@Test
	public void TestGroupBranded() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlSearchEngineComparison + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText().equals(SeleniumConstants.kTextAllResult)) {
      mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
      mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

      mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
      mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
      Thread.sleep(5000);
    }
    
		
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordGroupName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdKeywordGroupName)).click();
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextBranded)).click();
		Thread.sleep(5000);
		
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordGroupName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdKeywordGroupName)).click();
		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextOther)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextOther)).click();
		Thread.sleep(5000);

		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th")).size();
		int posMin = 2;
		int posMax = size;
		for (int i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[3]/th[" + i + "]"))
						.getText();
				if (!text.trim().isEmpty()) {
					if (text.equals(SeleniumConstants.kTextCompetition) || text.equals(SeleniumConstants.kTextSearches) || text.equals(SeleniumConstants.kTextCPC)) {
						posMin++;
					}
				}
			} catch (Exception e) {
				posMax = i - 1;
				break;
			}
		}
		int secNoOfKeywords = mTest.GetAllSortingElementsFromSEC(mDriver, posMin).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordSettings);
		Thread.sleep(5000);
		
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdFilterActions)));
		mDriver.findElement(By.id(SeleniumConstants.kIdFilterActions)).click();
		Thread.sleep(3000);
		size = mDriver.findElements(By.xpath("//ul[@id='actions_dropdown_groups']/li")).size();
		
		for(int i=1; i<=size; i++) {
			WebElement el = mDriver.findElement(By.xpath("//ul[@id='actions_dropdown_groups']/li[" + i + "]"));
			if(el.getText().contains(SeleniumConstants.kLinkTextOther)) {
				mDriver.findElement(By.xpath("//ul[@id='actions_dropdown_groups']/li[" + i + "]/a/span")).click();
				break;
			}
		}
		

		Thread.sleep(2000);
		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextBrandedAndNonBranded)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextBrandedAndNonBranded)).click();
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextBranded)).click();
		int settingNoKeywords = mTest.GetKeywords(mDriver).size();

		Assert.assertEquals(secNoOfKeywords, settingNoKeywords);
	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		mDriver.quit();
	}
}