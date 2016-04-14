package br.ufpb.dicomflow.integrationAPI.message.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="service")
public class StorageUpdate extends Service {
	
	public static final String STORAGE_UPDATE_NAME = "Storage";
	public static final String STORAGE_UPDATE_ACTION = "Update";
	public static final String STORAGE_UPDATE_VERSION = "1.0";
		
	private Object object;
	private URL url;
	
	public StorageUpdate(){
		super.setName(STORAGE_UPDATE_NAME);
		super.setAction(STORAGE_UPDATE_ACTION);
		super.setVersion(STORAGE_UPDATE_VERSION);
		super.setType(ServiceIF.STORAGE_UPDATE);
		
	}
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public URL getUrl() {
		return url;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	
	

	
}
