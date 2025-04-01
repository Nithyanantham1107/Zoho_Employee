package com.logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AccessLogger {

    private static Logger accessLogger = Logger.getLogger(com.logger.AccessLogger.class.getName());
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void configureAccessLogger(String filePath) throws IOException {
        System.out.println("here the date time of teh data is" + dateFormat.format(new Date()));
     FileHandler fileHandler = new FileHandler(filePath, true);
        fileHandler.setFormatter(new SimpleFormatter());
        accessLogger.addHandler(fileHandler);

    }

    public static String accessLogGeneroter(String Endpoint, String method, String ipAddress, String message) {

        return "Endpoint:" + Endpoint + " || Method:" + method + " || IP:" + ipAddress + " || Message:" + message;

    }

    public static String accessLogGeneroter(String Endpoint, String method, String ipAddress) {

        return "Endpoint:" + Endpoint + " || Method:" + method + " || IP:" + ipAddress;

    }

    public static Logger getAccessLogger() {
        return accessLogger;
    }
}
