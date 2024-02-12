<?php

class SaveDeleteAliasApiCallTest extends TestBase
{

  const kClientId = 722;
  const kToken = "123";
  const kalias="awr.com";
  const kDomain="domain";
  const kDomainID="domain_id";


  public function testSaveAliasProject()
  {

    $inputs = array(
      ApiCallParameters::kProjectId => 4,
      ApiCallParameters::kAliases => array(ApiCallParameters::kId =>"",ApiCallParameters::kName=>static::kalias,ApiCallParameters::kType=>4,static::kDomainID=>2,static::kDomain=>"www.advancedwebranking.com")
    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . SaveAliasApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);


    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);
    $alias_id = json_decode($decodedResponse[ApiCallResponse::kMessageKey][static::kalias], true);
    $this->DeleteAliasProject($alias_id);
  }

  public function DeleteAliasProject($alias_id)
  {

    $inputs = array(
      ApiCallParameters::kProjectId => 4,
      ApiCallParameters::kAliases => array(ApiCallParameters::kId =>$alias_id,ApiCallParameters::kName=>static::kalias,ApiCallParameters::kType=>1,static::kDomainID=>"2",static::kDomain=>"www.advancedwebranking.com")
    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . DeleteAliasApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

  }

}
