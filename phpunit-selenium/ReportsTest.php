<?php
/**
 * Created by PhpStorm.
 * User: Alex Barac
 * Date: 4/18/2017
 * Time: 9:33 AM
 */

class ReportsTest extends ReportSeleniumUtils
{

  /**
   * @group acceptance
   *
   */
  public function test_report_keyword_ranking_table()
  {
    $projectID = '2';
    $this->login();

    $this->url(UtilsSelenium::$devUrl . '/reports/overview?project_id=' . $projectID);

    $tableOptionSelector = '[data-test-label]';

    $this->create_report_from_scratch();
    sleep(1);
    $this->add_widget(0,$tableOptionSelector);
    $xpath = "//*[@data-test-ok-button]";
    $this->clickByXpath($xpath);
    // check if advanced filter exist
    $this->assertCountElementByXpath(self::$addFilterButton, 1);

    sleep(2);
    $this->clickByXpath($xpath);

    $xpath = "//*[@data-test-show-details]";
    $this->waitForXpath($xpath);
  }

  /**
   * @group acceptance
   *
   */
  public function test_create_report_template()
  {
    $projectID = '2';

    $this->login();

    $this->url(UtilsSelenium::$devUrl . '/reports/overview?project_id=' . $projectID);

    $id = 'tempReportOptions-trigger';

    $this->waitUntil(function () use ($id) {
      if (strpos($this->byId($id)->attribute("class"), 'active') != false) {
        return true;
      }
      $this->clickById($id);
      sleep(2);
      return null;
    });

    sleep(3);

    $this->clickByLinkText('Search Visibility Performance');

    $this->seeOnPage('Copy of Search Visibility Performance');
    $this->seeOnPage('Key Performance Indicators', true);

    sleep(2);
    //edit first widget
    $this->clickXpathAndWaitSelector(self::$editWidgetXpath, $this->convertXpathToSelector(self::$okButtonXpath));

    $this->clickByXpath(self::$okButtonXpath);

    $this->waitForXpath(self::$editWidgetXpath);

    //save report
    $this->clickByXpath(UtilsSelenium::$saveReport);

    $this->waitForXpath(UtilsSelenium::$saveReport);

    $this->url(UtilsSelenium::$devUrl . '/reports/overview?project_id=' . $projectID);

    $this->seeOnPage('Copy of Search Visibility Performance');
  }

  /**
   * @group regression
   *
   */
  public function test_report_settings()
  {
    $this->login(USER_C);
    $projectID = '2';

    $this->url(UtilsSelenium::$devUrl . '/reports/overview?project_id=' . $projectID);
    //check edit report
    $xpath = "//*[@data-test-settings-report]";
    $this->clickByXpath($xpath);
    $this->clickByLinkText('Edit report');
    $this->seeOnPage('Save');
    $this->seeOnPage('Go back to Reports');
    $this->seeOnPage('Preview');

    $this->url(UtilsSelenium::$devUrl . '/reports/overview');
    //check duplicate report
    $xpath = "//*[@data-test-settings-report]";
    $this->clickByXpath($xpath);
    $this->clickByLinkText('Duplicate report');
    $this->seeOnPage('Save');
    $this->seeOnPage('Go back to Reports');
    $this->seeOnPage('Preview');

    $this->url(UtilsSelenium::$devUrl . '/reports/overview');
    //create new scheduler
    $xpath = "//*[@data-test-settings-report]";
    $this->clickByXpath($xpath);
    $this->clickByLinkText('Delete report');
    $this->seeOnPage('Are you sure you want to delete selected report?');
  }

  /**
   * @group acceptance
   *
   */
  public function test_edit_report_scheduler()
  {
    $projectID = '2';
    $this->login(USER_I);

    $this->url(UtilsSelenium::$devUrl . '/reports/overview?project_id=' . $projectID);

    //create a scheduler
    $this->clickXpathAndWaitSelector(self::$settingsReportButton, UtilsSelenium::$show);

    $this->clickByLinkText('Create new report scheduler');
    $this->waitForXpath(self::$rowTableScheduler);

    $selector = $this->convertXpathToSelector(self::$rowTableScheduler);
    $this->clickXpathAndWaitSelectorDisappear(self::$saveSchedulerButton, $selector);

    //check if scheduler can be opened
    $this->openModal("schedule-reports", self::$editScheduler);
    $this->waitForXpath(self::$rowTableScheduler);

  }

  /**
   * @group acceptance
   *
   */
  public function test_sorted_order_of_reports(){
    /** This test check the if reports are sorted alphabetically when number of them are more than 10
     */

    $dropdown_selector ='[data-test-report]';

    $this->login(LOGIN_USER);

    $this->url(UtilsSelenium::$devUrl . '/reports/overview');

    $this->assertCountSelector($dropdown_selector,30);
    $this->assertEquals(trim($this->get_reports($dropdown_selector,0)),"All Charts");
    $this->assertEquals(trim($this->get_reports($dropdown_selector,25)),"serptest");
    $this->assertEquals(trim($this->get_reports($dropdown_selector,29)),"zzzzz");

  }

  /**
   * @param string $dropdown_selector
   * @param $report_index
   * @return string
   */
  public function get_reports(string $dropdown_selector, $report_index): string
  {
    $reports = 'return document.querySelectorAll(\'' . $dropdown_selector . '\')[' . $report_index . '].text';
    return $this->execute(array(
      'script' => $reports,
      'args' => array()
    ));
  }


}
