package com.dao;

import com.dbconnection.DBConnection;

import com.exception.DBConnectionException;
import com.exception.DBOperationException;
import com.model.Employee;
import com.model.Role;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBSetupDao {

    public static void createTables(List<String> tableQuery) throws DBOperationException {

        try (Connection conn = DBConnection.getConnection();) {

            conn.setAutoCommit(false);

            for (String createQuery : tableQuery) {

                Statement statement = conn.createStatement();
                int result = statement.executeUpdate(createQuery);


            }

            conn.commit();


        } catch (DBConnectionException e) {
            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {
            System.out.println("Here we got an SQLException" + e.getMessage());
            throw new DBOperationException("Error occured while creating tables" + e.getMessage());
        }

    }

    public static void addAdminUser() throws DBOperationException {
        Employee admin = EmployeeDao.getEmployee(1);
        Role role=new Role();
        role.setTeamName("admin");
        role.setRoleName("admin");
        Role adminRole=RoleDao.getRoleByName(role);


        if(adminRole==null ){
            RoleDao.addRole(role);

        }

        if (admin == null || !admin.getRole().getRoleName().equals("admin")) {
            Employee employee = new Employee();
            employee.setName("admin");
            employee.setPassword("admin");
            employee.setGender("male");
            employee.setEmail("admin@gmail.com");
            employee.setSalary(20000);
            employee.setAccountNo("1239234819112");
            employee.setPhoneNo("1234567890");
            employee.setPlace("chennai");
            employee.setManagerID(0);
            employee.setDob("11/07/2003");

            employee.setRole(role);
            EmployeeDao.addEmployee(employee);
        }


    }
}
