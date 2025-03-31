package com.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ApplicationLogger {

    private static Logger applicationLogger = Logger.getLogger(com.logger.ApplicationLogger.class.getName());

    public static void configureApplicationLogger(String filePath) throws IOException {
        FileHandler fileHandler = new FileHandler(filePath, true);

        fileHandler.setFormatter(new SimpleFormatter());
        applicationLogger.addHandler(fileHandler);

    }

    public static String applicationLogGeneroter( String message) {
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
