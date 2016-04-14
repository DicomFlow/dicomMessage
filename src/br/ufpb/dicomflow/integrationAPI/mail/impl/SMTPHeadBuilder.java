package br.ufpb.dicomflow.integrationAPI.mail.impl;

import java.util.Enumeration;

import javax.mail.Address;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public class SMTPHeadBuilder implements MailHeadBuilderIF {

	
	private String from;
	private String to;
	private String domain;
	
	public SMTPHeadBuilder(){
		
	}
	
	public SMTPHeadBuilder(String from, String to, String domain){
		this.from = from;
		this.to = to;
		this.domain = domain;
		
	}
	
	@Override
	public Message buildHead(Message message, ServiceIF service, MailContentBuilderIF contentBuilder) {
        try {
			message.setFrom(new InternetAddress(this.from));
			
			Address[] toUser = InternetAddress.parse(this.to);  
	        message.setRecipients(Message.RecipientType.TO, toUser);
	        
	        message.setSubject(service.getName()+"-"+service.getAction());
	        
	        message.addHeader(MailXTags.MESSAGE_ID_X_TAG, service.getMessageID()+"@"+this.domain);
	        message.addHeader(MailXTags.SERVICE_TYPE_X_TAG, String.valueOf(service.getType()));
			message.addHeader(MailXTags.CONTENT_BUILDER_X_TAG, String.valueOf(contentBuilder.getType()));
	        
			message.addHeader(MailXTags.DISPOSITION_NOTIFICATION_TO_X_TAG, this.from);
			//TODO Usar com PGP
			//message.addHeader(MailXTags.DISPOSITION_NOTIFICARION_KEY_ID_X_TAG, arg1);
	        
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return message;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public int getType(){
		return MailHeadBuilderIF.SMTP_HEAD_STRATEGY;
	}
	
	public String getHeaderValue(Message message, String header) {
		try {

			Enumeration headers = message.getAllHeaders();

			while (headers.hasMoreElements()) {
				Header h = (Header) headers.nextElement();

				if (h.getName().contains(header)) {
					return h.getValue();
				}
			}

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	

}
