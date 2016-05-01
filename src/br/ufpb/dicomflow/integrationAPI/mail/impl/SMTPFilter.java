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
			System.out.println("Data inicial: " + start.toString());
			
			ReceivedDateTerm startDateTerm = new ReceivedDateTerm(ComparisonTerm.GE, this.getInitialDate());
			if(term != null){
				term = new AndTerm(term, startDateTerm);
			}else{
				term = startDateTerm;
			}
		}
		
		if(this.getFinalDate() != null){
			Calendar end = Calendar.getInstance();
			end.setTime(this.getFinalDate());
			System.out.println("Data final: " + end.toString());
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
		System.out.println("Term : " + term);
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
