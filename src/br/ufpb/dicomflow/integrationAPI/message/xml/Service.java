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

@XmlType(name="service", propOrder={"version", "name", "action", "messageID", "timestamp", "timeout", "type"})
public class Service implements ServiceIF {
			
	private String version;	
	private String name;	
	private String action;
	private int type;
	
	private String messageID;
	private String timestamp;
	private String timeout;
	
	public Service(){
		
	}
	
	public Service(String name, String action, String version){
		this.name = name;
		this.action = action;
		this.version = version;
	}
	
	public String getVersion() {
		return version;
	}
	
	@XmlAttribute
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getName() {
		return name;
	}
	
	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAction() {
		return action;
	}
	
	@XmlAttribute
	public void setAction(String action) {
		this.action = action;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	@XmlAttribute
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}	
	
	
}
