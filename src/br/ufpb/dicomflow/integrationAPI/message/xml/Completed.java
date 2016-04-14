package br.ufpb.dicomflow.integrationAPI.message.xml;

import javax.xml.bind.annotation.XmlAttribute;

public class Completed {
	
	private String status;
	private String completedMessage;
	
	public String getStatus() {
		return status;
	}
	
	@XmlAttribute
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCompletedMessage() {
		return completedMessage;
	}

	public void setCompletedMessage(String completedMessage) {
		this.completedMessage = completedMessage;
	}	

}
