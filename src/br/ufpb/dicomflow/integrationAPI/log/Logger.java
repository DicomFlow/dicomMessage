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
package br.ufpb.dicomflow.integrationAPI.log;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private static List<LogHandler> handlers;
    
    private static LogSeverity minSeverity;
    
    static {
        init(true, false, false);        
        //minSeverity = Properties.getInstance().getMinimalSeverity();
    }
    
    /**
     * Init logging int the chosen outputs. 
     * By default, only console is enabled
     * @param console true if its to log on the console, false otherwise
     * @param db true if its to log on the database, false otherwise
     * @param file true if its to log on file, false otherwise
     */
    public static void init(boolean console, boolean db, boolean file) {
        ArrayList<LogHandler> list = new ArrayList<LogHandler>();
        if(console)
            list.add(new ConsoleHandler());
        if(db)
            list.add(new DBHandler());
        if(file)
            list.add(new FileHandler());
        setHandlers(list);
    }  
    /**
     * 
     * @return the minimal severity needed to add a register in the log
     */
    public static LogSeverity getMinSeverity() {
        return minSeverity;
    }

    /**
     * Set the minimal severity needed to add a register in the log.
     * The order of the severity are, from lower to higher is:
     * DEBUG, INFO, WARNING, ERROR, FATAL.
     * 
     * @param minSeverity the minimal severity needed to add a register in the log
     * @see LogSeverity
     */
    public static void setMinSeverity(LogSeverity minSeverity) {
        Logger.minSeverity = minSeverity;
    }
    
    /**
     * Saves the log registers in three possible different ways: - Database -
     * File - Console This ways are determined by a system parameter.
     *
     * @param register log register to be saved
     */
    public static void log(LogObject register) {
        for(LogSeverity os : LogSeverity.values()) {
            if(os == minSeverity)
                break;
            if(os == register.getSeverity()) 
                return;
        }
        
        if (handlers != null && !handlers.isEmpty()) {
            for (LogHandler handler : handlers) {
                handler.log(register);
            }
        }
        
        if(register.getSeverity() == LogSeverity.FATAL){
        	System.exit(1);
        }
    }
    
    
    public static void log(LogSeverity severity, String detail) {
        LogObject register = new LogObject();
        register.setSeverity(severity);
        register.setDetail(detail);
        log(register);
    }
    
    public static void log(LogSeverity severity, String operationName, String detail) {
        LogObject register = new LogObject();
        register.setSeverity(severity);
        register.setDetail(detail);
        log(register);
    }
    
    public static void log(LogSeverity severity, Exception e, String detail) {
        LogObject register = new LogObject();
        register.setSeverity(severity);
        if(detail == null)
            register.setDetail(e.getMessage());
        else
            register.setDetail(detail);
        log(register);
    }
    
    
    public static void e(Exception e) {
        log(LogSeverity.ERROR, e, null);
    }

    public static void e(String detail) {
        log(LogSeverity.ERROR, detail);
    }
    
    public static void e(Exception e, String detail) {
        log(LogSeverity.ERROR, e, detail);
    }
    
    public static void ef(Exception e, String detail) {
        log(LogSeverity.FATAL, e, detail);
    }
    
    public static void i(String operationName, String detail) {
        log(LogSeverity.INFO, operationName, detail);
    }

    public static void i(String detail) {
    	log(LogSeverity.INFO, detail);
    }

    public static void v(String detail) {
        log(LogSeverity.VERBOSE, detail);
    }
    
    public static void d(String detail) {
        log(LogSeverity.DEBUG, detail);
    }
    
    public static void w(Exception e) {
        log(LogSeverity.WARNING, e, null);
    }

    public static void w(String detail) {
        log(LogSeverity.WARNING, detail);
    }
    
    public static void w(Exception e, String detail) {
        log(LogSeverity.WARNING, e, detail);
    }
    
    public static void s(String detail){
    	//System.out.println(detail);
    }
    
    public static List<LogHandler> getHandlers() {
        return handlers;
    }

    public static void setHandlers(List<LogHandler> handlers) {
        Logger.handlers = handlers;
    }
}
