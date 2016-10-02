package br.ufpb.dicomflow.integrationAPI.tests;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import br.ufpb.dicomflow.integrationAPI.message.xml.Credentials;
import br.ufpb.dicomflow.integrationAPI.message.xml.Patient;
import br.ufpb.dicomflow.integrationAPI.message.xml.Serie;
import br.ufpb.dicomflow.integrationAPI.message.xml.SharingPut;
import br.ufpb.dicomflow.integrationAPI.message.xml.StorageResult;
import br.ufpb.dicomflow.integrationAPI.message.xml.Study;
import br.ufpb.dicomflow.integrationAPI.message.xml.URL;

public class SharingTestCase extends GenericTestCase {
	
	@Test
	public void testCreateSharingPut() {
		SharingPut sharingPut = new SharingPut();
		
		sharingPut.setMessageID("312312");		
		sharingPut.setTimestamp("12346567346");
		sharingPut.setTimeout("23123");
		
		URL url = new URL();
		url.setValue("urlValue");
		
		Credentials cred = new Credentials();
		cred.setValue("credentialValue");
		url.setCredentials(cred);
		
		Patient patient = new Patient();
		patient.setId("idValue");
		patient.setName("nameValue");
		patient.setGender("genderValue");
		patient.setBirthdate("birthdateValue");
		
		Study study = new Study();
		study.setId("idValue");
		study.setType("typeValue");
		study.setDescription("descriptionValue");
		study.setDatetime("datetimeValue");
		study.setSize(3232323);
		
		Serie serie = new Serie();
		serie.setId("idValue");
		serie.setDescription("descriptionValue");
		serie.setBodypart("bodyPartValue");
		serie.setInstances(361);
		
		study.addSerie(serie);
		
		serie = new Serie();
		serie.setId("idValue1");
		serie.setDescription("descriptionValue1");
		serie.setBodypart("bodyPartValue1");
		serie.setInstances(362);
		
		study.addSerie(serie);
		
		patient.addStudy(study);
		
		url.addPatient(patient);
			
		sharingPut.addUrl(url);
								
				
		
		try {
			
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			//File file = new File(outputDir + "storageResult.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(SharingPut.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//jaxbMarshaller.marshal(storageResult, file);
			jaxbMarshaller.marshal(sharingPut, os);
			System.out.println(os.toString());
			
			assertTrue( os!= null && os.size() > 0);
			//assertTrue(file.exists() && file.length() > 0);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
