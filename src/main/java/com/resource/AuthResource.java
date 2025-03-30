package com.resource;

import com.cache.EmployeeCache;
import com.dao.EmployeeDao;
import com.exception.DBOperationException;
import com.exception.EmployeeTypeException;
import com.model.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/auth")
public class AuthResource {
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logEmployee(Employee emp) {
        System.out.println("Logging Employee");
        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        StringBuilder token = new StringBuilder("{ \"Token\":\"");
        try {
            if (emp == null || emp.getPassword() == null || emp.getPassword().isEmpty() || emp.getEmail() == null || emp.getEmail().isEmpty()) {
                throw new EmployeeTypeException("Credentials is Invalid!");

            }


            Employee employee = EmployeeDao.authenticateEmployee(emp);
            if (employee == null) {

                message.append("Employee Not Found ").append("\"}");


                return Response.status(Response.Status.NOT_FOUND).entity(message.toString()).build();
            }
            if (emp.getPassword().equals(employee.getPassword())) {
                String tokenValue = UUID.randomUUID().toString() + System.currentTimeMillis();
                EmployeeCache.putEmployee(tokenValue, employee);
                token.append(tokenValue).append("\"}");
                return Response.status(Response.Status.OK).entity(token.toString()).build();

            } else {


                message.append("Invalid Password... Try Again").append("\"}");

                return Response.status(Response.Status.UNAUTHORIZED).entity(message.toString()).build();
            }
        } catch (EmployeeTypeException e) {
            message.append(e.getMessage()).append("\"}");
            return Response.status(Response.Status.BAD_REQUEST).entity(message.toString()).build();

        } catch (DBOperationException e) {
            message.append(e.getMessage()).append("\"}");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message.toString()).build();
        }
    }
}
