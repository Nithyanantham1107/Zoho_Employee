package com.resource;


import com.annotation.Secured;
import com.dao.AttendanceDao;
import com.exception.DBOperationException;
import com.exception.EmployeeDataTypeException;
import com.google.gson.JsonObject;
import com.logger.ApplicationLogger;
import com.model.Attendence;
import com.model.Employee;
import com.threadlocal.EmployeeThreadLocal;
import com.validation.AttendenceValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Secured
public class AttendenceResource {
    private static final Logger APPLICATIONLOGGER = ApplicationLogger.getApplicationLoggerLogger();


    @POST
    @Path("/checkin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkin(Attendence attendence) throws EmployeeDataTypeException, DBOperationException {
        System.out.println("here the Employeee checkIn");
        JsonObject resp = new JsonObject();
        AttendenceValidation.attendenceCheck(attendence);
        Employee currentEmployee = EmployeeThreadLocal.getEmployeeThreadLocal();
        if (currentEmployee.getEmpID() != attendence.getEmpID()) {
            resp.addProperty("Message", " Invalid EmployeeID ,Employee Should provide Their ID to CheckIn");
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(" Invalid EmployeeID ,Employee Should provide Their ID to CheckIn"));
            return Response.status(Response.Status.BAD_REQUEST).entity(resp.toString()).build();
        }
        if (AttendanceDao.checkIn(attendence)) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Employee CheckIn Success"));

            resp.addProperty("Message", "Employee CheckIn Success");
            return Response.status(Response.Status.OK).entity(resp.toString()).build();
        } else {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Employee CheckIn failed"));

            resp.addProperty("Message", "Employee CheckIn Failed");
            return Response.status(Response.Status.CONFLICT).entity(resp.toString()).build();


        }


    }


    @POST
    @Path("/checkout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkout(Attendence attendence) throws EmployeeDataTypeException, DBOperationException {
        System.out.println("here the Employeee checkOUT");
        JsonObject resp = new JsonObject();
        AttendenceValidation.attendenceCheck(attendence);
        Employee currentEmployee = EmployeeThreadLocal.getEmployeeThreadLocal();
        if (currentEmployee.getEmpID() != attendence.getEmpID()) {
            resp.addProperty("Message", "Employee Invalid EmployeeID ,Employee Should provide Their ID to CheckOUT");
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Employee Invalid EmployeeID ,Employee Should provide Their ID to CheckOut"));

            return Response.status(Response.Status.BAD_REQUEST).entity(resp.toString()).build();
        }

        if (AttendanceDao.checkOut(attendence)) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Employee CheckOut Success"));

            resp.addProperty("Message", "Employee CheckOut Success");
            return Response.status(Response.Status.OK).entity(resp.toString()).build();
        } else {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Employee CheckOut failed"));

            resp.addProperty("Message", "Employee CheckOut Failed");
            return Response.status(Response.Status.CONFLICT).entity(resp.toString()).build();


        }


    }


}
