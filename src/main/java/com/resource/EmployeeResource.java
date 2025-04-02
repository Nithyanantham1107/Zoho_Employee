package com.resource;

import com.annotation.AdminAccess;
import com.annotation.AdminAndManagerAccess;
import com.annotation.Secured;
import com.dao.EmployeeDao;
import com.dao.RoleDao;
import com.exception.DBOperationException;
import com.exception.EmployeeDataTypeException;
import com.google.gson.JsonObject;
import com.logger.ApplicationLogger;
import com.model.Employee;
import com.model.Role;
import com.utils.EmployeeUtils;
import com.validation.EmployeeValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("/employee")
@Secured
public class EmployeeResource {
    private static final Logger APPLICATIONLOGGER = ApplicationLogger.getApplicationLoggerLogger();

    @GET
    @AdminAccess
    @Path("/admin/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployee() throws DBOperationException {
        System.out.println("Get All employee");

//        StringBuilder message = new StringBuilder("{ \"Message\":\"");

        JsonObject resp = new JsonObject();
        List<Employee> employees = EmployeeDao.getAllEmployee();
        if (employees.isEmpty()) {

//            message.append("Employee DB is Empty ").append("\"}");

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Employee DB is Empty"));
            resp.addProperty("Message", "Employee DB is Empty");
            return Response.status(Response.Status.NOT_FOUND).entity(resp.toString()).build();
        }

        APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Employee data successfully retrieved"));

        return Response.status(Response.Status.OK).entity(employees).build();


    }

    @GET
    @AdminAndManagerAccess
    @Path("/manager/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployeeUnderManager(@QueryParam("empid") String empid) throws EmployeeDataTypeException, DBOperationException {
        System.out.println("Get All employee");

//        StringBuilder message = new StringBuilder("{ \"Message\":\"");

        JsonObject resp = new JsonObject();

        if (empid == null || empid.isEmpty()) {
            throw new EmployeeDataTypeException("Empid is  empty");

        }
        long empId = Long.parseLong(empid);


        List<Employee> employees = EmployeeDao.getAllEmployeeUnderManager(empId);
        if (employees == null || employees.size() == 0) {

//            message.append("No Employee Available ").append("\"}");
            resp.addProperty("Message", "No Employee Available");


            return Response.status(Response.Status.NOT_FOUND).entity(resp.toString()).build();
        }


        return Response.status(Response.Status.OK).entity(employees).build();


    }

