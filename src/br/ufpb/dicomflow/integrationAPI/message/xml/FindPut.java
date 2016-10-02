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

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="service")
public class FindPut extends Service {
	
	public static final String FIND_PUT_NAME = "Find";
	public static final String FIND_PUT_ACTION = "Put";
	public static final String FIND_PUT_VERSION = "1.0";
	
	private String timezone;
	private int priority;
	
	private List<Search> search;
	
	public FindPut(){
		super.setName(FIND_PUT_NAME);
		super.setAction(FIND_PUT_ACTION);
		super.setVersion(FIND_PUT_VERSION);
		super.setType(ServiceIF.FIND_PUT);
		this.search = new ArrayList<Search>();
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public List<Search> getSearch() {
		return search;
	}

	public void setSearch(List<Search> search) {
		this.search = search;
	}
	
	
	public boolean addSearch(Search search){
		return this.search.add(search);
	}
	
	
}