package br.ufpb.dicomflow.integrationAPI.mail;

import java.util.List;

import javax.mail.Message;

import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public interface MailServiceExtractorIF {
	
	
	public ServiceIF getService(Message message);
	
	public List<ServiceIF> getServices(List<Message> messages); 
	
	

}
