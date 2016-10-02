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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="service")
public class StorageDelete extends Service {
	
	public static final String STORAGE_DELETE_NAME = "Storage";
	public static final String STORAGE_DELETE_ACTION = "Delete";
	public static final String STORAGE_DELETE_VERSION = "1.0";		
	
	private List<Object> object;
		
	public StorageDelete(){
		super.setName(STORAGE_DELETE_NAME);
		super.setAction(STORAGE_DELETE_ACTION);
		super.setVersion(STORAGE_DELETE_VERSION);
		super.setType(ServiceIF.STORAGE_DELETE);
		this.object = new ArrayList<Object>();
	}
	

	public List<Object> getObject() {
		return object;
	}
	
	@XmlElementWrapper(name="objects")
	public void setObject(List<Object> object) {
		this.object = object;
	}
	
	public boolean addObject(Object object){
		return this.object.add(object);
	}

}
