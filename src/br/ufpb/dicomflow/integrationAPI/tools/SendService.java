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
package br.ufpb.dicomflow.integrationAPI.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Comparator;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import br.ufpb.dicomflow.integrationAPI.conf.IntegrationAPIProperties;
import br.ufpb.dicomflow.integrationAPI.exceptions.PropertyNotFoundException;
import br.ufpb.dicomflow.integrationAPI.log.Logger;
import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPAuthenticator;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPContentBuilder;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPHeadBuilder;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPSender;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

/**
 * A tool that sends xml services into a mail message.
 * 
 * @author Danilo Alexandre
 */
public class SendService {
	
	
	public static ResourceBundle rb = ResourceBundle.getBundle("br.ufpb.dicomflow.integrationAPI.tools.messages");
	
	public static Options opts;
	public static Properties properties;
	public static ServiceIF service;
	public static X509Certificate signer;
	public static X509Certificate cipher;
	public static PrivateKey privateKey;
	
	
	public static final String CONTEXT_PATH = "br.ufpb.dicomflow.integrationAPI.message.xml";
	
	public static final String RECEIVER_OPTION = "R";
	public static final String SERVICE_OPTION = "S";
	public static final String SERVICE_CLASS_OPTION = "T";
	public static final String ATTACH_OPTION = "A";
	
	
	
	
	
	public static void main(final String args[]){
		
		try {
			CommandLine cl = parseComandLine(args);
			
			CLIUtils.configureLog(cl);
			
			CLIUtils.configureProperties( properties, cl, CLIUtils.SEND_PROPERTIES);
			
			CLIUtils.configureEncryptProperties(signer, privateKey, cipher, cl);
			
			configureService(service, cl);
			
			sendService(cl);
			
			
		} catch (ParseException e) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.setOptionComparator(new Comparator<Option>() {
				
				@Override
				public int compare(Option o1, Option o2) {
					return 0;
				}
			});

	        formatter.printHelp(rb.getString("send-usage"), rb.getString("send-description"),  opts,  rb.getString("send-example") , true);
		} catch (PropertyNotFoundException e) {
			Logger.ef(e, rb.getString("load-property-exception"));
		} catch (JAXBException e) {
			Logger.ef(e, rb.getString("load-service-exception"));
		} catch (ClassNotFoundException e) {
			Logger.ef(e,rb.getString("load-service-exception"));
		} catch (UnrecoverableKeyException e) {
			Logger.ef(e,rb.getString("load-encrypt-exception"));
		} catch (KeyStoreException e) {
			Logger.ef(e,rb.getString("load-encrypt-exception"));
		} catch (NoSuchAlgorithmException e) {
			Logger.ef(e,rb.getString("load-encrypt-exception"));
		} catch (CertificateException e) {
			Logger.ef(e,rb.getString("load-encrypt-exception"));
		} catch (FileNotFoundException e) {
			Logger.ef(e,rb.getString("load-file-exception"));
		} catch (IOException e) {
			Logger.ef(e,rb.getString("load-encrypt-exception"));
		}
		
	}
	
	private static CommandLine parseComandLine(String[] args) throws ParseException{
        
		opts = new Options();
		
        addRequiredOptions(opts);
        addEncryptOptions(opts);
        addOtherOptions(opts);
        
  
        return CLIUtils.parseComandLine(args, opts);

	}
	
	private static void addOtherOptions(Options opts) {
		CLIUtils.addOtherOptions(opts);
		opts.addOption(Option.builder(SERVICE_CLASS_OPTION).hasArg().argName("class").longOpt("service-class").desc(rb.getString("service-class")).build());
		opts.addOption(Option.builder(ATTACH_OPTION).hasArg().argName("file").longOpt("attach").desc(rb.getString("attach")).build());
	}

	private static void addEncryptOptions(Options opts) {
		CLIUtils.addEncryptOptions(opts);
	}

	private static void addRequiredOptions(Options opts) {
		CLIUtils.addRequiredOptions(opts);
        opts.addOption(Option.builder(RECEIVER_OPTION).required().hasArg().argName("mail").longOpt("receiver").desc(rb.getString("receiver")).build());
        opts.addOption(Option.builder(SERVICE_OPTION).required().hasArg().argName("file").longOpt("service").desc(rb.getString("service")).build());
	}
	
	private static void configureService(ServiceIF service, CommandLine cl) throws ParseException, JAXBException, ClassNotFoundException{
		
		
		Logger.v(rb.getString("start-service-config"));
		if(!cl.hasOption(SERVICE_OPTION))
			throw new MissingArgumentException(rb.getString("missing-content-opt"));
		
		JAXBContext jaxbContext;
		if(cl.hasOption(SERVICE_CLASS_OPTION)){
			
			String serviceClass = cl.getOptionValue(SERVICE_CLASS_OPTION);
			jaxbContext = JAXBContext.newInstance(Class.forName(serviceClass));
			Logger.v(rb.getString("jaxb-context")+Class.forName(serviceClass));
			
		}else{
			
			jaxbContext = JAXBContext.newInstance(CONTEXT_PATH);
			Logger.v(rb.getString("jaxb-context")+CONTEXT_PATH);
			
		}
		
		
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	
		service = (ServiceIF) jaxbUnmarshaller.unmarshal(new File(cl.getOptionValue(SERVICE_OPTION)));
		
		Logger.v(rb.getString("loaded-service") + service.getName() + " - " + service.getAction() + " - " + service.getMessageID());
		
		Logger.v(rb.getString("finish-service-config"));
		
	}
	
	private static void sendService(CommandLine cl) throws FileNotFoundException {
		
		Logger.v(rb.getString("start-send-service"));
		
		MailAuthenticatorIF mailAuthenticator =  new SMTPAuthenticator(properties.getProperty(IntegrationAPIProperties.AUTHENTICATION_LOGIN), properties.getProperty(IntegrationAPIProperties.AUTHENTICATION_PASSWORD));
		MailHeadBuilderIF mailHeadBuilder = new SMTPHeadBuilder(properties.getProperty(IntegrationAPIProperties.AUTHENTICATION_LOGIN), cl.getOptionValue(RECEIVER_OPTION), properties.getProperty(IntegrationAPIProperties.DOMAIN));
		MailContentBuilderIF mailContentBuilder = new SMTPContentBuilder();
		
		SMTPSender sender = new SMTPSender();
		sender.setProperties(properties);
		sender.setAuthenticatorBuilder(mailAuthenticator);
		sender.setHeadBuilder(mailHeadBuilder);
		sender.setContentBuilder(mailContentBuilder);
		
		Logger.v(rb.getString("build-smtp-sender"));
		
		File attach = null;
		if(cl.hasOption(ATTACH_OPTION)){
			attach = new File(cl.getOptionValue(ATTACH_OPTION));
			if(!attach.exists()){
				throw new FileNotFoundException(rb.getString("invalid-attach-file") + cl.getOptionValue(ATTACH_OPTION)); 
			}
		}
		
		if(cl.hasOption(CLIUtils.ENCRYPT_OPTION))
			sender.sendCipher(service, attach, signer, cipher, privateKey);
		else
			sender.send(service, attach);
			
		Logger.v(rb.getString("finish-send-service"));
	}
	
}