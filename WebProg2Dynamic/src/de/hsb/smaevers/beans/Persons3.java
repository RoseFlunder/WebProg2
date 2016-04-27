package de.hsb.smaevers.beans;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Persons3 {
	
	private Map<String, String> persons = new HashMap<>();

	public Persons3() {
		try {
			
			File file = new File("WEB-INF/persons.xml");
			System.out.println(file.getAbsolutePath());			
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = builder.parse(file);
			
			NodeList personNodes = doc.getElementsByTagName("person");
			
			for (int i = 0; i < personNodes.getLength(); ++i){
				Node node = personNodes.item(i);
				
				if (node.hasChildNodes()){
					String firstName = null, lastName = null;
					for (int j = 0; j < node.getChildNodes().getLength(); ++j){
						Node child = node.getChildNodes().item(j);
						
						if ("firstname".equals(child.getNodeName()))
							firstName = child.getTextContent();
						else if ("lastname".equals(child.getNodeName()))
							lastName = child.getTextContent();
					}
					
					if (firstName != null){
						persons.put(firstName, lastName);
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Map<String, String> getPersons(){
		return persons;
	}

}
