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
