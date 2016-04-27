package de.hsb.smaevers.data.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import de.hsb.smaevers.data.PersonsDataTier;

public class PersonsDataTierFactory {
	
	private static boolean test;
	
	static {		
		Properties config = new Properties();
		try (InputStream input = new FileInputStream("configuration.props")) {
			config.load(input);
			test = "1".equals(config.getProperty("test"));
		} catch (Exception e) {
			e.printStackTrace();
			test = false;
		}
	}
	
	public static PersonsDataTier getPersonsDataTierInstance(){
		return test ? new PersonsDummyDataTier() : new PersonsDataTierImpl();
	}

}
