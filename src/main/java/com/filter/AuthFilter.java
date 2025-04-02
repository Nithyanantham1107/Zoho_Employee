package com.filter;


import com.annotation.Secured;
import com.cache.EmployeeCache;
import com.exception.InvalidRequestException;
import com.google.gson.JsonObject;
import com.logger.AccessLogger;
import com.model.Employee;
import com.threadlocal.EmployeeThreadLocal;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;


@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)

public class AuthFilter implements ContainerRequestFilter {
    private Logger ACCESSLOGGER = AccessLogger.getAccessLogger();

    @Context
    private HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext requestContext) {
//        StringBuilder message = new StringBuilder("{ \"message\": \"");
        JsonObject resp = new JsonObject();
        String requestURI = requestContext.getUriInfo().getRequestUri().getPath().toString();

        String requestIp = request.getRemoteAddr();
        String requestMethod = request.getMethod();

        try {
            String token = requestContext.getHeaderString("Authorization");

            if (token == null || token.isEmpty()) {

                throw new InvalidRequestException("Login to Access Data");

            }
            Employee employee = EmployeeCache.getEmployee(token);
            if (employee == null) {
                throw new InvalidRequestException("Invalid token");
            }
            EmployeeThreadLocal.setEmployeeThreadLocal(employee);
            ACCESSLOGGER.info(AccessLogger.accessLogGeneroter(requestURI, requestMethod, requestIp));
        } catch (InvalidRequestException e) {
            ACCESSLOGGER.warning(AccessLogger.accessLogGeneroter(requestURI, requestMethod, requestIp, e.getMessage()));
//            message.append(e.getMessage()).append("\"} ");
            resp.addProperty("Message",e.getMessage());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(resp.toString()).build());


        }


    }


}
