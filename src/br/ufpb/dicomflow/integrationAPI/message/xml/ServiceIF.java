package br.ufpb.dicomflow.integrationAPI.message.xml;


public interface ServiceIF {
	
	
	/**
	 * StogareSave Service Identifier 
	 */
	public static final int STORAGE_SAVE = 1;
	/**
	 * StogareUpdate Service Identifier 
	 */
	public static final int STORAGE_UPDATE = 2;
	/**
	 * StogareDelete Service Identifier 
	 */
	public static final int STORAGE_DELETE = 3;
	/**
	 * StogareResult Service Identifier 
	 */
	public static final int STORAGE_RESULT = 4;	
	/**
	 * DispositionNotification Service Identifier 
	 */
	public static final int DISPOSITION_NOTIFICATION = 5;

	public int getType();
	
	public void setType(int type);
	
	public String getVersion();
	
	
	public void setVersion(String version);
	
	public String getName();
	
	public void setName(String name);
	
	public String getAction();
	
	public void setAction(String action);

	public String getMessageID();

	public void setMessageID(String messageID);

	public String getTimestamp();

	public void setTimestamp(String timestamp);

	public String getTimeout();

	public void setTimeout(String timeout);

}
