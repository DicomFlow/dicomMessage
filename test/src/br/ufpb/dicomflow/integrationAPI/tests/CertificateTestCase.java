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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.ufpb.dicomflow.integrationAPI.conf.IntegrationAPIProperties;
import br.ufpb.dicomflow.integrationAPI.exceptions.PropertyNotFoundException;
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
import br.ufpb.dicomflow.integrationAPI.main.ServiceFactory;
import br.ufpb.dicomflow.integrationAPI.message.xml.CertificateRequest;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public class CertificateTestCase extends GenericTestCase {
	
	@Test
	public static void testSendCertificateRequest() throws PropertyNotFoundException {
		
		
		CertificateRequest certRequest = (CertificateRequest) ServiceFactory.createService(ServiceIF.CERTIFICATE_REQUEST);
		certRequest.setDomain("dominio.com");
		certRequest.setPort(8443);
		
		File attachment = new File("./bin/br/ufpb/dicomflow/integrationAPI/tests/dicommessage.crt");
						
			
		IntegrationAPIProperties.getInstance().load(IntegrationAPIProperties.CONFIG_FILE_PATH);
		Properties props = IntegrationAPIProperties.getInstance().getSendProperties();

        MailAuthenticatorIF smtpAuthenticatorStrategy =  new SMTPAuthenticator(props.getProperty("authentication.login"), props.getProperty("authentication.password"));
        MailHeadBuilderIF smtpHeadStrategy = new SMTPHeadBuilder(props.getProperty("authentication.login"), props.getProperty("authentication.login"), props.getProperty("domain"));
        MailContentBuilderIF smtpSimpleContentStrategy = new SMTPContentBuilder();
        
        SMTPSender sender = new SMTPSender();
        sender.setProperties(props);
        sender.setAuthenticatorBuilder(smtpAuthenticatorStrategy);
        sender.setHeadBuilder(smtpHeadStrategy);
        sender.setContentBuilder(smtpSimpleContentStrategy);
        
        messageID = sender.send(certRequest, attachment);
        System.out.println("MESSAGE ID ======> " + messageID);
        
        
        System.out.println("MESSAGE-ID : " + messageID);
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
		
		
		List<ServiceIF> services = receiver.receive(filter);//ServiceProcessor.receiveServices(null, null, null, null, filter);
		Iterator<ServiceIF> iterator = services.iterator();
		while (iterator.hasNext()) {
			ServiceIF serviceIF = (ServiceIF) iterator.next();
			//System.out.println("MessageID:" +serviceIF.getMessageID() + "Name: " + serviceIF.getName() + "Action: " +serviceIF.getAction());
		}
		
		List<byte[]> attachs = receiver.receiveAttachs(filter);//ServiceProcessor.receiveAttachs(null, null, null, null, filter);
		Iterator<byte[]> iterator2 = attachs.iterator();
		while (iterator2.hasNext()) {
			byte[] bs = (byte[]) iterator2.next();
			System.out.println("BYTE ARRAY LEGTH : " + bs.length);
			File cert = new File("./bin/br/ufpb/dicomflow/integrationAPI/tests/attach.crt");
			try {
				if(!cert.exists()){
					cert.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(cert);
				fos.write(bs);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
        

	}
	
	
}
