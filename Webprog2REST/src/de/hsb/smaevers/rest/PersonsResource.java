package de.hsb.smaevers.rest;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.spi.resource.Singleton;

import de.hsb.smaevers.beans.Firstname;
import de.hsb.smaevers.beans.Person;
import de.hsb.smaevers.beans.Persons;
import de.hsb.smaevers.data.PersonsDataTier;

@Singleton
@Path("persons")
public class PersonsResource {

	private Persons persons;
	
	@Context
	private ServletContext context;

	@PostConstruct
	private void init() {
		String file = context.getInitParameter("personsFile");
		PersonsDataTier pdt = new PersonsDataTierImpl(file);
		try {
			persons = pdt.getAllPersons();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	@GET
	@Path("/all")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=UTF-8", MediaType.TEXT_XML + "; charset=UTF-8" })
	public List<Person> getAllPersons() {
		return persons.getPersons();
	}
	
	@GET
	@Path("/all")
	@Produces({ MediaType.TEXT_HTML })
	public Viewable getAllPersonsHtml() {
		return new Viewable("persons.jspx", persons);
	}

	@GET
	@Path("/all/firstnames")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Firstname> getAllFirstnames() {
		List<Firstname> firstnames = persons.getPersons().stream().map(p -> new Firstname(p.getFirstname()))
				.collect(Collectors.toList());
		return firstnames;
	}

	@GET
	@Path("/firstname/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPersonByFirstname(@PathParam("value") String value) {
		return persons.getPersons().parallelStream().filter(p -> value.equals(p.getFirstname())).findFirst().get();
	}
	
	@GET
	@Path("/firstname/{value}")
	@Produces(MediaType.TEXT_HTML)
	public Viewable getPersonByFirstnameHtml(@PathParam("value") String value) {
		Person person = persons.getPersons().parallelStream().filter(p -> value.equals(p.getFirstname())).findFirst().get();
		return new Viewable("person.jspx", person);
	}
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPerson(Person p){
		persons.getPersons().add(p);
		return Response.status(Status.CREATED).build();
	}
	
	@DELETE
	@Path("/delete/{value}")
	public Response deletePerson(@PathParam("value") String firstname){
		boolean removed = persons.getPersons().removeIf(p -> p.getFirstname().equals(firstname));
		
		if (removed)
			return Response.ok().build();
		
		return Response.status(Status.NOT_FOUND).build();
	}

}
