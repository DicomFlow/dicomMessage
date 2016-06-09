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
