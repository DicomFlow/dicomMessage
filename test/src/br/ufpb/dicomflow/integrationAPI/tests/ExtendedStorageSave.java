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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufpb.dicomflow.integrationAPI.message.xml.StorageSave;

@XmlRootElement(name="service")
public class ExtendedStorageSave extends StorageSave {
	
	public static final String STORAGE_SAVE_NAME = "Storage";
	public static final String STORAGE_SAVE_ACTION = "ExtendedSave";
	public static final String STORAGE_SAVE_VERSION = "1.0";
	
	private String extraAttribute1;
	
	private String extraAttribute2;
	
	public ExtendedStorageSave(){
		super();
		super.setAction(STORAGE_SAVE_ACTION);
		
	}

	@XmlAttribute
	public String getExtraAttribute1() {
		return extraAttribute1;
	}

	public void setExtraAttribute1(String extraAttribute1) {
		this.extraAttribute1 = extraAttribute1;
	}

	public String getExtraAttribute2() {
		return extraAttribute2;
	}

	public void setExtraAttribute2(String extraAttribute2) {
		this.extraAttribute2 = extraAttribute2;
	}	
	

}
