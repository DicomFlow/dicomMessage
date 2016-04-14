package br.ufpb.dicomflow.integrationAPI.tests;

import javax.xml.bind.annotation.XmlRootElement;

import br.ufpb.dicomflow.integrationAPI.message.xml.Service;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

@XmlRootElement(name="service")
public class LimitedStorageSave extends Service {
	
	public static final String STORAGE_SAVE_NAME = "Storage";
	public static final String STORAGE_SAVE_ACTION = "LimitedSave";
	public static final String STORAGE_SAVE_VERSION = "1.0";
	
	public LimitedStorageSave(){
		super.setName(STORAGE_SAVE_NAME);
		super.setAction(STORAGE_SAVE_ACTION);
		super.setVersion(STORAGE_SAVE_VERSION);
		super.setType(ServiceIF.STORAGE_SAVE);
	}

}
