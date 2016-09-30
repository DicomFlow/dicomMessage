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
public class RequestPut extends Service {
	
	public static final String REQUEST_PUT_NAME = "Request";
	public static final String REQUEST_PUT_ACTION = "Put";
	public static final String REQUEST_PUT_VERSION = "1.0";
	
	public static final String TYPE_REPORT = "REPORT";
	public static final String TYPE_IMAGE_PROCESSING = "IMAGE_PROCESSING";
	
	private String requestType;
	
	private URL url;
	
	public RequestPut(){
		super.setName(REQUEST_PUT_NAME);
		super.setAction(REQUEST_PUT_ACTION);
		super.setVersion(REQUEST_PUT_VERSION);
		super.setType(ServiceIF.REQUEST_PUT);
	}
	
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
	
	
}