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
			MailHeadBuilderIF headBuilder = MailHeadBuilderFactory.createHeadStrategy(MailHeadBuilderIF.SMTP_HEAD_STRATEGY);
			String tagValue = headBuilder.getHeaderValue(message,tag);
			return tagValue.equals(value);
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
