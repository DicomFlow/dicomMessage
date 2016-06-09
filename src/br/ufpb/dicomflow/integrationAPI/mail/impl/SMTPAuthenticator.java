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

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import br.ufpb.dicomflow.integrationAPI.mail.MailAuthenticatorIF;

public class SMTPAuthenticator implements MailAuthenticatorIF{
	
	private String email;
	private String password;
	private Authenticator authenticator;
	
	
	public SMTPAuthenticator(String mail, String passwd){
		this.email = mail;
		this.password = passwd;
		this.authenticator = new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                  return new PasswordAuthentication(email, password);
            }
       };
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Authenticator getAuthenticator() {
		return authenticator;
	}


	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}
	
	
	
	

}
