package br.ufpb.dicomflow.integrationAPI.mail;

import java.util.List;

import javax.mail.Message;
import javax.mail.Session;

public interface MailMessageReaderIF {
	
	public static final int SMTP_MESSAGE_STRATEGY = 1;

	public List<Message> getMessages(Session session, FilterIF filter);
	
	//TODO getMesssages em lotes para limitações de ambiente
	
	public int getType();

}
