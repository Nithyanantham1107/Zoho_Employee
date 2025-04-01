package com.dao;

import com.dbconnection.DBConnection;
import com.exception.DBConnectionException;
import com.exception.DBOperationException;
import com.logger.ApplicationLogger;
import com.model.Employee;
import com.model.Role;
import com.threadlocal.EmployeeThreadLocal;
import com.utils.tableenum.EmployeeEnum;
import com.utils.tableenum.EmployeeRoleEnum;
import com.utils.tableenum.RoleEnum;

import java.sql.*;
import java.util.logging.Logger;

public class RoleDao {
    private static final Logger APPLICATIONLOGGER = ApplicationLogger.getApplicationLoggerLogger();


    public static Boolean addRole(Role role) throws DBOperationException {

        String query = "INSERT INTO Role (" +
                RoleEnum.ROLENAME.getColumn() + "," + RoleEnum.TEAMNAME.getColumn() + ") VALUES (?,?);";

        System.out.println("here the query" + query);
        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, role.getRoleName());
            ps.setString(2, role.getTeamName());
            int result = ps.executeUpdate();
            if (result > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    role.setRoleID(rs.getLong(1));
                }
                return true;
            } else {

                return false;
            }


        } catch (DBConnectionException e) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("Error occured while Inserting the role data");
        }


    }

    public static Role getRoleByName(Role role) throws DBOperationException {
        String query = "select * from Role  where " + RoleEnum.ROLENAME.getColumn() +
                "= ? AND " + RoleEnum.TEAMNAME.getColumn() + "=?;";
        System.out.println("here the query" + query);

        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, role.getRoleName());
            ps.setString(2, role.getTeamName());
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                Role empRole = new Role();
                empRole.setRoleID(result.getLong(RoleEnum.ROLEID.getColumn()));
                empRole.setRoleName(result.getString(RoleEnum.ROLENAME.getColumn()));
                empRole.setTeamName(result.getString(RoleEnum.TEAMNAME.getColumn()));
                return empRole;
            } else {
                return null;
            }

        } catch (DBConnectionException e) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));


            throw new DBOperationException("Error occured while retrieving  the role by Name data");
        }


    }

    public static Role getRoleByID(long roleID) throws DBOperationException {
        String query = "select * from Role  where " + RoleEnum.ROLEID.getColumn() + "= ?;";
        System.out.println("here the query" + query);

        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, roleID);
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                Role role = new Role();
                role.setRoleID(result.getInt(RoleEnum.ROLEID.getColumn()));
                role.setRoleName(result.getString(RoleEnum.ROLENAME.getColumn()));
                role.setTeamName(result.getString(RoleEnum.TEAMNAME.getColumn()));
                return role;
            } else {
                return null;
            }

        } catch (DBConnectionException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("Error occured while retrieving  the role data");
        }


    }


    public static Boolean updateRole(Role role) throws DBOperationException {

        String query = "UPDATE  Role  SET "
                + RoleEnum.ROLENAME.getColumn() + "=?," +
                RoleEnum.TEAMNAME.getColumn() +
                " =? WHERE " + RoleEnum.ROLEID.getColumn() + " = ?;";
        System.out.println("here the query" + query);
        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, role.getRoleName());
            ps.setString(2, role.getTeamName());
            ps.setLong(3, role.getRoleID());
            int result = ps.executeUpdate();

            return result > 0;

        } catch (DBConnectionException e) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));


            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("Error occured while Updating  the role data");
        }


    }

    public static Boolean deleteRole(Role role) throws DBOperationException {
        String query = "DELETE FROM ROLE WHERE  " + RoleEnum.ROLEID.getColumn() + "=?;";
        System.out.println("here the query" + query);

        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, role.getRoleID());

            int result = ps.executeUpdate();

            return result > 0;

        } catch (DBConnectionException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));


            throw new DBOperationException("Error occured while Deleting  the role data");
        }


    }




}
