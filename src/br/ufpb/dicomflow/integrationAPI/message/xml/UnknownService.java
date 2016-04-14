package br.ufpb.dicomflow.integrationAPI.message.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="service")
public class UnknownService extends Service {
	
	public static final String UNKNOWN_SERVICE_NAME = "Unknown";
	public static final String UNKNOWN_SERVICE_ACTION = "Service";
	public static final String UNKNOWN_SERVICE_VERSION = "1.0";
	public static final int UNKNOWN_SERVICE = -1;
		
	public UnknownService(){
		super.setName(UNKNOWN_SERVICE_NAME);
		super.setAction(UNKNOWN_SERVICE_ACTION);
		super.setVersion(UNKNOWN_SERVICE_VERSION);
		super.setType(UNKNOWN_SERVICE);
	}
	
	public UnknownService(String description){
		this();
		this.description = description;
	}
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	


}
