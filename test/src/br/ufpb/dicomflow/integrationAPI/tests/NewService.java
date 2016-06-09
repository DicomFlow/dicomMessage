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

@XmlRootElement(name="service")
public class NewService extends Service{
	
	public static final String NEW_SERVICE_NAME = "New";
	public static final String NEW_SERVICE_ACTION = "Service";
	public static final String NEW_SERVICE_VERSION = "1.0";
	public static final int NEW_SERVICE = 666;
	
	public NewService(){
		super.setName(NEW_SERVICE_NAME);
		super.setAction(NEW_SERVICE_ACTION);
		super.setVersion(NEW_SERVICE_VERSION);
		super.setType(NEW_SERVICE);
	}

}
