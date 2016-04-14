package br.ufpb.dicomflow.integrationAPI.log;

public class ConsoleHandler implements LogHandler {

    @Override
    public void log(LogObject logRegister) {
        
        LogSeverity severity = logRegister.getSeverity();        
        System.err.println();
    	System.err.println(severity + " - " + logRegister.toString());
    	System.err.println();
    	
    }
    
}
