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

import java.util.Properties;

import br.ufpb.dicomflow.integrationAPI.mail.AbstractMailSender;
import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;

public class SMTPSender extends AbstractMailSender {
	
	private Properties properties;
	private MailAuthenticatorIF authenticatorBuilder;
	private MailHeadBuilderIF headBuilder;
	private MailContentBuilderIF contentBuilder;

	@Override
	public Properties getProperties() {
		return this.properties;
	}

	@Override
	public MailAuthenticatorIF getAuthenticatorBuilder() {
		return this.authenticatorBuilder;
	}

	@Override
	public MailHeadBuilderIF getHeadBuilder() {
		return this.headBuilder;
	}

	@Override
	public MailContentBuilderIF getContentBuilder() {
		return this.contentBuilder;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setAuthenticatorBuilder(MailAuthenticatorIF authenticatorBuilder) {
		this.authenticatorBuilder = authenticatorBuilder;
	}

	public void setHeadBuilder(MailHeadBuilderIF headBuilder) {
		this.headBuilder = headBuilder;
	}

	public void setContentBuilder(MailContentBuilderIF contentBuilder) {
		this.contentBuilder = contentBuilder;
	}

	
	
}
