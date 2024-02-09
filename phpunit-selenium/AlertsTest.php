<?php

/**
 * Created by PhpStorm.
 * User: Alex Barac
 * Date: 4/18/2017
 * Time: 9:33 AM
 */

class AlertsTest extends UtilsSelenium
{

  const addAlertButtonXpath = "//*[@data-test-add]";
  const alertUrl = "/alerts/overview";
  const selectProjectsXpath = "//*[@data-test-select-projects]";
  const metricDropDownXpath = '//*[@data-test-keywordgroup-dropdown]';
  const inputText = '//*[@data-test-input-text]';
  const secondInputText = '//*[@data-test-second-input-text]';
  const inputTextAlertName = '//*[@data-test-alert-name]';
  const saveButton = '//*[@data-test-save-button]';
  const alertDropDown = 'alertsDropdown';
  const addTrigger = '//*[@data-test-add-trigger]';
  const checkboxSpecificUrl = '//*[@data-test-checkbox-specific-url]';
  const inputSpecUrl = '//*[@data-test-input-spec-url]';
  const tagsRadio = '//*[@data-test-tags-radio]';
  const projectsRadio = '//*[@data-test-projects-radio]';
  const selectProjectsDropDown = '//*[@data-test-select-projects]';
  const selectKeywordGroupDropDown = '(//*[@data-test-selectable-column-dropdown])[2]';
  const selectCustomKeywordsDropDown = '(//*[@data-test-selectable-column-dropdown])[1]';
  const selectKeywordDropDown = '//*[@data-test-select-keyword]';
  const dropDownItemXpath = '//*[@data-test-dropdown-item]';
  const checkboxAlertTable = "(//*[@data-test-select-checkbox='alerts-table'])[1]";
  const editAlert = '//*[@data-test-edit-alert]';
  const duplicateAlert = '//*[@data-test-duplicate-alert]';
  const enableAlert = '//*[@data-test-enable-alert]';
  const okModalButton = '#confirmModal_ok';


  /**
   * @group acceptance
   *
   */
  public function test_add_alert()
  {

    $elementsList = array(
      //array(UtilsSelenium::$baseDropDown_1, 'Google.com Desktop Organic'),
      array(UtilsSelenium::$baseDropDown_2, 'amazon.com'),
      array(self::metricDropDownXpath, "Visibility Score"),
      array(UtilsSelenium::$baseDropDown_3, 'Is less than or equal to')

    );

    $this->login(USER_M);

    $this->url(UtilsSelenium::$devUrl . self::alertUrl);

    //check if notification alert dropdown exist in dom
    $this->byId(self::alertDropDown);

    // check if template alerts exists
    $this->assertCountSelector(UtilsSelenium::$mainRow, 5);

    $this->clickByXpath(self::addAlertButtonXpath);

    $this->waitForXpath(self::selectProjectsXpath);

    //change dropdown items
    foreach ($elementsList as $value)
      $this->changeDropDownValue($value[0], $value[1]);

    $this->waitForXpath(self::inputText);
    $this->byXPath(self::inputText)->value("10");

    $this->byXPath(self::inputTextAlertName)->value("Check Visibility Score");

    $this->clickByXpath(self::saveButton);

    $this->seeOnPage("Your latest alert has been successfully created.");

    $this->seeOnPage("Check Visibility Score");

    //edit Alert
    $this->url(UtilsSelenium::$devUrl . '/create-alert?id=6');

    foreach ($elementsList as $value)
      $this->assertXpathHasText($value[0], $value[1]);

  }

  public function changeDropDownValue($dropDown, $expectedValue)
  {

    $this->waitUntil(function () use ($dropDown, $expectedValue) {
      try {
        if ($this->byCssSelector('.dropdown-menu.show')->displayed()) {
          sleep(0.5);
          if ($dropDown === self::metricDropDownXpath) {
            $this->clickByXpath($this->findXpathBySpan($expectedValue));
          } else {
            $this->clickByLinkText($expectedValue);

          }

          return true;
        }
      } catch (PHPUnit_Extensions_Selenium2TestCase_WebDriverException $e) {
        $this->clickByXpath($dropDown);
      }
      return null;
    }, 60000);
  }

  /**
   * @group acceptance
   *
   */
  public function test_alert_options()
  {

    $this->login(USER_M);

    $options = array('Edit', 'Duplicate', 'Enable');

    foreach ($options as $index => $option) {
      $this->url(UtilsSelenium::$devUrl . self::alertUrl);
      $this->clickXpathAndWaitSelector(self::checkboxAlertTable, UtilsSelenium::$manageButton);

      switch ($option) {
        case 'Edit':
          $this->clickByXpath(Self::editAlert);
          $this->waitForXpath(self::inputTextAlertName);
          break;
        case 'Duplicate':
          $this->clickByXpath(Self::duplicateAlert);
          $this->waitForXpath(self::inputTextAlertName);
          break;
        case 'Enable':
          $this->clickByXpath(Self::enableAlert);
          $this->clickByCssSelector(self::okModalButton);
          $this->seeOnPage('You enabled 1 alerts.');
          break;
        case 'Delete':
          sleep(2);
          $this->clickByXpath(UtilsSelenium::$deleteRow);
          $this->clickByXpath(UtilsSelenium::$dangerModalButton);
          $this->seeOnPage('You deleted 1 alert!');
          $this->assertCountSelector(UtilsSelenium::$mainRow, 4);
          break;

      }
    }

  }
}
