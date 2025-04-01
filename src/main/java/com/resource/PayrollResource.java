package com.resource;


import com.annotation.AdminAccess;
import com.annotation.Secured;
import com.dao.EmployeeDao;
import com.dao.PayrollDao;
import com.dao.RoleDao;
import com.exception.DBOperationException;
import com.exception.EmployeeTypeException;
import com.google.gson.JsonObject;
import com.logger.ApplicationLogger;
import com.model.Employee;
import com.model.Payroll;
import com.model.Role;
import com.validation.EmployeeValidation;
import com.validation.PayrollValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Secured
public class PayrollResource {
    private static final Logger APPLICATIONLOGGER = ApplicationLogger.getApplicationLoggerLogger();


    @POST
    @AdminAccess
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRole(Payroll payroll) throws EmployeeTypeException, DBOperationException {


//        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        JsonObject resp = new JsonObject();
        if (payroll == null) {

            throw new EmployeeTypeException("payroll is empty!");

        }

        PayrollValidation.employeeCheck(payroll);

        PayrollDao.addPayroll(payroll);
//        message.append("Payroll Added  successfully").append("\"}");
        resp.addProperty("Message", "Payroll added Successfully");
        APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Payroll Added successfully"));

        return Response.status(Response.Status.CREATED).entity(resp.toString()).build();


    }


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPayrolls(@QueryParam("empid") long empId) throws EmployeeTypeException, DBOperationException {


        System.out.println("Get employee payroll");

//        StringBuilder message = new StringBuilder("{ \"Message\":\"");

        JsonObject resp = new JsonObject();
        List<Payroll> payrolls;

        if (EmployeeDao.employeeRoleCheck(empId)) {
            payrolls = PayrollDao.getEmployeePayroll(empId);
        } else {
            throw new EmployeeTypeException("  Payroll for  Employee ID" + empId + " Not Accessible");


        }


        if (payrolls.isEmpty()) {

//            message.append("Payrolls Not Found ").append("\"}");

            resp.addProperty("Message", "Payroll for  Employee ID" + empId + " Not Found");
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Payrolls Not Found "));

            return Response.status(Response.Status.NOT_FOUND).entity(resp.toString()).build();
        }


        APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Payrolls retrieved ,EmpID:" + empId));

        return Response.status(Response.Status.OK).entity(payrolls).build();


    }
}
