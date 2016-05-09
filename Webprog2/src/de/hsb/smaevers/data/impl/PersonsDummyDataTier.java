package de.hsb.smaevers.data.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import de.hsb.smaevers.beans.Person;
import de.hsb.smaevers.beans.Persons;
import de.hsb.smaevers.data.PersonsDataTier;

public class PersonsDummyDataTier implements PersonsDataTier {

	@Override
	public Persons getAllPersons() {
		List<Person> persons = new ArrayList<>();
		
		persons.add(new Person("1", "Max", "Kaiser", "Bremerhaven", null));
		persons.add(new Person("2", "Ina", "Renke", "Paris", null));
		
		return new Persons(persons);
	}

	@Override
	public void addPerson(Person p) throws RemoteException {
		
	}

}
