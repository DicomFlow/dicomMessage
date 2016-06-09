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

import java.util.Properties;

import org.junit.Test;

import br.ufpb.dicomflow.integrationAPI.exceptions.ServiceCreationException;
import br.ufpb.dicomflow.integrationAPI.mail.FilterIF;
import br.ufpb.dicomflow.integrationAPI.mail.impl.SMTPFilter;
import br.ufpb.dicomflow.integrationAPI.main.DefaultIdMessageGeneratorStrategy;
import br.ufpb.dicomflow.integrationAPI.main.ServiceFactory;
import br.ufpb.dicomflow.integrationAPI.main.ServiceProcessor;
import br.ufpb.dicomflow.integrationAPI.message.xml.Service;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;
import br.ufpb.dicomflow.integrationAPI.message.xml.StorageDelete;

public class ServicesTestCase extends GenericTestCase {
	
	@Test
	public static void testSendStorageDelete() {
		StorageDelete obj = (StorageDelete) ServiceFactory.createService(ServiceIF.STORAGE_DELETE, DefaultIdMessageGeneratorStrategy.getInstance());// new StorageDelete();

		Properties props = new Properties();        		
        props.put("mail.smtp.host", "smtp.gmail.com");//
        props.put("mail.smtp.socketFactory.port", "25");//
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//
        props.put("mail.smtp.auth", "true");//
        props.put("mail.smtp.port", "25");//
        props.put("mail.smtp.starttls.enable", "true");//
        props.put("mail.debug", "true");//
        props.put("mail.smtp.socketFactory.fallback", "false");//
        props.put("authentication.login", "protocolointegracao@gmail.com");//
        props.put("authentication.password", "pr0t0c0l0ap1");//
        props.put("domain", "dominio.com");//
        
//        
//        props.put(ServiceProcessor.EMAIL, "protocolointegracao@gmail.com");
//        props.put(ServiceProcessor.PASSWORD, "pr0t0c0l0ap1");
//        props.put(ServiceProcessor.FROM, "protocolointegracao@gmail.com");
//        props.put(ServiceProcessor.TO, "dicomflow@gmail.com");
//        props.put(ServiceProcessor.DOMAIN, "dominio.com");                

		
		try {
			ServiceProcessor.sendMessage(obj, "dicomflow@gmail.com", null, null, null, null);
		} catch (ServiceCreationException e) {
			fail();
		}
	}
	
	@Test
	public static void testReceiveStorageDelete() {                

		
		try {
			FilterIF filter = new SMTPFilter();
			filter.setServiceType(new Integer(ServiceIF.STORAGE_DELETE));
			ServiceProcessor.receiveMessage(null, null, null, null, filter);
		} catch (ServiceCreationException e) {
			fail();
		}
	}
}
