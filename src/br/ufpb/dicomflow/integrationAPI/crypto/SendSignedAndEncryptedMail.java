package br.ufpb.dicomflow.integrationAPI.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

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
import org.bouncycastle.mail.smime.SMIMEException;
import org.bouncycastle.mail.smime.SMIMESignedGenerator;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.Strings;

import br.ufpb.dicomflow.integrationAPI.main.DefaultIdMessageGeneratorStrategy;
import br.ufpb.dicomflow.integrationAPI.main.ServiceFactory;
import br.ufpb.dicomflow.integrationAPI.message.xml.Credentials;
import br.ufpb.dicomflow.integrationAPI.message.xml.Object;
import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;
import br.ufpb.dicomflow.integrationAPI.message.xml.StorageDelete;

/**
 * Example that sends a signed and encrypted mail message.
 */
public class SendSignedAndEncryptedMail
{
	public static void main(final String args[])
	{
		if (args.length != 6)
		{
			System.err.println("usage: SendSignedAndEncryptedMail <jksKeystore> <password> <keyalias> <smtp server> <email address> <email password>");
			System.exit(0);
		}

		try
		{
			MailcapCommandMap mailcap = (MailcapCommandMap) CommandMap
					.getDefaultCommandMap();

			mailcap.addMailcap("application/pkcs7-signature;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.pkcs7_signature");
			mailcap.addMailcap("application/pkcs7-mime;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.pkcs7_mime");
			mailcap.addMailcap("application/x-pkcs7-signature;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.x_pkcs7_signature");
			mailcap.addMailcap("application/x-pkcs7-mime;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.x_pkcs7_mime");
			mailcap.addMailcap("multipart/signed;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.multipart_signed");

			CommandMap.setDefaultCommandMap(mailcap);

			/* Add BC */
//			Security.addProvider(new BouncyCastleProvider());

			/* Open the keystore */
//			KeyStore keystore = KeyStore.getInstance("PKCS12", "BC");
			KeyStore keystore = KeyStore.getInstance("JKS");
			keystore.load(new FileInputStream(args[0]), args[1].toCharArray());
			Certificate[] chain = keystore.getCertificateChain(args[2]);

			/* Get the private key to sign the message with */
			PrivateKey privateKey = (PrivateKey)keystore.getKey(args[2],
					args[1].toCharArray());
			if (privateKey == null)
			{
				throw new Exception("cannot find private key for alias: "
						+ args[2]);
			}

			/* Create the message to sign and encrypt */
//			Properties props = System.getProperties();
//			props.put("mail.smtp.host", args[3]);
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.socketFactory.port", "25");
	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.port", "25");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.debug", "true");
	        props.put("mail.smtp.socketFactory.fallback", "false");
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication()
	            {
	                  return new PasswordAuthentication(args[4], args[5]);
	            }
	       });

			MimeMessage body = new MimeMessage(session);
			body.setFrom(new InternetAddress(args[4]));
			body.setRecipient(Message.RecipientType.TO, new InternetAddress(
					args[4]));
			body.setSubject("Another example encrypted message");
			
			
			StorageDelete storageDelete = (StorageDelete) ServiceFactory.createService(ServiceIF.STORAGE_DELETE, DefaultIdMessageGeneratorStrategy.getInstance());// new StorageDelete();
			storageDelete.setTimestamp("12346567346");
			storageDelete.setTimeout("23123");		
			
			Object obj1 = new Object();
			obj1.setId("1");
			obj1.setType("type1");
			Credentials cred1 = new Credentials();
			cred1.setValue("asdfh");
			obj1.setCredential(cred1);
			
			Object obj2 = new Object();
			obj2.setId("2");
			obj2.setType("type2");
			Credentials cred2 = new Credentials();
			cred2.setValue("1we1233");
			obj2.setCredential(cred2);
			
			List<Object> objects = new ArrayList<Object>();
			objects.add(obj1);
			objects.add(obj2);
			storageDelete.setObject(objects);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(storageDelete.getClass());
			
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter str = new StringWriter();
			
			jaxbMarshaller.marshal(storageDelete, str);
			
			body.setContent(str.toString(), "text/xml");
			body.saveChanges();

			/* Create the SMIMESignedGenerator */
			SMIMECapabilityVector capabilities = new SMIMECapabilityVector();
			capabilities.addCapability(SMIMECapability.dES_EDE3_CBC);
			capabilities.addCapability(SMIMECapability.rC2_CBC, 128);
			capabilities.addCapability(SMIMECapability.dES_CBC);

			ASN1EncodableVector attributes = new ASN1EncodableVector();
			attributes.add(new SMIMEEncryptionKeyPreferenceAttribute(
					new IssuerAndSerialNumber(
							new X500Name(((X509Certificate)chain[0])
									.getIssuerDN().getName()),
							((X509Certificate)chain[0]).getSerialNumber())));
			attributes.add(new SMIMECapabilitiesAttribute(capabilities));

			SMIMESignedGenerator signer = new SMIMESignedGenerator();
