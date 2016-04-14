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
