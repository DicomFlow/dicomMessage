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
import javax.mail.search.SearchTerm;

import br.ufpb.dicomflow.integrationAPI.mail.FilterIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailMessageReaderIF;

public class SMTPMessageReader implements MailMessageReaderIF {
	
	private String hostProvider;
	private String folderName;
	private Folder folder;
	private Store store;

	 
	
	public SMTPMessageReader(String hostProvider,String folderName) {
//		this.provider = provider;
		this.hostProvider = hostProvider;
		this.folderName = folderName;
	}

	public void openFolder(Session session){
		try {
			this.store = session.getStore(/*this.provider*/);
			this.store.connect(this.hostProvider, null, null);

		    this.folder = store.getFolder(this.folderName);
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<Message> getMessages(FilterIF filter) {
		
		List<Message> messages = new ArrayList<Message>();
		
		try {
			
		    
		    if (this.folder == null) {
		    	return messages;
		    }
		    
		    this.folder.open(Folder.READ_WRITE);
		    
		    SearchTerm term = filter.getTerm();
		    if(term != null){
		    	messages.addAll(Arrays.asList(folder.search(term)));
		    }else{
		    	messages.addAll(Arrays.asList(folder.getMessages()));
		    }
		    
		    
	    
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return messages;
	}

	
	public void closeFolder(){
		if(this.folder != null && this.store != null){
			try {
				this.folder.close(false);
				this.store.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			
		}
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
