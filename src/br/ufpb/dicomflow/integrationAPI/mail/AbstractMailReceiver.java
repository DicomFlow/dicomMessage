package br.ufpb.dicomflow.integrationAPI.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;

import br.ufpb.dicomflow.integrationAPI.mail.impl.MailContentBuilderFactory;
import br.ufpb.dicomflow.integrationAPI.mail.impl.MailXTags;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public abstract class AbstractMailReceiver implements MailReceiverIF {

		@Override
		public abstract Properties getProperties();

		@Override
		public abstract MailMessageReaderIF getMessageReader();
		
		@Override
		public abstract MailAuthenticatorIF getAuthenticatorBuilder();
		
		@Override
		public abstract MailServiceExtractorIF getServiceExtractor();

		@Override
		public Iterator<ServiceIF> receive(FilterIF filter) {

			List<ServiceIF> services = new ArrayList<ServiceIF>();

			try {

				Session session = Session.getInstance(getProperties(), getAuthenticatorBuilder().getAuthenticator());

				List<Message> messages = getMessageReader().getMessages(session, filter);

				services = getServiceExtractor().getServices(messages);

			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			return services.iterator();

		}
		
		@Override
		public List<Message> getMessages(FilterIF filter) {

			List<Message> messages = new ArrayList<Message>();

			try {

				Session session = Session.getInstance(getProperties(), getAuthenticatorBuilder().getAuthenticator());

				messages = getMessageReader().getMessages(session, filter);


			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			return messages;

		}

		

}
