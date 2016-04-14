package br.ufpb.dicomflow.integrationAPI.mail.impl;

import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;

public class MailHeadBuilderFactory {
	
	public static MailHeadBuilderIF createHeadStrategy(int type){
		
		MailHeadBuilderIF headStrategy;
		
		switch (type) {
		case MailHeadBuilderIF.SMTP_HEAD_STRATEGY:
			headStrategy = new SMTPHeadBuilder();
			break;
		default:
			headStrategy = null;
			break;
		}
		
		return headStrategy;
	}

}
