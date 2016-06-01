package br.ufpb.dicomflow.integrationAPI.conf;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import br.ufpb.dicomflow.integrationAPI.exceptions.PropertyNotFoundException;

public class IntegrationAPIProperties {
	
	public static final String DOMAIN = "domain";
	public static final String MAIL_DEBUG = "mail.debug";
	public static final String MAIL_SMTP_HOST = "mail.smtp.host";
	public static final String MAIL_SMTP_AUTH ="mail.smtp.auth";
	public static final String MAIL_SMTP_SOCKET_FACTORY_PORT ="mail.smtp.socketFactory.port";
	public static final String MAIL_IMAP_SOCKET_FACTORY_PORT ="mail.imap.socketFactory.port";
	public static final String MAIL_SMTP_PORT ="mail.smtp.port";
	public static final String MAIL_IMAP_PORT ="mail.imap.port";
	public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	public static final String MAIL_SMTP_SOCKET_FACTORY_CLASS ="mail.smtp.socketFactory.class";
	public static final String MAIL_IMAP_SOCKET_FACTORY_CLASS = "mail.imap.socketFactory.class";
	public static final String MAIL_SMTP_SOCKET_FACTORY_FALLBACK ="mail.smtp.socketFactory.fallback";
	public static final String MAIL_IMAP_SOCKET_FACTORY_FALLBACK = "mail.imap.socketFactory.fallback";
	public static final String MAIL_STORE_PROTOCOL = "mail.store.protocol";
	public static final String AUTHENTICATION_LOGIN ="authentication.login";
	public static final String AUTHENTICATION_PASSWORD ="authentication.password";
	public static final String PROVIDER_HOST = "provider.host";
	public static final String PROVIDER_FOLDER = "provider.folder";
	public static final String LOG_MIN_SEVERITY ="log.min.severity";

	
	public static final String CONFIG_FILE_PATH = "conf/config.properties";
	private static IntegrationAPIProperties instance;
	private Properties properties;
	
	
	public static IntegrationAPIProperties getInstance() {
		if (instance == null) {
			instance = new IntegrationAPIProperties();
		}
		return instance;
	}

	
	private IntegrationAPIProperties () {
		super();		
		properties = new Properties();
		
	}
	
	
	public void load(String path){
		try {			
			//configFilePath = findAppDir() + configFilePath;
			properties = new Properties();
			properties.load(new FileInputStream(path));
		} catch (Exception e) {
			//TODO - Log exception
			e.printStackTrace();
		}
	}
	
	
	public static String findAppDir() {
		String path = "";
		File dir = new File(IntegrationAPIProperties.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		path = dir.toString();		
		return path;
	}
	
	/* Properties */	
	
	public String getProperty(String property) throws PropertyNotFoundException{
		if(properties.getProperty(property) == null || properties.getProperty(property).equals("")){
			throw new PropertyNotFoundException(property + " not found."); 
		}
		return properties.getProperty(property);
	}
	

	
	public Properties getSendProperties() throws PropertyNotFoundException{
		Properties sendProperties = new Properties();
		sendProperties.put(DOMAIN, getProperty(DOMAIN));
		sendProperties.put(MAIL_DEBUG, getProperty(MAIL_DEBUG));
		sendProperties.put(MAIL_SMTP_HOST, getProperty(MAIL_SMTP_HOST));
		sendProperties.put(MAIL_SMTP_AUTH, getProperty(MAIL_SMTP_AUTH));
		sendProperties.put(MAIL_SMTP_SOCKET_FACTORY_PORT, getProperty(MAIL_SMTP_SOCKET_FACTORY_PORT));
		sendProperties.put(MAIL_SMTP_PORT, getProperty(MAIL_SMTP_PORT));
		sendProperties.put(MAIL_SMTP_STARTTLS_ENABLE, getProperty(MAIL_SMTP_STARTTLS_ENABLE));
		sendProperties.put(MAIL_SMTP_SOCKET_FACTORY_CLASS, getProperty(MAIL_SMTP_SOCKET_FACTORY_CLASS));
		sendProperties.put(MAIL_SMTP_SOCKET_FACTORY_FALLBACK, getProperty(MAIL_SMTP_SOCKET_FACTORY_FALLBACK));
		sendProperties.put(AUTHENTICATION_LOGIN, getProperty(AUTHENTICATION_LOGIN));
		sendProperties.put(AUTHENTICATION_PASSWORD, getProperty(AUTHENTICATION_PASSWORD));
		return sendProperties;
	}
	
	public Properties getReceiveProperties() throws PropertyNotFoundException{
		Properties receiveProperties = new Properties();
		receiveProperties.put(DOMAIN, getProperty(DOMAIN));
		receiveProperties.put(MAIL_IMAP_SOCKET_FACTORY_PORT, getProperty(MAIL_IMAP_SOCKET_FACTORY_PORT));
		receiveProperties.put(MAIL_IMAP_PORT, getProperty(MAIL_SMTP_PORT));
		receiveProperties.put(MAIL_IMAP_SOCKET_FACTORY_CLASS, getProperty(MAIL_IMAP_SOCKET_FACTORY_CLASS));
		receiveProperties.put(MAIL_IMAP_SOCKET_FACTORY_FALLBACK, getProperty(MAIL_IMAP_SOCKET_FACTORY_FALLBACK));
		receiveProperties.put(MAIL_STORE_PROTOCOL, getProperty(MAIL_STORE_PROTOCOL));
		receiveProperties.put(AUTHENTICATION_LOGIN, getProperty(AUTHENTICATION_LOGIN));
		receiveProperties.put(AUTHENTICATION_PASSWORD, getProperty(AUTHENTICATION_PASSWORD));
		receiveProperties.put(PROVIDER_HOST, getProperty(PROVIDER_HOST));
		receiveProperties.put(PROVIDER_FOLDER, getProperty(PROVIDER_FOLDER));
		return receiveProperties;
	}
	
	public String toString(){
		return properties.toString();
	}
							
}
