package de.hsb.smaevers.data;

import java.rmi.Remote;
import java.rmi.RemoteException;

import de.hsb.smaevers.beans.Persons;

public interface PersonsDataTier extends Remote {
	
	public Persons getAllPersons() throws RemoteException;

}
