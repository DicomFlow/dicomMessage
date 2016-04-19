package br.ufpb.dicomflow.integrationAPI.tests;

import org.junit.Test;

import br.ufpb.dicomflow.integrationAPI.conf.IntegrationAPIProperties;
import br.ufpb.dicomflow.integrationAPI.exceptions.PropertyNotFoundException;

public class UtilTestCase extends GenericTestCase {
	
	@Test
	public static void testLoadConfigs() {
		IntegrationAPIProperties properties = IntegrationAPIProperties.getInstance();
		properties.load(IntegrationAPIProperties.CONFIG_FILE_PATH);
		try {
			System.out.println(properties.getProperty(IntegrationAPIProperties.MAIL_SMTP_HOST));
		} catch (PropertyNotFoundException e) {
			e.printStackTrace();
		}
		properties.toString();
	}

}
