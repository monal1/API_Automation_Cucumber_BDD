package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	// Place tagName and keep inside the @Before keyword so that cucumber understands that
	// before I actually run that deletePlace scenario I have to execute this method beforeScenario()
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		// Write a code that will give you place id
		// execute this code only when place id is null
		
		// Create object for StepDefinition
		StepDefinition m = new StepDefinition();
		
		// Run this code only when place_id is equal to null
		// Because place_id is static variable so we call using className StepDefinition
		// If not static then do m.place_id
		if(StepDefinition.place_id==null) {
			m.add_place_payload_with("Shetty", "French", "Asia");
			m.user_calls_with_post_http_request("AddPlaceAPI", "POST");
			m.verify_place_id_created_maps_to_using("Shetty", "getPlaceAPI");
		}

	}
}
