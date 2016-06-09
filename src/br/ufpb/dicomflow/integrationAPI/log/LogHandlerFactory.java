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

public class LogHandlerFactory {
    
    public static final String DB_HANDLER = "db";
    public static final String FILE_HANDLER = "file";
    public static final String CONSOLE_HANDLER = "console";
    
    public static LogHandler createLogHandler(String type){
        if(type != null){
            if (type.equals(DB_HANDLER)) {
                return createDBHandler();
            } else if(type.equals(FILE_HANDLER)){
                return createFileHandler();
            } else if(type.equals(CONSOLE_HANDLER)){
                return createConsoleHandler();
            }
        }
        
        return null;
    }
    
    public static List<LogHandler> createLogHandler(List<String> types){
        
        List<LogHandler> handlers = new ArrayList<LogHandler>();
        
        if(types != null && !types.isEmpty()){
            for (String type : types) {
                LogHandler handler = createLogHandler(type);
                
                if(handler != null){
                    handlers.add(handler);
                }
            }
        }
        
        
        return handlers;
    }
    
    public static LogHandler createDBHandler(){
        return new DBHandler();
    }
    
    public static LogHandler createFileHandler(){
        return new FileHandler();
    }
    
    public static LogHandler createConsoleHandler(){
        return new ConsoleHandler();
    }
    
}
