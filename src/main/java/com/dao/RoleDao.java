package com.dao;

import com.dbconnection.DBConnection;
import com.exception.DBConnectionException;
import com.exception.DBOperationException;
import com.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDao {

    public static Role getRoleByID(long roleID) throws DBOperationException {
        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement("select * from role  where roleid = ?");
            ps.setLong(1, roleID);
            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Role role = new Role();
                role.setRoleID(result.getInt("roleid"));
                role.setRoleName(result.getString("rolename"));
                role.setTeamName(result.getString("teamname"));
                role.setRead(result.getBoolean("read"));
                role.setDelete(result.getBoolean("delete"));
                role.setUpdate(result.getBoolean("update"));
                role.setWrite(result.getBoolean("write"));

                return role;
            }else{
                return null;
            }

        } catch (DBConnectionException e) {
            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {
            throw new DBOperationException("Error occured while retrieving  the role data");
        }


    }


    public static Role updateRoleByID(long roleID) throws DBOperationException {
        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement("update  role  where roleid = ?");
            ps.setLong(1, roleID);
            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Role role = new Role();
                role.setRoleID(result.getInt("roleid"));
                role.setRoleName(result.getString("rolename"));
                role.setTeamName(result.getString("teamname"));
                role.setRead(result.getBoolean("read"));
                role.setDelete(result.getBoolean("delete"));
                role.setUpdate(result.getBoolean("update"));
                role.setWrite(result.getBoolean("write"));

                return role;
            }else{
                return null;
            }

        } catch (DBConnectionException e) {
            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {
            throw new DBOperationException("Error occured while retrieving  the role data");
        }


    }


}
