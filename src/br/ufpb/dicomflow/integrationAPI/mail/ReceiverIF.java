package br.ufpb.dicomflow.integrationAPI.mail;

import java.util.Iterator;
import java.util.Map;

import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public interface ReceiverIF {
	
	public Iterator<ServiceIF> receive(FilterIF filter);
	public Iterator<byte[]> receiveAttachs(FilterIF filter);
	public Iterator<Map<ServiceIF, byte[]>> receiveServiceAndAttachs(FilterIF filter);

}
