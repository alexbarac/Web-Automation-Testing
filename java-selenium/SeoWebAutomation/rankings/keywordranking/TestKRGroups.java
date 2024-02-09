package com.seo.selenium.ui.rankings.keywordranking;

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

public class TestKRGroups {
	private static WebDriver mDriver;
	private static UserActions mTest;
	private static WebDriverWait mWait;

	//put on 100 kwspage
	@BeforeClass
	public static void Login() {
		mDriver = com.seo.selenium.ui.SeleniumUtils.BuildDriver();
		mTest = new UserActions();
		mTest.Login(mDriver, SeleniumConstants.kHostDev, SeleniumConstants.kUserEmail2, SeleniumConstants.kUserPassword2);
		mWait = new WebDriverWait(mDriver, 30);

		try {
			mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
			Thread.sleep(5000);
			if (!mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).getText()
					.equals(SeleniumConstants.kTextAllResult)) 
			{
				mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelFilterDisplay)));
				mDriver.findElement(By.id(SeleniumConstants.kIdSelFilterDisplay)).click();

				mWait.until(ExpectedConditions.elementToBeClickable(By
						.partialLinkText(SeleniumConstants.kLinkTextAllResults)));
				mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextAllResults)).click();
			}
			Thread.sleep(5000);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestBranded() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);
		
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
		select.selectByValue(SeleniumConstants.kValue100);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordGroupName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdKeywordGroupName)).click();

		Thread.sleep(5000);
		mWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(SeleniumConstants.kLinkTextBranded)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextBranded)).click();

		Thread.sleep(5000);
		int posPosition = 0;
		int thSizes = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i = 0;
		for (i = 1; i < thSizes + 1; i++) 
		{
		  try
		  {
		    if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
            .equals(SeleniumConstants.kTextPosition)) {
          break;
		    }
			} catch (Exception e)
			{
			}
		}
		
		if(i>=thSizes)
		{
		  mWait.until(ExpectedConditions.elementToBeClickable(By
	        .xpath("//table[@id='rankingtable']/thead/tr[1]/td[1]/div/div/span/i")));
	    mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[1]/td/div/div/span/i")).click();
	    Thread.sleep(5000);

      mDriver.findElement(By.name(SeleniumConstants.kNamePosition)).click();
      mDriver.findElement(By.name(SeleniumConstants.kNamePosition)).submit();
		
      Thread.sleep(5000);
		
  		for (i = 1; i < thSizes + 1; i++) 
  		{
  		  try
        {
          if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
              .equals(SeleniumConstants.kTextPosition)) {
            break;
          }
        } catch (Exception e)
        {
        }
      }
		}
		
		posPosition = i + 1;
		int krNoOfKeywords = mTest.GetAllPositionsForKR(mDriver, posPosition).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordSettings);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By
				.partialLinkText(SeleniumConstants.kLinkTextBrandedAndNonBranded)));
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextBrandedAndNonBranded)).click();
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextBranded)).click();

		int settingNoKeywords = mTest.GetKeywords(mDriver).size();
		Assert.assertEquals(krNoOfKeywords, settingNoKeywords);
	}

	@Test
	public void TestNonBranded() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev +  SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(5000);
		
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
    select.selectByValue(SeleniumConstants.kValue100);
    Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordGroupName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdKeywordGroupName)).click();

		Thread.sleep(5000);
		mWait.until(ExpectedConditions.elementToBeClickable(By.linkText(SeleniumConstants.kLinkTextNonBranded)));
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextNonBranded)).click();
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
		
		if(i>=thSizes)
    {
      mWait.until(ExpectedConditions.elementToBeClickable(By
          .xpath("//table[@id='rankingtable']/thead/tr[1]/td[1]/div/div/span/i")));
      mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[1]/td/div/div/span/i")).click();
      Thread.sleep(5000);

      mDriver.findElement(By.name(SeleniumConstants.kNamePosition)).click();
      mDriver.findElement(By.name(SeleniumConstants.kNamePosition)).submit();
    
      Thread.sleep(5000);
    
      for (i = 1; i < thSizes + 1; i++) 
      {
        try
        {
          if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
              .equals(SeleniumConstants.kTextPosition)) {
            break;
          }
        } catch (Exception e)
        {
        }
      }
    }
		
		posPosition = i + 1;
		int krNoOfKeywords = mTest.GetAllPositionsForKR(mDriver, posPosition).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordSettings);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdFilterBranded)));
		mDriver.findElement(By.id(SeleniumConstants.kIdFilterBranded)).click();
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextNonBranded)).click();

		int settingNoKeywords = mTest.GetKeywords(mDriver).size();
		Assert.assertEquals(krNoOfKeywords, settingNoKeywords);
	}

	@Test
	public void TestGroups() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev +  SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);
		
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
    select.selectByValue(SeleniumConstants.kValue100);
    Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordGroupName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdKeywordGroupName)).click();

		int expectedNumberOfKeywords = Integer.parseInt(mDriver
				.findElement(By.partialLinkText(SeleniumConstants.kLinkTextOther)).getText().split("  ")[1].replace(
				")", "").replace("(", ""));

		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextOther)).click();
		Thread.sleep(5000);

		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i;
		for (i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]"))
						.getText();
				if (text.equals(SeleniumConstants.kTextPosition)) {
					break;
				}
			} catch (Exception e) {
				break;
			}
		}
		
		if(i>=size)
    {
      mWait.until(ExpectedConditions.elementToBeClickable(By
          .xpath("//table[@id='rankingtable']/thead/tr[1]/td[1]/div/div/span/i")));
      mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[1]/td/div/div/span/i")).click();
      Thread.sleep(5000);

      mDriver.findElement(By.name(SeleniumConstants.kNamePosition)).click();
      mDriver.findElement(By.name(SeleniumConstants.kNamePosition)).submit();
    
      Thread.sleep(5000);
    
      for (i = 1; i < size + 1; i++) 
      {
        try
        {
          if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
              .equals(SeleniumConstants.kTextPosition)) {
            break;
          }
        } catch (Exception e)
        {
        }
      }
    }

		int posPosition = i + 1;
		int actualNumberOfElements = mTest.GetAllPositionsForKR(mDriver, posPosition).size();

		Assert.assertEquals(actualNumberOfElements, expectedNumberOfKeywords);
	}

	@Test
	public void TestGroupBranded() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);

		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
    select.selectByValue(SeleniumConstants.kValue100);
    Thread.sleep(5000);
    
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordGroupName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdKeywordGroupName)).click();
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextOther)).click();

		Thread.sleep(5000);
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordGroupName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdKeywordGroupName)).click();

		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextBranded)).click();
		Thread.sleep(5000);

		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i;
		for (i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]"))
						.getText();
				if (text.equals(SeleniumConstants.kTextPosition)) {
					break;
				}
			} catch (Exception e) {
				break;
			}
		}
		
		if(i>=size)
    {
      mWait.until(ExpectedConditions.elementToBeClickable(By
          .xpath("//table[@id='rankingtable']/thead/tr[1]/td[1]/div/div/span/i")));
      mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[1]/td/div/div/span/i")).click();
      Thread.sleep(5000);

      mDriver.findElement(By.name(SeleniumConstants.kNamePosition)).click();
      mDriver.findElement(By.name(SeleniumConstants.kNamePosition)).submit();
    
      Thread.sleep(5000);
    
      for (i = 1; i < size + 1; i++) 
      {
        try
        {
          if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
              .equals(SeleniumConstants.kTextPosition)) {
            break;
          }
        } catch (Exception e)
        {
        }
      }
    }

		int posPosition = i + 1;
		int actualNumberOfElements = mTest.GetAllPositionsForKR(mDriver, posPosition).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordSettings);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdFilterActions)));
		mDriver.findElement(By.id(SeleniumConstants.kIdFilterActions)).click();
		Thread.sleep(1000);
		size = mDriver.findElements(By.xpath("//ul[@id='actions_dropdown_groups']/li")).size();

		for (i = 1; i <= size; i++) {
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
		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextBranded)).click();

		int settingNoKeywords = mTest.GetKeywords(mDriver).size();
		Assert.assertEquals(actualNumberOfElements, settingNoKeywords);
	}

	@Test
	public void TestGroupNonBranded() throws Exception {
		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordRankings + SeleniumConstants.kProjectId1);
		Thread.sleep(3000);
		
		Select select = new Select(mDriver.findElement(By.name(SeleniumConstants.kNamePerPage)));
    select.selectByValue(SeleniumConstants.kValue100);
    Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordGroupName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdKeywordGroupName)).click();
		mDriver.findElement(By.partialLinkText(SeleniumConstants.kLinkTextOther)).click();

		Thread.sleep(5000);
		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordGroupName)));
		mDriver.findElement(By.id(SeleniumConstants.kIdKeywordGroupName)).click();

		mDriver.findElement(By.linkText(SeleniumConstants.kLinkTextNonBranded)).click();
		Thread.sleep(5000);

		int size = mDriver.findElements(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th")).size();
		int i;
		for (i = 1; i <= size; i++) {
			try {
				String text = mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]"))
						.getText();
				if (text.equals(SeleniumConstants.kTextPosition)) {
					break;
				}
			} catch (Exception e) {
				break;
			}
		}

		if(i>=size)
    {
      mWait.until(ExpectedConditions.elementToBeClickable(By
          .xpath("//table[@id='rankingtable']/thead/tr[1]/td[1]/div/div/span/i")));
      mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[1]/td/div/div/span/i")).click();
      Thread.sleep(5000);

      mDriver.findElement(By.name(SeleniumConstants.kNamePosition)).click();
      mDriver.findElement(By.name(SeleniumConstants.kNamePosition)).submit();
    
      Thread.sleep(5000);
    
      for (i = 1; i < size + 1; i++) 
      {
        try
        {
          if (mDriver.findElement(By.xpath("//table[@id='rankingtable']/thead/tr[2]/th[" + i + "]")).getText()
              .equals(SeleniumConstants.kTextPosition)) {
            break;
          }
        } catch (Exception e)
        {
        }
      }
    }
		
		int posPosition = i + 1;
		int actualNumberOfElements = mTest.GetAllPositionsForKR(mDriver, posPosition).size();

		mDriver.get(SeleniumConstants.kHostDev + SeleniumConstants.kUrlKeywordSettings);
		Thread.sleep(5000);

		mWait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdFilterActions)));
		mDriver.findElement(By.id(SeleniumConstants.kIdFilterActions)).click();
		Thread.sleep(1000);
		size = mDriver.findElements(By.xpath("//ul[@id='actions_dropdown_groups']/li")).size();

		for (i = 1; i <= size; i++) {
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
		Assert.assertEquals(actualNumberOfElements, settingNoKeywords);
	}

	@AfterClass
	public static void CloseWindow() throws Exception {
		mDriver.quit();
	}
}
