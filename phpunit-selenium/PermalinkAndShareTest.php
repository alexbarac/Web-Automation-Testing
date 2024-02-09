<?php

/**
 * Created by PhpStorm.
 * User: Alex Barac
 * Date: 4/18/2017
 * Time: 9:33 AM
 */
use PHPUnit_Extensions_Selenium2TestCase_Keys as Keys;

class PermalinkAndShareTest extends UtilsSelenium
{

  /**
   * @group acceptance
   *
   */
  public function test_share_modal()
  {
    $this->markTestSkipped("This test has been replaced with cypress test.");
    $this->login();
    $this->openModal(UtilsSelenium::$shareLinkModal, UtilsSelenium::$shareLink);
    sleep(2);
    // only "Websites ranking" is selected by default
    $this->assertSelectorHasText(UtilsSelenium::$permalinkContainer, UtilsSelenium::$keywordRankingPageText);
    //Add existing users
    sleep(2);
//    $this->clickByCssSelector(UtilsSelenium::$permalinkUserDropDown);
//    $this->byCssSelector(UtilsSelenium::$permalinkUserDropDown)->value(Keys::ENTER);
//    $this->seeOnPage(UtilsSelenium::$permalinkUser);
    $this->clickById(UtilsSelenium::$permalinkReportsDropDown);
    $this->seeOnPage('ReportVisibility');
    $this->clickXpathAndWaitSelectorDisappear(UtilsSelenium::$dangerModalButton, UtilsSelenium::$dangerModal);
  }


  /**
   * @group acceptance
   *
   */
  public function test_project_permalink_redirects()
  {
    $this->url(UtilsSelenium::$devUrl . UtilsSelenium::$shareUrl . UtilsSelenium::$userToken);
    $this->url(UtilsSelenium::$devUrl . UtilsSelenium::$keywordRankingUrl);

    $this->seeOnPage(UtilsSelenium::$bcrKeyword);

    $this->url(UtilsSelenium::$devUrl . UtilsSelenium::$websiteRankingUrl);

    $this->seeOnPage(UtilsSelenium::$websitePermalink);
    $this->seeOnPage(UtilsSelenium::$bcrKeyword);

    $this->url(UtilsSelenium::$devUrl . UtilsSelenium::$comparisonSearchEngine);

    $this->seeOnPage(UtilsSelenium::$bcrKeyword);

    $this->url(UtilsSelenium::$devUrl . UtilsSelenium::$comparisonWebsites);

    $this->seeOnPage(UtilsSelenium::$bcrKeyword);

    $this->url(UtilsSelenium::$devUrl . UtilsSelenium::$visibilitySearchEngineUrl);

    $this->seeOnPage(UtilsSelenium::$visibilityScoreLinkText);
    $this->seeOnPage(UtilsSelenium::$estimatedVisitsText);

    $this->url(UtilsSelenium::$devUrl . UtilsSelenium::$visibilityWebsiteUrl);

    $this->seeOnPage(UtilsSelenium::$visibilityScoreLinkText);
    $this->seeOnPage(UtilsSelenium::$estimatedVisitsText);

    $this->url(UtilsSelenium::$devUrl . UtilsSelenium::$visibilityGroupsUrl);

    $this->seeOnPage(UtilsSelenium::$visibilityScoreLinkText);
    $this->seeOnPage(UtilsSelenium::$estimatedVisitsText);

    $this->clickByXpath(UtilsSelenium::$leaveReportsButton);

    $this->seeOnPage(UtilsSelenium::$signInButtonText);
  }

}
