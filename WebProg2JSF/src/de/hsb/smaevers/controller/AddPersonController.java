package de.hsb.smaevers.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import de.hsb.smaevers.beans.Person;
import de.hsb.smaevers.data.PersonsBusinessTier;
import de.hsb.smaevers.data.PersonsBusinessTierFactory;

@RequestScoped
@ManagedBean
public class AddPersonController {
	// TODO: converter für children..

	private PersonsBusinessTier personsBusinessTier;

	private String id;
	private String firstname;
	private String lastname;
	private String residence;
	private List<Person> persons;
	private List<String> children;

	@PostConstruct
	private void init() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		Object obj = ctx.getExternalContext().getApplicationMap().get("PersonsBusinessTier");
		if (obj == null) {
			try {
				personsBusinessTier = PersonsBusinessTierFactory.getPersonsBusinessTierInstance();
				ctx.getExternalContext().getApplicationMap().put("PersonsBusinessTier", personsBusinessTier);
			} catch (RemoteException | NotBoundException e) {
				e.printStackTrace();
			}
		} else {
			personsBusinessTier = (PersonsBusinessTier) obj;
		}

		try {
			persons = personsBusinessTier.getAllPersons().getPersons();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void addPerson() {
		List<Person> childrenList = persons.parallelStream()
				.filter(p -> children.parallelStream().anyMatch(c -> c.equals(p.getId()))).collect(Collectors.toList());

		Person p = new Person(id, firstname, lastname, residence, childrenList);
		try {
			personsBusinessTier.addPerson(p);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void validateId(FacesContext facesContext, UIComponent toValidate, Object object) {
		final String input = (String) object;

		if (persons.parallelStream().anyMatch(p -> p.getId().equals(input))) {
			((UIInput) toValidate).setValid(false);
			FacesMessage errorMessage = new FacesMessage("ID is already in use");
			errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			facesContext.addMessage(toValidate.getClientId(facesContext), errorMessage);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public List<String> getChildren() {
		return children;
	}

	public void setChildren(List<String> children) {
		this.children = children;
	}
}
