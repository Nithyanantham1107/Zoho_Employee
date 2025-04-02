package com.filter;

import com.annotation.AdminAccess;
import com.annotation.AdminAndManagerAccess;
import com.exception.UnauthorizedAccesException;
import com.google.gson.JsonObject;
import com.model.Employee;
import com.threadlocal.EmployeeThreadLocal;
import com.utils.EmployeeUtils;
import com.utils.tableenum.EmployeeRoleEnum;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


@Provider
@AdminAndManagerAccess
@Priority(Priorities.AUTHORIZATION)
public class AdminAndManagerFilter implements ContainerRequestFilter {

    public void filter(ContainerRequestContext requestContext) {
//        StringBuilder message = new StringBuilder("{ \"message\": \"");
        JsonObject resp=new JsonObject();
        try {
            Employee employee = EmployeeThreadLocal.getEmployeeThreadLocal();
            System.out.println("Admin and Manager role Check");
            boolean isValidRole = EmployeeUtils.employeeRoleCheck(employee, EmployeeRoleEnum.ADMIN.getRole()) || EmployeeUtils.employeeRoleCheck(employee, EmployeeRoleEnum.MANAGER.getRole());
            if (!isValidRole) {
                throw new UnauthorizedAccesException(employee.getRole().getRoleName() + "  can't access this endpoint");
            }
        } catch (UnauthorizedAccesException e) {
//            message.append(e.getMessage()).append("\"} ");
            resp.addProperty("Message",e.getMessage());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(resp.toString()).build());

        }
    }
}
