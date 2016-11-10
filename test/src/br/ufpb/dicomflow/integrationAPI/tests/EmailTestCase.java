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
package br.ufpb.dicomflow.integrationAPI.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import br.ufpb.dicomflow.integrationAPI.conf.DicomMessageProperties;
import br.ufpb.dicomflow.integrationAPI.exceptions.PropertyNotFoundException;
import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailMessageReaderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailServiceExtractorIF;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPAuthenticator;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPContentBuilder;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPFilter;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPHeadBuilder;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPMessageReader;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPReceiver;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPSender;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPServiceExtractor;
import br.ufpb.dicomflow.integrationAPI.main.DefaultIdMessageGeneratorStrategy;
import br.ufpb.dicomflow.integrationAPI.main.ServiceFactory;
import br.ufpb.dicomflow.integrationAPI.message.xml.Credentials;
import br.ufpb.dicomflow.integrationAPI.message.xml.Object;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;
import br.ufpb.dicomflow.integrationAPI.message.xml.SharingPut;
import br.ufpb.dicomflow.integrationAPI.message.xml.StorageDelete;
import br.ufpb.dicomflow.integrationAPI.message.xml.URL;

public class EmailTestCase extends GenericTestCase {
	
	@Test
	public void testReceive() throws PropertyNotFoundException {	
		
		
		Properties props = DicomMessageProperties.getInstance().getReceiveProperties();
		
		MailAuthenticatorIF smtpAuthenticatorStrategy =  new SMTPAuthenticator(props.getProperty("authentication.login"), props.getProperty("authentication.password"));
		MailMessageReaderIF smtpMesssaStrategy = new SMTPMessageReader(props.getProperty("provider.host"), props.getProperty("provider.folder"));
		MailServiceExtractorIF serviceExtractor = new SMTPServiceExtractor();
		
		SMTPReceiver receiver = new SMTPReceiver();
		receiver.setProperties(props);
		receiver.setAuthenticatorBuilder(smtpAuthenticatorStrategy);
		receiver.setMessageReader(smtpMesssaStrategy);
		receiver.setServiceExtractor(serviceExtractor);
		
		List<ServiceIF> services = receiver.receive(new SMTPFilter());
		Iterator<ServiceIF> iterator = services.iterator();
		while (iterator.hasNext()) {
			ServiceIF serviceIF = (ServiceIF) iterator.next();
			System.out.println("MessageID:" +serviceIF.getMessageID() + "Name: " + serviceIF.getName() + "Action: " +serviceIF.getAction());
		}
	}
	

	
	@Test
	public static void testSendStorageDelete() throws PropertyNotFoundException {
		
		StorageDelete storageDelete = (StorageDelete) ServiceFactory.createService(ServiceIF.STORAGE_DELETE, DefaultIdMessageGeneratorStrategy.getInstance());// new StorageDelete();
		storageDelete.setTimestamp("12346567346");
		storageDelete.setTimeout("23123");		
		
		Object obj1 = new Object();
		obj1.setId("1");
		obj1.setType("type1");
		Credentials cred1 = new Credentials();
		cred1.setValue("asdfh");
		obj1.setCredential(cred1);
		
		Object obj2 = new Object();
		obj2.setId("2");
		obj2.setType("type2");
		Credentials cred2 = new Credentials();
		cred2.setValue("1we1233");
		obj2.setCredential(cred2);
		
		List<Object> objects = new ArrayList<Object>();
		objects.add(obj1);
		objects.add(obj2);
		storageDelete.setObject(objects);
						
		DicomMessageProperties.getInstance().load(DicomMessageProperties.CONFIG_FILE_PATH);
		Properties props = DicomMessageProperties.getInstance().getSendProperties();
		

		MailAuthenticatorIF smtpAuthenticatorStrategy =  new SMTPAuthenticator(props.getProperty("authentication.login"), props.getProperty("authentication.password"));
        MailHeadBuilderIF smtpHeadStrategy = new SMTPHeadBuilder(props.getProperty("authentication.login"), DicomMessageProperties.getInstance().getProperty("authentication.login.2"), props.getProperty("domain"));
        MailContentBuilderIF smtpSimpleContentStrategy = new SMTPContentBuilder();
        
        SMTPSender sender = new SMTPSender();
        sender.setProperties(props);
        sender.setAuthenticatorBuilder(smtpAuthenticatorStrategy);
        sender.setHeadBuilder(smtpHeadStrategy);
        sender.setContentBuilder(smtpSimpleContentStrategy);
        
        sender.send(storageDelete);
        

	}
	
