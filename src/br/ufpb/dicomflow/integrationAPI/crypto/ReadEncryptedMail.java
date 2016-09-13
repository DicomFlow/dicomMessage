package br.ufpb.dicomflow.integrationAPI.crypto;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
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

/**
 * a simple example that reads an encrypted email.
 * <p>
 * The key store can be created using the class in
 * org.bouncycastle.jce.examples.PKCS12Example - the program expects only one
 * key to be present.
 */
public class ReadEncryptedMail
{
    public static void main(
        final String args[])
        throws Exception
    {
        if (args.length != 6)
        {
            System.err.println("usage: ReadEncryptedMail <jksKeystore> <password> <alias> <email address> <email password> <imap server> <folder>");
            System.exit(0);
        }

        //
        // Open the key store
        //
        KeyStore    ks = KeyStore.getInstance("JKS");
//        KeyStore    ks = KeyStore.getInstance("PKCS12", "BC");

        ks.load(new FileInputStream(args[0]), args[1].toCharArray());

        Enumeration e = ks.aliases();
        String      keyAlias = null;

        while (e.hasMoreElements())
        {
            String  alias = (String)e.nextElement();

            if (ks.isKeyEntry(alias))
            {
                keyAlias = alias;
            }
        }

        if (keyAlias == null)
        {
            System.err.println("can't find a private key!");
            System.exit(0);
        }

        //
        // find the certificate for the private key and generate a 
        // suitable recipient identifier.
        //
        X509Certificate cert = (X509Certificate)ks.getCertificate(keyAlias);
        RecipientId     recId = new JceKeyTransRecipientId(cert);

        //
        // Get a Session object with the default properties.
        //         
//        Properties props = System.getProperties();
        Properties props = new Properties();
        props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.imap.socketFactory.fallback", "false");
		props.put("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                  return new PasswordAuthentication(args[2], args[3]);
            }
       });
        
        Store store = session.getStore(/*this.provider*/);
		store.connect(args[4], null, null);

	    Folder folder = store.getFolder(args[5]);
	    
	    folder.open(Folder.READ_WRITE);
	    
	    
	    List<Message> messages = new ArrayList<Message>();
	    messages.addAll(Arrays.asList(folder.getMessages()));
	    
	    Iterator<Message> it = messages.iterator();
	    while (it.hasNext()) {
			MimeMessage message = (MimeMessage) it.next();
//			MimeMessage mimeMessage = new MimeMessage(session, message.getInputStream());
			
			SMIMEEnveloped       m = new SMIMEEnveloped(message);

	        RecipientInformationStore   recipients = m.getRecipientInfos();
	        RecipientInformation        recipient = recipients.get(recId);

//	        MimeBodyPart        res = SMIMEUtil.toMimeBodyPart(recipient.getContent(new JceKeyTransEnvelopedRecipient((PrivateKey)ks.getKey(keyAlias, null)).setProvider("BC")));
	        MimeBodyPart        res = SMIMEUtil.toMimeBodyPart(recipient.getContent(new JceKeyTransEnvelopedRecipient((PrivateKey)ks.getKey(keyAlias, args[3].toCharArray()))));
	        MimeMultipart content = (MimeMultipart) res.getContent();
	        for (int i = 0; i < content.getCount(); i++) {
	        	Part part = content.getBodyPart(i);
				// pegando um tipo do conteúdo
				String contentType = part.getContentType();

				// Tela do conteúdo
				if (contentType.toLowerCase().startsWith("text/xml")) {
					System.out.println("Message Contents");
			        System.out.println("----------------");
					System.out.println(part.getContent().toString());
				}
				
				else if(contentType.toLowerCase().startsWith("multipart/mixed")){
					MimeMultipart mixedContent = (MimeMultipart) part.getContent();
					for (int j = 0; j < mixedContent.getCount(); j++) {
						Part part2 = mixedContent.getBodyPart(j);
						// pegando um tipo do conteúdo
						String contentType2 = part2.getContentType();

						// Tela do conteúdo
						if (contentType2.toLowerCase().startsWith("text/xml")) {
							System.out.println("Message Contents");
					        System.out.println("----------------");
							System.out.println(part2.getContent().toString());
						}
					}
				}else if (contentType.toLowerCase().startsWith("multipart/signed"))
				{
//					SMIMESignedParser s = new SMIMESignedParser(new JcaDigestCalculatorProviderBuilder().build(), (MimeMultipart)message.getContent());
					SMIMESigned s = new SMIMESigned(content);

					if(verify(s)){
						System.out.println("assinatura verificada!!!!!");
					}else{
						System.out.println("assinatura NÃO verificada!!!!!");
					}
				}
				else if (contentType.toLowerCase().startsWith("application/pkcs7-mime") || contentType.toLowerCase().startsWith("application/x-pkcs7-mime") || contentType.toLowerCase().startsWith("application/pkcs7-signature"))
				{
					//
					// in this case the content is wrapped in the signature block.
					//
//					SMIMESignedParser s = new SMIMESignedParser(new JcaDigestCalculatorProviderBuilder().build(), message);
					SMIMESigned s = new SMIMESigned(content);

					if(verify(s)){
						System.out.println("assinatura verificada!!!!!");
					}else{
						System.out.println("assinatura NÃO verificada!!!!!");
					}
				}
				
				
	        }
	        
//	        System.out.println(res.getContent());
		}

//        MimeMessage msg = new MimeMessage(session, new FileInputStream("encrypted.message"));
//
//        SMIMEEnveloped       m = new SMIMEEnveloped(msg);
//
//        RecipientInformationStore   recipients = m.getRecipientInfos();
//        RecipientInformation        recipient = recipients.get(recId);
//
////        MimeBodyPart        res = SMIMEUtil.toMimeBodyPart(recipient.getContent(new JceKeyTransEnvelopedRecipient((PrivateKey)ks.getKey(keyAlias, null)).setProvider("BC")));
//        MimeBodyPart        res = SMIMEUtil.toMimeBodyPart(recipient.getContent(new JceKeyTransEnvelopedRecipient((PrivateKey)ks.getKey(keyAlias, null))));
//
//        System.out.println("Message Contents");
//        System.out.println("----------------");
//        System.out.println(res.getContent());
    }
	
	/**
     * verify the signature (assuming the cert is contained in the message)
     */
	private static boolean verify(SMIMESigned s) throws Exception {
//	private static boolean verify(SMIMESignedParser s) throws Exception {
		//
		// extract the information to verify the signatures.
		//

		//
		// certificates and crls passed in the signature - this must happen before
		// s.getSignerInfos()
		//
		org.bouncycastle.util.Store certs = s.getCertificates();

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
			// verify that the sig is correct and that it was generated
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
}