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

import java.util.Properties;

import br.ufpb.dicomflow.integrationAPI.mail.AbstractMailReceiver;
import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailMessageReaderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailServiceExtractorIF;

public class SMTPReceiver extends AbstractMailReceiver{
	
	private Properties properties;
	private MailAuthenticatorIF authenticatorBuilder;
	private MailMessageReaderIF messageReader;
	private MailServiceExtractorIF serviceExtractor;
	
	@Override
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	@Override
	public MailAuthenticatorIF getAuthenticatorBuilder() {
		return authenticatorBuilder;
	}
	public void setAuthenticatorBuilder(MailAuthenticatorIF authenticatorBuilder) {
		this.authenticatorBuilder = authenticatorBuilder;
	}
	
	@Override
	public MailMessageReaderIF getMessageReader() {
		return messageReader;
	}
	public void setMessageReader(MailMessageReaderIF messageBuilder) {
		this.messageReader = messageBuilder;
	}
	
	@Override
	public MailServiceExtractorIF getServiceExtractor() {
		return serviceExtractor;
	}
	
	public void setServiceExtractor(MailServiceExtractorIF serviceExtractor) {
		this.serviceExtractor = serviceExtractor;
	}
	
	
	
	

	

}
