package br.ufpb.dicomflow.integrationAPI.message.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="service")
public class StorageResult extends Service {
	
	public static final String STORAGE_RESULT_NAME = "Storage";
	public static final String STORAGE_RESULT_ACTION = "Result";
	public static final String STORAGE_RESULT_VERSION = "1.0";
	
	private List<Result> result;
	
	public StorageResult(){
		super.setName(STORAGE_RESULT_NAME);
		super.setAction(STORAGE_RESULT_ACTION);
		super.setVersion(STORAGE_RESULT_NAME);
		super.setType(ServiceIF.STORAGE_RESULT);
		
	}
	
	@XmlElementWrapper(name="results")
	public void setResult(List<Result> result) {
		this.result = result;
	}

	public List<Result> getResult() {
		return result;
	}


}
