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

import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;

public class MailAuthenticatorFactory {
	
	public static MailAuthenticatorIF createHeadStrategy(int type, String mail, String pwd){
		
		MailAuthenticatorIF authStrategy;
		
		switch (type) {
		case MailAuthenticatorIF.SMTP_AUTHENTICATOR:
			authStrategy = new SMTPAuthenticator(mail, pwd);
			break;
		default:
			authStrategy = null;
			break;
		}
		
		return authStrategy;
	}

}
