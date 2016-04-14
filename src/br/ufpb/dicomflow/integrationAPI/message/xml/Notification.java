package br.ufpb.dicomflow.integrationAPI.message.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="notification")
public class Notification {
	
	private String originalMessageID;
	private String response;
	private String dispositionField;
	private String ErrorCode;
	private String comment;
	
	//TODO Error Codes and DispositionFields
	
	public String getOriginalMessageID() {
		return originalMessageID;
	}
	public void setOriginalMessageID(String originalMessageID) {
		this.originalMessageID = originalMessageID;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getDispositionField() {
		return dispositionField;
	}
	public void setDispositionField(String dispositionField) {
		this.dispositionField = dispositionField;
	}
	public String getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}		

}
