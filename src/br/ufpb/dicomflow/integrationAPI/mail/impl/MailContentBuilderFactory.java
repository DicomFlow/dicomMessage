package br.ufpb.dicomflow.integrationAPI.mail.impl;

import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;

public class MailContentBuilderFactory {
	
	public static MailContentBuilderIF createContentStrategy(int type){
		
		MailContentBuilderIF contentStrategy;
		
		switch (type) {
		case MailContentBuilderIF.SMTP_SIMPLE_CONTENT_STRATEGY:
			contentStrategy = new SMTPContentBuilder();
			break;
		default:
			contentStrategy = null;
			break;
		}
		
		return contentStrategy;
	}

}
