package br.ufpb.dicomflow.integrationAPI.mail.impl;

import java.util.HashMap;
import java.util.Map;

import br.ufpb.dicomflow.integrationAPI.mail.MessageIF;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public class SMTPMessage implements MessageIF{
	
	private Map<String, Object> mailXTags;
	
	private ServiceIF service;
	
	private byte[] attach;
	
	public SMTPMessage(){
		this.mailXTags = new HashMap<String, Object>();
		this.attach = new byte[]{};
	}

	public Map<String, Object> getMailXTags() {
		return mailXTags;
	}
	
	
	public void setMailXTags(Map<String, Object> mailXTags) {
		this.mailXTags = mailXTags;
	}

	@Override
	public Object getMailTag(String tag) {
		return mailXTags.get(tag);
	}

	@Override
	public void addMailXTag(String tag, Object value){
		mailXTags.put(tag, value);
	}
	
	public ServiceIF getService() {
		return service;
	}

	public void setService(ServiceIF service) {
		this.service = service;
	}

	public byte[] getAttach() {
		return attach;
	}

	public void setAttach(byte[] attach) {
		this.attach = attach;
	}

	
	
	
	

}
