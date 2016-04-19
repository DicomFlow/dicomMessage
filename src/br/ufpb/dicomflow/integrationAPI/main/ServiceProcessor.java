package br.ufpb.dicomflow.integrationAPI.main;

import java.util.Iterator;
import java.util.Properties;

import br.ufpb.dicomflow.integrationAPI.conf.IntegrationAPIProperties;
import br.ufpb.dicomflow.integrationAPI.exceptions.ServiceCreationException;
import br.ufpb.dicomflow.integrationAPI.mail.FilterIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailMessageReaderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailServiceExtractorIF;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPAuthenticator;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPContentBuilder;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPHeadBuilder;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPMessageReader;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPReceiver;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPSender;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPServiceExtractor;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public class ServiceProcessor {

	public static void sendMessage(ServiceIF obj, String destinationMail, Properties props, MailAuthenticatorIF mailAuthenticator, MailHeadBuilderIF mailHeadBuilder, MailContentBuilderIF mailContentBuilder) throws ServiceCreationException {
		
		try {
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
	        
	        sender.send(obj);

		} catch (Exception e) {
			throw new ServiceCreationException(e.getMessage());
		}
		
	}
	
	public static Iterator<ServiceIF> receiveMessage(Properties props, MailAuthenticatorIF mailAuthenticator, MailServiceExtractorIF mailServiceExtractor, MailMessageReaderIF mailMessageReader, FilterIF filter) throws ServiceCreationException{
		
		try{
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
			
			return receiver.receive(filter);
		} catch (Exception e) {
			throw new ServiceCreationException(e.getMessage());
		}
		
		
	}
	
	//TODO metodos para realizar as operações - ex: verificar mensagens
	// 											enviar um storage de um objeto
	//											get ID's / links dos estudos
	//TODO metodos de integracao com gateways
	//TODO Strategy para mandar as mensagens para o Sender (se vai criptografada, plano, etc)
	
}
