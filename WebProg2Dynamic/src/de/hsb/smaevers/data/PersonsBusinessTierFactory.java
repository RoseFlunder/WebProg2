package de.hsb.smaevers.data;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class PersonsBusinessTierFactory {
	
	public static PersonsBusinessTier getPersonsBusinessTierInstance() throws RemoteException, NotBoundException{
		return new PersonsBusinessTierImpl();
	}

}
