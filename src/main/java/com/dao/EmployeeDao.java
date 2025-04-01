package com.dao;

import com.dbconnection.DBConnection;
import com.exception.DBConnectionException;
import com.exception.DBOperationException;
import com.logger.ApplicationLogger;
import com.model.Employee;
import com.model.Role;
import com.threadlocal.EmployeeThreadLocal;
import com.utils.EmployeeUtils;
import com.utils.tableenum.EmployeeEnum;
import com.utils.tableenum.EmployeeRoleEnum;
import com.utils.tableenum.RoleEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import java.util.List;
import java.util.logging.Logger;


public class EmployeeDao {
    private static final Logger APPLICATIONLOGGER = ApplicationLogger.getApplicationLoggerLogger();


    public static Employee authenticateEmployee(Employee emp) throws DBOperationException {

        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement("select * from Employee  LEFT JOIN Role ON Employee.role=Role.roleID where " + EmployeeEnum.EMAIL.getColumn() + " =? and " + EmployeeEnum.PASSWORD.getColumn() + " = ?");

            ps.setString(1, emp.getEmail());
            ps.setString(2, emp.getPassword());
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                Employee employee = new Employee();
                employee.setEmpID(result.getLong(EmployeeEnum.EMPID.getColumn()));
                employee.setName(result.getString(EmployeeEnum.NAME.getColumn()));
                employee.setGender(result.getString(EmployeeEnum.GENDER.getColumn()));
                employee.setSalary(result.getInt(EmployeeEnum.SALARY.getColumn()));
                employee.setAccountNo(result.getString(EmployeeEnum.ACCOUNTNO.getColumn()));
                employee.setEmail(result.getString(EmployeeEnum.EMAIL.getColumn()));
                employee.setPassword(result.getString(EmployeeEnum.PASSWORD.getColumn()));
                employee.setPhoneNo(result.getString(EmployeeEnum.PHONENO.getColumn()));
                employee.setDob(EmployeeUtils.convertEmployeeDobToString(result.getLong(EmployeeEnum.BIRTHDATE.getColumn())));
                employee.setManagerID(result.getInt(EmployeeEnum.MANAGERID.getColumn()));
                employee.setPlace(result.getString(EmployeeEnum.PLACE.getColumn()));

                Role role = new Role();
                role.setRoleID(result.getLong(RoleEnum.ROLEID.getColumn()));
                role.setRoleName(result.getString(RoleEnum.ROLENAME.getColumn()));
                role.setTeamName(result.getString(RoleEnum.TEAMNAME.getColumn()));
                employee.setRole(role);


             return employee;
            } else {

                return null;
            }


        } catch (DBConnectionException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("Error occured while retrieving Employees ");
        }


    }

    public static List<Employee> getAllEmployee() throws DBOperationException {

        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement("select * from Employee  LEFT JOIN Role ON Employee.role=Role.roleID");
            ResultSet result = ps.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (result.next()) {
                Employee employee = new Employee();
                employee.setEmpID(result.getLong(EmployeeEnum.EMPID.getColumn()));
                employee.setName(result.getString(EmployeeEnum.NAME.getColumn()));
                employee.setGender(result.getString(EmployeeEnum.GENDER.getColumn()));
                employee.setSalary(result.getInt(EmployeeEnum.SALARY.getColumn()));
                employee.setAccountNo(result.getString(EmployeeEnum.ACCOUNTNO.getColumn()));
                employee.setEmail(result.getString(EmployeeEnum.EMAIL.getColumn()));
                employee.setPassword(result.getString(EmployeeEnum.PASSWORD.getColumn()));
                employee.setPhoneNo(result.getString(EmployeeEnum.PHONENO.getColumn()));
                employee.setDob(EmployeeUtils.convertEmployeeDobToString(result.getLong(EmployeeEnum.BIRTHDATE.getColumn())));
                employee.setManagerID(result.getInt(EmployeeEnum.MANAGERID.getColumn()));
                employee.setPlace(result.getString(EmployeeEnum.PLACE.getColumn()));
                Role role=new Role();
                role.setRoleName(result.getString(RoleEnum.ROLENAME.getColumn()));
                role.setRoleID(result.getLong(RoleEnum.ROLEID.getColumn()));
                role.setTeamName(result.getString(RoleEnum.TEAMNAME.getColumn()));
                employee.setRole(role);
                employees.add(employee);

            }
            return employees;


        } catch (DBConnectionException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));


            throw new DBOperationException("Error occured while retrieving Employees ");
        }


    }

    public static List<Employee> getAllEmployeeUnderManager(long empID) throws DBOperationException {

        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement("select * from Employee  LEFT JOIN Role ON Employee.role=Role.roleID where  " +EmployeeEnum.MANAGERID.getColumn()+"=?;");
            ps.setLong(1, empID);
            ResultSet result = ps.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (result.next()) {
                Employee employee = new Employee();
                employee.setEmpID(result.getLong(EmployeeEnum.EMPID.getColumn()));
                employee.setName(result.getString(EmployeeEnum.NAME.getColumn()));
                employee.setGender(result.getString(EmployeeEnum.GENDER.getColumn()));
                employee.setSalary(result.getInt(EmployeeEnum.SALARY.getColumn()));
                employee.setAccountNo(result.getString(EmployeeEnum.ACCOUNTNO.getColumn()));
                employee.setEmail(result.getString(EmployeeEnum.EMAIL.getColumn()));
                employee.setPassword(result.getString(EmployeeEnum.PASSWORD.getColumn()));
                employee.setPhoneNo(result.getString(EmployeeEnum.PHONENO.getColumn()));
                employee.setDob(EmployeeUtils.convertEmployeeDobToString(result.getLong(EmployeeEnum.BIRTHDATE.getColumn())));
                employee.setManagerID(result.getInt(EmployeeEnum.MANAGERID.getColumn()));
                employee.setPlace(result.getString(EmployeeEnum.PLACE.getColumn()));
                Role role = new Role();
                role.setRoleName(result.getString(RoleEnum.ROLENAME.getColumn()));
                role.setRoleID(result.getLong(RoleEnum.ROLEID.getColumn()));
                role.setTeamName(result.getString(RoleEnum.TEAMNAME.getColumn()));
                employee.setRole(role);
                employees.add(employee);

            }
            return employees;


        } catch (DBConnectionException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("Error occured while retrieving Employees for the ManagerID" + empID);
        }


    }

    public static Employee getEmployee(long empID) throws DBOperationException {

        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement("select * from Employee  LEFT JOIN Role ON Employee.role=Role.roleID where " + EmployeeEnum.EMPID.getColumn() + " = ?;");
            ps.setLong(1, empID);
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                Employee employee = new Employee();
                employee.setEmpID(result.getLong(EmployeeEnum.EMPID.getColumn()));
                employee.setName(result.getString(EmployeeEnum.NAME.getColumn()));
                employee.setGender(result.getString(EmployeeEnum.GENDER.getColumn()));
                employee.setSalary(result.getInt(EmployeeEnum.SALARY.getColumn()));
                employee.setAccountNo(result.getString(EmployeeEnum.ACCOUNTNO.getColumn()));
                employee.setEmail(result.getString(EmployeeEnum.EMAIL.getColumn()));
                employee.setPassword(result.getString(EmployeeEnum.PASSWORD.getColumn()));
                employee.setPhoneNo(result.getString(EmployeeEnum.PHONENO.getColumn()));
                employee.setDob(EmployeeUtils.convertEmployeeDobToString(result.getLong(EmployeeEnum.BIRTHDATE.getColumn())));
                employee.setManagerID(result.getInt(EmployeeEnum.MANAGERID.getColumn()));
                employee.setPlace(result.getString(EmployeeEnum.PLACE.getColumn()));
                Role role = new Role();
                role.setRoleName(result.getString(RoleEnum.ROLENAME.getColumn()));
                role.setRoleID(result.getLong(RoleEnum.ROLEID.getColumn()));
                role.setTeamName(result.getString(RoleEnum.TEAMNAME.getColumn()));
                employee.setRole(role);
                return employee;
            } else {
                return null;
            }


        } catch (DBConnectionException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("Error occured while retrieving Employee data");
        }


    }


    public static boolean addEmployee(Employee employee) throws DBOperationException {

        try (Connection conn = DBConnection.getConnection();) {


            Role empRole = RoleDao.getRoleByName(employee.getRole());

            if (empRole == null) {
                throw new DBOperationException("Invalid Role Type");
            } else {

                employee.setRole(empRole);
            }

            PreparedStatement ps = conn.prepareStatement("insert into Employee (" + EmployeeEnum.NAME.getColumn() +
                    "," + EmployeeEnum.ROLE.getColumn() + "," + EmployeeEnum.GENDER.getColumn() + "," + EmployeeEnum.SALARY.getColumn()
                    + "," + EmployeeEnum.ACCOUNTNO.getColumn() + "," + EmployeeEnum.EMAIL.getColumn() + "," + EmployeeEnum.PASSWORD.getColumn() + ","
                    + EmployeeEnum.PHONENO.getColumn() + "," + EmployeeEnum.BIRTHDATE.getColumn() + "," + EmployeeEnum.PLACE.getColumn() + ") values(?,?,?,?,?,?,?,?,?,?);");
            ps.setString(1, employee.getName());
            ps.setLong(2, employee.getRole().getRoleID());
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

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {
            System.out.println("On Employee Add" + e.getMessage());
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("Erro Occured in While inserting Employee data");
        }


    }

    public static boolean deleteEmployee(long empID) throws DBOperationException {
        {

            try (Connection conn = DBConnection.getConnection();) {
                PreparedStatement ps = conn.prepareStatement("delete from Employee where " + EmployeeEnum.EMPID.getColumn() + " = ?");
                ps.setLong(1, empID);
                int result = ps.executeUpdate();

                return result > 0;
            } catch (DBConnectionException e) {

                APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

                throw new DBOperationException("unable make Connection with Database");

            } catch (SQLException e) {

                APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

                throw new DBOperationException("Error occured while Deleting Employee data");
            }

        }
    }

    public static boolean updateEmployee(Employee employee) throws DBOperationException {
String query ="update  Employee set " + EmployeeEnum.NAME.getColumn() + "=?,"
        + EmployeeEnum.GENDER.getColumn() + "=?,"
        + EmployeeEnum.SALARY.getColumn() + "=?,"
        + EmployeeEnum.ACCOUNTNO.getColumn() + "=?,"
        + EmployeeEnum.EMAIL.getColumn() + "=?,"
        + EmployeeEnum.PASSWORD.getColumn() + "=?,"
        + EmployeeEnum.PHONENO.getColumn() + "=?,"
        + EmployeeEnum.BIRTHDATE.getColumn() + "=?,"
        + EmployeeEnum.PLACE.getColumn() + "=?,"
        + EmployeeEnum.ROLE.getColumn() + "=?"
        + " where " + EmployeeEnum.EMPID.getColumn() + "=?;";
System.out.println("here the Employee Update Query is"+query);
        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getGender());
            ps.setLong(3, employee.getSalary());
            ps.setString(4, employee.getAccountNo());
            ps.setString(5, employee.getEmail());
            ps.setString(6, employee.getPassword());
            ps.setString(7, employee.getPhoneNo());
            ps.setLong(8, EmployeeUtils.convertEmployeeDobToLong(employee.getDob()));
            ps.setString(9, employee.getPlace());
            ps.setLong(10, employee.getRole().getRoleID());
            ps.setLong(11, employee.getEmpID());

            int result = ps.executeUpdate();

            return result > 0;
        } catch (DBConnectionException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");
        } catch (SQLException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("Erro Occured in While Updating Employee data");
        }


    }


    public static Employee employeeUnderManager(long empID) throws DBOperationException {


        Employee empthread = EmployeeThreadLocal.getEmployeeThreadLocal();

        Employee employee = EmployeeDao.getEmployee(empID);
        if (employee == null || employee.getManagerID() != empthread.getEmpID()) {
            throw new DBOperationException("Employee Not Found");
        } else {

            return employee;
        }


    }

    public static boolean employeeRoleCheck(long empID) throws DBOperationException {


        Employee emp = EmployeeThreadLocal.getEmployeeThreadLocal();
        if (emp.getEmpID() == empID) {

            return true;
        }
        if (emp.getRole().getRoleName().equalsIgnoreCase(EmployeeRoleEnum.ADMIN.getRole())) {

            return true;
        }

        if (emp.getRole().getRoleName().equalsIgnoreCase(EmployeeRoleEnum.MANAGER.getRole())) {
            Employee empUnderManager = EmployeeDao.getEmployee(empID);
            if (empUnderManager != null && emp.getEmpID() == empUnderManager.getManagerID()) {
                return true;
            }

        }


        return false;


    }


    public static boolean setManager(Employee employee) throws DBOperationException {

        try (Connection conn = DBConnection.getConnection();) {


            if (employee.getEmpID() == employee.getManagerID()) {
                throw new DBOperationException("Employee with id " + employee.getEmpID() + " same as manager");
            }

            Employee emp = EmployeeDao.getEmployee(employee.getEmpID());
            Employee manager = EmployeeDao.getEmployee(employee.getManagerID());

            if (emp == null) {

                throw new DBOperationException("Employee with id " + employee.getEmpID() + " does not exist");
            }
            if (manager == null) {

                throw new DBOperationException("Employee with id " + employee.getManagerID() + " does not exist");
            }


            PreparedStatement ps = conn.prepareStatement("update  Employee  set" + EmployeeEnum.MANAGERID.getColumn() + "=? where " + EmployeeEnum.EMPID.getColumn() + "=?;");

            ps.setLong(1, employee.getManagerID());
            ps.setLong(2, employee.getEmpID());
            int result = ps.executeUpdate();
            return result > 0;


        } catch (DBConnectionException e) {



            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));


            throw new DBOperationException("Erro Occured in While setting mananger to  Employee ID" + employee.getEmpID());
        }


    }
}
