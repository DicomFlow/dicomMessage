package br.ufpb.dicomflow.integrationAPI.mail;

import java.util.List;
import java.util.Map;

import javax.mail.Message;

import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public interface MailServiceExtractorIF {
	
	
	public ServiceIF getService(Message message);
	
	public List<ServiceIF> getServices(List<Message> messages); 
	
	public byte[] getAttach(Message message);
	
	public List<byte[]> getAttachs(List<Message> messages);
	
	public Map<ServiceIF,byte[]> getServiceAndAttach(Message message);
	
	public List<Map<ServiceIF,byte[]>> getServicesAndAttachs(List<Message> messages);
	
	

}
