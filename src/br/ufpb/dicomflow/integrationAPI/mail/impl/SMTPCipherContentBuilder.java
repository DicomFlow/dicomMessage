package br.ufpb.dicomflow.integrationAPI.mail.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;
import br.ufpb.dicomflow.integrationAPI.main.ServiceFactory;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;
import br.ufpb.dicomflow.integrationAPI.message.xml.UnknownService;

public class SMTPCipherContentBuilder implements MailContentBuilderIF {

	@Override
	public Message buildContent(Message message, ServiceIF service) {
		try {
			Multipart multipart = buildServiceBodyPart(service);
            
            message.setContent(multipart);
            
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
		return message;

	}

	
	
	@Override
	public Message buildContent(Message message, ServiceIF service, File attachment) {
		try {
			Multipart multipart = buildServiceBodyPart(service);
			
            DataSource ds = new FileDataSource(attachment) {
                public String getContentType() {
                    return "application/octet-stream";
                }
            };
            BodyPart mbp = new MimeBodyPart();
            mbp.setDataHandler(new DataHandler(ds));
            mbp.setFileName(attachment.getName());
            mbp.setDisposition(Part.ATTACHMENT);
            multipart.addBodyPart(mbp);
            
            message.setContent(multipart);
            
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
		return message;

	}
	
	private Multipart buildServiceBodyPart(ServiceIF service) throws JAXBException, PropertyException, MessagingException {
		Multipart multipart = new MimeMultipart();
		
		JAXBContext jaxbContext = JAXBContext.newInstance(service.getClass());
		
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter str = new StringWriter();
		
		jaxbMarshaller.marshal(service, str);
		
		
		
		MimeBodyPart attachment0 = new MimeBodyPart();
		
		attachment0.setContent(str.toString(),"text/xml; charset=UTF-8");
		
		multipart.addBodyPart(attachment0);
		return multipart;
	}
	
	public int getType(){
		return MailContentBuilderIF.SMTP_SIMPLE_CONTENT_STRATEGY;
	}

	@Override
	public byte[] getAttach(Multipart content) {
		try {
			for (int i = 0; i < content.getCount(); i++) {

				Part part = content.getBodyPart(i);
				// pegando um tipo do conteúdo
				String contentType = part.getContentType();
				System.out.println("PART : " + contentType.toLowerCase());
				// Tela do conteúdo
				if (contentType.toLowerCase().startsWith("application/octet-stream")) {
					System.out.println("ATTACH : " + part.getFileName());

					InputStream is = part.getInputStream();
					List<Byte> bytes = new ArrayList<Byte>();
					byte[] buf = new byte[1];
					while (is.read(buf) != -1) {
						bytes.add(buf[0]);
					}
					byte[] result = new byte[bytes.size()];
					for (int j = 0; j < bytes.size(); j++) {
						result[j] = bytes.get(j);
					}

					System.out.println("ATTACH LENTGH : " + result.length);
					return result;
					
				}										
			}


		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new byte[]{};
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
	
	@Override
	public Map<ServiceIF, byte[]> getServiceAndAttach(Multipart content, int type) {
		Map<ServiceIF, byte[]> map = new HashMap<ServiceIF,byte[]>();
		ServiceIF service = getService(content, type);
		byte[] attach = getAttach(content);
		map.put(service, attach);
		return map;
	}

}