	@Test
	public static void testSendSharingPut() throws PropertyNotFoundException {
		
		SharingPut sharingPut = (SharingPut) ServiceFactory.createService(ServiceIF.SHARING_PUT, DefaultIdMessageGeneratorStrategy.getInstance());
		sharingPut.setTimestamp("12346567346");
		sharingPut.setTimeout("23123");		
		
		sharingPut.setMessageID("123456");
		sharingPut.setTimestamp("12346567346");
		sharingPut.setTimeout("23123");			

		URL url = new URL();
		url.setValue("a");
		Credentials cred = new Credentials();
		cred.setValue("credentialValue");
		url.setCredentials(cred);
		
		ArrayList<URL> urls = new ArrayList<URL>();
		urls.add(url);		
		sharingPut.setUrl(urls);
						
			
		DicomMessageProperties.getInstance().load(DicomMessageProperties.CONFIG_FILE_PATH);
		Properties props = DicomMessageProperties.getInstance().getSendProperties();
		
		MailAuthenticatorIF smtpAuthenticatorStrategy =  new SMTPAuthenticator(props.getProperty("authentication.login"), props.getProperty("authentication.password"));
        MailHeadBuilderIF smtpHeadStrategy = new SMTPHeadBuilder(props.getProperty("authentication.login"), DicomMessageProperties.getInstance().getProperty("authentication.login.2"), props.getProperty("domain"));
        MailContentBuilderIF smtpSimpleContentStrategy = new SMTPContentBuilder();
        
        SMTPSender sender = new SMTPSender();
        sender.setProperties(props);
        sender.setAuthenticatorBuilder(smtpAuthenticatorStrategy);
        sender.setHeadBuilder(smtpHeadStrategy);
        sender.setContentBuilder(smtpSimpleContentStrategy);
        
        sender.send(sharingPut);     
	}
	
	@Test
	public static void testeReadUnkownService() throws PropertyNotFoundException{
												
		DicomMessageProperties.getInstance().load(DicomMessageProperties.CONFIG_FILE_PATH);
		Properties props = DicomMessageProperties.getInstance().getSendProperties();
		

		MailAuthenticatorIF smtpAuthenticatorStrategy =  new SMTPAuthenticator(props.getProperty("authentication.login"), props.getProperty("authentication.password"));
        MailHeadBuilderIF smtpHeadStrategy = new SMTPHeadBuilder(props.getProperty("authentication.login"), DicomMessageProperties.getInstance().getProperty("authentication.login.2"), props.getProperty("domain"));
        MailContentBuilderIF smtpSimpleContentStrategy = new SMTPContentBuilder();
        
        SMTPSender sender = new SMTPSender();
        sender.setProperties(props);
        sender.setAuthenticatorBuilder(smtpAuthenticatorStrategy);
        sender.setHeadBuilder(smtpHeadStrategy);
        sender.setContentBuilder(smtpSimpleContentStrategy);
        
        NewService newService = new NewService();
        newService.setMessageID(DefaultIdMessageGeneratorStrategy.getInstance().getNextId());
		
        sender.send(newService);
        
        ExtendedStorageSave extended = new ExtendedStorageSave();
        extended.setMessageID(DefaultIdMessageGeneratorStrategy.getInstance().getNextId());
		extended.setExtraAttribute1("value1");
		extended.setExtraAttribute2("value2");
		extended.setTimestamp("12346567346");
		extended.setTimeout("23123");
		
		Credentials credentials = new Credentials();
		credentials.setValue("999999999999");
		
		URL url = new URL();
		url.setCredentials(credentials);
		url.setValue("http://google.com");
		
		extended.setUrl(url);
		
		sender.send(extended);
		
		LimitedStorageSave limited = new LimitedStorageSave();
		limited.setMessageID(DefaultIdMessageGeneratorStrategy.getInstance().getNextId());
		limited.setTimestamp("12346567346");
		limited.setTimeout("23123");
		
		sender.send(limited);
		
		OtherTypeService otherType = new OtherTypeService();
		otherType.setMessageID(DefaultIdMessageGeneratorStrategy.getInstance().getNextId());
		
        sender.send(otherType);
        
        props = DicomMessageProperties.getInstance().getReceiveProperties();
		
        MailAuthenticatorIF smtpAuthenticatorStrategy2 =  new SMTPAuthenticator(DicomMessageProperties.getInstance().getProperty("authentication.login.2"), DicomMessageProperties.getInstance().getProperty("authentication.password.2"));
		MailMessageReaderIF smtpMesssaStrategy = new SMTPMessageReader(props.getProperty("provider.host"), props.getProperty("provider.folder"));
		MailServiceExtractorIF serviceExtractor2 = new SMTPServiceExtractor();
		SMTPReceiver receiver = new SMTPReceiver();
		receiver.setProperties(props);
		receiver.setAuthenticatorBuilder(smtpAuthenticatorStrategy2);
		receiver.setMessageReader(smtpMesssaStrategy);
		receiver.setServiceExtractor(serviceExtractor2);
		
		List<ServiceIF> services = receiver.receive(new SMTPFilter());
		Iterator<ServiceIF> iterator = services.iterator();
		while (iterator.hasNext()) {
			ServiceIF serviceIF = (ServiceIF) iterator.next();
			//System.out.println("MessageID:" +serviceIF.getMessageID() + "Name: " + serviceIF.getName() + "Action: " +serviceIF.getAction());
		}
		
		
	}
}
