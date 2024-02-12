<?php

class AddGetDeleteAssignTagApiCallTest extends TestBase
{

  const kClientId = 722;
  const kToken = "123";
  const kManualOffset = "manual_offset";
  const kPageToOffset = "pages_to_offset";
  const kTag = "testTag";
  const kUpdatedTag="testTagUpdate";

  public function testAddTag()
  {
    $inputs = array(
      ApiCallParameters::kProjectId => 2,
      ApiCallParameters::kTag => static::kTag,
    );


    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . AddProjectTagsApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);


    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

  }


  public function testGetProjectsTag()
  {
    $this->markTestSkipped("TODO - check and fix this test.");
    $inputs = array(
      ApiCallParameters::kProjectId => 2,
      ApiCallParameters::kLimit => 10,
      static::kManualOffset => 0,
      static::kPageToOffset => array(),
      ApiCallParameters::kOffset => 0,
      ApiCallParameters::kNameFilter => "",
      ApiCallParameters::kSortKey => -1,
      ApiCallParameters::kSortDirection => 1,
      ApiCallParameters::kItems => array(),

    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . GetProjectTagsApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

    $results = $decodedResponse[ApiCallResponse::kMessageKey];

    $details = $results['details'];
    $body = $results['body'];
    $header = $results['header'];

    $this->assertGreaterThanOrEqual(1, count($details));
    $this->assertGreaterThanOrEqual(1, count($body));
    $this->assertGreaterThanOrEqual(1, count($header));

    $this->assertEquals("Tag", $details["sort_columns"][0]["name"]);
    $this->assertEquals("Websites", $header[1]["name"]);
    $tag_id = array();

    foreach ($body as $item) {

      if ($item['name'] == static::kTag) {
        foreach ($item as $val => $option) {
          if ($val == "id") {
            array_push($tag_id, $option);
            break;
          }
        }

      }
    }

    //change name of tag
    $this->UpdateTag(intval($tag_id[0]),static::kUpdatedTag);
    $this->UpdateTag(intval($tag_id[0]),static::kTag);

    $this->assertEquals(1, count($tag_id));

    //assign tag to project
    $this->AssignToProjectTags($tag_id);

    $this->assertContains(static::kTag, $this->GetWebsitesTest());

    //unassign tag from project
    $this->UnAssignToProjectTags(intval($tag_id[0]));

    $this->assertNotContains(static::kTag, $this->GetWebsitesTest());

    $this->DeleteTagTest($tag_id);


  }

  public function AssignToProjectTags($tag_id)
  {

    $inputs = array(
      ApiCallParameters::kProjectId => 2,
      ApiCallParameters::kItems => array("4"),
      ApiCallParameters::kTag => $tag_id,


    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . AssignToProjectTagsApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

  }

  public function GetWebsitesTest()
  {

    $inputs = array(
      ApiCallParameters::kProjectId => 2,
      ApiCallParameters::kItems => array("4"),


    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . GetWebsitesApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);
    return $response;

  }

  public function UnAssignToProjectTags($tag_id)
  {

    $inputs = array(
      ApiCallParameters::kProjectId => 4,
      ApiCallParameters::kTag => $tag_id,


    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . UntagWebsiteApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

  }

  public function DeleteTagTest($tag_id)
  {

    $inputs = array(
      ApiCallParameters::kProjectId => 2,
      ApiCallParameters::kItems => $tag_id
    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . DeleteProjectTagsApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);

    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);


  }

  public function UpdateTag($tag_id,$name)
  {



    $inputs = array(
      ApiCallParameters::kProjectId => 2,
      ApiCallParameters::kTag => $tag_id,
      ApiCallParameters::kName => $name,

    );

    $url = static::GetApiTestsEndpoint() . "?" . ApiCallParameters::kAction . "=" . UpdateTagNameApiCall::kAction
      . "&" . ApiCallParameters::kToken . "=" . static::kToken . "&" . ApiCallParameters::kInputs . "=" . json_encode($inputs);


    $response = $this->GetResponse($url);

    $decodedResponse = json_decode($response, true);
    $this->assertEquals(ApiResponseCodes::kOK, $decodedResponse[ApiCallResponse::kCodeKey], "Unexpected status code! " . $decodedResponse[ApiCallParameters::kMessage]);

  }

}
