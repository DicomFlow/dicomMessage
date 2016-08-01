package br.ufpb.dicomflow.integrationAPI.mail;

import java.util.Map;

import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public interface MessageIF {
	
	public Map<String, Object> getMailXTags();
	
	public void setMailXTags(Map<String, Object> mailXTags);
	
	public Object getMailTag(String tag);
	
	public void addMailXTag(String tag, Object value);

	public ServiceIF getService();

	public void setService(ServiceIF service);

	public byte[] getAttach();

	public void setAttach(byte[] attach);
	
	

}
