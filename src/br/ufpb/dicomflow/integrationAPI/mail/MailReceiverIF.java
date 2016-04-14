package br.ufpb.dicomflow.integrationAPI.mail;

import java.util.Properties;

public interface MailReceiverIF extends ReceiverIF{
	
	public Properties getProperties();
	
	public MailAuthenticatorIF getAuthenticatorBuilder();
	
	public MailMessageReaderIF getMessageReader();
	
	public MailServiceExtractorIF getServiceExtractor();
	
}
