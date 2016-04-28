package br.ufpb.dicomflow.integrationAPI.mail;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;

public interface MailReceiverIF extends ReceiverIF{
	
	public Properties getProperties();
	
	public MailAuthenticatorIF getAuthenticatorBuilder();
	
	public MailMessageReaderIF getMessageReader();
	
	public MailServiceExtractorIF getServiceExtractor();
	
	public List<Message> getMessages(FilterIF filter);
	
}
