package com.filter;

import com.annotation.AdminAccess;
import com.annotation.AdminAndManagerAccess;
import com.exception.UnauthorizedAccesException;
import com.model.Employee;
import com.threadlocal.EmployeeThreadLocal;
import com.utils.EmployeeUtils;

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

        StringBuilder message = new StringBuilder("{ \"message\": \"");

        try {
            Employee employee = EmployeeThreadLocal.getEmployeeThreadLocal();
            String adminRole = "admin";
            String managerRole = "manager";
            System.out.println("Admin and Manager role Check");
            Boolean isValidRole = EmployeeUtils.employeeRoleCheck(employee, adminRole) || EmployeeUtils.employeeRoleCheck(employee, managerRole);
            if (!isValidRole) {
                throw new UnauthorizedAccesException(employee.getRole() + "  can't access this endpoint");
            }
        } catch (UnauthorizedAccesException e) {
            message.append(e.getMessage()).append("\"} ");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(message.toString()).build());

        }
    }
}
