package de.hsb.smaevers.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import de.hsb.smaevers.beans.Person;
import de.hsb.smaevers.data.PersonsBusinessTier;
import de.hsb.smaevers.data.PersonsBusinessTierFactory;

@ManagedBean
@ViewScoped
public class PersonsController {
	
	private List<Person> persons;
	
	private PersonsBusinessTier personsBusinessTier;
	
	public PersonsController() {
		
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
		
		try {
			persons = personsBusinessTier.getAllPersons().getPersons();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public String showAllPersons(){
		return null;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
}
