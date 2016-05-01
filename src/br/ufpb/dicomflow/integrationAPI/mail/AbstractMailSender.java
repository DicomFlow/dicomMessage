package br.ufpb.dicomflow.integrationAPI.mail;
import java.io.File;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public abstract class AbstractMailSender implements MailSenderIF {

		public String send(ServiceIF service) {

			try {
				Message message = buildMessage(service, null);
				
				Transport.send(message);

				
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return service.getMessageID();

		}

		
		
		public String send(ServiceIF service, File attachement) {

			try {
				Message message = buildMessage(service, attachement);
				
				Transport.send(message);

				
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return service.getMessageID();

		}
		
		private Message buildMessage(ServiceIF service, File attachment) {
			Session session = Session.getInstance(getProperties(),
					getAuthenticatorBuilder().getAuthenticator());
			session.setDebug(true);
			
			Message message = new MimeMessage(session);

			message = getHeadBuilder().buildHead(message, service, getContentBuilder());
			
			if(attachment != null && attachment.exists()){
				message = getContentBuilder().buildContent(message, service, attachment);
			}else{
				message = getContentBuilder().buildContent(message, service);
			}
			return message;
		}

		@Override
		public abstract Properties getProperties();

		@Override
		public abstract MailAuthenticatorIF getAuthenticatorBuilder();

		@Override
		public abstract MailHeadBuilderIF getHeadBuilder();

		@Override
		public abstract MailContentBuilderIF getContentBuilder();

}
