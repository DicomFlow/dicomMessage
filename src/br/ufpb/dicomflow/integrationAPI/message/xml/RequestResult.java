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

/**
 * 
 * @author Danilo
 *
 */

@XmlRootElement(name="service")
public class RequestResult extends Service {
	
	public static final String REQUEST_RESULT_NAME = "Request";
	public static final String REQUEST_RESULT_ACTION = "Result";
	public static final String REQUEST_RESULT_VERSION = "1.0";
	
	private List<Result> result;
	
	public RequestResult(){
		super.setName(REQUEST_RESULT_NAME);
		super.setAction(REQUEST_RESULT_ACTION);
		super.setVersion(REQUEST_RESULT_NAME);
		super.setType(ServiceIF.REQUEST_RESULT);
		this.result = new ArrayList<Result>();
		
	}
	
	@XmlElementWrapper(name="results")
	public void setResult(List<Result> result) {
		this.result = result;
	}

	public List<Result> getResult() {
		return result;
	}
	
	public boolean addResult(Result result){
		return this.result.add(result);
	}


}
