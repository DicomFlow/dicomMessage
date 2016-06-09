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
package br.ufpb.dicomflow.integrationAPI.main;

import java.util.Calendar;
import java.util.UUID;
/**
 * Default Strategy to Generate IDMesssage attribute.
 * @author Danilo
 *
 */
public class DefaultIdMessageGeneratorStrategy implements IdMessageGeneratorStrategyIF {
	
	private static DefaultIdMessageGeneratorStrategy IdMessageGenerator;
	
	/**
	 * Generate a object value to next IDMessage. 
	 */
	@Override
	public String getNextId() {
		
		return String.valueOf( UUID.randomUUID().toString());//Calendar.getInstance().getTime().getTime());
		
	}
	
	private DefaultIdMessageGeneratorStrategy(){
		
	}

	public static synchronized IdMessageGeneratorStrategyIF getInstance(){
		if(IdMessageGenerator == null){
			IdMessageGenerator = new DefaultIdMessageGeneratorStrategy();
		}
		return IdMessageGenerator;
	}
	
}
