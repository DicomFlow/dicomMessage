package br.ufpb.dicomflow.integrationAPI.message.xml;

public class DispositionFields {

	//Sucess
	public static final String DISPLAYED = "displayed";
	
	//Success but minor problems occurred, which did not affect the successful data	delivery
	public static final String DISPLAYED_WARNING = "displayed/warning";
	
	//Error occurred – data could not be delivered
	public static final String DELETED_ERROR = "deleted/error";
	
	//Error occurred – data could not be delivered
	public static final String deleted = "deleted/error";
	
}
