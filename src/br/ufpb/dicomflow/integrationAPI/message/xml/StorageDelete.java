package br.ufpb.dicomflow.integrationAPI.message.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="service")
public class StorageDelete extends Service {
	
	public static final String STORAGE_DELETE_NAME = "Storage";
	public static final String STORAGE_DELETE_ACTION = "Delete";
	public static final String STORAGE_DELETE_VERSION = "1.0";		
		
	public StorageDelete(){
		super.setName(STORAGE_DELETE_NAME);
		super.setAction(STORAGE_DELETE_ACTION);
		super.setVersion(STORAGE_DELETE_VERSION);
		super.setType(ServiceIF.STORAGE_DELETE);
	}
	
	private List<Object> object;

	public List<Object> getObject() {
		return object;
	}
	
	@XmlElementWrapper(name="objects")
	public void setObject(List<Object> object) {
		this.object = object;
	}

}
