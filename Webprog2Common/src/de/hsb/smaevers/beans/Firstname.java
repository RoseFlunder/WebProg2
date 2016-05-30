package de.hsb.smaevers.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Firstname implements Serializable {
	private static final long serialVersionUID = 1L;
	private String firstname;
	
	public Firstname() {
	}
	
	public Firstname(String firstname){
		this.firstname = firstname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
}
