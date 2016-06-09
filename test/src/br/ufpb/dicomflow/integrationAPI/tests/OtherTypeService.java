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

import javax.xml.bind.annotation.XmlRootElement;

import br.ufpb.dicomflow.integrationAPI.message.xml.Service;

@XmlRootElement(name="otherTypeService")
public class OtherTypeService extends Service {
	
	public static final String OTHER_SERVICE_NAME = "Other";
	public static final String OTHER_SERVICE_ACTION = "Type";
	public static final String OTHER_SERVICE_VERSION = "1.0";
	public static final int OTHER_SERVICE = 171;
	
	public OtherTypeService(){
		super.setName(OTHER_SERVICE_NAME);
		super.setAction(OTHER_SERVICE_ACTION);
		super.setVersion(OTHER_SERVICE_VERSION);
		super.setType(OTHER_SERVICE);
	}

}
