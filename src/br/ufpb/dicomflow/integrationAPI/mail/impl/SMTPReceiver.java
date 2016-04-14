package br.ufpb.dicomflow.integrationAPI.mail.impl;

import java.util.Properties;

import br.ufpb.dicomflow.integrationAPI.mail.AbstractMailReceiver;
import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailMessageReaderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailServiceExtractorIF;

public class SMTPReceiver extends AbstractMailReceiver{
	
	private Properties properties;
	private MailAuthenticatorIF authenticatorBuilder;
	private MailMessageReaderIF messageReader;
	private MailServiceExtractorIF serviceExtractor;
	
	@Override
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	@Override
	public MailAuthenticatorIF getAuthenticatorBuilder() {
		return authenticatorBuilder;
	}
	public void setAuthenticatorBuilder(MailAuthenticatorIF authenticatorBuilder) {
		this.authenticatorBuilder = authenticatorBuilder;
	}
	
	@Override
	public MailMessageReaderIF getMessageReader() {
		return messageReader;
	}
	public void setMessageReader(MailMessageReaderIF messageBuilder) {
		this.messageReader = messageBuilder;
	}
	
	@Override
	public MailServiceExtractorIF getServiceExtractor() {
		return serviceExtractor;
	}
	
	public void setServiceExtractor(MailServiceExtractorIF serviceExtractor) {
		this.serviceExtractor = serviceExtractor;
	}
	
	
	
	

	

}
