package br.ufpb.dicomflow.integrationAPI.log;

import java.io.FileWriter;
import java.io.IOException;

public class FileHandler implements LogHandler {

    private String fileName = "Dicomflow_log";
    private String fileFormat = "txt";

    public FileHandler() {
    }

    @Override
    public void log(LogObject logObject) {
        try {
            FileWriter writer = new FileWriter(fileName + "." + fileFormat);
            writer.write(logObject.toString());
            writer.close();
        } catch (IOException ex) {
            Logger.e(ex);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }
    
}
