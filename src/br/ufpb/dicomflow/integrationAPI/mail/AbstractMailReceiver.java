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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;

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
		public List<ServiceIF> receive(FilterIF filter) {

			List<ServiceIF> services = new ArrayList<ServiceIF>();

			try {

				Session session = Session.getInstance(getProperties(), getAuthenticatorBuilder().getAuthenticator());
				
				getMessageReader().openFolder(session);

				List<Message> messages = getMessageReader().getMessages( filter);

				services = getServiceExtractor().getServices(messages);
				
				getMessageReader().closeFolder();

			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			return services;

		}
		
		@Override
		public List<byte[]> receiveAttachs(FilterIF filter) {

			List<byte[]> attachs = new ArrayList<byte[]>();

			try {

				Session session = Session.getInstance(getProperties(), getAuthenticatorBuilder().getAuthenticator());
				
				getMessageReader().openFolder(session);

				List<Message> messages = getMessageReader().getMessages(filter);
				
				attachs = getServiceExtractor().getAttachs(messages);

				getMessageReader().closeFolder();

			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			return attachs;

		}
		
		
		@Override
		public List<MessageIF> receiveMessages(FilterIF filter) {

			List<MessageIF> messagesIF = new ArrayList<MessageIF>();

			try {

				Session session = Session.getInstance(getProperties(), getAuthenticatorBuilder().getAuthenticator());
				
				getMessageReader().openFolder(session);
				
				List<Message> messages = getMessageReader().getMessages(filter);

				messagesIF = getServiceExtractor().getServicesAndAttachs(messages);
				
				getMessageReader().closeFolder();

			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			return messagesIF;

		}

		@Override
		public  void releaseMessages(){
			getMessageReader().closeFolder();
		}
		

}
