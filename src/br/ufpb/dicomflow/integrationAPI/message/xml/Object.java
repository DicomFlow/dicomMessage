package br.ufpb.dicomflow.integrationAPI.message.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="object")
public class Object {

	private String type;
	private String id;
	
	private Credentials credential;

	public String getType() {
		return type;
	}
	
	@XmlAttribute
	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	@XmlAttribute
	public void setId(String id) {
		this.id = id;
	}

	public Credentials getCredential() {
		return credential;
	}

	public void setCredential(Credentials credential) {
		this.credential = credential;
	}

	
}
