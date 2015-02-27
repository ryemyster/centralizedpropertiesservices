/**
 * 
 */
package com.properties.restservices;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.properties.PropertiesServices;
import com.properties.StorageAdapter;
import com.properties.StorageAdapterFactory;
import com.properties.database.DatabaseServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author ryanmcdonald
 * 
 *         TODO: Load Test
 */
@Component
@Path("/propertiesmanager")
/**
 * @author ryanmcdonald
 *
 */
public class PropertiesServiceEndpoint {

	private PropertiesServices propertiesServices;
	private StorageAdapterFactory storageAdapterFactory;

	/**
	 * @return the propertiesServices
	 */
	public PropertiesServices getPropertiesServices() {
		return propertiesServices;
	}

	/**
	 * @param propertiesServices
	 *            the propertiesServices to set
	 */
	public void setPropertiesServices(
			final PropertiesServices propertiesServices) {
		this.propertiesServices = propertiesServices;
	}

	/**
	 * @return String
	 * 
	 *         TODO: add div for status check in HTML
	 */
	@GET
	@Path("/status")
	@Produces(MediaType.TEXT_HTML)
	public String getServerStatus() {
		return "<html><head></head><body> Current Server Time in ms: "
				+ System.currentTimeMillis() + "</body><html>";
	}

	/**
	 * TODO: RE-FACTOR, ERROR HANDLING FOR EXISTING INSTANCES TO BE UPDATED ONLY
	 * AND NOT A NEW ID GENERATED
	 * 
	 * How will instances be created // and use this API?
	 * 
	 */
	@POST
	@Path("/properties/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProperties(final String json) {

		// return no content if empty
		if (json == null || json.isEmpty()) {
			return Response.noContent().build();
		}

		UUID instanceId = UUID.randomUUID();

		Gson gson = new Gson();
		JsonElement je = gson.fromJson(json, JsonElement.class);
		JsonObject jo = je.getAsJsonObject();

		if (jo.get("instance_id") == null) {

			// for now ... add instance id in future using below if else code if
			// not exists
			propertiesServices.addProperties(UUID.randomUUID(), jo.toString());
			// return Response.noContent().build();

		} else if (jo.get("instance_id").isJsonNull()) {
			// if instance id is null, then throw error
			if (propertiesServices.hasProperty(UUID.fromString(jo.get(
					"instance_id").toString()))) {
				propertiesServices.addProperties(instanceId, jo.toString());
			}

		} else {
			jo.add("instance_id", new JsonPrimitive(instanceId.toString()));
			propertiesServices.addProperties(instanceId, jo.toString());
		}

		return Response.ok().build();
	}

	/**
	 * 
	 * TODO: handle instanceId
	 */
	@POST
	@Path("/storageconfig/persist")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updatePersist(
			@QueryParam("persistFile") String persistFile,
			@QueryParam("persistDatabase") String persistDatabase,
			@QueryParam("persistQueue") String persistQueue) {

		StorageAdapter sa = storageAdapterFactory.instanceOf();

		sa.setPersistDatabase(new Boolean(persistDatabase));
		sa.setPersistFile(new Boolean(persistFile));
		sa.setPersistQueue(new Boolean(persistQueue));
		return Response.ok().build();
	}

	/**
	 * @return Response
	 * 
	 *         TODO: Change this function to be /properties/instances
	 * 
	 *         We will need to query database for all available instance Id's
	 *         and change this to return instance ID's
	 * 
	 *         To start store instance ID's in redis database and retrieve from
	 *         that.
	 */
	@GET
	@Path("/properties/instances")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProperties() {

		Logger logger = LoggerFactory.getLogger(this.getClass());

		try {
			// TODO: EDIT THESE CALLS TO NOMECLTURE OF GET_INSTANCE_INFORMATION
			// JSON IS being parsed as a single line. Need to revisit this and
			// use a JSON Object and marshaller.
			List<Object> instanceIds = propertiesServices.getInstanceIds();
			logger.debug("->" + instanceIds.toString());
			logger.debug(new Gson().toJson(instanceIds).toString());

			return Response.ok(new Gson().toJson(instanceIds)).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	/**
	 * @return Response
	 * 
	 * 
	 */
	@GET
	@Path("/properties")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProperties(
			@QueryParam("instanceId") final UUID instanceId) {
		try {
			String jsonString = propertiesServices.getProperties(instanceId);
			return Response.ok(jsonString).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	/**
	 * @return the storageAdapterFactory
	 */
	public StorageAdapterFactory getStorageAdapterFactory() {
		return storageAdapterFactory;
	}

	/**
	 * @param storageAdapterFactory
	 *            the storageAdapterFactory to set
	 */
	public void setStorageAdapterFactory(
			StorageAdapterFactory storageAdapterFactory) {
		this.storageAdapterFactory = storageAdapterFactory;
	}

}
