package br.ufpb.dicomflow.integrationAPI.mail.impl;

import java.util.Properties;

import br.ufpb.dicomflow.integrationAPI.mail.AbstractMailSender;
import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;

public class SMTPSender extends AbstractMailSender {
	
	private Properties properties;
	private MailAuthenticatorIF authenticatorBuilder;
	private MailHeadBuilderIF headBuilder;
	private MailContentBuilderIF contentBuilder;

	@Override
	public Properties getProperties() {
		return this.properties;
	}

	@Override
	public MailAuthenticatorIF getAuthenticatorBuilder() {
		return this.authenticatorBuilder;
	}

	@Override
	public MailHeadBuilderIF getHeadBuilder() {
		return this.headBuilder;
	}

	@Override
	public MailContentBuilderIF getContentBuilder() {
		return this.contentBuilder;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setAuthenticatorBuilder(MailAuthenticatorIF authenticatorBuilder) {
		this.authenticatorBuilder = authenticatorBuilder;
	}

	public void setHeadBuilder(MailHeadBuilderIF headBuilder) {
		this.headBuilder = headBuilder;
	}

	public void setContentBuilder(MailContentBuilderIF contentBuilder) {
		this.contentBuilder = contentBuilder;
	}

	
	
}
