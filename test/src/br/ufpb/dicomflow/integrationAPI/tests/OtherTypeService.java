package br.ufpb.dicomflow.integrationAPI.tests;

import javax.xml.bind.annotation.XmlRootElement;

import br.ufpb.dicomflow.integrationAPI.message.xml.Service;

@XmlRootElement(name="otherTypeService")
public class OtherTypeService extends Service {
	
	public static final String OTHER_SERVICE_NAME = "Other";
	public static final String OTHER_SERVICE_ACTION = "Type";
	public static final String OTHER_SERVICE_VERSION = "1.0";
	public static final int OTHER_SERVICE = 171;
	
	public OtherTypeService(){
		super.setName(OTHER_SERVICE_NAME);
		super.setAction(OTHER_SERVICE_ACTION);
		super.setVersion(OTHER_SERVICE_VERSION);
		super.setType(OTHER_SERVICE);
	}

}
