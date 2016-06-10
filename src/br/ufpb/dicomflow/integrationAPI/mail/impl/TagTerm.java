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
package br.ufpb.dicomflow.integrationAPI.mail.impl;

import javax.mail.Message;
import javax.mail.search.SearchTerm;

import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;

public class TagTerm extends SearchTerm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4250882121740050776L;
	
	private String value;
	private String tag;
	
	public TagTerm(){
		super();
	}
	
	public TagTerm(String tag, String value){
		super();
		this.tag = tag;
		this.value = value;
	}
	
	

	@Override
	public boolean match(Message message) {
		if(tag != null && value !=  null){
			//System.out.println("TAG : " + tag);
			//System.out.println("VALUE : " + value);
			MailHeadBuilderIF headBuilder = MailHeadBuilderFactory.createHeadStrategy(MailHeadBuilderIF.SMTP_HEAD_STRATEGY);
			String tagValue = headBuilder.getHeaderValue(message,tag);
			if(tagValue != null){
				//System.out.println("MATCH : " + tagValue.equals(value));
				return tagValue.equals(value);
			}
		}
		return false;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
}
