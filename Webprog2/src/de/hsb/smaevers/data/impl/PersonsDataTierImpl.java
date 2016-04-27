package de.hsb.smaevers.data.impl;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import de.hsb.smaevers.beans.Persons;
import de.hsb.smaevers.data.PersonsDataTier;

public class PersonsDataTierImpl implements PersonsDataTier {

	private Persons persons;

	public PersonsDataTierImpl() {
		File file = new File("persons.xml");

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Persons.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			persons = (Persons) unmarshaller.unmarshal(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Persons getAllPersons() {
		return persons;
	}

}
