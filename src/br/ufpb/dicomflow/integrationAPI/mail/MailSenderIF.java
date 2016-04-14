package br.ufpb.dicomflow.integrationAPI.mail;

import java.util.Properties;


public interface MailSenderIF extends SenderIF{
	
	public Properties getProperties();
	
	public MailHeadBuilderIF  getHeadBuilder();
	
	public MailAuthenticatorIF getAuthenticatorBuilder();
	
	public MailContentBuilderIF getContentBuilder();
	

}
