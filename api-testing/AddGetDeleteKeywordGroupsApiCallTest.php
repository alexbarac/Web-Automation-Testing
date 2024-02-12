<?php

class AddGetDeleteKeywordGroupsApiCallTest extends TestBase
{

  const kClientId = 722;
  const kToken = "123";
  const kLimit = "limit";
  const kManualOffset = "manual_offset";
  const kPagesToOffset = "pages_to_offset";
  const kOffset = "offset";
  const kNameFilter = "nameFilter";
  const kGroupName = "grouptest";
  const kUpdatedGroup = "GroupTestUpdated";


  public function testAddKeywordGroups()
  {

    $inputs = array(
      ApiCallParameters::kProjectId => 2,
      ApiCallParameters::kTag => static::kGroupName
    );


    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . AddKeywordGroupApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

  }


  public function testGetKeywordGroups()
  {
    $this->markTestSkipped("TODO - check and fix this test.");

    $inputs = array(
      ApiCallParameters::kProjectId => 2,
      ApiCallParameters::kLimit => 10,
      static::kManualOffset => 0,
      static::kPagesToOffset => array(),
      ApiCallParameters::kOffset => 0,
      ApiCallParameters::kNameFilter => "",
      ApiCallParameters::kSortKey => 0,
      ApiCallParameters::kSortDirection => 1,
      ApiCallParameters::kItems => array(),

    );


    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . GetKeywordGroupsApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

    $results = $decodedResponse[ApiCallResponse::kMessageKey];

    $details = $results['details'];
    $body = $results['body'];
    $header = $results['header'];

    $this->assertEquals("Group", $details["sort_columns"][0]["name"]);
    $this->assertEquals("Keywords", $header[1]["name"]);
    $group_id = array();
    foreach ($body as $item)
    {
      if ($item['name'] == static::kGroupName)
      {
        foreach ($item as $val => $option)
        {
          if ($val == "id")
          {
            array_push($group_id, $option);
            break;
          }
        }

      }
    }

    $this->UpdateKeywordGroups(intval($group_id[0]),static::kUpdatedGroup);
    $this->UpdateKeywordGroups(intval($group_id[0]),static::kGroupName);



    $this->assertEquals(1, count($group_id));

    $this->DeleteKeywordGroups($group_id);

  }

  public function DeleteKeywordGroups($group_id)
  {

    $inputs = array(
      ApiCallParameters::kProjectId => 2,
      ApiCallParameters::kItems => $group_id
    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . DeleteKeywordGroupsApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

    $this->assertNotContains(static::kGroupName, $response);

  }

  public function UpdateKeywordGroups($group_id,$name)
  {

    $inputs = array(
      ApiCallParameters::kProjectId => 2,
      ApiCallParameters::kKeywordGroupID => $group_id,
      ApiCallParameters::kKeywordGroup => $name
    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . UpdateGroupNameApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);


  }

}
