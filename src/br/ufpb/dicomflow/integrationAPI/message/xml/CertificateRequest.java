package br.ufpb.dicomflow.integrationAPI.message.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="service")
public class CertificateRequest extends Service {
	
	public static final String CERTIFICATE_REQUEST_NAME = "Certificate";
	public static final String CERTIFICATE_REQUEST_ACTION = "Request";
	public static final String CERTIFICATE_REQUEST_VERSION = "1.0";
	
	private String mail;
	private String domain;
	private Integer port;
	
	public CertificateRequest(){
		super.setName(CERTIFICATE_REQUEST_NAME);
		super.setAction(CERTIFICATE_REQUEST_ACTION);
		super.setVersion(CERTIFICATE_REQUEST_VERSION);
		super.setType(ServiceIF.CERTIFICATE_REQUEST);
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
	
	

}
