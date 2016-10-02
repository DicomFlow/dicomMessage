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
public class SharingPut extends Service {
	
	public static final String SHARING_PUT_NAME = "Sharing";
	public static final String SHARING_PUT_ACTION = "Put";
	public static final String SHARING_PUT_VERSION = "1.0";
	
	private List<URL> url;
	
	public SharingPut(){
		super.setName(SHARING_PUT_NAME);
		super.setAction(SHARING_PUT_ACTION);
		super.setVersion(SHARING_PUT_VERSION);
		super.setType(ServiceIF.SHARING_PUT);
		this.url = new ArrayList<URL>();
	}

	@XmlElementWrapper(name="urls")
	public List<URL> getUrl() {
		return url;
	}

	public void setUrl(List<URL> url) {
		this.url = url;
	}
	
	public boolean addUrl(URL url){
		return this.url.add(url);
	}
	
}