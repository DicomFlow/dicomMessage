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

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import br.ufpb.dicomflow.integrationAPI.conf.IntegrationAPIProperties;
import br.ufpb.dicomflow.integrationAPI.mail.FilterIF;
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
import br.ufpb.dicomflow.integrationAPI.message.xml.StorageDelete;

public class EncryptedMessageTestCase extends GenericTestCase {
	
	@Test
	public static void testSendEncryptedMessage() throws Exception {
		
		
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
		
						
			
		
        
        IntegrationAPIProperties.getInstance().load(IntegrationAPIProperties.CONFIG_FILE_PATH);
		Properties props = IntegrationAPIProperties.getInstance().getSendProperties();
        
        
        KeyStore keystore = KeyStore.getInstance("JKS");
		keystore.load(new FileInputStream("./bin/br/ufpb/dicomflow/integrationAPI/tests/dicomflow.jks"), "pr0t0c0l0ap1".toCharArray());
		Certificate[] signChain = keystore.getCertificateChain("dicomflow.org");
		Certificate encryptCert = keystore.getCertificate("apiintegracao.org");

		PrivateKey privateKey = (PrivateKey)keystore.getKey("dicomflow.org",
				"pr0t0c0l0ap1".toCharArray());
		if (privateKey == null)
		{
			throw new Exception("cannot find private key for alias: "
					+ "dicomflow.org");
		}
        
        MailAuthenticatorIF smtpAuthenticatorStrategy =  new SMTPAuthenticator(props.getProperty("authentication.login"), props.getProperty("authentication.password"));
        MailHeadBuilderIF smtpHeadStrategy = new SMTPHeadBuilder(props.getProperty("authentication.login"), props.getProperty("authentication.login"), props.getProperty("domain"));
        MailContentBuilderIF smtpCipherContentStrategy = new SMTPContentBuilder();
        
        SMTPSender sender = new SMTPSender();
        sender.setProperties(props);
        sender.setAuthenticatorBuilder(smtpAuthenticatorStrategy);
        sender.setHeadBuilder(smtpHeadStrategy);
        sender.setContentBuilder(smtpCipherContentStrategy);
        
        messageID = sender.sendCipher(storageDelete, (X509Certificate) signChain[0], (X509Certificate) encryptCert, privateKey);
        System.out.println("MESSAGE ID ======> " + messageID);
        
        
        System.out.println("MESSAGE-ID : " + messageID);
        
        
        keystore = KeyStore.getInstance("JKS");
		keystore.load(new FileInputStream("./bin/br/ufpb/dicomflow/integrationAPI/tests/apiintegracao.jks"), "pr0t0c0l0ap1".toCharArray());
		Certificate[] encryptChain = keystore.getCertificateChain("apiintegracao.org");
		Certificate signCert = keystore.getCertificate("apidicomflow.org");

		privateKey = (PrivateKey)keystore.getKey("apiintegracao.org",
				"pr0t0c0l0ap1".toCharArray());
		if (privateKey == null)
		{
			throw new Exception("cannot find private key for alias: "
					+ "apiintegracao.org");
		}
		
        FilterIF filter = new SMTPFilter();
		filter.setIdMessage(messageID+"@"+props.getProperty("domain"));
		
		props = IntegrationAPIProperties.getInstance().getReceiveProperties();
		
		
		MailAuthenticatorIF smtpAuthenticatorStrategy2 =  new SMTPAuthenticator(props.getProperty("authentication.login"), props.getProperty("authentication.password"));
		MailMessageReaderIF smtpMesssaStrategy = new SMTPMessageReader(props.getProperty("provider.host"), props.getProperty("provider.folder"));
		MailServiceExtractorIF serviceExtractor2 = new SMTPServiceExtractor();
		
		
		
		SMTPReceiver receiver = new SMTPReceiver();
		receiver.setProperties(props);
		receiver.setAuthenticatorBuilder(smtpAuthenticatorStrategy2);
		receiver.setMessageReader(smtpMesssaStrategy);
		receiver.setServiceExtractor(serviceExtractor2);
		
		
		List<ServiceIF> services = receiver.receiveCipher(filter, (X509Certificate) signCert, (X509Certificate) encryptChain[0], privateKey);
		Iterator<ServiceIF> iterator = services.iterator();
		while (iterator.hasNext()) {
			ServiceIF serviceIF = (ServiceIF) iterator.next();
			System.out.println("MessageID:" +serviceIF.getMessageID() + "Name: " + serviceIF.getName() + "Action: " +serviceIF.getAction());
		}
        

	}
	
	
}
