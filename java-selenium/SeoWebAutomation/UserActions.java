package com.seo.selenium.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seo.selenium.ui.rankings.clickshare.CSSortingElement;
import com.seo.selenium.ui.rankings.keywordgroups.KGSortingElement;
import com.seo.selenium.ui.rankings.secomparison.SECSortingElement;
import com.seo.selenium.ui.rankings.websitecomparison.WebCompSortingElement;

public class UserActions
{
  public void Login(WebDriver aDriver,  String aHost, String aEmail, String aPassword)
  {
    aDriver.get(aHost + SeleniumConstants.kUrlLogin);
    try
    {
      Thread.sleep(5000);
    } catch (Exception e)
    {
    }

    WebElement element = aDriver.findElement(By.id(SeleniumConstants.kIdInputEmail));
    element.clear();
    element.sendKeys(aEmail);
    element = aDriver.findElement(By.id(SeleniumConstants.kIdInputPassword));
    element.clear();
    element.sendKeys(aPassword);

    if (aDriver.findElement(By.name(SeleniumConstants.kNameRememberMe)).isSelected())
    {
      aDriver.findElement(By.name(SeleniumConstants.kNameRememberMe)).click();
    }

    element.submit();
  }

  public List<String> GetKeywords(WebDriver driver)
  {
    WebDriverWait wait = new WebDriverWait(driver, 10); // 20
    WebElement element = null;
    List<String> strings = new ArrayList<String>();
    WebElement table = null;
    do
    {
      if (element == null)
      {
        try
        {
          table = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordsTable)));
          int size = table.findElements(By.xpath("//tbody/tr")).size();
          for (int i = 1; i <= size; i++)
          {
            strings.add(driver.findElement(By.xpath("//table[@id='keywords_table']/tbody/tr[" + i + "]/td[3]"))
                .getText());
          }

          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .className(SeleniumConstants.kClassTablePagination)));
          element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
        } catch (Exception e)
        {
          return strings;
        }
      }
      if (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null)
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        table = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywordsTable)));
        int size = table.findElements(By.xpath("//tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          strings
              .add(driver.findElement(By.xpath("//table[@id='keywords_table']/tbody/tr[" + i + "]/td[3]")).getText());
        }

        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .className(SeleniumConstants.kClassTablePagination)));
        element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
      }
    } while (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null);
    return strings;
  }

  public int AddKeywords(WebDriver aDriver, String[] aKeywords)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 40);
    WebElement element = wait
        .until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdAddNewKeywords)));
    element.click();
    try
    {
      Thread.sleep(5000);
    } catch (InterruptedException e)
    {
    }

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywords)));
    element.clear();
    for (int i = 0; i < aKeywords.length; i++)
    {
      element.sendKeys(aKeywords[i]);
    }

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdAddYourKeywords)));
    element.click();
    try
    {
      Thread.sleep(5000);
    } catch (InterruptedException e)
    {
    }

    return aKeywords.length;
  }

  public void EmptyKeywords(WebDriver aDriver, String aHost)
  {
    while (GetKeywords(aDriver).size() > 0)
    {
      WebDriverWait wait = new WebDriverWait(aDriver, 20);
      WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSelAllKws)));
      element = aDriver.findElement(By.id(SeleniumConstants.kIdSelAllKws));
      element.click();

      element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdDropActions)));
      element.click();

      element = wait.until(ExpectedConditions.elementToBeClickable(By
          .linkText(SeleniumConstants.kLinkTextDeleteSelected)));
      element.click();

      element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'OK')]")));
      element.click();
      aDriver.get(aHost + SeleniumConstants.kUrlKeywordSettings);
    }
  }

  public int AddKeywordsToNewGroup(WebDriver aDriver)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 40);
    String[] keywords = { SeleniumConstants.kUserKeywordsArray[0] + Keys.ENTER,
        SeleniumConstants.kUserKeywordsArray[1] + Keys.ENTER, SeleniumConstants.kUserKeywordsArray[2] + Keys.ENTER };
    WebElement element = wait
        .until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdAddNewKeywords)));
    element.click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywords)));
    element.clear();
    for (int i = 0; i < keywords.length; i++)
    {
      element.sendKeys(keywords[i]);
    }

    wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdGroupId)));
    Select select = new Select(aDriver.findElement(By.id(SeleniumConstants.kIdGroupId)));
    select.selectByVisibleText(SeleniumConstants.kTextNewGroup);

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdNewGroup)));
    element.sendKeys(SeleniumConstants.kUserGroupName);

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdAddYourKeywords)));
    element.click();

    return keywords.length;
  }

  public int AddKeywordsToExistingGroup(WebDriver aDriver, String aGroup)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 40);
    String[] keywords = { SeleniumConstants.kUserKeywordsArray[3] + Keys.ENTER,
        SeleniumConstants.kUserKeywordsArray[4] + Keys.ENTER, SeleniumConstants.kUserKeywordsArray[5] + Keys.ENTER };
    WebElement element = wait
        .until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdAddNewKeywords)));
    element.click();

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdKeywords)));
    element.clear();
    for (int i = 0; i < keywords.length; i++)
    {
      element.sendKeys(keywords[i]);
    }

    wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdGroupId)));
    Select select = new Select(aDriver.findElement(By.id(SeleniumConstants.kIdGroupId)));
    select.selectByVisibleText(aGroup);

    element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdAddYourKeywords)));
    element.click();

    return keywords.length;

  }

  public void ChangeFilter(WebDriver aDriver, String aKey)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 40);
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdFilterActions)));
    element.click();

    wait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassLiSearchable)));
    List<WebElement> elements = aDriver.findElements(By.className(SeleniumConstants.kClassLiSearchable));
    for (int i = 0; i < elements.size(); i++)
    {
      if (elements.get(i).getAttribute(SeleniumConstants.kAttributeDataSearchable).equalsIgnoreCase(aKey))
      {
        elements.get(i).findElements(By.xpath(".//*")).get(2).click();
        break;
      }
    }
  }

  public void UpdateGroup(WebDriver aDriver, String aFrom, String aTo)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 40);
    wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdFilterActions)));
    aDriver.findElement(By.id(SeleniumConstants.kIdFilterActions)).click();

    wait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassLiSearchable)));
    List<WebElement> elements = aDriver.findElements(By.className(SeleniumConstants.kClassLiSearchable));
    for (int i = 0; i < elements.size(); i++)
    {
      if (elements.get(i).getAttribute(SeleniumConstants.kAttributeDataSearchable).equalsIgnoreCase(aFrom))
      {
        elements.get(i).findElements(By.xpath(".//*")).get(1).click();
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By
            .id(SeleniumConstants.kIdEditGroupForm)));
        element = element.findElement(By.id(SeleniumConstants.kIdGroupName));
        element.clear();
        element.sendKeys(aTo);
        element.submit();
        break;
      }
    }
  }

  public void DeleteGroup(WebDriver aDriver, String aFrom)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 40);

    wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdFilterActions)));
    aDriver.findElement(By.id(SeleniumConstants.kIdFilterActions)).click();

    wait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassLiSearchable)));
    List<WebElement> elements = aDriver.findElements(By.className(SeleniumConstants.kClassLiSearchable));
    for (int i = 0; i < elements.size(); i++)
    {
      if (elements.get(i).getAttribute(SeleniumConstants.kAttributeDataSearchable).equalsIgnoreCase(aFrom))
      {
        elements.get(i).findElements(By.xpath(".//*")).get(1).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdEditGroupForm)));
        WebElement element = aDriver.findElement(By.id(SeleniumConstants.kIdEditGroupForm));
        element = element.findElement(By.partialLinkText(SeleniumConstants.kLinkTextDeleteGroup));
        element.click();

        try {
          Thread.sleep(5000);
        } catch(Exception e)
        {
          
        }
        
