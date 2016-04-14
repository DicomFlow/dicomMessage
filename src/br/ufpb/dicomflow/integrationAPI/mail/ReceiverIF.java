package br.ufpb.dicomflow.integrationAPI.mail;

import java.util.Iterator;

import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public interface ReceiverIF {
	
	public Iterator<ServiceIF> receive(FilterIF filter);

}
