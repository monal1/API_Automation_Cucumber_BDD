Feature: Validating Place API's 
@AddPlace @Regression
Scenario Outline: Verify if Place is being Successfully added using AddPlaceAPI 
	Given Add Place Payload with "<name>" "<language>" "<address>"
	When User calls "AddPlaceAPI" with "Post" http request 
	Then The API call is success with status code 200 
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify place_Id created maps to "<name>" using "getPlaceAPI"
	
	
Examples: 
	|name    | language | address           |
	|AAhouse | English  | World cross center|
#	|BBhouse | Spanish  | Sea cross center  |
	
@DeletePlace @Regression
Scenario: Verify id Delete Place functionality is working
	Given DeletePlace Payload
	When User calls "deletePlaceAPI" with "Post" http request 
	Then The API call is success with status code 200 
	And "status" in response body is "OK"