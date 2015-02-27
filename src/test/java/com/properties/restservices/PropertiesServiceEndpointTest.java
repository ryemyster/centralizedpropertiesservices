/**
 * 
 */
package com.properties.restservices;

import static org.junit.Assert.*;

import org.junit.Test;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * @author ryanmcdonald
 * 
 */
public class PropertiesServiceEndpointTest {

	private static final String BASE_URL = "http://localhost:8080/PropertiesServiceEndpoint-1.0/properties/propertiesmanager";
	private static final String PERSIST_SETTING_PATH = "/storageconfig/persist";
	private static final String PROPERTY_STORAGE = "/properties/add";

	private Client client;
	private boolean queue = false;
	private boolean database = true;
	private boolean file = false;

	/**
	 * 
	 */
	public PropertiesServiceEndpointTest() {
		client = Client.create();
	}

	/**
	 * 
	 */
	public void setProperties() {
		System.out.println("Setting properties...");
		try {

			String json = "{\"instance_id\": \"\",\"instance_url\": \"INSTANCE_HOST_ID_DNS\",\"instance_name\": \"DESCRIPTIVE NAME\",\"properties\": [{\"property_description\": \"DESCRIPTIVE NAME\",\"property_type_id\": \"ID\",\"property_key\": \"KEY\",\"property_value\": \"VALUE\"},{\"property_description\": \"DESCRIPTIVE NAMETYPE\",\"property_type_id\": \"ID\",\"property_key\": \"KEY\",\"property_value\": \"VALUE\"}]}";

			WebResource webResource = client.resource(BASE_URL
					+ PROPERTY_STORAGE);
			ClientResponse cr = webResource.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, json);

			if (cr.getStatus() != 200) {
				throw new Exception("Error: " + cr.toString());
			} else {
				System.out.println("Properties Stored!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void setPersistance() {
		try {
			System.out.println("Setting Persistence...");
			MultivaluedMap<String, String> params = new MultivaluedMapImpl();
			params.add("persistFile", String.valueOf(file));
			params.add("persistDatabase", String.valueOf(database));
			params.add("persistQueue", String.valueOf(queue));

			WebResource webResource = client.resource(BASE_URL
					+ PERSIST_SETTING_PATH);
			ClientResponse cr = webResource.queryParams(params)
					.accept(MediaType.APPLICATION_FORM_URLENCODED)
					.post(ClientResponse.class);

			if (cr.getStatus() != 200) {
				throw new Exception("Error: " + cr.toString());
			} else {
				System.out.println("Persistence Set...");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void checkStatus() {
		try {
			System.out.println("Checking Status...");

			WebResource webResource = client.resource(BASE_URL + "/status");
			ClientResponse cr = webResource.accept(MediaType.TEXT_HTML).get(
					ClientResponse.class);

			if (cr.getStatus() != 200) {
				throw new Exception("Error: " + cr.toString());
			} else {
				System.out.println(cr.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		PropertiesServiceEndpointTest test = new PropertiesServiceEndpointTest();
		test.checkStatus();
		test.setPersistance();
		// for (int i = 0; i < 1; i++) {
		// test.setProperties();
		// }
	}
}