    @GET

    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@QueryParam("empid") String empid) throws EmployeeDataTypeException, DBOperationException {
        System.out.println("Get employee");

//        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        JsonObject resp = new JsonObject();


        Employee employee;
        if (empid == null || empid.isEmpty()) {
            throw new EmployeeDataTypeException("Empid is  empty");

        }
        long empId = Long.parseLong(empid);
        if (EmployeeDao.employeeRoleCheck(empId)) {
            employee = EmployeeDao.getEmployee(empId);
        } else {
            throw new EmployeeDataTypeException("Employee ID" + empid + " Not Accessible");


        }


        if (employee == null) {

//            message.append("Employee Not Found ").append("\"}");

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Employee Not Found "));
            resp.addProperty("Message", "Employee Not Found");
            return Response.status(Response.Status.NOT_FOUND).entity(resp.toString()).build();
        }


        APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Employee Found ,EmpID:" + empId));

        return Response.status(Response.Status.OK).entity(employee).build();

    }

    @POST
    @AdminAccess
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEmployee(Employee employee) throws EmployeeDataTypeException, DBOperationException {
//        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        JsonObject resp = new JsonObject();
        if (employee == null) {

            throw new EmployeeDataTypeException("Employee is empty!");

        }

//            System.out.println("hi employee" + employee.getManagerID());
        EmployeeValidation.employeeCheck(employee);
        EmployeeDao.addEmployee(employee);
//        message.append("Employee created successfully").append("\"}");
        resp.addProperty("Message", "Employee created successfully");

        APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Employee Created successfully"));

        return Response.status(Response.Status.CREATED).entity(resp.toString()).build();


    }


    @Path("/role")
    public RoleResource roleResource() {

        System.out.println("Get Role");
        return new RoleResource();
    }

    @Path("/payroll")
    public PayrollResource payrollResource() {
        System.out.println("Get Payroll");
        return new PayrollResource();
    }

    @Path("/attendence")
    public AttendenceResource attendenceResource() {
        System.out.println("Get Attendence");
        return new AttendenceResource();
    }


    @DELETE
    @AdminAccess
    @Path("/{empid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmployee(@PathParam("empid") String empid) throws EmployeeDataTypeException, DBOperationException {

        System.out.println("Get employee");
//        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        JsonObject resp = new JsonObject();
        if (empid == null || empid.isEmpty()) {
            throw new EmployeeDataTypeException("Empid is  empty");

        }
        long empId = Long.parseLong(empid);


        if (EmployeeDao.deleteEmployee(empId)) {

//            message.append("Employee deleted successfully for ID " + empid).append("\"}");
            resp.addProperty("Message", "Employee deleted successfully");
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Employee deleted successfully  for ID" + empid));

            return Response.status(Response.Status.OK).entity(resp.toString()).build();
        } else {
//            message.append("Employee Not Found").append("\"}");
            resp.addProperty("Message", "Employee Not Found");
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Employee Not Found "));

            return Response.status(Response.Status.NOT_FOUND).entity(resp.toString()).build();
        }


    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(Employee employee) throws EmployeeDataTypeException, DBOperationException {
//        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        JsonObject resp = new JsonObject();
        if (employee == null) {

            throw new EmployeeDataTypeException("Employee is empty!");

        }

        System.out.println("hi update employee" + employee.getManagerID());
        EmployeeValidation.employeeCheck(employee);
        if (EmployeeDao.employeeRoleCheck(employee.getEmpID())) {

            if (EmployeeUtils.ValidRoleToUpdate(employee)) {
                Role role = RoleDao.getRoleByName(employee.getRole());
                employee.setRole(role);

            }
            EmployeeDao.updateEmployee(employee);

//            message.append("Employee Updated successfully").append("\"}");

            resp.addProperty("Message", "Employee updated successfully");
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Employee Updated successfully for empID: " + employee.getEmpID()));

            return Response.status(Response.Status.OK).entity(resp.toString()).build();
        } else {
//            message.append("Employee doesn't have access to update employee with EmpId" + employee.getEmpID()).append("\"}");

            resp.addProperty("Message", "Employee doesn't have access to update employee with EmpId" + employee.getEmpID());

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Employee doesn't have access to update employee with EmpId " + employee.getEmpID()));

            return Response.status(Response.Status.OK).entity(resp.toString()).build();

        }


    }


    @PUT
    @AdminAccess
    @Path("admin/manager/set")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setManager(Employee employee) throws EmployeeDataTypeException, DBOperationException {
//        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        JsonObject resp = new JsonObject();
        if (employee == null || employee.getEmpID() < 0 || employee.getManagerID() < 0) {

            throw new EmployeeDataTypeException("Employee is empty!");

        }

        if (EmployeeDao.setManager(employee)) {
//            message.append("Manager set for Employee ID" + employee.getEmpID() + " successfully").append("\"}");

            resp.addProperty("Message", "Manager set for Employee ID" + employee.getEmpID() + " successfully");
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Manager set for Employee ID " + employee.getEmpID() + " successfully"));

            return Response.status(Response.Status.OK).entity(resp.toString()).build();
        } else {

//            message.append("Failed To Set Manager for EmployeeID" + employee.getEmpID()).append("\"}");
            resp.addProperty("Message", "Failed To Set Manager for EmployeeID" + employee.getEmpID());
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter("Failed To Set Manager for Employee ID " + employee.getEmpID()));


            return Response.status(Response.Status.BAD_REQUEST).entity(resp.toString()).build();
        }


    }


}
