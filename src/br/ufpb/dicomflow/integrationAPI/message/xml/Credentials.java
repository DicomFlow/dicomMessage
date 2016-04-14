package br.ufpb.dicomflow.integrationAPI.message.xml;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="credentials")
public class Credentials {
		
	
	private String value;	
	
	public Credentials(){
		
	}

	public Credentials(String value){
		this.value = value;
	}
	
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
		
}
