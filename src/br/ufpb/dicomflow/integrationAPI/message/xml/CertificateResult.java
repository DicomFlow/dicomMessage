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
package br.ufpb.dicomflow.integrationAPI.message.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="service")
public class CertificateResult extends Service {
	
	public static final String CERTIFICATE_RESULT_NAME = "Certificate";
	public static final String CERTIFICATE_RESULT_ACTION = "Result";
	public static final String CERTIFICATE_RESULT_VERSION = "1.0";
	
	private String mail;
	private String domain;
	private Integer port;
	private String credential;
	private String status;
	
	public CertificateResult(){
		super.setName(CERTIFICATE_RESULT_NAME);
		super.setAction(CERTIFICATE_RESULT_ACTION);
		super.setVersion(CERTIFICATE_RESULT_VERSION);
		super.setType(ServiceIF.CERTIFICATE_RESULT);
	}
	
	public String getMail() {
		return mail;
	}



	public void setMail(String mail) {
		this.mail = mail;
	}



	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
