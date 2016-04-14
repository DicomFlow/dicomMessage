package br.ufpb.dicomflow.integrationAPI.message.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="service")
public class StorageSave extends Service {
	
	public static final String STORAGE_SAVE_NAME = "Storage";
	public static final String STORAGE_SAVE_ACTION = "Save";
	public static final String STORAGE_SAVE_VERSION = "1.0";
	
	private URL url;
	
	public StorageSave(){
		super.setName(STORAGE_SAVE_NAME);
		super.setAction(STORAGE_SAVE_ACTION);
		super.setVersion(STORAGE_SAVE_VERSION);
		super.setType(ServiceIF.STORAGE_SAVE);
	}
	
	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
	
}