//        wait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassBootbox)));
//        element = aDriver.findElement(By.className(SeleniumConstants.kClassBootbox));
//        element = element.findElement(By.className(SeleniumConstants.kClassModalDialog));
//        element = element.findElement(By.className(SeleniumConstants.kClassModalContent));
//        element = element.findElement(By.className(SeleniumConstants.kClassModalFooter));
//
//        wait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassBtn)));
//        List<WebElement> elementsList = element.findElements(By.className(SeleniumConstants.kClassBtn));
//        for (int j = 0; j < elementsList.size(); j++)
//        {
//          if (elementsList.get(j).getText().equals(SeleniumConstants.kTextYes))
//          {
//            elementsList.get(j).click();
//          }
//        }
        
        aDriver.findElement(By.xpath("//button[contains(text(), 'Yes')]")).click();
        break;
      }
    }

  }

  public List<WebElement> GetSearchEngines(WebDriver aDriver)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 40); // 20
    WebElement element = null;
    List<WebElement> customer = new ArrayList<WebElement>();
    WebElement table = null;
    do
    {
      if (element == null)
      {
        try
        {

          table = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSearchengines)));
          customer.addAll(table.findElements(By.xpath("//form/div/table/tbody/tr")));

          element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSearchengines)));
          element = element.findElement(By.className(SeleniumConstants.kClassTablePagination));
          element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
        } catch (Exception e)
        {
          return customer;
        }
      }

      if (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null)
      {
        element.click();

        table = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSearchengines)));
        customer.addAll(table.findElements(By.xpath("//form/div/table/tbody/tr")));

        element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSearchengines)));
        element = element.findElement(By.className(SeleniumConstants.kClassTablePagination));
        element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
      }

    } while (element != null && (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null));
    return customer;

  }

  public void EmptySearchengines(WebDriver aDriver, String aHost)
  {
    while (GetSearchEngines(aDriver).size() > 0)
    {
      WebDriverWait wait = new WebDriverWait(aDriver, 40); // 20
      WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By
          .className(SeleniumConstants.kClassTableStriped)));
      element.findElement(By.id(SeleniumConstants.kIdSelAllSe)).click();

      element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdDelSel)));
      element.click();

      element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'OK')]")));
      element.click();
      try
      {
        Thread.sleep(2000);
      } catch (Exception e)
      {
      }
      aDriver.get(aHost + SeleniumConstants.kUrlSearchengineSettings);
    }

  }

  public List<WebElement> GetCompetitors(WebDriver aDriver)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10);
    WebElement element = null;
    List<WebElement> customer = new ArrayList<WebElement>();
    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdCompetitors)));
          customer.addAll(aDriver.findElements(By.xpath("//div/div/fieldset/table/tbody/tr")));
          customer = RemoveEmptyElements(customer);

          element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdCompetitors)));
          element = element.findElement(By.className(SeleniumConstants.kClassTablePagination));
          element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
        } catch (Exception e)
        {
          return customer;
        }
      }

      if (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null)
      {
        element.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdCompetitors)));
        customer.addAll(aDriver.findElements(By.xpath("//div/div/fieldset/table/tbody/tr")));

        wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdCompetitors)));
        element = aDriver.findElement(By.id(SeleniumConstants.kIdCompetitors));
        element = element.findElement(By.className(SeleniumConstants.kClassTablePagination));
        element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
      }
    } while (element != null && (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null));
    return customer;
  }

  public List<WebElement> RemoveEmptyElements(List<WebElement> aWebElements)
  {
    for (int i = 0; i < aWebElements.size(); i++)
    {
      if (aWebElements.get(i).getText().trim().isEmpty())
      {
        aWebElements.remove(i);
        i--;
      }
    }

    return aWebElements;
  }

  public List<String> RemoveStringEmptyElements(List<String> aWebElements)
  {
    for (int i = 0; i < aWebElements.size(); i++)
    {
      if (aWebElements.get(i).trim().isEmpty())
      {
        aWebElements.remove(i);
        i--;
      }
    }

    return aWebElements;
  }

  public void EmptyCompetitors(WebDriver aDriver, String aHost)
  {
    while (GetCompetitors(aDriver).size() > 0)
    {
      WebDriverWait wait = new WebDriverWait(aDriver, 20); // 20

      WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By
          .id(SeleniumConstants.kIdProjectCompetitors)));
      element.findElement(By.id(SeleniumConstants.kIdSelAllCompetitors)).click();

      element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdDropActions)));
      element.click();

      element = wait.until(ExpectedConditions.elementToBeClickable(By
          .linkText(SeleniumConstants.kLinkTextDeleteSelected)));
      element.click();

      element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'OK')]")));
      element.click();

      try
      {
        Thread.sleep(1000);
      } catch (Exception e)
      {
      }

      aDriver.get(aHost + SeleniumConstants.kUrlCompetitorsSettings
          + SeleniumConstants.kParameterActionManagerActiveCompetitors);
    }
  }

  public List<String> GetWebsites(WebDriver aDriver)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10); // 20
    WebElement element = null;
    List<String> websites = new ArrayList<String>();
    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.elementToBeClickable(By.className("table-responsive")));
          int size = aDriver.findElements(By.xpath("//div/table/tbody/tr")).size();
          for (int i = 1; i <= size; i++)
          {
            websites
                .add(aDriver.findElement(By.xpath("//div/table/tbody/tr[" + i + "]/td[2]/a")).getText().split("/n")[0]);
          }
          websites = RemoveStringEmptyElements(websites);
          element = wait.until(ExpectedConditions.elementToBeClickable(By.className("table-pagination")));
          element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
        } catch (Exception e)
        {
          return websites;
        }
      }

      if (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null)
      {
        element.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("table-responsive")));
        int size = aDriver.findElements(By.xpath("//div/table/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          websites
              .add(aDriver.findElement(By.xpath("//div/table/tbody/tr[" + i + "]/td[2]/a")).getText().split("/n")[0]);
        }
        element = wait.until(ExpectedConditions.elementToBeClickable(By.className("table-pagination")));
        element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
        websites = RemoveStringEmptyElements(websites);
      }
    } while (element != null && (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null));
    return websites;
  }

  public List<String> GetWebsitesProjectListCustomer(WebDriver aDriver)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10); // 20
    WebElement element = null;
    List<String> websites = new ArrayList<String>();
    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.elementToBeClickable(By.className("table-responsive")));
          int size = aDriver.findElements(By.xpath("//div/table/tbody/tr")).size();
          for (int i = 1; i <= size; i++)
          {
            websites
                .add(aDriver.findElement(By.xpath("//div/table/tbody/tr[" + i + "]/td[1]/a")).getText().split("/n")[0]);
          }
          websites = RemoveStringEmptyElements(websites);
          element = wait.until(ExpectedConditions.elementToBeClickable(By.className("table-pagination")));
          element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
        } catch (Exception e)
        {
          return websites;
        }
      }

      if (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null)
      {
        element.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("table-responsive")));
        int size = aDriver.findElements(By.xpath("//div/table/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          websites
              .add(aDriver.findElement(By.xpath("//div/table/tbody/tr[" + i + "]/td[1]/a")).getText().split("/n")[0]);
        }
        element = wait.until(ExpectedConditions.elementToBeClickable(By.className("table-pagination")));
        element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
        websites = RemoveStringEmptyElements(websites);
      }
    } while (element != null && (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null));
    return websites;
  }

  public List<String> GetWebsitesProjectListTeam(WebDriver aDriver)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10); // 20
    WebElement element = null;
    List<String> websites = new ArrayList<String>();
    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.elementToBeClickable(By.className("table-responsive")));
          int size = aDriver.findElements(By.xpath("//div/table/tbody/tr")).size();
          for (int i = 1; i <= size; i++)
          {
            websites
                .add(aDriver.findElement(By.xpath("//div/table/tbody/tr[" + i + "]/td[2]/a")).getText().split("/n")[0]);
          }
          websites = RemoveStringEmptyElements(websites);
          element = wait.until(ExpectedConditions.elementToBeClickable(By.className("table-pagination")));
          element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
        } catch (Exception e)
        {
          return websites;
        }
      }

      if (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null)
      {
        element.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("table-responsive")));
        int size = aDriver.findElements(By.xpath("//div/table/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          websites
              .add(aDriver.findElement(By.xpath("//div/table/tbody/tr[" + i + "]/td[2]/a")).getText().split("/n")[0]);
        }
        element = wait.until(ExpectedConditions.elementToBeClickable(By.className("table-pagination")));
        element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
        websites = RemoveStringEmptyElements(websites);
      }
    } while (element != null && (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null));
    return websites;
  }

  public int GetNumberOfOnDemandWebsites(WebDriver aDriver, String aHost, boolean aOnDemand)
  {
    aDriver.get(aHost + SeleniumConstants.kUrlProjectList);
    WebDriverWait wait = new WebDriverWait(aDriver, 10); // 20
    WebElement element = null;
    List<WebElement> websites = new ArrayList<WebElement>();
    int sizeOfElements = 0;
    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassTableResponsive)));
          int size = aDriver.findElements(By.xpath("//div/table/tbody/tr[1]/td")).size();
          websites.clear();
          websites.addAll(aDriver.findElements(By.xpath("//div/table/tbody/tr/td[" + size + "]/div/div/div/button")));
          websites = RemoveEmptyElements(websites);
          for (int i = 0; i < websites.size(); i++)
          {
            if (aOnDemand)
            {
              if (websites.get(i).getText().contains(SeleniumConstants.kTextOnDemand))
              {
                sizeOfElements++;
              }
            } else
            {
              if (!websites.get(i).getText().contains(SeleniumConstants.kTextOnDemand))
              {
                sizeOfElements++;
              }
            }

          }

          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .className(SeleniumConstants.kClassTablePagination)));
          element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
        } catch (Exception e)
        {
          aDriver.get(aHost + SeleniumConstants.kUrlProjectList);
          return sizeOfElements;
        }
      }

      if (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null)
      {
        element.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className(SeleniumConstants.kClassTableResponsive)));
        int size = aDriver.findElements(By.xpath("//div/table/tbody/tr[1]/td")).size();
        websites.clear();
        websites.addAll(aDriver.findElements(By.xpath("//div/table/tbody/tr/td[" + size + "]/div/div/div/button")));
        websites = RemoveEmptyElements(websites);
        for (int i = 0; i < websites.size(); i++)
        {
          if (aOnDemand)
          {
            if (websites.get(i).getText().contains(SeleniumConstants.kTextOnDemand))
            {
              sizeOfElements++;
            }
          } else
          {
            if (!websites.get(i).getText().contains(SeleniumConstants.kTextOnDemand))
            {
              sizeOfElements++;
            }
          }
        }

        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .className(SeleniumConstants.kClassTablePagination)));
        element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
      }
    } while (element != null && (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null));

    aDriver.get(aHost + SeleniumConstants.kUrlProjectList);
    return sizeOfElements;
  }

  public List<Double> GetVisibilityChangeScore(WebDriver aDriver)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10); // 20
    WebElement element = null;
    List<Double> visibilityScore = new ArrayList<Double>();
    Double score = 0.0;
    List<WebElement> websites = new ArrayList<WebElement>();
    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.elementToBeClickable(By.className("table-responsive")));
          websites.clear();
          websites.addAll(aDriver.findElements(By.xpath("//div/table/tbody/tr")));
          for (int i = 0; i < websites.size(); i++)
          {
            WebElement el = aDriver.findElement(By.xpath("//div/table/tbody/tr[" + (i + 1) + "]/td[4]/div"));
            if (el.getText().equals("No update yet."))
            {
              visibilityScore.add(0.0);
            } else
            {
              try
              {
                el = el.findElement(By.className("text-danger"));
                score = Double.parseDouble(el.getText().split("%")[0]);
                visibilityScore.add(score - 2 * score);
              } catch (Exception e)
              {
              }
              ;
              try
              {
                el = el.findElement(By.className("text-muted"));
                visibilityScore.add(0.0);
              } catch (Exception e)
              {
              }
              ;
              try
              {
                el = el.findElement(By.className("text-success"));
                visibilityScore.add(Double.parseDouble(el.getText().split("%")[0]));
              } catch (Exception e)
              {
              }
              ;
            }
          }
          element = wait.until(ExpectedConditions.elementToBeClickable(By.className("table-pagination")));
          element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
        } catch (Exception e)
        {
          return visibilityScore;
        }
      }

      if (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null)
      {
        element.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("table-responsive")));
        websites.clear();
        websites.addAll(aDriver.findElements(By.xpath("//div/table/tbody/tr")));
        for (int i = 0; i < websites.size(); i++)
        {
          WebElement el = aDriver.findElement(By.xpath("//div/table/tbody/tr[" + (i + 1) + "]/td[4]/div"));
          if (el.getText().equals("No update yet."))
          {
            visibilityScore.add(0.0);
          } else
          {
            try
            {
              el = el.findElement(By.className("text-danger"));
              score = Double.parseDouble(el.getText().split("%")[0]);
              visibilityScore.add(score - 2 * score);
            } catch (Exception e)
            {
            }
            try
            {
              el = el.findElement(By.className("text-muted"));
              visibilityScore.add(0.0);
            } catch (Exception e)
            {
            }
            try
            {
              el = el.findElement(By.className("text-success"));
              visibilityScore.add(Double.parseDouble(el.getText().split("%")[0]));
            } catch (Exception e)
            {
            }
          }
        }
        element = wait.until(ExpectedConditions.elementToBeClickable(By.className("table-pagination")));
        element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
      }
    } while (element != null && (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null));

    return visibilityScore;
  }

  public boolean ListIsSortedAscending(List<Double> aList)
  {
    boolean sorted = true;
    for (int i = 0; i < aList.size() - 1; i++)
    {
      if (aList.get(i).compareTo(aList.get(i + 1)) >= 0)
      {
        if (!((aList.get(i) == 0.0) && (i == 0 || aList.get(i) == aList.get(i - 1))))
        {
          sorted = false;
        }
      }
    }

    return sorted;
  }

  public boolean ListIsSortedDescending(List<Double> aList)
  {
    boolean sorted = true;
    for (int i = 0; i < aList.size() - 1; i++)
    {
      if (aList.get(i).compareTo(aList.get(i + 1)) <= 0)
      {
        if (!((aList.get(i + 1) == 0.0) && (i + 1 == aList.size() - 1 || aList.get(i + 2) == aList.get(i + 1))))
        {
          sorted = false;
        }
      }

    }
    return sorted;
  }

  public List<String> GetSearchEnginesNames(WebDriver aDriver)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 40); // 20
    WebElement element = null;
    List<String> customer = new ArrayList<String>();
    WebElement table = null;
    do
    {
      if (element == null)
      {
        try
        {
          table = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSearchengines)));
          List<WebElement> elements = table.findElements(By.xpath("//form/div/table/tbody/tr"));
          for (int i = 1; i <= elements.size(); i++)
          {
            String attribute = aDriver.findElement(By.xpath("//form/div/table/tbody/tr[" + i + "]/td[1]"))
                .getAttribute("onclick");
            String temp = attribute.split(";")[0].replace("_select(", "").replace(")", "");
            customer.add(temp);
          }

          element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSearchengines)));
          element = element.findElement(By.className(SeleniumConstants.kClassTablePagination));
          element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
        } catch (Exception e)
        {
          return customer;
        }
      }

      if (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null)
      {
        element.click();
        table = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSearchengines)));
        List<WebElement> elements = table.findElements(By.xpath("//form/div/table/tbody/tr"));
        for (int i = 0; i < elements.size(); i++)
        {
          String attribute = elements.get(i).findElement(By.xpath("//td[1]")).getAttribute("onclick");
          customer.add(attribute.split(";")[0].split("(")[1].replace(")", ""));
        }

        element = wait.until(ExpectedConditions.elementToBeClickable(By.id(SeleniumConstants.kIdSearchengines)));
        element = element.findElement(By.className(SeleniumConstants.kClassTablePagination));
        element = element.findElements(By.className(SeleniumConstants.kClassBtn)).get(1);
      }

    } while (element != null && (element.getAttribute(SeleniumConstants.kAttributeDiabled) == null));
    return customer;
  }

  public void GenerateQuickReport(WebDriver aDriver, String aHost, WebDriverWait aWait, String aTestName)
  {
    aWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='export_group']/a")));
    aDriver.findElement(By.xpath("//div[@id='export_group']/a")).click();

    aWait.until(ExpectedConditions.elementToBeClickable(By.id("dld_label")));
    aDriver.findElement(By.id("dld_label")).click();

    aWait.until(ExpectedConditions.elementToBeClickable(By.id("html_label")));
    aDriver.findElement(By.id("html_label")).click();

    WebElement element = aWait.until(ExpectedConditions.elementToBeClickable(By.id("quick_report_name")));
    element.clear();
    element.sendKeys(aTestName);

    aDriver.findElement(By.id("qr_send")).click();
    try
    {
      Thread.sleep(10000);
    } catch (InterruptedException e1)
    {
    }
    aDriver.get(aHost + SeleniumConstants.kUrlReports);

    try
    {
      Thread.sleep(5000);
    } catch (InterruptedException e1)
    {
    }

    String winHandleBefore = aDriver.getWindowHandle();
    List<WebElement> elementList = aDriver
        .findElements(By.xpath("//table[contains(@class, 'table-striped')]/tbody/tr"));
    for (int i = 0; i < elementList.size(); i++)
    {
      if (elementList.get(i).findElement(By.xpath("//td[2]/a")).getText().equals(aTestName))
      {
        Assert.assertEquals("font-weight: bold;",
            elementList.get(i).findElement(By.xpath("//td[2]")).getAttribute("style"));
        elementList.get(i).findElement(By.xpath("//td[2]/a")).click();
        break;
      }
    }

    for (String winHandle : aDriver.getWindowHandles())
    {
      aDriver.switchTo().window(winHandle);
    }
    // compute data in report
    aDriver.close();
    aDriver.switchTo().window(winHandleBefore);

    aWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@id='sel-all-header']/span")));
    aDriver.findElement(By.xpath("//span[@id='sel-all-header']/span")).click();

    try
    {
      Thread.sleep(5000);
    } catch (Exception e)
    {
    }
    aWait.until(ExpectedConditions.elementToBeClickable(By.id("drop_actions")));
    aDriver.findElement(By.id("drop_actions")).click();

    try
    {
      Thread.sleep(5000);
    } catch (Exception e)
    {
    }

    aWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='delete_selected']/ul/li[2]/a")));
    aDriver.findElement(By.xpath("//div[@id='delete_selected']/ul/li[2]/a")).click();

    try
    {
      Thread.sleep(5000);
    } catch (Exception e)
    {
    }

    aWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'modal-footer')]/button[2]")));
    aDriver.findElement(By.xpath("//div[contains(@class, 'modal-footer')]/button[2]")).click();
    try
    {
      Thread.sleep(3000);
    } catch (InterruptedException e)
    {
    }
  }

  public List<String> GetAllKeywordsFromSEC(WebDriver aDriver)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10); // 20
    WebElement element = null;
    List<String> strings = new ArrayList<String>();
    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rankingtable")));
          int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
          for (int i = 1; i <= size; i++)
          {
            String text = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[1]"))
                .getText();
            strings.add(text);
          }

          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .xpath("//div[contains(@class, 'text-right')]/a[2]")));
          element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));
        } catch (Exception e)
        {
          return strings;
        }
      }
      if (!element.getAttribute("class").contains("disabled"))
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rankingtable")));
        int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          String text = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[1]"))
              .getText();
          strings.add(text);
        }
        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .xpath("//div[contains(@class, 'text-right')]/a[2]")));
        element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));

      }
    } while (!element.getAttribute("class").contains("disabled"));
    return strings;
  }

  public List<String> GetAllKeywordsFromWebComp(WebDriver aDriver)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10); // 20
    WebElement element = null;
    List<String> strings = new ArrayList<String>();
    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rankingtable")));
          int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
          for (int i = 1; i <= size; i++)
          {
            String text = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[1]"))
                .getText();
            strings.add(text);
          }

          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .xpath("//div[contains(@class, 'pull-right')]/a[2]")));
          element = aDriver.findElement(By.xpath("//div[contains(@class, 'pull-right')]/a[2]"));
        } catch (Exception e)
        {
          return strings;
        }
      }
      if (!element.getAttribute("class").contains("disabled"))
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rankingtable")));
        int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          String text = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[1]"))
              .getText();
          strings.add(text);
        }
        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .xpath("//div[contains(@class, 'pull-right')]/a[2]")));
        element = aDriver.findElement(By.xpath("//div[contains(@class, 'pull-right')]/a[2]"));
      }
    } while (!element.getAttribute("class").contains("disabled"));
    return strings;
  }

  public List<SECSortingElement> GetAllSortingElementsFromSEC(WebDriver aDriver, int aSearchEnginePos)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10); // 20
    WebElement element = null;
    List<SECSortingElement> strings = new ArrayList<SECSortingElement>();
    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
          int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[1]/td[1]")).getText()
              .equals("No keywords matched your criteria"))
          {
            return strings;
          }
          for (int i = 1; i <= size; i++)
          {
            String keyword = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[1]"))
                .getText();
            int websitePosition = 0;
            try
            {
              websitePosition = Integer.parseInt(aDriver.findElement(
                  By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aSearchEnginePos + "]/div/a"))
                  .getText());
            } catch (Exception e)
            {
              websitePosition = Integer.MAX_VALUE;
            }

            strings.add(new SECSortingElement(keyword, websitePosition));
          }

          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .xpath("//div[contains(@class, 'text-right')]/a[2]")));
          element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));
        } catch (Exception e)
        {
          return strings;
        }
      }
      if (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kAttributeClass))
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
        int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          String keyword = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[1]"))
              .getText();
          int websitePosition = 0;
          try
          {
            websitePosition = Integer.parseInt(aDriver.findElement(
                By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aSearchEnginePos + "]/div/a"))
                .getText());
          } catch (Exception e)
          {
            websitePosition = Integer.MAX_VALUE;
          }

          strings.add(new SECSortingElement(keyword, websitePosition));
        }
        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .xpath("//div[contains(@class, 'text-right')]/a[2]")));
        element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));

      }
    } while (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled));
    return strings;
  }

  public List<SECSortingElement> GetAllSortingElementsFromWebComp(WebDriver aDriver, int aSearchEnginePos)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10);
    WebElement element = null;
    List<SECSortingElement> strings = new ArrayList<SECSortingElement>();
    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rankingtable")));
          int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[1]/td[1]")).getText()
              .equals("No keywords matched your criteria"))
          {
            return strings;
          }
          for (int i = 1; i <= size; i++)
          {
            String keyword = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[1]"))
                .getText();
            int websitePosition = 0;
            try
            {
              websitePosition = Integer.parseInt(aDriver.findElement(
                  By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aSearchEnginePos + "]/div/a"))
                  .getText());
            } catch (Exception e)
            {
              websitePosition = Integer.MAX_VALUE;
            }

            strings.add(new SECSortingElement(keyword, websitePosition));
          }

          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .xpath("//div[contains(@class, 'pull-right')]/a[2]")));
          element = aDriver.findElement(By.xpath("//div[contains(@class, 'pull-right')]/a[2]"));
        } catch (Exception e)
        {
          return strings;
        }
      }
      if (!element.getAttribute("class").contains("disabled"))
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rankingtable")));
        int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          String keyword = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[1]"))
              .getText();
          int websitePosition = 0;
          try
          {
            websitePosition = Integer.parseInt(aDriver.findElement(
                By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aSearchEnginePos + "]/div/a"))
                .getText());
          } catch (Exception e)
          {
            websitePosition = Integer.MAX_VALUE;
          }
          strings.add(new SECSortingElement(keyword, websitePosition));
        }

        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .xpath("//div[contains(@class, 'pull-right')]/a[2]")));
        element = aDriver.findElement(By.xpath("//div[contains(@class, 'pull-right')]/a[2]"));

      }
    } while (!element.getAttribute("class").contains("disabled"));
    return strings;
  }

  public List<Map<String, List<Double>>> GetAllKeywordsFromKR(WebDriver aDriver)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10);
    WebElement element = null;
    List<Map<String, List<Double>>> strings = new ArrayList<Map<String, List<Double>>>();

    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
          int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[1]/td[1]")).getText()
              .equals("No keywords matched your criteria"))
          {
            return strings;
          }

          for (int i = 1; i <= size; i++)
          {
            String elementClass = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
                .getAttribute(SeleniumConstants.kAttributeClass);
            if (elementClass.equals(SeleniumConstants.kClassTrOdd)
                || elementClass.equals(SeleniumConstants.kClassTrEven))
            {
              Map<String, List<Double>> map = new LinkedHashMap<String, List<Double>>();
              int sizeOfTds = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td"))
                  .size();
              String keyword = "";
              List<Double> list = new ArrayList<Double>();

              for (int j = 3; j <= sizeOfTds; j++)
              {
                if (j == 3)
                {
                  keyword = aDriver.findElement(
                      By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + j + "]/span[1]")).getText();
                } else
                {
                  try
                  {
                    String text = aDriver.findElement(
                        By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + j + "]")).getText();

                    if (text.contains(","))
                    {
                      list.add(Double.parseDouble(text.replace(",", "")));
                    } else if (text.contains("�"))
                    {
                      list.add(Double.parseDouble(text.replace("�", "")));
                    } else if (text.contains("\n"))
                    {
                      list.add(Double.parseDouble(text.split("\n")[0].replace(",", "")));
                    } else
                    {
                      list.add(Double.parseDouble(text));
                    }
                  } catch (Exception e)
                  {
                    list.add(Double.MAX_VALUE);
                  }
                }
              }

              map.put(keyword, list);
              strings.add(map);
            }
          }

          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .xpath("//div[contains(@class, 'text-right')]/a[2]")));
          element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));
        } catch (Exception e)
        {
          return strings;
        }
      }
      if (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled))
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
        int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();

        for (int i = 1; i <= size; i++)
        {
          String elementClass = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
              .getAttribute(SeleniumConstants.kAttributeClass);
          if (elementClass.equals(SeleniumConstants.kClassTrOdd) || elementClass.equals(SeleniumConstants.kClassTrEven))
          {
            Map<String, List<Double>> map = new LinkedHashMap<String, List<Double>>();
            int sizeOfTds = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td")).size();
            String keyword = "";
            List<Double> list = new ArrayList<Double>();

            for (int j = 3; j <= sizeOfTds; j++)
            {
              if (j == 3)
              {
                keyword = aDriver.findElement(
                    By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + j + "]/span[1]")).getText();
              } else
              {
                try
                {
                  String text = aDriver.findElement(
                      By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + j + "]")).getText();

                  if (text.contains(","))
                  {
                    list.add(Double.parseDouble(text.replace(",", "")));
                  } else if (text.contains("�"))
                  {
                    list.add(Double.parseDouble(text.replace("�", "")));
                  } else if (text.contains("\n"))
                  {
                    list.add(Double.parseDouble(text.split("\n")[0].replace(",", "")));
                  } else
                  {
                    list.add(Double.parseDouble(text));
                  }
                } catch (Exception e)
                {
                  list.add(Double.MAX_VALUE);
                }
              }
            }

            map.put(keyword, list);
            strings.add(map);
          }
        }

        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .xpath("//div[contains(@class, 'text-right')]/a[2]")));
        element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));

      }
    } while (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled));
    return strings;
  }

  public List<String> GetAllPositionsForKR(WebDriver aDriver, int aPosition)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10);
    WebElement element = null;
    List<String> strings = new ArrayList<String>();

    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
          int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[1]/td[1]")).getText()
              .equals("No keywords matched your criteria"))
          {
            return strings;
          }
          for (int i = 1; i <= size; i++)
          {
            String elementClass = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
                .getAttribute(SeleniumConstants.kAttributeClass);
            if (elementClass.equals(SeleniumConstants.kClassTrOdd)
                || elementClass.equals(SeleniumConstants.kClassTrEven))
            {

              strings.add(aDriver.findElement(
                  By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]")).getText());
            }
          }
          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .xpath("//div[contains(@class, 'text-right')]/a[2]")));
          element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));
        } catch (Exception e)
        {
          return strings;
        }
      }
      if (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled))
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
        int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          String elementClass = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
              .getAttribute(SeleniumConstants.kAttributeClass);
          if (elementClass.equals(SeleniumConstants.kClassTrOdd) || elementClass.equals(SeleniumConstants.kClassTrEven))
          {
            strings.add(aDriver.findElement(
                By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]")).getText());
          }
        }

        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .xpath("//div[contains(@class, 'text-right')]/a[2]")));
        element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));

      }
    } while (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled));
    return strings;
  }

  public List<Integer> GetAllChanged(WebDriver aDriver, int aPosition)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10);
    WebElement element = null;
    List<Integer> strings = new ArrayList<Integer>();

    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rankingtable")));
          int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[1]/td[1]")).getText()
              .equals("No keywords matched your criteria"))
          {
            return strings;
          }
          for (int i = 1; i <= size; i++)
          {
            if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]")).getAttribute("class")
                .contains("whiterow"))
            {

              if (aDriver
                  .findElement(
                      By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[2]/span"))
                  .getAttribute("class").contains("success"))
              {
                strings.add(Integer.parseInt(aDriver.findElement(
                    By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[2]/span"))
                    .getText()));
              }
              if (aDriver
                  .findElement(
                      By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[2]/span"))
                  .getAttribute("class").contains("danger"))
              {

                int value = Integer.parseInt(aDriver.findElement(
                    By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[2]/span"))
                    .getText());
                strings.add(value - (value * 2));
              }
            }
          }
          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .xpath("//div[contains(@class, 'text-right')]/a[2]")));
          element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));
        } catch (Exception e)
        {
          return strings;
        }
      }
      if (!element.getAttribute("class").contains("disabled"))
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rankingtable")));
        int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]")).getAttribute("class")
              .contains("whiterow"))
          {

            if (aDriver
                .findElement(
                    By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[2]/span"))
                .getAttribute("class").contains("success"))
            {
              strings.add(Integer.parseInt(aDriver.findElement(
                  By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[2]/span"))
                  .getText()));
            }
            if (aDriver
                .findElement(
                    By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[2]/span"))
                .getAttribute("class").contains("danger"))
            {

              int value = Integer.parseInt(aDriver.findElement(
                  By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[2]/span"))
                  .getText());
              strings.add(value - (value * 2));
            }
          }
        }

        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .xpath("//div[contains(@class, 'text-right')]/a[2]")));
        element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));

      }
    } while (!element.getAttribute("class").contains("disabled"));
    return strings;
  }

  public boolean GetDefaultUrls(WebDriver aDriver, int aPosition)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10);
    WebElement element = null;

    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rankingtable")));
          int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[1]/td[1]")).getText()
              .equals("No keywords matched your criteria"))
          {
            return true;
          }
          for (int i = 1; i <= size; i++)
          {
            if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
                .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassWhiterow))
            {
              if (!(aDriver
                  .findElement(
                      By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[1]"))
                  .getText().trim().isEmpty() || aDriver
                  .findElement(
                      By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span/span"))
                  .getAttribute(SeleniumConstants.kAttributeTitle).contains(SeleniumConstants.kTextDropped)))
              {

                try
                {
                  aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span[2]"))
                      .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassExpander);
                } catch (Exception e)
                {
                  try
                  {
                    aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span[3]"))
                        .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassExpander);
                  } catch (Exception ex)
                  {
                    return false;
                  }
                }
              }
            }
          }
          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .xpath("//div[contains(@class, 'text-right')]/a[2]")));
          element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));
        } catch (Exception e)
        {
          return false;
        }
      }
      if (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled))
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
        int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
              .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassWhiterow))
          {
            if (!(aDriver
                .findElement(
                    By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[1]"))
                .getText().trim().isEmpty() || aDriver
                .findElement(
                    By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span/span"))
                .getAttribute(SeleniumConstants.kAttributeTitle).contains(SeleniumConstants.kTextDropped)))
            {

              try
              {
                aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span[2]"))
                    .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassWhiterow);
              } catch (Exception e)
              {
                try
                {
                  aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span[3]"))
                      .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassExpander);
                } catch (Exception ex)
                {
                  return false;
                }
              }
            }
          }
        }
        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .xpath("//div[contains(@class, 'text-right')]/a[2]")));
        element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));

      }
    } while (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled));
    return true;
  }

  public Map<Integer, List<String>> GetBestPositionKR(WebDriver aDriver)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10);
    WebElement element = null;
    Map<Integer, List<String>> map = new LinkedHashMap<Integer, List<String>>();
    List<String> strings = null;
    int number = 0;

    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rankingtable")));
          int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[1]/td[1]")).getText()
              .equals("No keywords matched your criteria"))
          {
            return null;
          }
          for (int i = 1; i <= size; i++)
          {
            // e whiterow
            if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
                .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassWhiterow))
            {
              if (strings != null)
              {
                map.put(number, strings);
              }
              strings = new ArrayList<String>();

              try
              {
                if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span[2]"))
                    .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassWhiterow))
                {
                  number = 1;
                } else
                {
                  number = Integer.parseInt(aDriver.findElement(
                      By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span[2]")).getText());
                }
              } catch (Exception e)
              {
                if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span"))
                    .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassWhiterow))
                {
                  number = 1;
                } else
                {
                  number = Integer.parseInt(aDriver.findElement(
                      By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span[2]")).getText());
                }
              }
            } else if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
                .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kAttributeExpandable)
                && aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
                    .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassHidden))
            {
              strings.add(aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/a"))
                  .getText());
            }
          }
          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .xpath("//div[contains(@class, 'text-right')]/a[2]")));
          element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));
        } catch (Exception e)
        {
          return map;
        }
      }
      if (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled))
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
        int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          // e whiterow
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
              .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassWhiterow))
          {
            if (strings != null)
            {
              map.put(number, strings);
            }
            strings = new ArrayList<String>();

            try
            {
              if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span[2]"))
                  .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassExpander))
              {
                number = 1;
              } else
              {
                number = Integer.parseInt(aDriver.findElement(
                    By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span[2]")).getText());
              }
            } catch (Exception e)
            {
              if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span"))
                  .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassExpander))
              {
                number = 1;
              } else
              {
                number = Integer.parseInt(aDriver.findElement(
                    By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span")).getText());
              }
            }
          } else if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
              .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassExpandable)
              && aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
                  .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassHidden))
          {
            strings.add(aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/a"))
                .getText());
          }
        }
        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .xpath("//div[contains(@class, 'text-right')]/a[2]")));
        element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));

      }
    } while (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled));
    return map;
  }

  public boolean GetAllURLsKR(WebDriver aDriver)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10);
    WebElement element = null;
    Map<Integer, List<String>> map = new LinkedHashMap<Integer, List<String>>();
    List<String> strings = null;
    int number = 0;

    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
          int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[1]/td[1]")).getText()
              .equals("No keywords matched your criteria"))
          {
            return true;
          }
          for (int i = 1; i <= size; i++)
          {
            // e whiterow
            if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
                .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassWhiterow))
            {
              if (strings != null)
              {
                map.put(number, strings);
              }
              strings = new ArrayList<String>();

              try
              {
                if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span[2]"))
                    .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassExpander))
                {
                  number = 1;
                } else
                {
                  number = Integer.parseInt(aDriver.findElement(
                      By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span[2]")).getText());
                }
              } catch (Exception e)
              {
                if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span"))
                    .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassExpander))
                {
                  number = 1;
                } else
                {
                  number = Integer.parseInt(aDriver.findElement(
                      By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span")).getText());
                }
              }
            } else if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
                .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassExpandable))
            {

              if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
                  .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassHidden))
              {
                return false;
              }
              strings.add(aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/a"))
                  .getText());
            }
          }
          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .xpath("//div[contains(@class, 'text-right')]/a[2]")));
          element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));
        } catch (Exception e)
        {
          return false;
        }
      }
      if (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled))
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
        int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          // e whiterow
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
              .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassWhiterow))
          {
            if (strings != null)
            {
              map.put(number, strings);
            }
            strings = new ArrayList<String>();

            try
            {
              if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span[2]"))
                  .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassExpander))
              {
                number = 1;
              } else
              {
                number = Integer.parseInt(aDriver.findElement(
                    By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span[2]")).getText());
              }
            } catch (Exception e)
            {
              if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span"))
                  .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassExpander))
              {
                number = 1;
              } else
              {
                try
                {
                  number = Integer.parseInt(aDriver.findElement(
                      By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span")).getText());
                } catch (Exception ex)
                {
                  if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/span"))
                      .getText().trim().isEmpty())
                  {
                    return false;
                  }
                }
              }
            }
          } else if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
              .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassExpandable))
          {

            if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]"))
                .getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassHidden))
            {
              return false;
            }
            strings.add(aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[3]/a"))
                .getText());
          }
        }
        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .xpath("//div[contains(@class, 'text-right')]/a[2]")));
        element = aDriver.findElement(By.xpath("//div[contains(@class, 'text-right')]/a[2]"));

      }
    } while (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled));
    return true;
  }

  public List<KGSortingElement> GetAllKeywordsFromKG(WebDriver aDriver, int aPosition)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10);
    WebElement element = null;
    List<KGSortingElement> strings = new ArrayList<KGSortingElement>();
    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rankingtable")));
          int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[1]/td[1]")).getText()
              .equals("No keywords matched your criteria"))
          {
            return strings;
          }
          for (int i = 1; i <= size; i++)
          {
            String keyword = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[2]"))
                .getText();
            int value = 0;

            try
            {
              String val = aDriver.findElement(
                  By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[1]"))
                  .getText();

              if (val.contains(","))
              {
                String bef = val.split(",")[0];
                String aft = val.split(",")[1];

                Double power = Math.pow(10, aft.length());
                value = Integer.parseInt(bef) * power.intValue() + Integer.parseInt(aft);
              } else
              {
                value = Integer.parseInt(val);
              }

            } catch (Exception e)
            {
              value = Integer.MIN_VALUE;
            }

            strings.add(new KGSortingElement(keyword, value));
          }

          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .xpath("//div[contains(@class, 'pull-right')]/a[2]")));
          element = aDriver.findElement(By.xpath("//div[contains(@class, 'pull-right')]/a[2]"));
        } catch (Exception e)
        {
          return strings;
        }
      }
      if (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled))
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
        int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          String keyword = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[2]"))
              .getText();
          int value = 0;
          try
          {
            String val = aDriver.findElement(
                By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[1]"))
                .getText();

            if (val.contains(","))
            {
              String bef = val.split(",")[0];
              String aft = val.split(",")[1];

              value = Integer.parseInt(bef) * aft.length() + Integer.parseInt(aft);
            } else
            {
              value = Integer.parseInt(val);
            }

          } catch (Exception e)
          {
            value = Integer.MIN_VALUE;
          }

          strings.add(new KGSortingElement(keyword, value));
        }

        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .xpath("//div[contains(@class, 'pull-right')]/a[2]")));
        element = aDriver.findElement(By.xpath("//div[contains(@class, 'pull-right')]/a[2]"));

      }
    } while (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled));
    return strings;
  }

  public List<KGSortingElement> GetAllKeywordsFromKGTopPositions(WebDriver aDriver, int aPosition)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10);
    WebElement element = null;
    List<KGSortingElement> strings = new ArrayList<KGSortingElement>();
    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rankingtable")));
          int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[1]/td[1]")).getText()
              .equals("No keywords matched your criteria"))
          {
            return strings;
          }
          for (int i = 1; i <= size; i++)
          {
            String keyword = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[1]"))
                .getText();
            int value = 0;
            try
            {
              String val = aDriver.findElement(
                  By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[1]"))
                  .getText();

              if (val.contains(","))
              {
                String bef = val.split(",")[0];
                String aft = val.split(",")[1];

                Double power = Math.pow(10, aft.length());
                value = Integer.parseInt(bef) * power.intValue() + Integer.parseInt(aft);
              } else
              {
                value = Integer.parseInt(val);
              }

            } catch (Exception e)
            {
              value = Integer.MIN_VALUE;
            }

            strings.add(new KGSortingElement(keyword, value));
          }

          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .xpath("//div[contains(@class, 'pull-right')]/a[2]")));
          element = aDriver.findElement(By.xpath("//div[contains(@class, 'pull-right')]/a[2]"));
        } catch (Exception e)
        {
          return strings;
        }
      }
      if (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled))
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
        int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          String keyword = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[1]"))
              .getText();
          int value = 0;
          try
          {
            String val = aDriver.findElement(
                By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[1]"))
                .getText();

            if (val.contains(","))
            {
              String bef = val.split(",")[0];
              String aft = val.split(",")[1];

              value = Integer.parseInt(bef) * aft.length() + Integer.parseInt(aft);
            } else
            {
              value = Integer.parseInt(val);
            }

          } catch (Exception e)
          {
            value = Integer.MIN_VALUE;
          }

          strings.add(new KGSortingElement(keyword, value));
        }

        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .xpath("//div[contains(@class, 'pull-right')]/a[2]")));
        element = aDriver.findElement(By.xpath("//div[contains(@class, 'pull-right')]/a[2]"));

      }
    } while (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled));
    return strings;
  }

  public List<CSSortingElement> GetClickShareFromCS(WebDriver aDriver, int aPosition)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10);
    WebElement element = null;
    List<CSSortingElement> strings = new ArrayList<CSSortingElement>();
    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rankingtable")));
          int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[1]/td[1]")).getText()
              .equals("No keywords matched your criteria"))
          {
            return strings;
          }
          for (int i = 1; i <= size; i++)
          {
            String keyword = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[2]"))
                .getText();
            Double value = 0.0;
            try
            {
              String val = aDriver.findElement(
                  By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[1]"))
                  .getText();

              value = Double.parseDouble(val.replace("%", ""));

            } catch (Exception e)
            {
              value = Double.MIN_VALUE;
            }

            strings.add(new CSSortingElement(keyword, value));
          }

          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .xpath("//div[contains(@class, 'pull-right')]/a[2]")));
          element = aDriver.findElement(By.xpath("//div[contains(@class, 'pull-right')]/a[2]"));
        } catch (Exception e)
        {
          return strings;
        }
      }
      if (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled))
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
        int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          String keyword = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[2]"))
              .getText();
          Double value = 0.0;
          try
          {
            String val = aDriver.findElement(
                By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[1]"))
                .getText();

            value = Double.parseDouble(val.replace("%", ""));

          } catch (Exception e)
          {
            value = Double.MIN_VALUE;
          }

          strings.add(new CSSortingElement(keyword, value));
        }

        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .xpath("//div[contains(@class, 'pull-right')]/a[2]")));
        element = aDriver.findElement(By.xpath("//div[contains(@class, 'pull-right')]/a[2]"));

      }
    } while (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled));
    return strings;
  }

  public List<KGSortingElement> GetAllKeywordsFromCS(WebDriver aDriver, int aPosition)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10);
    WebElement element = null;
    List<KGSortingElement> strings = new ArrayList<KGSortingElement>();
    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
          int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[1]/td[1]")).getText()
              .equals("No keywords matched your criteria"))
          {
            return strings;
          }
          for (int i = 1; i <= size; i++)
          {
            String keyword = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[2]"))
                .getText();
            int value = 0;
            try
            {
              aDriver.findElement(
                  By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[1]/span"))
                  .getText();
              value = Integer.MIN_VALUE;
            } catch (Exception e)
            {
              try
              {
                String val = aDriver.findElement(
                    By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[1]"))
                    .getText();

                if (val.contains(","))
                {
                  String bef = val.split(",")[0];
                  String aft = val.split(",")[1];

                  Double power = Math.pow(10, aft.length());
                  value = Integer.parseInt(bef) * power.intValue() + Integer.parseInt(aft);
                } else
                {
                  value = Integer.parseInt(val);
                }
              } catch (Exception ex)
              {
                value = Integer.MIN_VALUE;
              }
            }

            strings.add(new KGSortingElement(keyword, value));
          }

          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .xpath("//div[contains(@class, 'pull-right')]/a[2]")));
          element = aDriver.findElement(By.xpath("//div[contains(@class, 'pull-right')]/a[2]"));
        } catch (Exception e)
        {
          return strings;
        }
      }
      if (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled))
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
        int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          String keyword = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[2]"))
              .getText();
          int value = 0;
          try
          {
            aDriver.findElement(
                By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[1]"))
                .getText();
            value = Integer.MIN_VALUE;
          } catch (Exception e)
          {
            try
            {
              String val = aDriver.findElement(
                  By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aPosition + "]/div/span[1]/span"))
                  .getText();

              if (val.contains(","))
              {
                String bef = val.split(",")[0];
                String aft = val.split(",")[1];

                Double power = Math.pow(10, aft.length());
                value = Integer.parseInt(bef) * power.intValue() + Integer.parseInt(aft);
              } else
              {
                value = Integer.parseInt(val);
              }
            } catch (Exception ex)
            {
              value = Integer.MIN_VALUE;
            }
          }

          strings.add(new KGSortingElement(keyword, value));
        }

        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .xpath("//div[contains(@class, 'pull-right')]/a[2]")));
        element = aDriver.findElement(By.xpath("//div[contains(@class, 'pull-right')]/a[2]"));

      }
    } while (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled));
    return strings;
  }

  public List<WebCompSortingElement> GetAllSortingElementsFromWebComp(WebDriver aDriver, int aSearchEnginePos,
      int aCompetitorSearchEnginePos)
  {
    WebDriverWait wait = new WebDriverWait(aDriver, 10);
    WebElement element = null;
    List<WebCompSortingElement> strings = new ArrayList<WebCompSortingElement>();
    do
    {
      if (element == null)
      {
        try
        {
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rankingtable")));
          int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
          if (aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[1]/td[1]")).getText()
              .contains("No keywords matched your criteria"))
          {
            return strings;
          }
          for (int i = 1; i <= size; i++)
          {
            String keyword = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[1]"))
                .getText();
            int websitePosition = 0;
            int competitorWebsite = 0;
            try
            {
              websitePosition = Integer.parseInt(aDriver.findElement(
                  By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aSearchEnginePos + "]/div/a"))
                  .getText());
            } catch (Exception e)
            {
              websitePosition = Integer.MAX_VALUE;
            }

            try
            {
              competitorWebsite = Integer.parseInt(aDriver.findElement(
                  By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aCompetitorSearchEnginePos
                      + "]/div/a")).getText());
            } catch (Exception e)
            {
              competitorWebsite = Integer.MAX_VALUE;
            }

            strings.add(new WebCompSortingElement(keyword, websitePosition, competitorWebsite));
          }

          element = wait.until(ExpectedConditions.elementToBeClickable(By
              .xpath("//div[contains(@class, 'pull-right')]/a[2]")));
          element = aDriver.findElement(By.xpath("//div[contains(@class, 'pull-right')]/a[2]"));
        } catch (Exception e)
        {
          return strings;
        }
      }
      if (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled))
      {
        element.click();
        try
        {
          Thread.sleep(5000);
        } catch (Exception e)
        {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SeleniumConstants.kIdRankingTable)));
        int size = aDriver.findElements(By.xpath("//table[@id='rankingtable']/tbody/tr")).size();
        for (int i = 1; i <= size; i++)
        {
          String keyword = aDriver.findElement(By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[1]"))
              .getText();
          int websitePosition = 0;
          int competitorWebsite = 0;
          try
          {
            websitePosition = Integer.parseInt(aDriver.findElement(
                By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aSearchEnginePos + "]/div/a"))
                .getText());
          } catch (Exception e)
          {
            websitePosition = Integer.MAX_VALUE;
          }

          try
          {
            competitorWebsite = Integer.parseInt(aDriver
                .findElement(
                    By.xpath("//table[@id='rankingtable']/tbody/tr[" + i + "]/td[" + aCompetitorSearchEnginePos
                        + "]/div/a")).getText());
          } catch (Exception e)
          {
            competitorWebsite = Integer.MAX_VALUE;
          }
          strings.add(new WebCompSortingElement(keyword, websitePosition, competitorWebsite));
        }

        element = wait.until(ExpectedConditions.elementToBeClickable(By
            .xpath("//div[contains(@class, 'pull-right')]/a[2]")));
        element = aDriver.findElement(By.xpath("//div[contains(@class, 'pull-right')]/a[2]"));
      }
    } while (!element.getAttribute(SeleniumConstants.kAttributeClass).contains(SeleniumConstants.kClassDisabled));
    return strings;
  }

  // 1 - xpath
  // 2 - partialLinkText
  public void RetryClick(WebDriver aDriver, String selector, int typeOfRetry)
  {
    int retryCount = 0;

    while (retryCount < 5)
    {
      try
      {
        if (typeOfRetry == 1)
        {
          aDriver.findElement(By.xpath(selector)).click();
        } else if (typeOfRetry == 2)
        {
          aDriver.findElement(By.partialLinkText(selector)).click();
        }
        return;
      } catch (Exception e)
      {
        retryCount++;
        try
        {
          Thread.sleep(5000);
        } catch (InterruptedException ie)
        {
        }
      }

    }
  }
}
