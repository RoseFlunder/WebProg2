package de.hsb.smaevers.data;

import java.rmi.Remote;
import java.rmi.RemoteException;

import de.hsb.smaevers.beans.Person;
import de.hsb.smaevers.beans.Persons;

public interface PersonsBusinessTier extends Remote {
	
	public Persons getAllPersons() throws RemoteException;
	
	public Person getPerson(String id) throws RemoteException;
	
	public Person getPersonByFirstname(String firstname) throws RemoteException;

}
