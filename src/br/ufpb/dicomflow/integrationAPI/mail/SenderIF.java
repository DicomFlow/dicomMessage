package br.ufpb.dicomflow.integrationAPI.mail;

import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;


public interface SenderIF {
	
	public String send(ServiceIF service);

}
