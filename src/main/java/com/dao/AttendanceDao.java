package com.dao;

import com.dbconnection.DBConnection;
import com.exception.DBConnectionException;
import com.exception.DBOperationException;
import com.model.Attendence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendanceDao {


    public static Boolean checkIn(long attendenceID, long empID) throws DBOperationException {

        try (Connection conn = DBConnection.getConnection();) {

            Attendence attendence = getAttendence(attendenceID, empID);
            if (attendence == null) {
                PreparedStatement prepared = conn.prepareStatement("insert into attendence(attendenceID, empID,IN) values(?,?,?);");


                prepared.setLong(1, attendenceID);
                prepared.setLong(2, empID);
                prepared.setLong(3,System.currentTimeMillis());
            }else{

                PreparedStatement prepared = conn.prepareStatement("update  attendence(IN) values(?,?,?);");
                prepared.setLong(1, attendenceID);
                prepared.setLong(2, empID);
                prepared.setLong(3,System.currentTimeMillis());

            }

            return true;
        } catch (DBConnectionException | SQLException e) {

            throw new DBOperationException("unable make Connection with Database");

        }
    }

    public static Attendence getAttendence(long attendenceID, long empID) throws DBOperationException {
        try (Connection conn = DBConnection.getConnection();) {

            PreparedStatement prepared = conn.prepareStatement("select * from attendence where ID=? and empID=?");
            prepared.setLong(1, attendenceID);
            prepared.setLong(2, empID);

            ResultSet result = prepared.executeQuery();

            if (result.next()) {

                Attendence attendence = new Attendence();
                attendence.setAttendenceId(result.getLong("ID"));
                attendence.setEmpId(result.getLong("empID"));
                attendence.setIn(result.getLong("in"));
                attendence.setOut(result.getLong("out"));

                return attendence;
            } else {

                return null;
            }
        } catch (DBConnectionException e) {

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {

            throw new DBOperationException("Error Occured while getting attendence data");
        }


    }
}
