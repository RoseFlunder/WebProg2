package de.hsb.smaevers.main;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;

import de.hsb.smaevers.data.PersonsDataTier;
import de.hsb.smaevers.data.impl.PersonsDataTierFactory;

public class StartRMI {
	
	public static void main(String[] args) throws RemoteException, AlreadyBoundException{
		
		LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		
		PersonsDataTier pdt = PersonsDataTierFactory.getPersonsDataTierInstance();
		PersonsDataTier stub = (PersonsDataTier) UnicastRemoteObject.exportObject(pdt, 0);
		RemoteServer.setLog(System.out);
		
		Registry registry = LocateRegistry.getRegistry();
		registry.bind(PersonsDataTier.class.getName(), stub);
		
		System.out.println("PersonsDataTier registered");
	}

}
