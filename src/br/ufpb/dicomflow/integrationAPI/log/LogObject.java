package br.ufpb.dicomflow.integrationAPI.log;

import java.io.Serializable;

public class LogObject implements Serializable {
    
	private static final long serialVersionUID = -4684144141723157010L;       
    
	private String stack;           
    private String detail;                
    private LogSeverity severity;
                
    public LogObject() {
        this.severity = LogSeverity.INFO;
    }

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public LogSeverity getSeverity() {
		return severity;
	}

	public void setSeverity(LogSeverity severity) {
		this.severity = severity;
	}    
        
}
