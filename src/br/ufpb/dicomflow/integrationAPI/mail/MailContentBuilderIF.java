package br.ufpb.dicomflow.integrationAPI.mail;

import javax.mail.Message;
import javax.mail.Multipart;

import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public interface MailContentBuilderIF {
	
	public static final int SMTP_SIMPLE_CONTENT_STRATEGY = 1;
	
	public Message buildContent(Message message, ServiceIF service);

	public int getType();

	public ServiceIF getService(Multipart content, int type);
	
}
