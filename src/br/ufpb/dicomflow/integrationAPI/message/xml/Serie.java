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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author Danilo
 *
 */

@XmlType(name="serie", propOrder={"id", "bodypart", "description", "instances" })
public class Serie {
	
	private String id;
	private String bodypart;
	private String description;
	private int instances;
	
	public String getId() {
		return id;
	}
	
	@XmlAttribute
	public void setId(String id) {
		this.id = id;
	}
	
	public String getBodypart() {
		return bodypart;
	}
	
	@XmlAttribute
	public void setBodypart(String bodypart) {
		this.bodypart = bodypart;
	}
	
	public String getDescription() {
		return description;
	}
	
	@XmlAttribute
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getInstances() {
		return instances;
	}
	
	@XmlAttribute
	public void setInstances(int instances) {
		this.instances = instances;
	}

}
