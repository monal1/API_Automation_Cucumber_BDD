package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	
	// Making it static so to use the single instance for the entire execution
	// req variable is shared across all your test cases in that particular execution
	public static RequestSpecification req;
	
	public RequestSpecification requestSepcification() throws IOException {
		
		// if req is null it will create the logging file and if it is not then 
		// simply return the req object
		if(req == null) {
			// Log to separate file
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			
			// Serialization
			// Not required: RestAssured.baseURI = "https://rahulshettyacademy.com";

			// Generalize and reuse code
			// Lets build object of Request Spec Builder, we use set
			req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")) // Add base URi
			.addQueryParam("key", "qaclick123") // Add query parameter
			.setContentType(ContentType.JSON) // Add ContentType in our case it is optional
			.addFilter(RequestLoggingFilter.logRequestTo(log)) // adding request to file
			.addFilter(ResponseLoggingFilter.logResponseTo(log)) // adding response to file
			.build(); // This will build entire request spec build
			return req;
		}
		return req;

	}
	
	public static String getGlobalValue(String key) throws IOException {
		// Creating object for Properties class to access properties data
		Properties prop = new Properties();
		// Read values from the file global.properties
		FileInputStream fis = new FileInputStream("/Users/monmodha1/eclipse-workspace/APIFrameworkMavenCucumber/src/test/java/resources/global.properties");
		prop.load(fis); // Now your prop object have knowledge of file as well
		return prop.getProperty(key);
		
	}
	
	public String getJsonPath(Response response, String key) {
		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(key).toString();
	}
}
