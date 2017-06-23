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
	
	public Boolean getUnreadOnly();

	public void setUnreadOnly(Boolean unreadOnly);
	
	public SearchTerm getTerm();
	
	

}
