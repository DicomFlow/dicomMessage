package br.ufpb.dicomflow.integrationAPI.mail;

import java.util.Date;

import javax.mail.search.SearchTerm;

public interface FilterIF {
	
	public void setInitialDate(Date initialDate);
	
	public void setFinalDate(Date finalDate);
	
	public void setServiceType(Integer serviceType);
	
	public void setIdMessage(String idMessage);
	
	public Date getInitialDate();
	
	public Date getFinalDate();
	
	public Integer getServiceType();
	
	public String getIdMessage();
	
	public SearchTerm getTerm();
	
	

}
