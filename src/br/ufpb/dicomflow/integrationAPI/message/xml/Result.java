package br.ufpb.dicomflow.integrationAPI.message.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="result")
public class Result {
	
	private String originalMessageID;
	private String timestamp;
	private List<Object> objects;
	private Completed completed;
	
		
	public String getOriginalMessageID() {
		return originalMessageID;
	}

	public void setOriginalMessageID(String originalMessageID) {
		this.originalMessageID = originalMessageID;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public List<Object> getObjects() {
		return objects;
	}

	@XmlElementWrapper(name="objects")
	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}

	public Completed getCompleted() {
		return completed;
	}

	public void setCompleted(Completed completed) {
		this.completed = completed;
	}		
	
}
