package com.dao;

import com.dbconnection.DBConnection;
import com.exception.DBConnectionException;
import com.exception.DBOperationException;
import com.logger.ApplicationLogger;
import com.model.Attendence;
import com.utils.EmployeeUtils;
import com.utils.tableenum.AttendanceEnum;
import com.utils.tableenum.EmployeeEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AttendanceDao {
    private static final Logger APPLICATIONLOGGER = ApplicationLogger.getApplicationLoggerLogger();

    public static Boolean checkIn(Attendence attendence) throws DBOperationException {
        String query = "insert into attendence(" +
                "" + AttendanceEnum.DATE.getColumn() + "," +
                AttendanceEnum.EMPID.getColumn() + "," +
                AttendanceEnum.CHECKIN.getColumn() + ")   values(?,?,?);";
        System.out.println("here the attendence Query is" + query);
        try (Connection conn = DBConnection.getConnection();) {
            Attendence empAttendence = getAttendence(attendence);
            if (empAttendence == null) {
                PreparedStatement prepared = conn.prepareStatement(query);
                prepared.setLong(1, EmployeeUtils.convertDobToLong(attendence.getDate()));
                prepared.setLong(2, attendence.getEmpID());
                prepared.setLong(3, EmployeeUtils.convertStringTimeToSeconds(attendence.getCheckin()));
                prepared.executeUpdate();
                return true;
            } else {
                return false;

            }


        } catch (DBConnectionException e) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("Error Occured while Inserting Attendence Table data");
        }
    }


    public static Boolean checkOut(Attendence attendence) throws DBOperationException {
        String query = "update attendence  set " +
                "" + AttendanceEnum.CHECKOUT.getColumn() + "=? where " + AttendanceEnum.DATE.getColumn() + "=? and "
                + AttendanceEnum.EMPID.getColumn() + "=?;";

        System.out.println("here the attendence check out  Query is" + query);
        try (Connection conn = DBConnection.getConnection();) {

            PreparedStatement prepared = conn.prepareStatement(query);
            prepared.setLong(1, EmployeeUtils.convertStringTimeToSeconds(attendence.getCheckout()));
            prepared.setLong(2, EmployeeUtils.convertDobToLong(attendence.getDate()));
            prepared.setLong(3, attendence.getEmpID());

            int result = prepared.executeUpdate();
            return result > 0;

        } catch (DBConnectionException e) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("Error Occured while Updating Attendence table");
        }
    }

    public static Attendence getAttendence(Attendence attendance) throws DBOperationException {
        try (Connection conn = DBConnection.getConnection();) {

            PreparedStatement prepared = conn.prepareStatement("select * from attendence where " + AttendanceEnum.DATE.getColumn() + "=? and " + AttendanceEnum.EMPID.getColumn() + "=?;");
            prepared.setLong(1, EmployeeUtils.convertDobToLong(attendance.getDate()));
            prepared.setLong(2, attendance.getEmpID());
            ResultSet result = prepared.executeQuery();
            if (result.next()) {
                Attendence attendence = new Attendence();
                attendence.setAttendenceID(result.getLong(AttendanceEnum.ATTENDENCEID.getColumn()));
                attendence.setDate(EmployeeUtils.convertDobToString(result.getLong(AttendanceEnum.DATE.getColumn())));
                attendence.setEmpID(result.getLong(AttendanceEnum.EMPID.getColumn()));
                attendence.setCheckin(EmployeeUtils.convertLongTimeToString(result.getLong(AttendanceEnum.CHECKIN.getColumn())));
                attendence.setCheckout(EmployeeUtils.convertLongTimeToString(result.getLong(AttendanceEnum.CHECKOUT.getColumn())));
                return attendence;
            } else {

                return null;
            }
        } catch (DBConnectionException e) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("Error Occured while getting attendence data");
        }


    }

    public static int getAttendenceDayCount(long fromDate, long toDate, long empID) throws DBOperationException {

        String query = "select COUNT(*) from attendence where " + AttendanceEnum.DATE.getColumn() + " between ? and ? and " + AttendanceEnum.EMPID.getColumn() + "=?;";
        System.out.println("here the attendence " + query);
        try (Connection conn = DBConnection.getConnection();) {

            PreparedStatement prepared = conn.prepareStatement(query);
            prepared.setLong(1, fromDate);
            prepared.setLong(2, toDate);
            prepared.setLong(3, empID);
            ResultSet result = prepared.executeQuery();
            if (result.next()) {


                return result.getInt(1);
            } else {

                return 0;
            }
        } catch (DBConnectionException e) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {
            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("Error Occured while getting acount of Employee Attendence data");
        }


    }
}
