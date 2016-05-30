package de.hsb.smaevers.rest.client;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import de.hsb.smaevers.beans.Person;

public class Webprog2Client {

	public static void main(String[] args) {
		ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);

		add(c);
		delete(c);
	}
	
	public static void delete(Client c){
		WebResource resource = c.resource("http://localhost:8080/Webprog2REST/rest/persons/delete");
		resource.path("/Pu").delete();
	}
	
	public static void add(Client c){
		WebResource resource = c.resource("http://localhost:8080/Webprog2REST/rest/persons/add");
		Person p = new Person("10", "Kai", "Kaiser", "Kaiserhof", new ArrayList<>());
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, p);
		System.out.println(response.getStatus());
	}

}
