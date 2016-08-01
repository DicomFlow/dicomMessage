package br.ufpb.dicomflow.integrationAPI.tests;


import net.suberic.crypto.*;

import javax.mail.*;
import javax.mail.internet.*;

import java.util.Set;
import java.security.Key;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * An example of using this API to encrypt a JavaMail Message.
 */
public class EncryptMessage {

  /**
   * Runs the Example.
   */
  public static void main(String[] argv) {
    try {
      Session mailSession = Session.getDefaultInstance(System.getProperties());
      
      // create the message.
      
      MimeMessage newMessage = new MimeMessage(mailSession);
      
      String content = "This is a test message.  \n\nIsn't this exciting?\n\nWouldn't you want to encrypt this message, too?\n\n";
      
      newMessage.setContent(content, "text/plain");
      
      newMessage.setFrom();
      newMessage.setRecipients(Message.RecipientType.TO, "daniloalexandre@gmail.com");
      newMessage.setSubject("Look!  An encrypted message!");
      
      newMessage.saveChanges();
      
      // get our encryptor(s)
//      EncryptionUtils smimeUtils = EncryptionManager.getEncryptionUtils(EncryptionManager.SMIME);
      EncryptionUtils pgpUtils = EncryptionManager.getEncryptionUtils(EncryptionManager.PGP);
      
      // load the associated store(s)
      
//      char[] smimePw = new String("hello world").toCharArray();
//      EncryptionKeyManager smimeKeyMgr = smimeUtils.createKeyManager();
//      smimeKeyMgr.loadPublicKeystore(new FileInputStream(new File("./id.p12")), smimePw);

      EncryptionKeyManager pgpKeyMgr = pgpUtils.createKeyManager();
      pgpKeyMgr.loadPublicKeystore(new FileInputStream(new File("./alice.pkr")), null);

      // get our keys.
//      java.security.Key smimeKey = smimeKeyMgr.getPublicKey("Eric's Key");
      java.security.Key pgpKey = pgpKeyMgr.getPublicKey((String) pgpKeyMgr.publicKeyAliases().iterator().next());

      // write smime message.

//    MimeMessage smimeEncryptedMsg = smimeUtils.encryptMessage(mailSession, newMessage, smimeKey);
//    smimeEncryptedMsg.writeTo(new FileOutputStream(new File("smimeEncrypted.msg")));

    // write pgp message
    MimeMessage pgpEncryptedMsg = pgpUtils.encryptMessage(mailSession, newMessage, pgpKey);
    pgpEncryptedMsg.writeTo(new FileOutputStream(new File("pgpEncrypted.msg")));
    
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

