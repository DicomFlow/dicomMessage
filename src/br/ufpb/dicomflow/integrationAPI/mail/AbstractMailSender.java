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
