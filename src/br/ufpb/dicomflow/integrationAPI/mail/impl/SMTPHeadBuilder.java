/*
 * 	This file is part of DicomFlow.
 * 
 * 	DicomFlow is free software: you can redistribute it and/or modify
 * 	it under the terms of the GNU General Public License as published by
 * 	the Free Software Foundation, either version 3 of the License, or
 * 	(at your option) any later version.
 * 
 * 	This program is distributed in the hope that it will be useful,
 * 	but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * 	GNU General Public License for more details.
 * 
 * 	You should have received a copy of the GNU General Public License
 * 	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package br.ufpb.dicomflow.integrationAPI.mail.impl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
	        
	        message.addHeader(MailXTags.MESSAGE_ID_X_TAG, MailXTags.buildMessageIDXTag(service.getMessageID(),this.domain));
	        message.addHeader(MailXTags.SERVICE_TYPE_X_TAG, String.valueOf(service.getType()));
			message.addHeader(MailXTags.CONTENT_BUILDER_X_TAG, String.valueOf(contentBuilder.getType()));
	        
			message.addHeader(MailXTags.DISPOSITION_NOTIFICATION_TO_X_TAG, this.from);
	        
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

	@Override
	public Map<String, Object> getMailXTags(Message message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(MailXTags.CONTENT_BUILDER_X_TAG, getHeaderValue(message, MailXTags.CONTENT_BUILDER_X_TAG));
		map.put(MailXTags.DISPOSITION_NOTIFICARION_KEY_ID_X_TAG, getHeaderValue(message, MailXTags.DISPOSITION_NOTIFICARION_KEY_ID_X_TAG));
		map.put(MailXTags.DISPOSITION_NOTIFICATION_TO_X_TAG, getHeaderValue(message, MailXTags.DISPOSITION_NOTIFICATION_TO_X_TAG));
		map.put(MailXTags.HEAD_BUILDER_X_TAG, getHeaderValue(message, MailXTags.HEAD_BUILDER_X_TAG));
		map.put(MailXTags.MESSAGE_ID_X_TAG, getHeaderValue(message, MailXTags.MESSAGE_ID_X_TAG));
		map.put(MailXTags.SERVICE_TYPE_X_TAG, getHeaderValue(message, MailXTags.SERVICE_TYPE_X_TAG));
		return map;
	}
	
	
	

}
