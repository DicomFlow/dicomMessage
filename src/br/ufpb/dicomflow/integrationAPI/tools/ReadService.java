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
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import br.ufpb.dicomflow.integrationAPI.conf.DicomMessageProperties;
import br.ufpb.dicomflow.integrationAPI.exceptions.PropertyNotFoundException;
import br.ufpb.dicomflow.integrationAPI.log.Logger;
import br.ufpb.dicomflow.integrationAPI.mail.FilterIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailMessageReaderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailServiceExtractorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MessageIF;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPAuthenticator;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPFilter;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPMessageReader;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPReceiver;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPServiceExtractor;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

/**
 * A tool that read services from a mail message
 * 
 * @author Danilo Alexandre
 */
public class ReadService{
	
	public static ResourceBundle rb = ResourceBundle.getBundle("br.ufpb.dicomflow.integrationAPI.tools.messages");
	
	public static Options opts;
	public static Properties properties;
	public static X509Certificate signer;
	public static X509Certificate cipher;
	public static PrivateKey privateKey;
	public static FilterIF filter;
	
	public static final String DEST_DIR_OPTION = "D";
	private static final String FILTER_OPTION = "F";
	private static final String INITIAL_DATE_OPTION = "i";
	private static final String FINAL_DATE_OPTION = "f";
	private static final String SERVICE_TYPE_OPTION = "t";
	private static final String ID_MESSAGE_OPTION = "m";
    
	
	public static void main(final String args[]) {
    
		try {
			CommandLine cl = parseComandLine(args);
			
			CLIUtils.configureLog(cl);
			
			CLIUtils.configureProperties( properties, cl, CLIUtils.READ_PROPERTIES);
			
			CLIUtils.configureEncryptProperties(cipher, privateKey, signer, cl);
			
			configureFilterProperties(filter, cl);
			
			readService(cl);
			
		} catch (ParseException e) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.setOptionComparator(new Comparator<Option>() {
				
				@Override
				public int compare(Option o1, Option o2) {
					return 0;
				}
			});

	        formatter.printHelp(rb.getString("read-usage"), rb.getString("read-description"),  opts,  rb.getString("read-example") , true);
		} catch (PropertyNotFoundException e) {
			Logger.ef(e, rb.getString("load-property-exception"));
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
			Logger.ef(e,rb.getString("load-file-exception"));
		} catch (NumberFormatException e) {
			Logger.ef(e,rb.getString("load-filter-exception"));
		} catch (java.text.ParseException e) {
			Logger.ef(e,rb.getString("load-filter-exception"));
		} catch (JAXBException e) {
			Logger.ef(e, rb.getString("load-service-exception"));
		}
	
	}

	private static CommandLine parseComandLine(String[] args) throws ParseException{
        
		opts = new Options();
		
        addRequiredOptions(opts);
        addEncryptOptions(opts);
        addOtherOptions(opts);
        
        CommandLineParser parser = new DefaultParser();
        CommandLine cl = parser.parse(opts, args);
        return cl;

	}

	private static void addOtherOptions(Options opts) {
		CLIUtils.addOtherOptions(opts);
		opts.addOption(Option.builder(FILTER_OPTION).desc(rb.getString("filter")).build());
		opts.addOption(Option.builder(INITIAL_DATE_OPTION).hasArg().argName("initialDate").longOpt("initial-date").desc(rb.getString("initial-date")).build());
		opts.addOption(Option.builder(FINAL_DATE_OPTION).hasArg().argName("finalDate").longOpt("final-date").desc(rb.getString("final-date")).build());
		opts.addOption(Option.builder(SERVICE_TYPE_OPTION).hasArg().argName("serviceType").longOpt("service-type").desc(rb.getString("service-type")).build());
		opts.addOption(Option.builder(ID_MESSAGE_OPTION).hasArg().argName("idMessage").longOpt("message-id").desc(rb.getString("message-id")).build());
	}

	private static void addEncryptOptions(Options opts) {
		CLIUtils.addEncryptOptions(opts);
	}

	private static void addRequiredOptions(Options opts) {
		CLIUtils.addRequiredOptions(opts);
		opts.addOption(Option.builder(DEST_DIR_OPTION).required().hasArg().argName("dir").longOpt("dest-dir").desc(rb.getString("dest-dir")).build());
		
	}
	
	private static void configureFilterProperties(FilterIF filter, CommandLine cl) throws java.text.ParseException, NumberFormatException {
		if(cl.hasOption(FILTER_OPTION)){
			
			filter = new SMTPFilter();
			
			if(cl.hasOption(INITIAL_DATE_OPTION)){
				DateFormat formater = new SimpleDateFormat("dd/MM/aaaa");
				filter.setInitialDate(formater.parse(cl.getOptionValue(INITIAL_DATE_OPTION)));
				
			}
			
			if(cl.hasOption(FINAL_DATE_OPTION)){
				DateFormat formater = new SimpleDateFormat("dd/MM/aaaa");
				filter.setInitialDate(formater.parse(cl.getOptionValue(FINAL_DATE_OPTION)));
				
			}
			
			if(cl.hasOption(SERVICE_TYPE_OPTION)){
				Integer serviceType = Integer.parseInt(cl.getOptionValue(SERVICE_TYPE_OPTION));
				filter.setServiceType(serviceType);
			}
			
			if(cl.hasOption(ID_MESSAGE_OPTION)){
				filter.setIdMessage(cl.getOptionValue(ID_MESSAGE_OPTION));
			}
			
		}
		
	}
	
	private static void readService(CommandLine cl) throws ParseException, JAXBException, IOException {
		
		Logger.v(rb.getString("start-receive-service"));
		
		MailAuthenticatorIF mailAuthenticator =  new SMTPAuthenticator(properties.getProperty(DicomMessageProperties.AUTHENTICATION_LOGIN), properties.getProperty(DicomMessageProperties.AUTHENTICATION_PASSWORD));
		MailServiceExtractorIF mailServiceExtractor = new SMTPServiceExtractor();
		MailMessageReaderIF mailMessageReader = new SMTPMessageReader(properties.getProperty(DicomMessageProperties.PROVIDER_HOST), properties.getProperty(DicomMessageProperties.PROVIDER_FOLDER));
		
		SMTPReceiver receiver = new SMTPReceiver();
		receiver.setProperties(properties);
		receiver.setAuthenticatorBuilder(mailAuthenticator);
		receiver.setMessageReader(mailMessageReader);
		receiver.setServiceExtractor(mailServiceExtractor);
		
		if(!cl.hasOption(DEST_DIR_OPTION)){
			
			throw new MissingArgumentException(rb.getString("missing-content-opt"));
			
		}else{
			
			File destDir = new File(cl.getOptionValue(DEST_DIR_OPTION));
			if(!destDir.exists()){
				throw new FileNotFoundException(rb.getString("invalid-dest-dir") + cl.getOptionValue(DEST_DIR_OPTION));
			}
			
			List<MessageIF> messages = new ArrayList<MessageIF>();
			if(cl.hasOption(CLIUtils.ENCRYPT_OPTION)){
				messages = receiver.receiveCipherMessages(filter, signer, cipher, privateKey);	
			}else{
				messages = receiver.receiveMessages(filter);
			}
			
			saveMessages(messages, destDir);
			
		}
		
		Logger.v(rb.getString("finish-receive-service"));
		

	}

	private static void saveMessages(List<MessageIF> messages, File destDir) throws JAXBException, IOException {
		Iterator<MessageIF> it = messages.iterator();
		while (it.hasNext()) {
			MessageIF messageIF = (MessageIF) it.next();
			ServiceIF service = messageIF.getService();
			Logger.v(rb.getString("loaded-service") + service.getName() + " - " + service.getAction() + " - " + service.getMessageID());
			
			JAXBContext jaxbContext = JAXBContext.newInstance(messageIF.getService().getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			FileOutputStream fos = new FileOutputStream(destDir.getAbsolutePath() + File.pathSeparator + service.getName() + "_" + service.getAction() + "_" + service.getMessageID());
			jaxbMarshaller.marshal(messageIF.getService(), fos);
			
			if(messageIF.getAttach() != null && messageIF.getAttach().length >0){
				fos = new FileOutputStream(destDir.getAbsolutePath() + File.pathSeparator + service.getName() + "_" + service.getAction() + "_" + service.getMessageID() + "_attach");
				fos.write(messageIF.getAttach());
				fos.close();
			}
			
			
			
		}
	}
	

}