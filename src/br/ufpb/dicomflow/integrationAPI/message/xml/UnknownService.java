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

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="service")
public class UnknownService extends Service {
	
	public static final String UNKNOWN_SERVICE_NAME = "Unknown";
	public static final String UNKNOWN_SERVICE_ACTION = "Service";
	public static final String UNKNOWN_SERVICE_VERSION = "1.0";
	public static final int UNKNOWN_SERVICE = -1;
		
	public UnknownService(){
		super.setName(UNKNOWN_SERVICE_NAME);
		super.setAction(UNKNOWN_SERVICE_ACTION);
		super.setVersion(UNKNOWN_SERVICE_VERSION);
		super.setType(UNKNOWN_SERVICE);
	}
	
	public UnknownService(String description){
		this();
		this.description = description;
	}
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	


}
