package br.ufpb.dicomflow.integrationAPI.main;

import br.ufpb.dicomflow.integrationAPI.exceptions.ServiceCreationException;
import br.ufpb.dicomflow.integrationAPI.message.xml.BasicService;
import br.ufpb.dicomflow.integrationAPI.message.xml.CertificateRequest;
import br.ufpb.dicomflow.integrationAPI.message.xml.CertificateResult;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;
import br.ufpb.dicomflow.integrationAPI.message.xml.StorageDelete;
import br.ufpb.dicomflow.integrationAPI.message.xml.StorageResult;
import br.ufpb.dicomflow.integrationAPI.message.xml.StorageSave;
import br.ufpb.dicomflow.integrationAPI.message.xml.StorageUpdate;
/**
 * Class for creating services
 * @author Danilo
 *
 */
public class ServiceFactory {
	/**
	 * Strategy of building services. Create a corresponding service provided identifier
	 * @param serviceType provided Identifier
	 * @return returns the Service. If serviceType is undefined returns a basic Service with serviceType
	 */
	public static ServiceIF createService(int serviceType){
		
		ServiceIF service = null;
		switch (serviceType) {
		case ServiceIF.STORAGE_SAVE:
			service = createStorageSave();
			break;
		case ServiceIF.STORAGE_UPDATE:
			service = createStorageUpdate();
			break;
		case ServiceIF.STORAGE_DELETE:
			service = createStorageDelete();
			break;
		case ServiceIF.STORAGE_RESULT:
			service = createStorageResult();
			break;
		case ServiceIF.CERTIFICATE_REQUEST:
			service = createCertificateRequest();
			break;
		case ServiceIF.CERTIFICATE_RESULT:
			service = createCertificateResult();
			break;
		default:
			service = new BasicService();
			service.setType(serviceType);
			break;
		}
		
		return service;
	}
	
	/**
	 * Strategy of building services. Create a corresponding service provided identifier
	 * @param serviceType provided Identifier
	 * @param idMessageGenerator Strategy to generate messageID
	 * @return returns the Service or null if the serviceId is invalid 
	 */
	public static ServiceIF createService(int serviceType, IdMessageGeneratorStrategyIF idMessageGenerator){
		
		ServiceIF service = createService(serviceType);
		
		if(service != null){
			service.setMessageID(idMessageGenerator.getNextId());
		}
		
		return service;
	}
	
	protected static ServiceIF createStorageSave(){
		
		ServiceIF service = null;
		
		try {
			
			service = createGenericService(StorageSave.class);
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ServiceCreationException e) {
			e.printStackTrace();
		}
		return service; 
		
		
	}
	
	protected  static ServiceIF createStorageUpdate(){
		
		ServiceIF service = null;
		
		try {
			
			service = createGenericService(StorageUpdate.class);
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ServiceCreationException e) {
			e.printStackTrace();
		}
		return service; 
		
		
	}
	
	
	protected static ServiceIF createStorageDelete(){
		
		ServiceIF service = null;
		
		try {
			
			service = createGenericService(StorageDelete.class);
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ServiceCreationException e) {
			e.printStackTrace();
		}
		return service; 
		
		
	}
	
	protected static ServiceIF createStorageResult(){
		
		ServiceIF service = null;
		
		try {
			
			service = createGenericService(StorageResult.class);
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ServiceCreationException e) {
			e.printStackTrace();
		}
		return service; 
		
		
	}
	
	protected static ServiceIF createCertificateRequest(){
		
		ServiceIF service = null;
		
		try {
			
			service = createGenericService(CertificateRequest.class);
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ServiceCreationException e) {
			e.printStackTrace();
		}
		return service; 
		
		
	}
	
	protected static ServiceIF createCertificateResult(){
		
		ServiceIF service = null;
		
		try {
			
			service = createGenericService(CertificateResult.class);
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ServiceCreationException e) {
			e.printStackTrace();
		}
		return service; 
		
		
	}
	
	protected  static ServiceIF createGenericService(Class serviceClass, String name, String action, String version) throws InstantiationException, IllegalAccessException, ServiceCreationException{
		
		
		ServiceIF service = createGenericService(serviceClass);
		
		service.setName(name);
		service.setAction(action);
		service.setVersion(version);
		return service;
				
			
	}
	
	
	protected  static ServiceIF createGenericService(Class serviceClass) throws InstantiationException, IllegalAccessException, ServiceCreationException{
		
		
		Object object = serviceClass.newInstance();
		
		if(object instanceof ServiceIF){
			
			ServiceIF service = (ServiceIF) object;
			service.setMessageID(DefaultIdMessageGeneratorStrategy.getInstance().getNextId());
			
			return (ServiceIF) object;
			
		}else{
			//TODO elaborar os tipos de exception e internacionalizar as mensagens.
			throw new ServiceCreationException("Classe não é um serviço válido");
		}	
			
	}
}
