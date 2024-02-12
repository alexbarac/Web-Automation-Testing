<?php

class GetCountriesApiCallTest extends TestBase
{

  const kClientId = 722;
  const kToken = "123";

  public function testGetCountries()
  {
    $inputs = array(
      ApiCallParameters::kNameFilter => ""
    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . GetCountriesApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

    $results = $decodedResponse[ApiCallResponse::kMessageKey];

    $this->assertGreaterThan(100, count($results));
    $this->assertEquals("USA", $results[1]['country']);
  }

  public function testGetCountriesWithFilter()
  {
    $inputs = array(
      ApiCallParameters::kNameFilter => "USA"
    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . GetCountriesApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

    $results = $decodedResponse[ApiCallResponse::kMessageKey];

    $this->assertEquals("USA", $results[1]['country']);
    $this->assertEquals("us", $results[1]['flag']);
    $this->assertEquals("US", $results[1]['abrev']);
  }

  public function testGetCountriesWithFilterNoMatch()
  {
    $inputs = array(
      ApiCallParameters::kNameFilter => "blabla"
    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . GetCountriesApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

    $results = $decodedResponse[ApiCallResponse::kMessageKey];

    $this->assertEquals(0, count($results));
  }
}
