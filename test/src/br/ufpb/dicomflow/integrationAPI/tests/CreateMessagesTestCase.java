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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import br.ufpb.dicomflow.integrationAPI.message.xml.Completed;
import br.ufpb.dicomflow.integrationAPI.message.xml.Credentials;
import br.ufpb.dicomflow.integrationAPI.message.xml.Object;
import br.ufpb.dicomflow.integrationAPI.message.xml.Result;
import br.ufpb.dicomflow.integrationAPI.message.xml.SharingPut;
import br.ufpb.dicomflow.integrationAPI.message.xml.StorageResult;
import br.ufpb.dicomflow.integrationAPI.message.xml.StorageSave;
import br.ufpb.dicomflow.integrationAPI.message.xml.URL;

public class CreateMessagesTestCase extends GenericTestCase {
	
	
	@Test
	public void testStorageResultMessage() {
		StorageResult storageResult = new StorageResult();
		
		storageResult.setMessageID("312312");		
		storageResult.setTimestamp("12346567346");
		storageResult.setTimeout("23123");			
								
		Result result1 = new Result();
		result1.setOriginalMessageID("273912873912");
		result1.setTimestamp("88729371923");
		
		Completed completed = new Completed();
		completed.setStatus("1");
		completed.setCompletedMessage("Completed message 001 002");
		result1.setCompleted(completed);
		
		Object obj1 = new Object();
		obj1.setId("12345");
		obj1.setType("objType1");
		
		List<Result> results = new ArrayList<Result>();
		results.add(result1);
		storageResult.setResult(results);
		
		List<Object> objects = new ArrayList<Object>();
		objects.add(obj1);
		result1.setObjects(objects);		
		
		try {
			
			
			StringBuffer xmlStr = new StringBuffer();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			//File file = new File(outputDir + "storageResult.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(StorageResult.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//jaxbMarshaller.marshal(storageResult, file);
			jaxbMarshaller.marshal(storageResult, os);
			//System.out.println(os.toString());
			
			assertTrue( os!= null && os.size() > 0);
			//assertTrue(file.exists() && file.length() > 0);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Test	
	public void testStorageSendMessage() {

		StorageSave storageSave = new StorageSave();

		storageSave.setMessageID("123456");
		storageSave.setTimestamp("12346567346");
		storageSave.setTimeout("23123");

		URL url = new URL();
		url.setValue("a");

		Credentials cred = new Credentials();
		cred.setValue("credentialValue");
		url.setCredentials(cred);
		storageSave.setUrl(url);

		try {
			File file = new File(outputDir + "storageSave.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(SharingPut.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(storageSave, file);
			jaxbMarshaller.marshal(storageSave, System.out);
			
			assertTrue(file.exists() && file.length() > 0);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test	
	public void testSharingPutMessage() {

		SharingPut sharingPut = new SharingPut();

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
		sharingPut.setUrls(urls);

		try {
			File file = new File(outputDir + "sharingPut.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(SharingPut.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(sharingPut, file);
			jaxbMarshaller.marshal(sharingPut, System.out);
			
			assertTrue(file.exists() && file.length() > 0);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}


}
