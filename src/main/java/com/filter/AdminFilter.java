package com.filter;

import com.annotation.AdminAccess;
import com.exception.UnauthorizedAccesException;
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
@AdminAccess
@Priority(Priorities.AUTHORIZATION)
public class AdminFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext)  {
        StringBuilder message = new StringBuilder("{ \"message\": \"");
        try {
            Employee employee=EmployeeThreadLocal.getEmployeeThreadLocal();
            System.out.println("heer teh data is"+employee);
            System.out.println("employee role here is"+employee.getRole());

            System.out.println("Admin role Check");
            if (!EmployeeUtils.employeeRoleCheck(employee, EmployeeRoleEnum.ADMIN.getRole())) {
                throw new UnauthorizedAccesException(employee.getRole().getRoleName()+"  can't access this endpoint");
            }

        } catch (UnauthorizedAccesException e) {
            message.append(e.getMessage()).append("\"} ");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(message.toString()).build());




        }
    }
}
