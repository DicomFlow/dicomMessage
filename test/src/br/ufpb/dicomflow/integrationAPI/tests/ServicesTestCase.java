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
