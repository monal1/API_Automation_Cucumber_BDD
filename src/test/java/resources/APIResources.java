package resources;

// enum is a special class in Java which has collection of constants and methods
public enum APIResources {

	// methods
	// my only concern is to return the string when someone calls AddPlaceAPI method
	AddPlaceAPI("/maps/api/place/add/json"), 
	getPlaceAPI("/maps/api/place/get/json"),
	deletePlaceAPI("/maps/api/place/delete/json");

	// Global variable
	private String resource;

	// Note: Constructor declaration needed and it should be after the method
	// declaration otherwise error
	APIResources(String resource) {
		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}

}
