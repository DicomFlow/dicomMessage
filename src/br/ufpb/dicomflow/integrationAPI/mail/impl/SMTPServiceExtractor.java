package br.ufpb.dicomflow.integrationAPI.mail.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailServiceExtractorIF;
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
	public Map<ServiceIF,byte[]> getServiceAndAttach(Message message) {
		
		try {
		
			MailHeadBuilderIF headBuilder = MailHeadBuilderFactory.createHeadStrategy(MailHeadBuilderIF.SMTP_HEAD_STRATEGY);
			
			int contentType = Integer.valueOf(headBuilder.getHeaderValue(message, MailXTags.CONTENT_BUILDER_X_TAG));
			MailContentBuilderIF contentStrategy = MailContentBuilderFactory.createContentStrategy(contentType);
			int serviceType = Integer.valueOf(headBuilder.getHeaderValue(message,MailXTags.SERVICE_TYPE_X_TAG));
	
			Map<ServiceIF,byte[]> serviceAndAttach = contentStrategy.getServiceAndAttach((Multipart) message.getContent(), serviceType);
			
			return serviceAndAttach;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (MessagingException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<Map<ServiceIF,byte[]>> getServicesAndAttachs(List<Message> messages) {
		
		List<Map<ServiceIF,byte[]>> servicesAndattachs = new ArrayList<Map<ServiceIF,byte[]>>();
		
		Iterator<Message> iterator = messages.iterator();
		while (iterator.hasNext()) {
			
			Message message = (Message) iterator.next();
			Map<ServiceIF,byte[]> serviceAndAttach = getServiceAndAttach(message);
			servicesAndattachs.add(serviceAndAttach);

		}
		return servicesAndattachs;
	}

}
