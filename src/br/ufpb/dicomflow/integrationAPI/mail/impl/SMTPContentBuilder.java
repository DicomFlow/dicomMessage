package br.ufpb.dicomflow.integrationAPI.mail.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.rmi.UnmarshalException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;
import br.ufpb.dicomflow.integrationAPI.main.ServiceFactory;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;
import br.ufpb.dicomflow.integrationAPI.message.xml.UnknownService;
import br.ufpb.dicomflow.integrationAPI.tests.NewService;

public class SMTPContentBuilder implements MailContentBuilderIF {

	@Override
	public Message buildContent(Message message, ServiceIF service) {
		Multipart multipart = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(service.getClass());
			
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter str = new StringWriter();
			
			jaxbMarshaller.marshal(service, str);
			
			multipart = new MimeMultipart();
            
            MimeBodyPart attachment0 = new MimeBodyPart();
            
            attachment0.setContent(str.toString(),"text/xml; charset=UTF-8");
            
            multipart.addBodyPart(attachment0);
            
            message.setContent(multipart);
            
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
		return message;

	}
	
	public int getType(){
		return MailContentBuilderIF.SMTP_SIMPLE_CONTENT_STRATEGY;
	}

	@Override
	public ServiceIF getService(Multipart content, int type) {
		try {
			for (int i = 0; i < content.getCount(); i++) {

				Part part = content.getBodyPart(i);
				// pegando um tipo do conteúdo
				String contentType = part.getContentType();

				// Tela do conteúdo
				if (contentType.toLowerCase().startsWith("text/xml")) {
					
					StringBuffer xmlStr = new StringBuffer();
					xmlStr.append(part.getContent().toString());
					System.out.println("O XML DO SERVICO \n" + xmlStr);

					ServiceIF service;
					try {
						service = ServiceFactory.createService(type);
						JAXBContext jaxbContext = JAXBContext.newInstance(service.getClass());
						Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					
						service = (ServiceIF) jaxbUnmarshaller.unmarshal(new StreamSource( new StringReader( xmlStr.toString() ) ));
					} catch (JAXBException e) {
						return new UnknownService(e.getMessage());
					}
					
					return service;										
				}
					
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
