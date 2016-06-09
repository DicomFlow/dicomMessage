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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import br.ufpb.dicomflow.integrationAPI.mail.FilterIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailMessageReaderIF;

public class SMTPMessageReader implements MailMessageReaderIF {
	
	private String hostProvider;
	private String folderName;

	 
	
	public SMTPMessageReader(String hostProvider,String folderName) {
//		this.provider = provider;
		this.hostProvider = hostProvider;
		this.folderName = folderName;
	}

	@Override
	public List<Message> getMessages(Session session, FilterIF filter) {
		
		List<Message> messages = new ArrayList<Message>();
		
		try {
			
			
			Store store = session.getStore(/*this.provider*/);
			store.connect(this.hostProvider, null, null);

		    Folder folder = store.getFolder(this.folderName);
		    
		    if (folder == null) {
		    	return messages;
		    }
		    
		    folder.open(Folder.READ_WRITE);
		    
		    SearchTerm term = filter.getTerm();
		    if(term != null){
		    	messages.addAll(Arrays.asList(folder.search(term)));
		    }else{
		    	messages.addAll(Arrays.asList(folder.getMessages()));
		    }
		    
		    //TODO resolver o fechamento do folder e o store
		    //folder.close(false);
		    //store.close();
	    
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return messages;
	}

	

//	public String getProvider() {
//		return provider;
//	}
//
//	public void setProvider(String provider) {
//		this.provider = provider;
//	}

	public String getHostProvider() {
		return hostProvider;
	}

	public void setHostProvider(String hostProvider) {
		this.hostProvider = hostProvider;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	@Override
	public int getType() {
		return MailMessageReaderIF.SMTP_MESSAGE_STRATEGY;
	}

	
	
	
}
