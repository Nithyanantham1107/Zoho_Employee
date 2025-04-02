package com.exception.exceptionmapper;

import com.exception.EmployeeDataTypeException;
import com.google.gson.JsonObject;
import com.logger.ApplicationLogger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class EmployeeTypeExceptionMapper implements ExceptionMapper<EmployeeDataTypeException> {

    private static Logger applicationLogger = ApplicationLogger.getApplicationLoggerLogger();

    @Override
    public Response toResponse(EmployeeDataTypeException e) {

        System.out.println("here the Mapper is printed see!!");
//        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        JsonObject resp = new JsonObject();
        applicationLogger.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

//        message.append(e.getMessage()).append("\"}");
        resp.addProperty("Message", e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(resp.toString()).build();

    }
}
