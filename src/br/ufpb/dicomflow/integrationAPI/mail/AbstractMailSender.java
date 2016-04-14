package br.ufpb.dicomflow.integrationAPI.mail;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public abstract class AbstractMailSender implements MailSenderIF {

		public void send(ServiceIF service) {

			try {
				Session session = Session.getInstance(getProperties(),
						getAuthenticatorBuilder().getAuthenticator());
				session.setDebug(true);
				
				Message message = new MimeMessage(session);

				message = getHeadBuilder().buildHead(message, service, getContentBuilder());
				message = getContentBuilder().buildContent(message, service);
				
				Transport.send(message);

			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

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
