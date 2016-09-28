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

import java.util.Calendar;
import java.util.Date;

import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import br.ufpb.dicomflow.integrationAPI.mail.FilterIF;

public class SMTPFilter implements FilterIF {
	
	private Date initialDate;
	private Date finalDate;
	private Integer serviceType;
	private String idMessage;

	@Override
	public Date getFinalDate() {
		return finalDate;
	}

	@Override
	public String getIdMessage() {
		return idMessage;
	}

	@Override
	public Date getInitialDate() {
		return initialDate;
	}

	@Override
	public Integer getServiceType() {
		return serviceType;
	}

	@Override
	public SearchTerm getTerm() {
		SearchTerm term = null;
		
		if(this.getInitialDate() != null){
			Calendar start = Calendar.getInstance();
			start.setTime(this.getInitialDate());
			//System.out.println("Data inicial: " + start.toString());
			
			ReceivedDateTerm startDateTerm = new ReceivedDateTerm(ComparisonTerm.GE, this.getInitialDate());
			
			term = startDateTerm;
		
		}
		
		if(this.getFinalDate() != null){
			Calendar end = Calendar.getInstance();
			end.setTime(this.getFinalDate());
			//System.out.println("Data final: " + end.toString());
			ReceivedDateTerm endDateTerm = new ReceivedDateTerm(ComparisonTerm.LT, this.getFinalDate());
			if(term != null){
				term = new AndTerm(term, endDateTerm);
			}else{
				term = endDateTerm;
			}
		}
		
		if(this.getServiceType() != null){
			SearchTerm tagTerm = new TagTerm(MailXTags.SERVICE_TYPE_X_TAG, String.valueOf(this.getServiceType()));
			if(term != null){
				term = new AndTerm(term, tagTerm);
			}else{
				term = tagTerm;
			}
			
		}
		
		if(this.getIdMessage() != null){
			SearchTerm tagTerm = new TagTerm(MailXTags.MESSAGE_ID_X_TAG, this.getIdMessage());
			if(term != null){
				term = new AndTerm(term, tagTerm);
			}else{
				term = tagTerm;
			}
			
		}
		//System.out.println("Term : " + term);
		return term;
	}

	@Override
	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}

	@Override
	public void setIdMessage(String idMessage) {
		this.idMessage = idMessage;
	}

	@Override
	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	@Override
	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

}
