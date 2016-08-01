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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailServiceExtractorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MessageIF;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;
import br.ufpb.dicomflow.integrationAPI.message.xml.UnknownService;

public class SMTPServiceExtractor implements MailServiceExtractorIF {

	@Override
	public ServiceIF getService(Message message) {
		
		try {
		
			MailHeadBuilderIF headBuilder = MailHeadBuilderFactory.createHeadStrategy(MailHeadBuilderIF.SMTP_HEAD_STRATEGY);
			
			int contentType = Integer.valueOf(headBuilder.getHeaderValue(message, MailXTags.CONTENT_BUILDER_X_TAG));
			MailContentBuilderIF contentStrategy = MailContentBuilderFactory.createContentStrategy(contentType);
	
			int serviceType = Integer.valueOf(headBuilder.getHeaderValue(message,MailXTags.SERVICE_TYPE_X_TAG));
			ServiceIF service = contentStrategy.getService((Multipart) message.getContent(), serviceType);
			
			return service;
			
		} catch (IOException e) {
			e.printStackTrace();
			return new UnknownService(e.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			return new UnknownService(e.getMessage());
		}
		
	}

	@Override
	public List<ServiceIF> getServices(List<Message> messages) {
		
		List<ServiceIF> services = new ArrayList<ServiceIF>();
		
		Iterator<Message> iterator = messages.iterator();
		while (iterator.hasNext()) {
			
			Message message = (Message) iterator.next();
			ServiceIF service = getService(message);
			services.add(service);

		}
		return services;
	}
	
	@Override
	public byte[] getAttach(Message message) {
		
		try {
		
			MailHeadBuilderIF headBuilder = MailHeadBuilderFactory.createHeadStrategy(MailHeadBuilderIF.SMTP_HEAD_STRATEGY);
			
			int contentType = Integer.valueOf(headBuilder.getHeaderValue(message, MailXTags.CONTENT_BUILDER_X_TAG));
			MailContentBuilderIF contentStrategy = MailContentBuilderFactory.createContentStrategy(contentType);
	
			byte[] attach = contentStrategy.getAttach((Multipart) message.getContent());
			
			return attach;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (MessagingException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<byte[]> getAttachs(List<Message> messages) {
		
		List<byte[]> attachs = new ArrayList<byte[]>();
		
		Iterator<Message> iterator = messages.iterator();
		while (iterator.hasNext()) {
			
			Message message = (Message) iterator.next();
			byte[] file = getAttach(message);
			attachs.add(file);

		}
		return attachs;
	}
	
	@Override
	public MessageIF getServiceAndAttach(Message message) {
		
		try {
		
			
			
			MailHeadBuilderIF headBuilder = MailHeadBuilderFactory.createHeadStrategy(MailHeadBuilderIF.SMTP_HEAD_STRATEGY);
			
			int contentType = Integer.valueOf(headBuilder.getHeaderValue(message, MailXTags.CONTENT_BUILDER_X_TAG));
			MailContentBuilderIF contentStrategy = MailContentBuilderFactory.createContentStrategy(contentType);
			int serviceType = Integer.valueOf(headBuilder.getHeaderValue(message,MailXTags.SERVICE_TYPE_X_TAG));
			
			MessageIF smtpMessage = new SMTPMessage();
			smtpMessage.setMailXTags(headBuilder.getMailXTags(message));
			smtpMessage.setService(contentStrategy.getService((Multipart) message.getContent(), serviceType));
			smtpMessage.setAttach(contentStrategy.getAttach((Multipart) message.getContent()));
			
			return smtpMessage;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (MessagingException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<MessageIF> getServicesAndAttachs(List<Message> messages) {
		
		List<MessageIF> servicesAndattachs = new ArrayList<MessageIF>();
		
		Iterator<Message> iterator = messages.iterator();
		while (iterator.hasNext()) {
			
			Message message = (Message) iterator.next();
			MessageIF messageIF = getServiceAndAttach(message);
			servicesAndattachs.add(messageIF);

		}
		return servicesAndattachs;
	}

}
