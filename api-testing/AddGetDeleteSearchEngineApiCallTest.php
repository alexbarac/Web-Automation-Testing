<?php

class AddGetDeleteSearchEngineApiCallTest extends TestBase
{

  const kClientId = 722;
  const kToken = "123";
  const kManualOffset = "manual_offset";
  const kPageToOffset = "pages_to_offset";

  public function testAddSearchEngine()
  {

    $inputs = array(
      ApiCallParameters::kCountry => "FR",
      ApiCallParameters::kGroup => "1",
      ApiCallParameters::kType => "2",
      ApiCallParameters::kLanguage => "fr",
      ApiCallParameters::kLocationType => "anywhere",
      ApiCallParameters::kLocations => "",
      ApiCallParameters::kLocationsLatLong => array(),
      ApiCallParameters::kCountryType => "0",
      ApiCallParameters::kLanguageType => "0",
      ApiCallParameters::kSafeSearchType => "0",
      ApiCallParameters::kChecked => array(),
      ApiCallParameters::kProjectId => 4,
    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . AddSearchEnginesApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);


    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

    $results = $decodedResponse[ApiCallResponse::kMessageKey];

    $se_id = key($results);
    $awr_id = $results[key($results)]['awr_id'];
    $se_type = $results[key($results)]['se_type'];
    $display_name = $results[key($results)]['display_name'];
    $projectId = 4;

    $this->AddProjectToSearchEngine($se_id, $awr_id, $se_type, $display_name, $projectId);

    $this->GetSearchEngineApiCallTest($se_id);
    $this->assertContains(strval($se_id), $response);

    $this->DeleteSearchEngineApiCallTest($se_id);
    $this->assertContains(strval($se_id), $response);

  }

  public function testAddInvalidSearchEngineCountry()
  {

    $inputs = array(
      ApiCallParameters::kCountry => "BBUBU",
      ApiCallParameters::kGroup => "1",
      ApiCallParameters::kType => "2",
      ApiCallParameters::kLanguage => "fr",
      ApiCallParameters::kLocationType => "anywhere",
      ApiCallParameters::kLocations => "",
      ApiCallParameters::kLocationsLatLong => array(),
      ApiCallParameters::kCountryType => "0",
      ApiCallParameters::kLanguageType => "0",
      ApiCallParameters::kSafeSearchType => "0",
      ApiCallParameters::kChecked => array(),
      ApiCallParameters::kProjectId => 4,
    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . AddSearchEnginesApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kInvalidSearchEngineCountry, $decodedResponse[ApiCallResponse::kCodeKey]);

    $results = $decodedResponse[ApiCallResponse::kMessageKey];

    $this->assertContains($results, "Invalid search engine country.");

  }


  public function AddProjectToSearchEngine($se_id, $awr_id, $se_type, $display_name, $projectId)
  {

    $inputs = array(
      ApiCallParameters::kItems => json_encode(array($se_id => array("awr_id" => $awr_id, "se_type" => $se_type, "display_name" => $display_name))),
      ApiCallParameters::kProjectId => $projectId
    );


    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . AddProjectSearchEngineApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);
    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);


  }

  public function GetSearchEngineApiCallTest($id)
  {

    $inputs = array(
      ApiCallParameters::kProjectId => 4,
      ApiCallParameters::kLimit => 10,
      static::kManualOffset => 0,
      static::kPageToOffset => array(),
      ApiCallParameters::kNameFilter => "",
      ApiCallParameters::kSortKey => -1,
      ApiCallParameters::kSortDirection => 1,
      ApiCallParameters::kItems => array($id),


    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . GetSearchEnginesApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

    $results = json_decode($decodedResponse[ApiCallResponse::kMessageKey], true);


  }

  public function DeleteSearchEngineApiCallTest($se_id)
  {

    $inputs = array(
      ApiCallParameters::kProjectId => 4,
      ApiCallParameters::kItems => array($se_id)
    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . DeleteSearchEnginesApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

  }

  public function testGetSearchEngineApiCallWithOffsetTest()
  {
    $inputs = array(
      ApiCallParameters::kProjectId => 371,
      ApiCallParameters::kLimit => 10,
      static::kManualOffset => 0,
      static::kPageToOffset => array(),
      ApiCallParameters::kOffset => 10,
      ApiCallParameters::kNameFilter => "",
      ApiCallParameters::kSortKey => 2,
      ApiCallParameters::kSortDirection => -1,
      ApiCallParameters::kItems => array(),
    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . GetSearchEnginesApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

    $results = $decodedResponse[ApiCallResponse::kMessageKey];
    $body = $results['body'];
    $this->assertEquals(1, count($body));

  }

}
