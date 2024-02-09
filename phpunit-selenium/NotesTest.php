<?php

/**
 * Author: Barac Alexandru
 */
class NotesTest extends UtilsSelenium
{
  const lineChartContainerXpath = "(//*[@data-test-chart-container-line])[1]";
  const doneButton = '//*[@data-cy="done-button"]';
  const okButton = '//*[@data-cy="ok-button"]';
  const deleteNoteButton = '//*[@data-test-delete-note]';
  const iconNoteSelector = '[custom-notes-container]';
  const keywordNote = '[data-test-keyword-note]';
  const calendarNoteModal = '#input-date-picker-wrap_0';
  const accessibility = '[data-test-accessibility]';
  const keywordTitleNote = '//*[@data-test-title-note]';
  const urlNotes = '/account/notes';
  const tableNotes = '[data-test-table="notes-table"]';
  const editRowNote = '//*[@data-test-edit-note]';
  const addNote = "//*[@data-test-add]";
  const projectRadio = "//*[@id='projectRadio']";

  /**
   * @group acceptance
   *
   */
  public function testAddChartNotes()
  {
    $this->markTestSkipped("This test has been replaced with cypress test.");
    $projectId = 2;
    $this->login();
    $nameNote = "TestNote";

    $this->url(UtilsSelenium::$devUrl . UtilsSelenium::$keywordRankingUrl . $projectId);
    $this->waitForXpath(UtilsSelenium::$KW_RANKING_TABLE_XPATH);
    $this->clickByXpath(self::$rowTableXpath);
    $this->clickXpathAndWaitSelector(self::lineChartContainerXpath, self::$modalFadeShow);

    $this->waitForSelector(self::calendarNoteModal);

    $this->byXPath(self::keywordTitleNote)->value($nameNote);
    $this->clickXpathAndWaitSelectorDisappear(self::doneButton, self::$modalFadeShow);

    sleep(1);
    $note = $this->byXPath('(//*[@id="custom-notes-container"])[1]');
    sleep(1);
    $this->moveto(array(
      'element' => $note,
      'xoffset' => 10,
      'yoffset' => 10,
    ));
    sleep(1);
    $script = 'return document.querySelector(\'[data-test-edit-note]\').click();';
    $result = $this->execute(array(
      'script' => $script,
      'args' => array()
    ));

    $this->waitForSelector(self::$modalFadeShow);
    $this->clickByXpath(self::deleteNoteButton);

    //add a note using add notes apicall and check if notes manager contains that note also if note can be edited
    self::addNote(USER_L);

    $this->url(UtilsSelenium::$devUrl . self::urlNotes);
    $this->waitForSelector(self::tableNotes);
    $this->assertCountSelector(self::$mainRow, 1);
    // check if note can be edited
    $this->clickXpathAndWaitSelector(self::editRowNote, self::$modalFadeShow);
    $this->clickXpathAndWaitSelectorDisappear(self::doneButton, self::$modalFadeShow);

    // add a project note from notes manager
    $this->clickXpathAndWaitSelector(self::addNote, self::$modalFadeShow);
    $this->byXPath(self::keywordTitleNote)->value($nameNote);
    $this->clickXpathAndWaitSelectorDisappear(self::doneButton, self::$modalFadeShow);
    $this->assertCountSelector(self::$mainRow, 2);

  }

}
