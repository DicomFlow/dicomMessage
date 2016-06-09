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

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="result")
public class Result {
	
	private String originalMessageID;
	private String timestamp;
	private List<Object> objects;
	private Completed completed;
	
		
	public String getOriginalMessageID() {
		return originalMessageID;
	}

	public void setOriginalMessageID(String originalMessageID) {
		this.originalMessageID = originalMessageID;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public List<Object> getObjects() {
		return objects;
	}

	@XmlElementWrapper(name="objects")
	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}

	public Completed getCompleted() {
		return completed;
	}

	public void setCompleted(Completed completed) {
		this.completed = completed;
	}		
	
}
