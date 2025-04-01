package com.resource;

import com.annotation.Secured;
import com.cache.EmployeeCache;
import com.dao.EmployeeDao;
import com.exception.DBOperationException;
import com.exception.EmployeeTypeException;
import com.google.gson.JsonObject;
import com.model.Employee;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.UUID;

@Path("/auth")
public class AuthResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response logEmployee(Employee emp) throws DBOperationException, EmployeeTypeException {
        System.out.println("Logging Employee");
//        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        JsonObject resp = new JsonObject();
//        StringBuilder token = new StringBuilder("{ \"Token\":\"");
        JsonObject token = new JsonObject();


        if (emp == null || emp.getPassword() == null || emp.getPassword().isEmpty() || emp.getEmail() == null || emp.getEmail().isEmpty()) {
            throw new EmployeeTypeException("Credentials is Invalid!");

        }


        Employee employee = EmployeeDao.authenticateEmployee(emp);
        if (employee == null) {

//            message.append("Employee Not Found ").append("\"}");

            resp.addProperty("Message", "Employee Not Found");

            return Response.status(Response.Status.NOT_FOUND).entity(resp.toString()).build();
        }
        if (emp.getPassword().equals(employee.getPassword())) {
            String tokenValue = UUID.randomUUID().toString() + System.currentTimeMillis();
            EmployeeCache.putEmployee(tokenValue, employee);
//            token.append(tokenValue).append("\"}");
            token.addProperty("token", tokenValue);
            return Response.status(Response.Status.OK).entity(token.toString()).build();

        } else {


//            message.append("Invalid Password... Try Again").append("\"}");
            resp.addProperty("Message", "Invalid Password");
            return Response.status(Response.Status.UNAUTHORIZED).entity(resp.toString()).build();
        }

    }

    @Secured
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)

    public Response deleteEmployee(@Context HttpHeaders requestHeader) {

        System.out.println("Logging  Out Employee");
        String token = requestHeader.getHeaderString("Authorization");
//        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        JsonObject resp = new JsonObject();

//        message.append("Successfully Logged Out" + "\"}");
        resp.addProperty("Message", "Successfully Logged Out");
        EmployeeCache.removeEmployee(token);
        return Response.status(Response.Status.OK).entity(resp.toString()).build();

    }

}
