<?php

class AddGetDeleteKeywordApiCallTest extends TestBase
{

  const kClientId = 722;
  const kToken = "123";
  const kLimit = "limit";
  const kManualOffset = "manual_offset";
  const kPagesToOffset = "pages_to_offset";
  const kOffset = "offset";
  const kNameFilter = "nameFilter";

  const kProjectId = 16;

  public function testSearchKeywordsInclude()
  {
    $inputs = array(
      ApiCallParameters::kKeywordGroup => null,
      ApiCallParameters::kProjectId => 365,
      ApiCallParameters::kLimit => 10,
      ApiCallParameters::kManualOffset => 0,
      ApiCallParameters::kPagesToOffset => array(),
      ApiCallParameters::kOffset => 0,
      ApiCallParameters::kNameFilter => "",
      ApiCallParameters::kSortKey => 1,
      ApiCallParameters::kSortDirection => -1,
      ApiCallParameters::kItems => array(),
      ApiCallParameters::kIsPermaUser => 0,
      ApiCallParameters::kDynamicFilter => array(
        "filter" => array (
          "stringFilter" => array(
            "value" => array("web", "seo"),
            "operation" => 1
          )
        ),
        "id" => -1,
        "name" => ""
      ),
      ApiCallParameters::kSearchOperation => 1, // include keywords
      ApiCallParameters::kMultipleSearchTerms => array());

    $response = $this->GetResponse(self::GetApiCallURL(
      GetKeywordsApiCall::kAction, self::kToken, $inputs));
    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey],
      "Search failed. Unexpected status code! " . $decodedResponse[ApiCallResponse::kMessageKey]);

    $body = $decodedResponse[ApiCallResponse::kMessageKey]["body"];
    $this->assertCount(10, $body, "Unexpected number of keywords.");
    foreach ($body as $item) {
      if ((strpos($item[0], "web") === false) && (strpos($item[0], "seo") === false)) {
        self::fail("Inclusive search failed. Unexpected keyword was included: " . $item[0]);
      }
    }

  }

  public function testSearchKeywordsExclude()
  {
    $inputs = array(
      ApiCallParameters::kKeywordGroup => null,
      ApiCallParameters::kProjectId => 365,
      ApiCallParameters::kLimit => 10,
      ApiCallParameters::kManualOffset => 0,
      ApiCallParameters::kPagesToOffset => array(),
      ApiCallParameters::kOffset => 0,
      ApiCallParameters::kNameFilter => "",
      ApiCallParameters::kSortKey => 1,
      ApiCallParameters::kSortDirection => -1,
      ApiCallParameters::kItems => array(),
      ApiCallParameters::kIsPermaUser => 0,
      ApiCallParameters::kDynamicFilter => array(
        "filter" => array (
          "stringFilter" => array(
            "value" => array("web", "seo"),
            "operation" => 2
          )
        ),
        "id" => -1,
        "name" => ""
      ),
      ApiCallParameters::kSearchOperation => 2, // exclude keywords
      ApiCallParameters::kMultipleSearchTerms => array());

    $response = $this->GetResponse(self::GetApiCallURL(
      GetKeywordsApiCall::kAction, self::kToken, $inputs));
    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey],
      "Search failed. Unexpected status code! " . $decodedResponse[ApiCallResponse::kMessageKey]);

    $body = $decodedResponse[ApiCallResponse::kMessageKey]["body"];
    $this->assertCount(10, $body, "Unexpected number of keywords.");
    foreach ($body as $item) {
      if ((strpos($item[0], "web") !== false) || (strpos($item[0], "seo") !== false)) {
        self::fail("Exclusive search failed. Unexpected keyword was found: " . $item[0]);
      }
    }

  }

  public function testAddKeywords()
  {

    $inputs = array(
      ApiCallParameters::kProjectId => 6,
      ApiCallParameters::kItems => array("key1", "key2"),
      ApiCallParameters::kMarkAsBranded => 0,
      ApiCallParameters::kKeywordGroupID => -1,
      ApiCallParameters::kKeywordGroup => "",

    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . AddKeywordsApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

    $results = json_decode($decodedResponse[ApiCallResponse::kMessageKey], true);
  }

  public function testAddKeywordsContainingPlus()
  {

    $inputs = array(
      ApiCallParameters::kProjectId => 6,
      ApiCallParameters::kItems => array("c++", "c"),
      ApiCallParameters::kMarkAsBranded => 0,
      ApiCallParameters::kKeywordGroupID => -1,
      ApiCallParameters::kKeywordGroup => "",

    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . AddKeywordsApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . urlencode(json_encode($inputs));

    echo $url;

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

    $this->assertEquals(2, count($decodedResponse[ApiCallResponse::kMessageKey]['inserted_keywords']));
  }

  public function testAddKeywordsLowercase()
  {
    // only one keyword should be inserted
    $inputs = array(
      ApiCallParameters::kProjectId => 6,
      ApiCallParameters::kItems => array("key", "KEY"),
      ApiCallParameters::kMarkAsBranded => 0,
      ApiCallParameters::kKeywordGroupID => -1,
      ApiCallParameters::kKeywordGroup => "",

    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . AddKeywordsApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . urlencode(json_encode($inputs));

    echo $url;

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

    $this->assertEquals(1, count($decodedResponse[ApiCallResponse::kMessageKey]['inserted_keywords']));
  }

  public function testGetKeywords()
  {
    $this->markTestSkipped("TODO - check and fix this test.");

    $inputs = array(
      ApiCallParameters::kProjectId => 6,
      ApiCallParameters::kLimit => 10,
      static::kManualOffset => 0,
      static::kPagesToOffset => array(),
      ApiCallParameters::kOffset => 0,
      ApiCallParameters::kNameFilter => "",
      ApiCallParameters::kSortKey => -1,
      ApiCallParameters::kSortDirection => 1,
      ApiCallParameters::kItems => array(),

    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . GetKeywordsApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

    $results = $decodedResponse[ApiCallResponse::kMessageKey];

    $details = $results['details'];
    $body = $results['body'];
    $header = $results['header'];

    $this->assertEquals("Keyword", $details["sort_columns"][0]["name"]);
    $this->assertEquals("Added on", $header[1]["name"]);
    $keyword_id = array();
    foreach ($body as $item) {

      if (in_array("key1", $item) || in_array("key2", $item)) {
        foreach ($item as $val => $option) {
          if ($val == "id")
            array_push($keyword_id, $option);
          break;
        }

      }
    }

    $this->assertEquals(2, count($keyword_id));

    $this->DeleteKeywords($keyword_id);

  }

  public function DeleteKeywords($keywords)
  {

    $inputs = array(
      ApiCallParameters::kProjectId => 6,
      ApiCallParameters::kItems => $keywords
    );


    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . DeleteKeywordsApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

    $this->assertNotContains("key1", $response);
    $this->assertNotContains("key2", $response);

  }


  public function testGetKeywordsBYOffset()
  {

    $inputs = array(
      ApiCallParameters::kProjectId => 371,
      ApiCallParameters::kLimit => 10,
      static::kManualOffset => 0,
      static::kPagesToOffset => array(),
      ApiCallParameters::kOffset => 10,
      ApiCallParameters::kNameFilter => "",
      ApiCallParameters::kSortKey => -1,
      ApiCallParameters::kSortDirection => 1,
      ApiCallParameters::kItems => array(),

    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . GetKeywordsApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

    $results = $decodedResponse[ApiCallResponse::kMessageKey];

    $body = $results['body'];
    $this->assertEquals(10,count($body));
  }

}
