package com.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AccessLogger {

    private static Logger accessLogger = Logger.getLogger(com.logger.AccessLogger.class.getName());

    public static void configureAccessLogger(String filePath) throws IOException {
        FileHandler fileHandler = new FileHandler(filePath, true);

        fileHandler.setFormatter(new SimpleFormatter());
        accessLogger.addHandler(fileHandler);

    }

    public static String accessLogGeneroter(String Endpoint,String method, String ipAddress,String message){

        return "Endpoint:"+Endpoint+" || Method:"+method+" || IP:"+ipAddress +" || Message:"+message;

    }
    public static String accessLogGeneroter(String Endpoint,String method, String ipAddress){

        return "Endpoint:"+Endpoint+" || Method:"+method+" || IP:"+ipAddress;

    }

    public static Logger getAccessLogger() {
        return accessLogger;
    }
}
