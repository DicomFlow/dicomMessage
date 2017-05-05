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
package br.ufpb.dicomflow.integrationAPI.conf;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import br.ufpb.dicomflow.integrationAPI.exceptions.PropertyNotFoundException;

public class DicomMessageProperties {
	
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
	private static DicomMessageProperties instance;
	private Properties properties;
	
	
	public static DicomMessageProperties getInstance() {
		if (instance == null) {
			instance = new DicomMessageProperties();
		}
		return instance;
	}

	
	private DicomMessageProperties () {
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
		File dir = new File(DicomMessageProperties.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		path = dir.toString();		
		return path;
	}
	
	/* Properties */	
	
	public String getProperty(String property) throws PropertyNotFoundException{
		if(properties.getProperty(property) == null || properties.getProperty(property).equals("")){
//			throw new PropertyNotFoundException(property + " not found."); 
			return null;
		}
		return properties.getProperty(property);
	}
	

	
	public Properties getSendProperties() throws PropertyNotFoundException{
		Properties sendProperties = new Properties();
		if(getProperty(DOMAIN) != null){
			sendProperties.put(DOMAIN, getProperty(DOMAIN));
		}
		if(getProperty(MAIL_DEBUG) != null){
			sendProperties.put(MAIL_DEBUG, getProperty(MAIL_DEBUG));
		}
		if(getProperty(MAIL_SMTP_HOST) != null){
			sendProperties.put(MAIL_SMTP_HOST, getProperty(MAIL_SMTP_HOST));
		}
		if(getProperty(MAIL_SMTP_AUTH) != null){
			sendProperties.put(MAIL_SMTP_AUTH, getProperty(MAIL_SMTP_AUTH));
		}
		if(getProperty(MAIL_SMTP_SOCKET_FACTORY_PORT) != null){
			sendProperties.put(MAIL_SMTP_SOCKET_FACTORY_PORT, getProperty(MAIL_SMTP_SOCKET_FACTORY_PORT));
		}
		if(getProperty(MAIL_SMTP_PORT) != null){
			sendProperties.put(MAIL_SMTP_PORT, getProperty(MAIL_SMTP_PORT));
		}
		if(getProperty(MAIL_SMTP_STARTTLS_ENABLE) != null){
			sendProperties.put(MAIL_SMTP_STARTTLS_ENABLE, getProperty(MAIL_SMTP_STARTTLS_ENABLE));
		}
		if(getProperty(MAIL_SMTP_SOCKET_FACTORY_CLASS) != null){
			sendProperties.put(MAIL_SMTP_SOCKET_FACTORY_CLASS, getProperty(MAIL_SMTP_SOCKET_FACTORY_CLASS));
		}
		if(getProperty(MAIL_SMTP_SOCKET_FACTORY_FALLBACK) != null){
			sendProperties.put(MAIL_SMTP_SOCKET_FACTORY_FALLBACK, getProperty(MAIL_SMTP_SOCKET_FACTORY_FALLBACK));
		}
		if(getProperty(AUTHENTICATION_LOGIN) != null){
			sendProperties.put(AUTHENTICATION_LOGIN, getProperty(AUTHENTICATION_LOGIN));
		}
		if(getProperty(AUTHENTICATION_PASSWORD) != null){
			sendProperties.put(AUTHENTICATION_PASSWORD, getProperty(AUTHENTICATION_PASSWORD));
		}
		return sendProperties;
	}
	
	public Properties getReceiveProperties() throws PropertyNotFoundException{
		Properties receiveProperties = new Properties();
		if(getProperty(DOMAIN) != null){
			receiveProperties.put(DOMAIN, getProperty(DOMAIN));
		}
		if(getProperty(MAIL_IMAP_SOCKET_FACTORY_PORT) != null){
			receiveProperties.put(MAIL_IMAP_SOCKET_FACTORY_PORT, getProperty(MAIL_IMAP_SOCKET_FACTORY_PORT));
		}
		if(getProperty(MAIL_IMAP_PORT) != null){
			receiveProperties.put(MAIL_IMAP_PORT, getProperty(MAIL_IMAP_PORT));
		}
		if(getProperty(MAIL_IMAP_SOCKET_FACTORY_CLASS) != null){
			receiveProperties.put(MAIL_IMAP_SOCKET_FACTORY_CLASS, getProperty(MAIL_IMAP_SOCKET_FACTORY_CLASS));
		}
		if(getProperty(MAIL_IMAP_SOCKET_FACTORY_FALLBACK) != null){
			receiveProperties.put(MAIL_IMAP_SOCKET_FACTORY_FALLBACK, getProperty(MAIL_IMAP_SOCKET_FACTORY_FALLBACK));
		}
		if(getProperty(MAIL_STORE_PROTOCOL) != null){
			receiveProperties.put(MAIL_STORE_PROTOCOL, getProperty(MAIL_STORE_PROTOCOL));
		}
		if(getProperty(AUTHENTICATION_LOGIN) != null){
			receiveProperties.put(AUTHENTICATION_LOGIN, getProperty(AUTHENTICATION_LOGIN));
		}
		if(getProperty(AUTHENTICATION_PASSWORD) != null){
			receiveProperties.put(AUTHENTICATION_PASSWORD, getProperty(AUTHENTICATION_PASSWORD));
		}
		if(getProperty(PROVIDER_HOST) != null){
			receiveProperties.put(PROVIDER_HOST, getProperty(PROVIDER_HOST));
		}
		if(getProperty(PROVIDER_FOLDER) != null){
			receiveProperties.put(PROVIDER_FOLDER, getProperty(PROVIDER_FOLDER));
		}
		return receiveProperties;
	}
	
	public String toString(){
		return properties.toString();
	}
							
}
