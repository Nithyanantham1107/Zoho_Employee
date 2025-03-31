package com.exception.exceptionmapper;

import com.exception.DBOperationException;
import com.logger.ApplicationLogger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class DBOperationExceptionMapper implements ExceptionMapper<DBOperationException> {
    private static Logger APPLICATIONLOGGER = ApplicationLogger.getApplicationLoggerLogger();


    @Override
    public Response toResponse(DBOperationException e) {
        System.out.println("here the DB operation exception Occured here outside");
        StringBuilder message = new StringBuilder("{ \"Message\":\"");

        APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

        message.append(e.getMessage()).append("\"}");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message.toString()).build();

    }
}
