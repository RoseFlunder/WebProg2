package de.hsb.smaevers.beans;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute
	private String id;
	
	private String firstname;
	private String lastname;
	private String residence;
	
	@XmlElementWrapper(name="children")
	@XmlElement(name="person")
	private List<Person> children;
	
	public Person(){
		
	}

	public Person(String id, String firstname, String lastname, String residence, List<Person> children) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.residence = residence;
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getResidence() {
		return residence;
	}

	public List<Person> getChildren() {
		return children;
	}
	
	

}
