<?php
/**
 * Created by PhpStorm.
 * User: Alex Barac
 * Date: 4/18/2017
 * Time: 9:33 AM
 */
class CompetitorsTest extends UtilsSelenium
{
  public $projectID = "25";

  /**
   * @group acceptance
   *
   */
  public function test_add_delete_competitor()
  {
    $this->markTestSkipped("This test has been replaced with cypress test.");
    //$this->markTestSkipped();
    $this->login();
    $this->url(self::$settingsCompetitorsUrl . $this->projectID);
    $this->openModal(self::$competitorModal, self::$addCompetitorButton);
    $this->byId(self::$IdCompetitorsTextarea)->value(self::$CompetitorA);
    $this->byId(self::$IdCompetitorsTextarea)->value(self::$CompetitorB);

    $this->clickXpathAndWaitSelector(self::$saveCompetitorButton,self::alertMessageSelector);
    $this->seeOnPage("You added 2 new competitors");
    $this->seeOnPage(self::$exportButtonText);
    $this->seeOnPage(self::$CompetitorB);
    $this->clickByXpath(self::$selectAllCheckBox);
    $this->waitForSelector(self::$manageButton);
    $this->clickByXpath(self::$deleteRow);
    $this->waitForSelector(self::$modalFadeShow);
    $this->clickXpathAndWaitSelectorDisappear(self::$dangerModalButton,self::$dangerModal);
    $this->seeOnPage(self::$NoTableRecordsText);
  }

  public function test_competitors_suggestions()
  {
    $this->markTestSkipped("This test has been replaced with cypress test.");
    $projectID = 20;

    $this->login(USER_G);
    $this->url(UtilsSelenium::$devUrl . UtilsSelenium::$settingsCompetitorsUrl . $projectID);
    //Scenario A - show suggestion when add competitor using "Add competitor button"
    $this->openModal(self::$competitorModal, self::$addCompetitorButton);
    $this->showSuggestionsSteps(self::$showSuggestionsButtonCompetitors,self::$selectAllCheckboxShowSuggestionCompetitors,self::$addSuggestionButtonCompetitors);
    //Scenario B - show suggestion when add competitor using option "Get Suggestion"



  }

}
