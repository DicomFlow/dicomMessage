package br.ufpb.dicomflow.integrationAPI.mail;

import javax.mail.Message;

import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public interface MailHeadBuilderIF {
	
	public static final int SMTP_HEAD_STRATEGY = 1;

	public Message buildHead(Message message, ServiceIF service, MailContentBuilderIF contentBuilder);
	
	public String getHeaderValue(Message message, String header);
	
	public int getType();
	
	public String getFrom();

	public void setFrom(String from);

	public String getTo();

	public void setTo(String to);

	public String getDomain();

	public void setDomain(String domain);

}
