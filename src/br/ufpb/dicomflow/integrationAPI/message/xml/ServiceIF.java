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
package br.ufpb.dicomflow.integrationAPI.message.xml;


public interface ServiceIF {
	
	
	/**
	 * StogareSave Service Identifier 
	 */
	public static final int STORAGE_SAVE = 1;
	/**
	 * StogareUpdate Service Identifier 
	 */
	public static final int STORAGE_UPDATE = 2;
	/**
	 * StogareDelete Service Identifier 
	 */
	public static final int STORAGE_DELETE = 3;
	/**
	 * StogareResult Service Identifier 
	 */
	public static final int STORAGE_RESULT = 4;	
	/**
	 * DispositionNotification Service Identifier 
	 */
	public static final int DISPOSITION_NOTIFICATION = 5;
	
	/**
	 * CertificateRequest Service Identifier
	 */
	public static final int CERTIFICATE_REQUEST = 6;
	
	/**
	 * CertificateResult Service Identifier
	 */
	public static final int CERTIFICATE_RESULT = 7;
	
	public static final int SHARING_PUT = 8;
	
	public static final int SHARING_RESULT = 9;

	public int getType();
	
	public void setType(int type);
	
	public String getVersion();
	
	
	public void setVersion(String version);
	
	public String getName();
	
	public void setName(String name);
	
	public String getAction();
	
	public void setAction(String action);

	public String getMessageID();

	public void setMessageID(String messageID);

	public String getTimestamp();

	public void setTimestamp(String timestamp);

	public String getTimeout();

	public void setTimeout(String timeout);

}
