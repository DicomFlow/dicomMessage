

package br.ufpb.dicomflow.integrationAPI.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import br.ufpb.dicomflow.integrationAPI.conf.DicomMessageProperties;
import br.ufpb.dicomflow.integrationAPI.exceptions.PropertyNotFoundException;
import br.ufpb.dicomflow.integrationAPI.log.LogSeverity;
import br.ufpb.dicomflow.integrationAPI.log.Logger;

public class CLIUtils {
	
	public static ResourceBundle rb = ResourceBundle.getBundle("br.ufpb.dicomflow.integrationAPI.tools.messages");
	
	public static final int SEND_PROPERTIES = 1;
	public static final int READ_PROPERTIES = 2;
	
	public static final String PROPERTIES_OPTION = "P";
	public static final String VERBOSE_OPTION = "V";
	public static final String ENCRYPT_OPTION = "E";
	public static final String KEYSTORE_OPTION = "k";
	public static final String KEYSTORE_PWD_OPTION = "p";
	public static final String PRIVATE_CERT_OPTION = "a";
	public static final String PRIVATE_CERT_PWD_OPTION = "w";
	public static final String PUBLIC_CERT_OPTION = "c";
	
	public static CommandLine parseComandLine(String[] args,Options opts) throws ParseException{
        
        CommandLineParser parser = new DefaultParser();
        CommandLine cl = parser.parse(opts, args);
        return cl;

	}
	
	public static void addOtherOptions(Options opts) {
        opts.addOption(Option.builder(VERBOSE_OPTION).desc(rb.getString("verbose")).build());
	}
	
	public static void addEncryptOptions(Options opts) {
		
		opts.addOption(Option.builder(ENCRYPT_OPTION).desc(rb.getString("encrypt")).build());
		opts.addOption(Option.builder(KEYSTORE_OPTION).hasArg().argName("file").longOpt("keystore").desc(rb.getString("keystore")).build());
		opts.addOption(Option.builder(KEYSTORE_PWD_OPTION).hasArg().argName("password").longOpt("keystore-pwd").desc(rb.getString("keystore-pwd")).build());
		opts.addOption(Option.builder(PRIVATE_CERT_OPTION).hasArg().argName("keyalias").longOpt("private-cert").desc(rb.getString("private-cert")).build());
		opts.addOption(Option.builder(PRIVATE_CERT_PWD_OPTION).hasArg().argName("password").longOpt("private-cert-pwd").desc(rb.getString("private-cert-pwd")).build());
		opts.addOption(Option.builder(PUBLIC_CERT_OPTION).hasArg().argName("keyalias").longOpt("public-cert").desc(rb.getString("public-cert")).build());
        
	}
	
	public static void addRequiredOptions(Options opts) {
		opts.addOption(Option.builder(PROPERTIES_OPTION).required().hasArg().argName("file").longOpt("properties").desc(rb.getString("properties")).build());
	}
	
	public static void configureLog(CommandLine cl) {
		if(cl.hasOption(VERBOSE_OPTION)){
			Logger.setMinSeverity(LogSeverity.VERBOSE);
		}else{
			Logger.setMinSeverity(LogSeverity.ERROR);
		}
		Logger.init(true, false, false);
	}
	
	public static void configureProperties(Properties properties, CommandLine cl, int propertyType) throws ParseException, PropertyNotFoundException {
		
		Logger.v(rb.getString("start-smtp-properties-config"));
		if(!cl.hasOption(PROPERTIES_OPTION))
			throw new MissingArgumentException(rb.getString("missing-content-opt"));
		
		String file = cl.getOptionValue(PROPERTIES_OPTION);
		
		DicomMessageProperties propertiesLoader = DicomMessageProperties.getInstance();
		propertiesLoader.load(file);
		switch (propertyType) {
		case SEND_PROPERTIES:
			properties = propertiesLoader.getSendProperties();
			break;
		case READ_PROPERTIES:
			properties = propertiesLoader.getSendProperties();
			break;
		default:
			throw new PropertyNotFoundException(rb.getString("invalid-property-type-exception") + propertyType);
		}
		
		Logger.v(properties.toString());
		
		Logger.v("finish-smtp-properties-config");
		
	}
	
	public static void configureEncryptProperties(X509Certificate privateCert, PrivateKey privateKey,
			X509Certificate publicCert, CommandLine cl) throws ParseException, KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException {
		if(cl.hasOption(ENCRYPT_OPTION)){
			if(!cl.hasOption(KEYSTORE_OPTION) || !cl.hasOption(KEYSTORE_PWD_OPTION) || !cl.hasOption(PRIVATE_CERT_OPTION) || !cl.hasOption(PUBLIC_CERT_OPTION) )
				throw new MissingArgumentException(rb.getString("missing-content-opt"));
			
			/* Open the keystore */
			KeyStore keystore = KeyStore.getInstance("JKS");
			keystore.load(new FileInputStream(cl.getOptionValue(KEYSTORE_OPTION)), cl.getOptionValue(KEYSTORE_PWD_OPTION).toCharArray());
			
			/* Get the certificate to cipher the message with */
			Certificate[] cipherChain = keystore.getCertificateChain(cl.getOptionValue(PUBLIC_CERT_OPTION));
			publicCert = (X509Certificate) cipherChain[0];
			if (publicCert == null)
				throw new CertificateException("cannot find cipher certificate for alias: " + cl.getOptionValue(PUBLIC_CERT_OPTION));
			
			/* Get the certificate to sign the message with */
			Certificate[] signerChain = keystore.getCertificateChain(cl.getOptionValue(PRIVATE_CERT_OPTION));
			privateCert = (X509Certificate) signerChain[0];
			if (privateCert == null)
				throw new CertificateException("cannot find signer certificate for alias: " + cl.getOptionValue(PRIVATE_CERT_OPTION));
			
			/* Get the private key to sign the message with */
			privateKey = (PrivateKey)keystore.getKey(cl.getOptionValue(PRIVATE_CERT_OPTION),
					cl.getOptionValue(PRIVATE_CERT_PWD_OPTION).toCharArray());
			if (privateKey == null)
				throw new UnrecoverableKeyException("cannot find private key for alias: " + cl.getOptionValue(PRIVATE_CERT_OPTION));
			
		}
	}

}
