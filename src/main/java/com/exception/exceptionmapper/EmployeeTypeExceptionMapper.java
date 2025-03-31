package com.exception.exceptionmapper;

import com.exception.EmployeeTypeException;
import com.logger.ApplicationLogger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;
@Provider
public class EmployeeTypeExceptionMapper implements ExceptionMapper<EmployeeTypeException> {

    private static Logger applicationLogger = ApplicationLogger.getApplicationLoggerLogger();

    @Override
    public Response toResponse(EmployeeTypeException e) {

        System.out.println("here the Mapper is printed see!!");
        StringBuilder message = new StringBuilder("{ \"Message\":\"");

        applicationLogger.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

        message.append(e.getMessage()).append("\"}");
        return Response.status(Response.Status.BAD_REQUEST).entity(message.toString()).build();

    }
}
