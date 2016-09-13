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
package br.ufpb.dicomflow.integrationAPI.main;

import java.io.File;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Properties;

import br.ufpb.dicomflow.integrationAPI.conf.IntegrationAPIProperties;
import br.ufpb.dicomflow.integrationAPI.exceptions.PropertyNotFoundException;
import br.ufpb.dicomflow.integrationAPI.exceptions.ServiceCreationException;
import br.ufpb.dicomflow.integrationAPI.mail.FilterIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailMessageReaderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailServiceExtractorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MessageIF;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPAuthenticator;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPContentBuilder;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPHeadBuilder;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPMessageReader;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPReceiver;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPSender;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPServiceExtractor;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public class ServiceProcessor {

	public static String sendCipherMessage(ServiceIF obj, File attach, String destinationMail, Properties props, MailAuthenticatorIF mailAuthenticator, MailHeadBuilderIF mailHeadBuilder, MailContentBuilderIF mailContentBuilder, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey) throws ServiceCreationException {
		
		try {
			SMTPSender sender = prepareSender(obj, destinationMail, props, mailAuthenticator, mailHeadBuilder, mailContentBuilder);
	        
	        return sender.sendCipher(obj, attach, signCert, encryptCert, privateKey);

		} catch (Exception e) {
			throw new ServiceCreationException(e.getMessage());
		}
		
	}
	
	public static String sendCipherMessage(ServiceIF obj, String destinationMail, Properties props, MailAuthenticatorIF mailAuthenticator, MailHeadBuilderIF mailHeadBuilder, MailContentBuilderIF mailContentBuilder, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey) throws ServiceCreationException {
		
		return sendCipherMessage(obj, null, destinationMail, props, mailAuthenticator, mailHeadBuilder, mailContentBuilder, signCert, encryptCert, privateKey);
		
	}	
	
	public static String sendMessage(ServiceIF obj, File attach, String destinationMail, Properties props, MailAuthenticatorIF mailAuthenticator, MailHeadBuilderIF mailHeadBuilder, MailContentBuilderIF mailContentBuilder) throws ServiceCreationException {
		
		try {
			SMTPSender sender = prepareSender(obj, destinationMail, props, mailAuthenticator, mailHeadBuilder, mailContentBuilder);
	        
	        return sender.send(obj, attach);

		} catch (Exception e) {
			throw new ServiceCreationException(e.getMessage());
		}
		
	}
	
	public static String sendMessage(ServiceIF obj, String destinationMail, Properties props, MailAuthenticatorIF mailAuthenticator, MailHeadBuilderIF mailHeadBuilder, MailContentBuilderIF mailContentBuilder) throws ServiceCreationException {
		
		return sendMessage(obj, null, destinationMail, props, mailAuthenticator, mailHeadBuilder, mailContentBuilder);
		
	}

	private static SMTPSender prepareSender(ServiceIF obj, String destinationMail, Properties props,
			MailAuthenticatorIF mailAuthenticator, MailHeadBuilderIF mailHeadBuilder,
			MailContentBuilderIF mailContentBuilder) throws PropertyNotFoundException {
		obj.setTimestamp(System.currentTimeMillis()+"");
		
		if(props == null){
			IntegrationAPIProperties.getInstance().load(IntegrationAPIProperties.CONFIG_FILE_PATH);
			props = IntegrationAPIProperties.getInstance().getSendProperties();
		}
		
		if (mailAuthenticator == null) {
			mailAuthenticator =  new SMTPAuthenticator(props.getProperty(IntegrationAPIProperties.AUTHENTICATION_LOGIN), props.getProperty(IntegrationAPIProperties.AUTHENTICATION_PASSWORD));
		}
		
		if (mailHeadBuilder == null) {
			mailHeadBuilder = new SMTPHeadBuilder(props.getProperty(IntegrationAPIProperties.AUTHENTICATION_LOGIN), destinationMail, props.getProperty(IntegrationAPIProperties.DOMAIN));
		}else{
			mailHeadBuilder.setTo(destinationMail);
		}
		
		
		if (mailContentBuilder == null) {
			mailContentBuilder = new SMTPContentBuilder();
		}	        	      
		
		SMTPSender sender = new SMTPSender();
		sender.setProperties(props);
		sender.setAuthenticatorBuilder(mailAuthenticator);
		sender.setHeadBuilder(mailHeadBuilder);
		sender.setContentBuilder(mailContentBuilder);
		return sender;
	}
	
	
	public static List<ServiceIF> receiveCipherServices(Properties props, MailAuthenticatorIF mailAuthenticator, MailServiceExtractorIF mailServiceExtractor, MailMessageReaderIF mailMessageReader, FilterIF filter, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey) throws ServiceCreationException{
		
		try{
			SMTPReceiver receiver = prepareReceiver(props, mailAuthenticator, mailServiceExtractor, mailMessageReader);
			
			return receiver.receiveCipher(filter, signCert, encryptCert, privateKey);
		} catch (Exception e) {
			throw new ServiceCreationException(e.getMessage());
		}
		
		
	}
	
	public static List<ServiceIF> receiveServices(Properties props, MailAuthenticatorIF mailAuthenticator, MailServiceExtractorIF mailServiceExtractor, MailMessageReaderIF mailMessageReader, FilterIF filter) throws ServiceCreationException{
		
		try{
			SMTPReceiver receiver = prepareReceiver(props, mailAuthenticator, mailServiceExtractor, mailMessageReader);
			
			return receiver.receive(filter);
		} catch (Exception e) {
			throw new ServiceCreationException(e.getMessage());
		}
		
		
	}

	public static List<MessageIF> receiveCipherMessages(Properties props, MailAuthenticatorIF mailAuthenticator, MailServiceExtractorIF mailServiceExtractor, MailMessageReaderIF mailMessageReader, FilterIF filter, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey) throws ServiceCreationException{
		
		try{
			SMTPReceiver receiver = prepareReceiver(props, mailAuthenticator, mailServiceExtractor, mailMessageReader);
			
			return receiver.receiveCipherMessages(filter, signCert, encryptCert, privateKey);
		} catch (Exception e) {
			throw new ServiceCreationException(e.getMessage());
		}
		
		
	}
	
	
	public static List<MessageIF> receiveMessages(Properties props, MailAuthenticatorIF mailAuthenticator, MailServiceExtractorIF mailServiceExtractor, MailMessageReaderIF mailMessageReader, FilterIF filter) throws ServiceCreationException{
		
		try{
			SMTPReceiver receiver = prepareReceiver(props, mailAuthenticator, mailServiceExtractor, mailMessageReader);
			
			return receiver.receiveMessages(filter);
		} catch (Exception e) {
			throw new ServiceCreationException(e.getMessage());
		}
		
		
	}
	
	public static List<byte[]> receiveCipherAttachs(Properties props, MailAuthenticatorIF mailAuthenticator, MailServiceExtractorIF mailServiceExtractor, MailMessageReaderIF mailMessageReader, FilterIF filter, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey) throws ServiceCreationException{
		
		try{
			SMTPReceiver receiver = prepareReceiver(props, mailAuthenticator, mailServiceExtractor, mailMessageReader);
			
			return receiver.receiveCipherAttachs(filter, signCert, encryptCert, privateKey);
		} catch (Exception e) {
			throw new ServiceCreationException(e.getMessage());
		}
		
		
	}
	
	public static List<byte[]> receiveAttachs(Properties props, MailAuthenticatorIF mailAuthenticator, MailServiceExtractorIF mailServiceExtractor, MailMessageReaderIF mailMessageReader, FilterIF filter) throws ServiceCreationException{
		
		try{
			SMTPReceiver receiver = prepareReceiver(props, mailAuthenticator, mailServiceExtractor, mailMessageReader);
			
			return receiver.receiveAttachs(filter);
		} catch (Exception e) {
			throw new ServiceCreationException(e.getMessage());
		}
		
		
	}
	
	private static SMTPReceiver prepareReceiver(Properties props, MailAuthenticatorIF mailAuthenticator,
			MailServiceExtractorIF mailServiceExtractor, MailMessageReaderIF mailMessageReader)
			throws PropertyNotFoundException {
		if(props == null){
			IntegrationAPIProperties.getInstance().load(IntegrationAPIProperties.CONFIG_FILE_PATH);
			props = IntegrationAPIProperties.getInstance().getReceiveProperties();
		}
		
		if (mailAuthenticator == null) {
			mailAuthenticator =  new SMTPAuthenticator(props.getProperty(IntegrationAPIProperties.AUTHENTICATION_LOGIN), props.getProperty(IntegrationAPIProperties.AUTHENTICATION_PASSWORD));
		}
		
		if(mailServiceExtractor == null){
			mailServiceExtractor = new SMTPServiceExtractor();
		}
		
		if(mailMessageReader == null){
			mailMessageReader = new SMTPMessageReader(props.getProperty(IntegrationAPIProperties.PROVIDER_HOST), props.getProperty(IntegrationAPIProperties.PROVIDER_FOLDER));
		}
		
		SMTPReceiver receiver = new SMTPReceiver();
		receiver.setProperties(props);
		receiver.setAuthenticatorBuilder(mailAuthenticator);
		receiver.setMessageReader(mailMessageReader);
		receiver.setServiceExtractor(mailServiceExtractor);
		return receiver;
	}
	
	//TODO metodos para realizar as operações - ex: verificar mensagens
	// 											enviar um storage de um objeto
	//											get ID's / links dos estudos
	//TODO metodos de integracao com gateways
	
}
