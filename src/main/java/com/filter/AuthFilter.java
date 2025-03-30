package com.filter;


import com.annotation.Secured;
import com.cache.EmployeeCache;
import com.exception.InvalidRequestException;
import com.model.Employee;
import com.threadlocal.EmployeeThreadLocal;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)

public class AuthFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
        StringBuilder message = new StringBuilder("{ \"message\": \"");
        try {


            String token = requestContext.getHeaderString("Authorization");

            if (token == null || token.isEmpty()) {

                throw new InvalidRequestException("Login to Access Data");

            }
            Employee employee = EmployeeCache.getEmployee(token);
            if (employee == null) {
//
                throw new InvalidRequestException("Invalid token");
            }
            EmployeeThreadLocal.setEmployeeThreadLocal(employee);


        } catch (InvalidRequestException e) {

            message.append(e.getMessage()).append("\"} ");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(message.toString()).build());


        }


    }


}
