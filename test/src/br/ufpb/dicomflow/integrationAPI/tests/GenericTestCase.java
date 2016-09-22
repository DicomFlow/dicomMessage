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
package br.ufpb.dicomflow.integrationAPI.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class GenericTestCase extends TestCase {
	
	public static String outputDir = "temp/";
	public static String messageID = "";
	
	@BeforeClass
	protected void setUp() throws IOException {		
		File dir = new File(outputDir);
		dir.mkdir();
		FileUtils.cleanDirectory(dir);
		//System.out.println("/************ Start ************/");
	}
	
	@AfterClass
	protected void tearDown() {
		//System.out.println("/************* End *************/");
		System.gc();
	}		

}
