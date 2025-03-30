package com.dao;

import com.dbconnection.DBConnection;
import com.exception.DBConnectionException;
import com.exception.DBOperationException;
import com.model.Employee;
import com.threadlocal.EmployeeThreadLocal;
import com.utils.EmployeeUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {


    public static Employee authenticateEmployee(Employee emp) throws DBOperationException {

        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement("select * from employee where email =? and password = ?");

            ps.setString(1, emp.getEmail());
            ps.setString(2, emp.getPassword());
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                Employee employee = new Employee();
                employee.setId(result.getLong("empid"));
                employee.setName(result.getString("name"));
                employee.setRole(result.getString("role"));
                employee.setGender(result.getString("gender"));
                employee.setSalary(result.getInt("salary"));
                employee.setAccountNo(result.getString("accountNo"));
                employee.setEmail(result.getString("email"));
                employee.setPassword(result.getString("password"));
                employee.setPhoneNo(result.getString("phoneNo"));
                employee.setDob(EmployeeUtils.convertEmployeeDobToString(result.getLong("dob")));
                employee.setManagerID(result.getInt("managerID"));
                employee.setPlace(result.getString("place"));
                return employee;
            } else {

                return null;
            }


        } catch (DBConnectionException e) {
            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {


            throw new DBOperationException("Error occured while retrieving Employees ");
        }


    }

    public static List<Employee> getAllEmployee() throws DBOperationException {

        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement("select * from employee ");
            ResultSet result = ps.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (result.next()) {
                Employee employee = new Employee();
                employee.setId(result.getLong("empid"));
                employee.setName(result.getString("name"));
                employee.setRole(result.getString("role"));
                employee.setGender(result.getString("gender"));
                employee.setSalary(result.getInt("salary"));
                employee.setAccountNo(result.getString("accountNo"));
                employee.setEmail(result.getString("email"));
                employee.setPassword(result.getString("password"));
                employee.setPhoneNo(result.getString("phoneNo"));
                employee.setDob(EmployeeUtils.convertEmployeeDobToString(result.getLong("dob")));
                employee.setManagerID(result.getInt("managerID"));
                employee.setPlace(result.getString("place"));
                employees.add(employee);

            }
            return employees;


        } catch (DBConnectionException e) {
            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {


            throw new DBOperationException("Error occured while retrieving Employees ");
        }


    }

    public static List<Employee> getAllEmployeeUnderManager(long empID) throws DBOperationException {

        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement("select * from employee where managerid =?");
            ps.setLong(1, empID);
            ResultSet result = ps.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (result.next()) {
                Employee employee = new Employee();
                employee.setId(result.getLong("empid"));
                employee.setName(result.getString("name"));
                employee.setRole(result.getString("role"));
                employee.setGender(result.getString("gender"));
                employee.setSalary(result.getInt("salary"));
                employee.setAccountNo(result.getString("accountNo"));
                employee.setEmail(result.getString("email"));
                employee.setPassword(result.getString("password"));
                employee.setPhoneNo(result.getString("phoneNo"));
                employee.setDob(EmployeeUtils.convertEmployeeDobToString(result.getLong("dob")));
                employee.setManagerID(result.getInt("managerID"));
                employee.setPlace(result.getString("place"));
                employees.add(employee);

            }
            return employees;


        } catch (DBConnectionException e) {
            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {


            throw new DBOperationException("Error occured while retrieving Employees for the ManagerID" + empID);
        }


    }

    public static Employee getEmployee(long empID) throws DBOperationException {

        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement("select * from employee where empid = ?");
            ps.setLong(1, empID);
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                Employee employee = new Employee();
                employee.setId(result.getLong("empid"));
                employee.setName(result.getString("name"));
                employee.setRole(result.getString("role"));
                employee.setGender(result.getString("gender"));
                employee.setSalary(result.getInt("salary"));
                employee.setAccountNo(result.getString("accountNo"));
                employee.setEmail(result.getString("email"));
                employee.setPassword(result.getString("password"));
                employee.setPhoneNo(result.getString("phoneNo"));
                employee.setDob(EmployeeUtils.convertEmployeeDobToString(result.getLong("dob")));
                employee.setManagerID(result.getInt("managerID"));
                employee.setPlace(result.getString("place"));
                return employee;
            } else {
                return null;
            }


        } catch (DBConnectionException e) {
            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {


            throw new DBOperationException("Error occured while retrieving Employee data");
        }


    }


    public static boolean addEmployee(Employee employee) throws DBOperationException {

        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement("insert into employee (name,role,gender,salary,accountno,email,password,phoneno,dob,place) values(?,?,?,?,?,?,?,?,?,?);");
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getRole());
            ps.setString(3, employee.getGender());
            ps.setLong(4, employee.getSalary());
            ps.setString(5, employee.getAccountNo());
            ps.setString(6, employee.getEmail());
            ps.setString(7, employee.getPassword());
            ps.setString(8, employee.getPhoneNo());
            ps.setLong(9, EmployeeUtils.convertEmployeeDobToLong(employee.getDob()));
            ps.setString(10, employee.getPlace());
            int result = ps.executeUpdate();

            return result > 0;


        } catch (DBConnectionException e) {
            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {
            System.out.println("On Employee Add" + e.getMessage());

            throw new DBOperationException("Erro Occured in While inserting Employee data");
        }


    }

    public static boolean deleteEmployee(long empID) throws DBOperationException {
        {

            try (Connection conn = DBConnection.getConnection();) {
                PreparedStatement ps = conn.prepareStatement("delete from employee where empid = ?");
                ps.setLong(1, empID);
                int result = ps.executeUpdate();

                return result > 0;
            } catch (DBConnectionException e) {
                throw new DBOperationException("unable make Connection with Database");

            } catch (SQLException e) {
                throw new DBOperationException("Error occured while Deleting Employee data");
            }

        }
    }

    public static boolean updateEmployee(Employee employee) throws DBOperationException {

        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement("update  employee set name=?,role=?,gender=?,salary=?,accountno=?,email=?,password=?,phoneno=?,dob=?,place=? where empid=?;");
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getRole());
            ps.setString(3, employee.getGender());
            ps.setLong(4, employee.getSalary());
            ps.setString(5, employee.getAccountNo());
            ps.setString(6, employee.getEmail());
            ps.setString(7, employee.getPassword());
            ps.setString(8, employee.getPhoneNo());
            ps.setLong(9, EmployeeUtils.convertEmployeeDobToLong(employee.getDob()));
            ps.setString(10, employee.getPlace());
            ps.setLong(11, employee.getId());

            int result = ps.executeUpdate();

            return result > 0;
        } catch (DBConnectionException e) {
            throw new DBOperationException("unable make Connection with Database");
        } catch (SQLException e) {
            throw new DBOperationException("Erro Occured in While Updating Employee data");
        }


    }


    public static Employee employeeUnderManager(long empID) throws DBOperationException {


        Employee empthread = EmployeeThreadLocal.getEmployeeThreadLocal();

        Employee employee = EmployeeDao.getEmployee(empID);
        if (employee == null || employee.getManagerID() != empthread.getId()) {
            throw new DBOperationException("Employee Not Found");
        } else {

            return employee;
        }


    }

    public static boolean employeeRoleCheck(long empID) throws DBOperationException {


        Employee emp = EmployeeThreadLocal.getEmployeeThreadLocal();
        if (emp.getId() == empID) {

            return true;
        }
        if (emp.getRole().equalsIgnoreCase("admin")) {

            return true;
        }

        if (emp.getRole().equalsIgnoreCase("manager")) {
            Employee empUnderManager = EmployeeDao.getEmployee(empID);
            if (empUnderManager != null && emp.getId() == empUnderManager.getManagerID()) {
                return true;
            }

        }


        return false;


    }


    public static boolean setManager(Employee employee) throws DBOperationException {

        try (Connection conn = DBConnection.getConnection();) {


            if (employee.getId() == employee.getManagerID()) {
                throw new DBOperationException("Employee with id " + employee.getId() + " same as manager");
            }

            Employee emp = EmployeeDao.getEmployee(employee.getId());
            Employee manager = EmployeeDao.getEmployee(employee.getManagerID());

            if (emp == null) {

                throw new DBOperationException("Employee with id " + employee.getId() + " does not exist");
            }
            if (manager == null) {

                throw new DBOperationException("Employee with id " + employee.getManagerID() + " does not exist");
            }


            PreparedStatement ps = conn.prepareStatement("update  employee  set managerid=? where empid=?;");

            ps.setLong(1, employee.getManagerID());
            ps.setLong(2, employee.getId());
            int result = ps.executeUpdate();
            return result > 0;


        } catch (DBConnectionException e) {
            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {


            throw new DBOperationException("Erro Occured in While setting mananger to  Employee ID" + employee.getId());
        }


    }
}
