package br.ufpb.dicomflow.integrationAPI.message.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="url")
public class URL {
	
	private String value;
	private Credentials credentials;
	
	public URL(){
	}
	
	public URL(String value, Credentials credentials){
		this.value = value;
		this.credentials = credentials;
	}

	public String getValue() {
		return value;
	}

	@XmlAttribute
	public void setValue(String value) {
		this.value = value;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}			
	
}
