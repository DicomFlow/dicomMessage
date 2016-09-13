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

import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.RecipientId;
import org.bouncycastle.cms.RecipientInformation;
import org.bouncycastle.cms.RecipientInformationStore;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientId;
import org.bouncycastle.mail.smime.SMIMEEnveloped;
import org.bouncycastle.mail.smime.SMIMEException;
import org.bouncycastle.mail.smime.SMIMESigned;
import org.bouncycastle.mail.smime.SMIMEUtil;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Store;

import br.ufpb.dicomflow.integrationAPI.mail.MailContentBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailHeadBuilderIF;
import br.ufpb.dicomflow.integrationAPI.mail.MailServiceExtractorIF;
import br.ufpb.dicomflow.integrationAPI.mail.MessageIF;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;
import br.ufpb.dicomflow.integrationAPI.message.xml.UnknownService;

public class SMTPServiceExtractor implements MailServiceExtractorIF {

	@Override
	public ServiceIF getService(Message message, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey) {
		
		try {
		
			MailHeadBuilderIF headBuilder = MailHeadBuilderFactory.createHeadStrategy(MailHeadBuilderIF.SMTP_HEAD_STRATEGY);
			
			int contentBuilderType = Integer.valueOf(headBuilder.getHeaderValue(message, MailXTags.CONTENT_BUILDER_X_TAG));
			MailContentBuilderIF contentStrategy = MailContentBuilderFactory.createContentStrategy(contentBuilderType);
	
			int serviceType = Integer.valueOf(headBuilder.getHeaderValue(message,MailXTags.SERVICE_TYPE_X_TAG));
			
			ServiceIF service = new UnknownService();
			
			if(signCert != null && encryptCert != null && privateKey != null){
				
				Multipart content = getDecryptedContent(message, signCert, encryptCert, privateKey);
				service = contentStrategy.getService(content, serviceType);
					
			}else{
				
				service = contentStrategy.getService((Multipart) message.getContent(), serviceType);
			}
			
			return service;
			
		} catch (IOException e) {
			e.printStackTrace();
			return new UnknownService(e.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			return new UnknownService(e.getMessage());
		} catch (CMSException e) {
			e.printStackTrace();
			return new UnknownService(e.getMessage());
		} catch (SMIMEException e) {
			e.printStackTrace();
			return new UnknownService(e.getMessage());
		} catch (OperatorCreationException e) {
			e.printStackTrace();
			return new UnknownService(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new UnknownService(e.getMessage());
		}
		
	}
	
	private Multipart getDecryptedContent(Message message, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey) throws OperatorCreationException, Exception {
		RecipientId     recId = new JceKeyTransRecipientId(encryptCert);
		SMIMEEnveloped       m = new SMIMEEnveloped((MimeMessage)message);

        RecipientInformationStore   recipients = m.getRecipientInfos();
        RecipientInformation        recipient = recipients.get(recId);

        MimeBodyPart        res = SMIMEUtil.toMimeBodyPart(recipient.getContent(new JceKeyTransEnvelopedRecipient(privateKey)));
        MimeMultipart content = (MimeMultipart) res.getContent();
        Multipart decryptedContent = null;
        if(checkSignature(content, signCert)){
	        for (int i = 0; i < content.getCount(); i++) {
	        	Part part = content.getBodyPart(i);
				
				String contentType = part.getContentType();
	
				if (contentType.toLowerCase().startsWith("multipart/mixed")) {
					decryptedContent = (Multipart) part.getContent();
				}
				
				
	        }
        }
        
        return decryptedContent;
	}

	
	

	private boolean checkSignature(MimeMultipart content, X509Certificate cert) throws OperatorCreationException, MessagingException, CMSException, IOException, SMIMEException, Exception {
		for (int i = 0; i < content.getCount(); i++) {
        	Part part = content.getBodyPart(i);
			
			String contentType = part.getContentType();
			
			if (contentType.toLowerCase().startsWith("multipart/signed") 
				     || contentType.toLowerCase().startsWith("application/pkcs7-mime") 
					 || contentType.toLowerCase().startsWith("application/x-pkcs7-mime") 
					 || contentType.toLowerCase().startsWith("application/pkcs7-signature"))
			{
				//
				// in this case the content is wrapped in the signature block.
				//
				SMIMESigned s = new SMIMESigned(content);
	
				return verify(s);
			}
			
		}
		return false;
			
	}
	
	/**
     * verify the signature (assuming the cert is contained in the message)
     */
	private boolean verify(SMIMESigned s) throws Exception {
		//
		// extract the information to verify the signatures.
		//

		//
		// certificates and crls passed in the signature - this must happen before
		// s.getSignerInfos()
		//
		Store certs = s.getCertificates();

		//
		// SignerInfo blocks which contain the signatures
		//
		SignerInformationStore  signers = s.getSignerInfos();

		Collection              c = signers.getSigners();
		Iterator                it = c.iterator();

		//
		// check each signer
		//
		while (it.hasNext())
		{
			SignerInformation   signer = (SignerInformation)it.next();
			Collection          certCollection = certs.getMatches(signer.getSID());

			Iterator        certIt = certCollection.iterator();
			X509Certificate cert = new JcaX509CertificateConverter().getCertificate((X509CertificateHolder)certIt.next());


			//
			// verify that the sign is correct and that it was generated
			// when the certificate was current
			//
			if (signer.verify(new JcaSimpleSignerInfoVerifierBuilder().build(cert)))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}


	@Override
	public List<ServiceIF> getServices(List<Message> messages, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey) {
		
		List<ServiceIF> services = new ArrayList<ServiceIF>();
		
		Iterator<Message> iterator = messages.iterator();
		while (iterator.hasNext()) {
			
			Message message = (Message) iterator.next();
			ServiceIF service = getService(message, signCert, encryptCert, privateKey);
			services.add(service);

		}
		return services;
	}
	
	@Override
	public byte[] getAttach(Message message, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey) {
		
		try {
		
			MailHeadBuilderIF headBuilder = MailHeadBuilderFactory.createHeadStrategy(MailHeadBuilderIF.SMTP_HEAD_STRATEGY);
			
			int contentType = Integer.valueOf(headBuilder.getHeaderValue(message, MailXTags.CONTENT_BUILDER_X_TAG));
			MailContentBuilderIF contentStrategy = MailContentBuilderFactory.createContentStrategy(contentType);
	
			
			byte[] attach = new byte[]{};
			if(signCert != null && encryptCert != null && privateKey != null){
					
				Multipart content = getDecryptedContent(message, signCert, encryptCert, privateKey);
				attach = contentStrategy.getAttach(content);
				
			}else{
				
				attach = contentStrategy.getAttach((Multipart) message.getContent());
			}
			
			return attach;
			
		} catch (IOException e) {
			e.printStackTrace();
			return new byte[]{};
		} catch (MessagingException e) {
			e.printStackTrace();
			return new byte[]{};
		} catch (CMSException e) {
			e.printStackTrace();
			return new byte[]{};
		} catch (SMIMEException e) {
			e.printStackTrace();
			return new byte[]{};
		} catch (OperatorCreationException e) {
			e.printStackTrace();
			return new byte[]{};
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[]{};
		}
		
	}
	
	@Override
	public List<byte[]> getAttachs(List<Message> messages, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey) {
		
		List<byte[]> attachs = new ArrayList<byte[]>();
		
		Iterator<Message> iterator = messages.iterator();
		while (iterator.hasNext()) {
			
			Message message = (Message) iterator.next();
			byte[] file = getAttach(message, signCert, encryptCert, privateKey);
			attachs.add(file);

		}
		return attachs;
	}
	
	@Override
	public MessageIF getServiceAndAttach(Message message, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey) {
		
		MailHeadBuilderIF headBuilder = MailHeadBuilderFactory.createHeadStrategy(MailHeadBuilderIF.SMTP_HEAD_STRATEGY);
		try {
			
			int contentType = Integer.valueOf(headBuilder.getHeaderValue(message, MailXTags.CONTENT_BUILDER_X_TAG));
			MailContentBuilderIF contentStrategy = MailContentBuilderFactory.createContentStrategy(contentType);
			int serviceType = Integer.valueOf(headBuilder.getHeaderValue(message,MailXTags.SERVICE_TYPE_X_TAG));
			
			MessageIF smtpMessage = new SMTPMessage();
			smtpMessage.setMailXTags(headBuilder.getMailXTags(message));
			
			if(signCert != null && encryptCert != null && privateKey != null){
					
				Multipart content = getDecryptedContent(message, signCert, encryptCert, privateKey);
		        smtpMessage.setService(contentStrategy.getService(content, serviceType));
				smtpMessage.setAttach(contentStrategy.getAttach(content));
				
			}else{
				
				smtpMessage.setService(contentStrategy.getService((Multipart) message.getContent(), serviceType));
				smtpMessage.setAttach(contentStrategy.getAttach((Multipart) message.getContent()));
			}
			
			return smtpMessage;
			
		} catch (IOException e) {
			e.printStackTrace();
			return createErrorMessageIF(message, headBuilder, e);
		} catch (MessagingException e) {
			e.printStackTrace();
			return createErrorMessageIF(message, headBuilder, e);
		} catch (CMSException e) {
			e.printStackTrace();
			return createErrorMessageIF(message, headBuilder, e);
		} catch (SMIMEException e) {
			e.printStackTrace();
			return createErrorMessageIF(message, headBuilder, e);
		} catch (OperatorCreationException e) {
			e.printStackTrace();
			return createErrorMessageIF(message, headBuilder, e);
		} catch (Exception e) {
			e.printStackTrace();
			return createErrorMessageIF(message, headBuilder, e);
		}
		
	}

	private MessageIF createErrorMessageIF(Message message, MailHeadBuilderIF headBuilder, Exception e) {
		MessageIF smtpMessage = new SMTPMessage();
		smtpMessage.setMailXTags(headBuilder.getMailXTags(message));
		smtpMessage.setService(new UnknownService(e.getMessage()));
		smtpMessage.setAttach(new byte[]{});
		return smtpMessage;
	}
	
	@Override
	public List<MessageIF> getServicesAndAttachs(List<Message> messages, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey) {
		
		List<MessageIF> servicesAndattachs = new ArrayList<MessageIF>();
		
		Iterator<Message> iterator = messages.iterator();
		while (iterator.hasNext()) {
			
			Message message = (Message) iterator.next();
			MessageIF messageIF = getServiceAndAttach(message, signCert, encryptCert, privateKey);
			servicesAndattachs.add(messageIF);

		}
		return servicesAndattachs;
	}

}
