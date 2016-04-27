package de.hsb.smaevers.data;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import de.hsb.smaevers.beans.Person;
import de.hsb.smaevers.beans.Persons;

public class PersonsBusinessTierImpl implements PersonsBusinessTier {

	private PersonsDataTier dataTier; // = PersonsDataTierFactory.getPersonsDataTierInstance();

	public PersonsBusinessTierImpl() throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry();
		dataTier = (PersonsDataTier) registry.lookup(PersonsDataTier.class.getName());
	}
	
	@Override
	public Persons getAllPersons() throws RemoteException {		
		return dataTier.getAllPersons();
	}

	@Override
	public Person getPerson(String id) throws RemoteException {
		Persons allPersons = dataTier.getAllPersons();
		return allPersons.getPersons().stream().filter(p -> id.equals(p.getId())).findFirst().get();
	}

	@Override
	public Person getPersonByFirstname(String firstname) throws RemoteException {
		return dataTier.getAllPersons().getPersons().stream().filter(p -> firstname.equals(p.getFirstname()))
				.findFirst().get();
	}

}
