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
package br.ufpb.dicomflow.integrationAPI.mail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
		public Iterator<byte[]> receiveAttachs(FilterIF filter) {

			List<byte[]> attachs = new ArrayList<byte[]>();

			try {

				Session session = Session.getInstance(getProperties(), getAuthenticatorBuilder().getAuthenticator());

				List<Message> messages = getMessageReader().getMessages(session, filter);
				
				attachs = getServiceExtractor().getAttachs(messages);

				

			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			return attachs.iterator();

		}
		
		@Override
		public Iterator<Map<ServiceIF, byte[]>> receiveServiceAndAttachs(FilterIF filter) {

			List<Map<ServiceIF, byte[]>> attachs = new ArrayList<Map<ServiceIF, byte[]>>();

			try {

				Session session = Session.getInstance(getProperties(), getAuthenticatorBuilder().getAuthenticator());

				List<Message> messages = getMessageReader().getMessages(session, filter);
				
				attachs = getServiceExtractor().getServicesAndAttachs(messages);

				

			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			return attachs.iterator();

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
