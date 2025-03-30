package com.resource;

import com.annotation.AdminAccess;
import com.annotation.AdminAndManagerAccess;
import com.dao.EmployeeDao;
import com.exception.DBOperationException;
import com.exception.EmployeeTypeException;
import com.annotation.Secured;
import com.model.Employee;
import com.threadlocal.EmployeeThreadLocal;
import com.validation.EmployeeValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/employee")
@Secured
public class EmployeeResource {

    @GET
    @AdminAccess
    @Path("/admin/all")

    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployee() {
        System.out.println("Get All employee");

        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        try {


            List<Employee> employees = EmployeeDao.getAllEmployee();
            if (employees == null || employees.size() == 0) {

                message.append("Employee DB is Empty ").append("\"}");


                return Response.status(Response.Status.NOT_FOUND).entity(message.toString()).build();
            }


            return Response.status(Response.Status.OK).entity(employees).build();

        } catch (DBOperationException e) {
            message.append(e.getMessage()).append("\"}");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message.toString()).build();
        }


    }

    @GET
    @AdminAndManagerAccess
    @Path("/manager/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployeeUnderManager(@QueryParam("empid") String empid) {
        System.out.println("Get All employee");

        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        try {
            if (empid == null || empid.isEmpty()) {
                throw new EmployeeTypeException("Empid is  empty");

            }
            long empId = Long.parseLong(empid);


            List<Employee> employees = EmployeeDao.getAllEmployeeUnderManager(empId);
            if (employees == null || employees.size() == 0) {

                message.append("No Employee Available ").append("\"}");


                return Response.status(Response.Status.NOT_FOUND).entity(message.toString()).build();
            }


            return Response.status(Response.Status.OK).entity(employees).build();

        } catch (EmployeeTypeException e) {
            message.append(e.getMessage()).append("\"}");
            return Response.status(Response.Status.BAD_REQUEST).entity(message.toString()).build();

        } catch (DBOperationException e) {
            message.append(e.getMessage()).append("\"}");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message.toString()).build();
        }


    }

    @GET

    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@QueryParam("empid") String empid) {
        System.out.println("Get employee");

        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        try {

            Employee employee;
            if (empid == null || empid.isEmpty()) {
                throw new EmployeeTypeException("Empid is  empty");

            }
            long empId = Long.parseLong(empid);
            if (EmployeeDao.employeeRoleCheck(empId)) {
                employee = EmployeeDao.getEmployee(empId);
            } else {
                throw new EmployeeTypeException("Employee ID" + empid + " Not Accessible");


            }


            if (employee == null) {

                message.append("Employee Not Found ").append("\"}");


                return Response.status(Response.Status.NOT_FOUND).entity(message.toString()).build();
            }


            return Response.status(Response.Status.OK).entity(employee).build();

        } catch (EmployeeTypeException e) {
            message.append(e.getMessage()).append("\"}");
            return Response.status(Response.Status.BAD_REQUEST).entity(message.toString()).build();

        } catch (DBOperationException e) {
            message.append(e.getMessage()).append("\"}");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message.toString()).build();
        }


    }

    @POST
    @AdminAccess
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEmployee(Employee employee) {
        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        try {
            if (employee == null) {

                throw new EmployeeTypeException("Employee is empty!");

            }

//            System.out.println("hi employee" + employee.getManagerID());
            EmployeeValidation.employeeCheck(employee);
            EmployeeDao.addEmployee(employee);
            message.append("Employee created successfully").append("\"}");
            return Response.status(Response.Status.CREATED).entity(message.toString()).build();
        } catch (EmployeeTypeException e) {
            message.append(e.getMessage()).append("\"}");
            return Response.status(Response.Status.BAD_REQUEST).entity(message.toString()).build();
        } catch (DBOperationException e) {

            message.append(e.getMessage()).append("\"}");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message.toString()).build();


        }


    }


    @DELETE
    @AdminAccess
    @Path("/{empid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmployee(@PathParam("empid") String empid) {

        System.out.println("Get employee");
        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        try {
            if (empid == null || empid.isEmpty()) {
                throw new EmployeeTypeException("Empid is  empty");

            }
            long empId = Long.parseLong(empid);


            if (EmployeeDao.deleteEmployee(empId)) {

                message.append("Employee deleted successfully for ID " + empid).append("\"}");
                return Response.status(Response.Status.OK).entity(message.toString()).build();
            } else {
                message.append("Employee Not Found").append("\"}");
                return Response.status(Response.Status.NOT_FOUND).entity(message.toString()).build();
            }


        } catch (EmployeeTypeException e) {
            message.append(e.getMessage()).append("\"}");
            return Response.status(Response.Status.BAD_REQUEST).entity(message.toString()).build();
        } catch (DBOperationException e) {
            message.append(e.getMessage()).append("\"}");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message.toString()).build();
        }

    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(Employee employee) {
        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        try {
            if (employee == null) {

                throw new EmployeeTypeException("Employee is empty!");

            }

            System.out.println("hi update employee" + employee.getManagerID());
            EmployeeValidation.employeeCheck(employee);
            if (EmployeeDao.employeeRoleCheck(employee.getId())) {

                EmployeeDao.updateEmployee(employee);
                message.append("Employee Updated successfully").append("\"}");
                return Response.status(Response.Status.OK).entity(message.toString()).build();
            } else {
                message.append("Employee doesn't have access to update employee with EmpId" + employee.getId()).append("\"}");
                return Response.status(Response.Status.OK).entity(message.toString()).build();

            }


        } catch (EmployeeTypeException e) {
            message.append(e.getMessage()).append("\"}");
            return Response.status(Response.Status.BAD_REQUEST).entity(message.toString()).build();
        } catch (DBOperationException e) {

            message.append(e.getMessage()).append("\"}");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message.toString()).build();


        }


    }


    @PUT
    @AdminAccess
    @Path("admin/manager/set")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setManager(Employee employee) {
        StringBuilder message = new StringBuilder("{ \"Message\":\"");
        try {
            if (employee == null || employee.getId() < 0 || employee.getManagerID() < 0) {

                throw new EmployeeTypeException("Employee is empty!");

            }

            if (EmployeeDao.setManager(employee)) {
                message.append("Manager set for Employee ID" + employee.getId() + " successfully").append("\"}");

                return Response.status(Response.Status.OK).entity(message.toString()).build();
            } else {

                message.append("Failed To Set Manager for EmployeeID" + employee.getId()).append("\"}");

                return Response.status(Response.Status.BAD_REQUEST).entity(message.toString()).build();
            }
        } catch (EmployeeTypeException e) {
            System.out.println("Employee type Exception");
            message.append(e.getMessage()).append("\"}");
            return Response.status(Response.Status.BAD_REQUEST).entity(message.toString()).build();
        } catch (DBOperationException e) {
            System.out.println("Employee DB Operation Exception" + e.getMessage());
            message.append(e.getMessage()).append("\"}");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message.toString()).build();


        }


    }


}
