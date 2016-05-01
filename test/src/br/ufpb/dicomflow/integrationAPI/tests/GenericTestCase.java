package br.ufpb.dicomflow.integrationAPI.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class GenericTestCase extends TestCase {
	
	public static String outputDir = "temp/";
	public static String certDir = "C:/Users/Danilo/";
	public static String messageID = "";
	
	@BeforeClass
	protected void setUp() throws IOException {		
		File dir = new File(outputDir);
		dir.mkdir();
		FileUtils.cleanDirectory(dir);
		System.out.println("/************ Start ************/");
	}
	
	@AfterClass
	protected void tearDown() {
		System.out.println("/************* End *************/");
		System.gc();
	}		

}