//			signer.addSignerInfoGenerator(new JcaSimpleSignerInfoGeneratorBuilder().setProvider("BC").setSignedAttributeGenerator(new AttributeTable(attributes)).build("DSA".equals(privateKey.getAlgorithm()) ? "SHA1withDSA" : "MD5withRSA", privateKey, (X509Certificate)chain[0]));
			signer.addSignerInfoGenerator(new JcaSimpleSignerInfoGeneratorBuilder().setSignedAttributeGenerator(new AttributeTable(attributes)).build("DSA".equals(privateKey.getAlgorithm()) ? "SHA1withDSA" : "MD5withRSA", privateKey, (X509Certificate)chain[0]));


			/* Add the list of certs to the generator */
			List certList = new ArrayList();
			certList.add(chain[0]);
			Store certs = new JcaCertStore(certList);
			signer.addCertificates(certs);

			/* Sign the message */
			MimeMultipart mm = signer.generate(body);
			MimeMessage signedMessage = new MimeMessage(session);

			/* Set all original MIME headers in the signed message */
			Enumeration headers = body.getAllHeaderLines();
			while (headers.hasMoreElements())
			{
				signedMessage.addHeaderLine((String)headers.nextElement());
			}

			/* Set the content of the signed message */
			signedMessage.setContent(mm);
			signedMessage.saveChanges();

			/* Create the encrypter */
			SMIMEEnvelopedGenerator encrypter = new SMIMEEnvelopedGenerator();
			encrypter.addRecipientInfoGenerator(new JceKeyTransRecipientInfoGenerator((X509Certificate)chain[0]));
//			encrypter.addRecipientInfoGenerator(new JceKeyTransRecipientInfoGenerator((X509Certificate)chain[0]).setProvider("BC"));

			/* Encrypt the message */
			MimeBodyPart encryptedPart = encrypter.generate(signedMessage,
					new JceCMSContentEncryptorBuilder(CMSAlgorithm.RC2_CBC).build());
//			MimeBodyPart encryptedPart = encrypter.generate(signedMessage,
//					new JceCMSContentEncryptorBuilder(CMSAlgorithm.RC2_CBC).setProvider("BC").build());

			/*
			 * Create a new MimeMessage that contains the encrypted and signed
			 * content
			 */
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			encryptedPart.writeTo(out);

			MimeMessage encryptedMessage = new MimeMessage(session,
					new ByteArrayInputStream(out.toByteArray()));

			/* Set all original MIME headers in the encrypted message */
			headers = body.getAllHeaderLines();
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

			Transport.send(encryptedMessage);
		}
		catch (SMIMEException ex)
		{
			ex.getUnderlyingException().printStackTrace(System.err);
			ex.printStackTrace(System.err);
		}
		catch (Exception ex)
		{
			ex.printStackTrace(System.err);
		}
	}
}