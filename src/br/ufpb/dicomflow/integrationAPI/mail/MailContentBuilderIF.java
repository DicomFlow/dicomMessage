package br.ufpb.dicomflow.integrationAPI.mail;

import java.io.File;
import java.util.Map;

import javax.mail.Message;
import javax.mail.Multipart;

import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public interface MailContentBuilderIF {
	
	public static final int SMTP_SIMPLE_CONTENT_STRATEGY = 1;
	
	public static final int SMTP_CIPHER_CONTENT_STRATEGY = 2;
	
	public Message buildContent(Message message, ServiceIF service);
	
	public Message buildContent(Message message, ServiceIF service, File attachment);

	public int getType();

	public ServiceIF getService(Multipart content, int type);

	public byte[] getAttach(Multipart content);
	
	public Map<ServiceIF, byte[]> getServiceAndAttach(Multipart content, int type);
	
}
