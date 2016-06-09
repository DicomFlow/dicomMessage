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
public class StorageUpdate extends Service {
	
	public static final String STORAGE_UPDATE_NAME = "Storage";
	public static final String STORAGE_UPDATE_ACTION = "Update";
	public static final String STORAGE_UPDATE_VERSION = "1.0";
		
	private Object object;
	private URL url;
	
	public StorageUpdate(){
		super.setName(STORAGE_UPDATE_NAME);
		super.setAction(STORAGE_UPDATE_ACTION);
		super.setVersion(STORAGE_UPDATE_VERSION);
		super.setType(ServiceIF.STORAGE_UPDATE);
		
	}
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public URL getUrl() {
		return url;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	
	

	
}
