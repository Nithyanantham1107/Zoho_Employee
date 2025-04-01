package com.resource;

import com.annotation.AdminAccess;
import com.annotation.Secured;
import com.dao.EmployeeDao;
import com.dao.RoleDao;
import com.exception.DBOperationException;
import com.exception.EmployeeTypeException;
import com.google.gson.JsonObject;
import com.logger.ApplicationLogger;
import com.model.Role;
import com.validation.EmployeeValidation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Secured
public class RoleResource {
    private static final Logger APPLICATIONLOGGER = ApplicationLogger.getApplicationLoggerLogger();


    @POST
    @AdminAccess
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRole(Role role) throws EmployeeTypeException, DBOperationException {


//        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        JsonObject resp = new JsonObject();
        if (role == null) {

            throw new EmployeeTypeException("Role is empty!");

        }

        EmployeeValidation.employeeRoleCheck(role);
        RoleDao.addRole(role);
//        message.append("Role created successfully").append("\"}");
        resp.addProperty("Message", "Role added successfully");
        APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Employee Created successfully"));

        return Response.status(Response.Status.CREATED).entity(resp.toString()).build();


    }


}
