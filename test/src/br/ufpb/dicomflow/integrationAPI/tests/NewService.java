package br.ufpb.dicomflow.integrationAPI.tests;

import javax.xml.bind.annotation.XmlRootElement;

import br.ufpb.dicomflow.integrationAPI.message.xml.Service;

@XmlRootElement(name="service")
public class NewService extends Service{
	
	public static final String NEW_SERVICE_NAME = "New";
	public static final String NEW_SERVICE_ACTION = "Service";
	public static final String NEW_SERVICE_VERSION = "1.0";
	public static final int NEW_SERVICE = 666;
	
	public NewService(){
		super.setName(NEW_SERVICE_NAME);
		super.setAction(NEW_SERVICE_ACTION);
		super.setVersion(NEW_SERVICE_VERSION);
		super.setType(NEW_SERVICE);
	}

}
