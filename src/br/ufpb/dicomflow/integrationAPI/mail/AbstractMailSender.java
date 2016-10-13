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
package br.ufpb.dicomflow.integrationAPI.mail;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import org.bouncycastle.asn1.smime.SMIMECapabilitiesAttribute;
import org.bouncycastle.asn1.smime.SMIMECapability;
import org.bouncycastle.asn1.smime.SMIMECapabilityVector;
import org.bouncycastle.asn1.smime.SMIMEEncryptionKeyPreferenceAttribute;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSAlgorithm;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoGeneratorBuilder;
import org.bouncycastle.cms.jcajce.JceCMSContentEncryptorBuilder;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator;
import org.bouncycastle.mail.smime.SMIMEEnvelopedGenerator;
import org.bouncycastle.mail.smime.SMIMESignedGenerator;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.Strings;

import br.ufpb.dicomflow.integrationAPI.exceptions.ContentBuilderException;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public abstract class AbstractMailSender implements MailSenderIF {

		public String send(ServiceIF service) {

			try {
				Message message = buildMessage(service, null);
				
				Transport.send(message);

				
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return service.getMessageID();

		}
		
		@Override
		public String sendCipher(ServiceIF service, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey ) {

			try {
				Message message = signAndEcrypt(buildMessage(service, null),signCert, encryptCert, privateKey);
				
				Transport.send(message);
			} catch (ContentBuilderException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return service.getMessageID();

		}

		
		@Override
		public String send(ServiceIF service, File attachement) {

			try {
				Message message = buildMessage(service, attachement);
				
				Transport.send(message);

				
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return service.getMessageID();

		}
		
		@Override
		public String sendCipher(ServiceIF service, File attachement, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey ) {

			try {
				Message message = signAndEcrypt(buildMessage(service, attachement),signCert, encryptCert, privateKey);
				
				Transport.send(message);
			} catch (ContentBuilderException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return service.getMessageID();

		}
		
		private Message buildMessage(ServiceIF service, File attachment) {
			Session session = Session.getInstance(getProperties(),
					getAuthenticatorBuilder().getAuthenticator());
			

			Message message = new MimeMessage(session);
			try {
				message.setText(" . ");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			message = getHeadBuilder().buildHead(message, service, getContentBuilder());
			
			if(attachment != null && attachment.exists()){
				message = getContentBuilder().buildContent(message, service, attachment);
			}else{
				message = getContentBuilder().buildContent(message, service);
			}
			return message;
		}
		
		private Message signAndEcrypt(Message message,X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey) throws Exception {
			MailcapCommandMap mailcap = (MailcapCommandMap) CommandMap.getDefaultCommandMap();

			mailcap.addMailcap("application/pkcs7-signature;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.pkcs7_signature");
			mailcap.addMailcap("application/pkcs7-mime;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.pkcs7_mime");
			mailcap.addMailcap("application/x-pkcs7-signature;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.x_pkcs7_signature");
			mailcap.addMailcap("application/x-pkcs7-mime;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.x_pkcs7_mime");
			mailcap.addMailcap("multipart/signed;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.multipart_signed");

			CommandMap.setDefaultCommandMap(mailcap);
			
			/* Create the Signer - SMIMESignedGenerator */
			SMIMECapabilityVector capabilities = new SMIMECapabilityVector();
			capabilities.addCapability(SMIMECapability.dES_EDE3_CBC);
			capabilities.addCapability(SMIMECapability.rC2_CBC, 128);
			capabilities.addCapability(SMIMECapability.dES_CBC);

			ASN1EncodableVector attributes = new ASN1EncodableVector();
			attributes.add(new SMIMEEncryptionKeyPreferenceAttribute(
					new IssuerAndSerialNumber(
							new X500Name(((X509Certificate)signCert)
									.getIssuerDN().getName()),
							((X509Certificate)signCert).getSerialNumber())));
			attributes.add(new SMIMECapabilitiesAttribute(capabilities));

			SMIMESignedGenerator signer = new SMIMESignedGenerator();
			signer.addSignerInfoGenerator(new JcaSimpleSignerInfoGeneratorBuilder().setSignedAttributeGenerator(new AttributeTable(attributes)).build("DSA".equals(privateKey.getAlgorithm()) ? "SHA1withDSA" : "MD5withRSA", privateKey, signCert));


			/* Add the list of certs to the generator */
			List certList = new ArrayList();
			certList.add(signCert);
			Store certs = new JcaCertStore(certList);
			signer.addCertificates(certs);

			/* Sign the message */
			MimeMultipart mm = signer.generate((MimeMessage)message);
			MimeMessage signedMessage = new MimeMessage(message.getSession());
			
			/* Set all original MIME headers in the signed message */
			Enumeration headers = ((MimeMessage)message).getAllHeaderLines();
			while (headers.hasMoreElements())
			{
				signedMessage.addHeaderLine((String)headers.nextElement());
			}

			/* Set the content of the signed message */
			signedMessage.setContent(mm);
			signedMessage.saveChanges();
			
			/* Create the encrypter - SMIMEEnvelopedGenerator */
			SMIMEEnvelopedGenerator encrypter = new SMIMEEnvelopedGenerator();
			encrypter.addRecipientInfoGenerator(new JceKeyTransRecipientInfoGenerator(encryptCert));

			/* Encrypt the message */
			MimeBodyPart encryptedPart = encrypter.generate(signedMessage, new JceCMSContentEncryptorBuilder(CMSAlgorithm.RC2_CBC).build());

			/*
			 * Create a new MimeMessage that contains the encrypted and signed
			 * content
			 */
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			encryptedPart.writeTo(out);

			MimeMessage encryptedMessage = new MimeMessage(message.getSession(),
					new ByteArrayInputStream(out.toByteArray()));

			/* Set all original MIME headers in the encrypted message */
			headers = ((MimeMessage)message).getAllHeaderLines();
			while (headers.hasMoreElements())
			{
				String headerLine = (String)headers.nextElement();
				/*
				 * Make sure not to override any content-* headers from the
				 * original message
				 */
				if (!Strings.toLowerCase(headerLine).startsWith("content-"))
				{
					encryptedMessage.addHeaderLine(headerLine);
				}
			}

			
			return encryptedMessage;
			
			
			
		}

		@Override
		public abstract Properties getProperties();

		@Override
		public abstract MailAuthenticatorIF getAuthenticatorBuilder();

		@Override
		public abstract MailHeadBuilderIF getHeadBuilder();

		@Override
		public abstract MailContentBuilderIF getContentBuilder();

}
