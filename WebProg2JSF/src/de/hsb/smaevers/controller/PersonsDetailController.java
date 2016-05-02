package de.hsb.smaevers.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import de.hsb.smaevers.beans.Person;
import de.hsb.smaevers.data.PersonsBusinessTier;
import de.hsb.smaevers.data.PersonsBusinessTierFactory;

@ManagedBean
@RequestScoped
public class PersonsDetailController {
	
	@ManagedProperty("#{param.id}")
	private String id;
	
	@ManagedProperty("#{param.firstname}")
	private String firstname;
	
	private Person person;
	
	private PersonsBusinessTier personsBusinessTier;
	
	public PersonsDetailController() {
		
	}
	
	@PostConstruct
	private void init(){
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		Object obj = ctx.getExternalContext().getApplicationMap().get("PersonsBusinessTier");
		if (obj == null){
			try {
				personsBusinessTier = PersonsBusinessTierFactory.getPersonsBusinessTierInstance();
				ctx.getExternalContext().getApplicationMap().put("PersonsBusinessTier", personsBusinessTier);
			} catch (RemoteException | NotBoundException e) {
				e.printStackTrace();
			}
		} else {
			personsBusinessTier = (PersonsBusinessTier) obj;
		}
		
		if (firstname != null){
			try {
				person = personsBusinessTier.getPersonByFirstname(firstname);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else if (id != null){
			try {
				person = personsBusinessTier.getPerson(id);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
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
}
