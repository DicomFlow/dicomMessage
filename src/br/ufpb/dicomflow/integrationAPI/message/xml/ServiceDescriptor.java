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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author Danilo
 *
 */
@XmlType(name="serviceDescriptor" , propOrder={ "name", "status", "actionDescriptor"})
public class ServiceDescriptor {
	
	private String name;
	private String status;
	
	private List<ActionDescriptor> actionDescriptor;
	
	
	public ServiceDescriptor(){
		this.actionDescriptor = new ArrayList<ActionDescriptor>();
	}

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	@XmlAttribute
	public void setStatus(String status) {
		this.status = status;
	}

	public List<ActionDescriptor> getActionDescriptor() {
		return actionDescriptor;
	}

	public void setActionDescriptor(List<ActionDescriptor> actionDescriptor) {
		this.actionDescriptor = actionDescriptor;
	}
	
	
	public boolean addActionDescriptor(ActionDescriptor action){
		return this.actionDescriptor.add(action);
	}
	
	

}
