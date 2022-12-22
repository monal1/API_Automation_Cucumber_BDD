package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;
import java.io.IOException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinition extends Utils {

	// Global variable
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	static String place_id;

	// Create object for TestDataBuild class
	TestDataBuild data = new TestDataBuild();

	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		// Using Request Spec Builder object here
		res = given().spec(requestSepcification())
		// body you have to serialize it and give Java Object of AddPlace Class
		.body(data.addPlacePayLoad(name, language, address));
	}

	@When("User calls {string} with {string} http request")
	public void user_calls_with_post_http_request(String resource, String method) {
		
		// Create Enum object: Constructor will be called with value of resource which you pass
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println("AddPlaceResource: "+resourceAPI.getResource());
		
		// Generalize and reuse code
		// Lets build object of Response Spec Builder, we use expect
	    resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    
	    if(method.equalsIgnoreCase("POST")) {
	    	// when post(resource) On this request req you are submitting something to post
			// To get response back
			response = res.when().post(resourceAPI.getResource());
	    }else if(method.equalsIgnoreCase("GET")) {
	    	// when get(resource) On this request req you are submitting something to get
			// To get response back
	    	response = res.when().get(resourceAPI.getResource());
	    }
		
		// then verify
		// Not here do at other place: .then().spec(resspec).extract().response();
	}

	@Then("The API call is success with status code {int}")
	public void the_api_call_is_success_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(), 200);
	}

	@And("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
	public void keyValue_in_response_body_is_ExpectedValue(String keyValue, String Expectedvalue) {
		assertEquals(getJsonPath(response, keyValue), Expectedvalue);
	}

	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		// Step 1: Prepare request Spec 
		// Get place_id
		place_id = getJsonPath(response,"place_id");
		// Using Request Spec Builder object here
		res = given().spec(requestSepcification()).queryParam("place_id", place_id);
		
		// Step 2: hit getAPI call re-use the method from above instead of writing in feature file
		user_calls_with_post_http_request(resource, "GET");
		
		// Step 3: Get response
		String actualName = getJsonPath(response,"name");
		
		// Step 4: Verify place_Id created maps to name with AAhouse using getPlaceAPI
		assertEquals(actualName, expectedName);
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    // Delete Place payload request with place_id
		res = given().spec(requestSepcification())
		.body(data.deletePlacePayload(place_id));
	}

}
