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

/**
 * 
 * @author Danilo
 *
 */

@XmlRootElement(name="service")
public class DiscoveryVerifyAllServices extends Service {
	
	
	
	public static final String DISCOVERY_VERIFY_ALL_SERVICES_NAME = "Discovery";
	public static final String DISCOVERY_VERIFY_ALL_SERVICES_ACTION = "VerifyAllServices";
	public static final String DISCOVERY_VERIFY_ALL_SERVICES_VERSION = "1.0";
	
	
	public static final int DETAIL_SERVICE = 1;
	public static final int DETAIL_ACTION = 2;
	public static final int DETAIL_FIELD = 3;
	
	public static final int PRIORITY_LOWEST = 5;
	public static final int PRIORITY_LOW = 4;
	public static final int PRIORITY_NORMAL = 3;
	public static final int PRIORITY_HIGH = 3;
	public static final int PRIORITY_HIGHER = 1;
	
	private String timezone;
	private int detail;
	private int priority;
	
	
	public DiscoveryVerifyAllServices(){
		super.setName(DISCOVERY_VERIFY_ALL_SERVICES_NAME);
		super.setAction(DISCOVERY_VERIFY_ALL_SERVICES_ACTION);
		super.setVersion(DISCOVERY_VERIFY_ALL_SERVICES_VERSION);
		super.setType(ServiceIF.DISCOVERY_VERIFY_ALL_SERVICES);
	}


	public String getTimezone() {
		return timezone;
	}


	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}


	public int getDetail() {
		return detail;
	}


	public void setDetail(int detail) {
		this.detail = detail;
	}


	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}

	
	
}