package com.logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ApplicationLogger {

    private static Logger applicationLogger = Logger.getLogger(com.logger.ApplicationLogger.class.getName());
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void configureApplicationLogger(String filePath) throws IOException {


        System.out.println("here the date time of teh data is" + dateFormat.format(new Date()));
//        String filename = "Applicationlog" + dateFormat.format(new Date()) + ".txt";

        FileHandler fileHandler = new FileHandler(filePath , true);

        fileHandler.setFormatter(new SimpleFormatter());
        applicationLogger.addHandler(fileHandler);

    }

    public static String applicationLogGeneroter(String message) {
        String classname = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodname = Thread.currentThread().getStackTrace()[2].getMethodName();


        return "Class:" + classname + " || Method:" + methodname + "  || Message:" + message;

    }

    public static String applicationLogGeneroter(String className, String method, String ExceptionName, String message) {

        return "Class:" + className + " || Method:" + method + " || Exception :" + ExceptionName + " || Message:" + message;

    }


    public static Logger getApplicationLoggerLogger() {
        return applicationLogger;
    }
}
