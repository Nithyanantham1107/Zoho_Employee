package com.dao;

import com.dbconnection.DBConnection;
import com.exception.DBConnectionException;
import com.exception.DBOperationException;
import com.logger.ApplicationLogger;
import com.model.Employee;
import com.model.Payroll;
import com.utils.EmployeeUtils;
import com.utils.tableenum.AttendanceEnum;
import com.utils.tableenum.PayrollEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PayrollDao {
    private static final Logger APPLICATIONLOGGER = ApplicationLogger.getApplicationLoggerLogger();


    public static boolean addPayroll(Payroll payroll) throws DBOperationException {
        String query = "insert into Payroll ("
                + PayrollEnum.FROMDATE.getColumn() + ","
                + PayrollEnum.TODATE.getColumn() + ","
                + PayrollEnum.PAYROLLSALARY.getColumn() + ","
                + PayrollEnum.EMPID.getColumn() + ")  values (?,?,?,?);";
        Employee employee = EmployeeDao.getEmployee(payroll.getEmpID());

        try (Connection conn = DBConnection.getConnection();) {
            long fromdate = EmployeeUtils.convertDobToLong(payroll.getFromDate());
            long todate = EmployeeUtils.convertDobToLong(payroll.getToDate());
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setLong(1, fromdate);
            ps.setLong(2, todate);

            int days = AttendanceDao.getAttendenceDayCount(fromdate, todate, payroll.getEmpID());
            int payrollSalary = EmployeeUtils.payrollSalaryCalculater(employee.getSalary(), days);
            ps.setInt(3, payrollSalary);
            ps.setLong(4, payroll.getEmpID());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (DBConnectionException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("Error occured while Inserting Payroll data");
        }

    }


    public static List<Payroll> getEmployeePayroll(long empID) throws DBOperationException {
        String query = "select * from Payroll where " + PayrollEnum.EMPID.getColumn() + "= ?;";

        System.out.println("here teh Query is " + query);
        List<Payroll> payrolls = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, empID);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                Payroll payroll = new Payroll();
                payroll.setEmpID(result.getLong(PayrollEnum.EMPID.getColumn()));
                payroll.setPayrollSalary(result.getInt(PayrollEnum.PAYROLLSALARY.getColumn()));
                payroll.setFromDate(EmployeeUtils.convertDobToString(result.getLong(PayrollEnum.FROMDATE.getColumn())));
                payroll.setToDate(EmployeeUtils.convertDobToString(result.getLong(PayrollEnum.TODATE.getColumn())));
                payroll.setPayRollID(result.getLong(PayrollEnum.PAYROLLID.getColumn()));
                payrolls.add(payroll);
            }

            return payrolls;
        } catch (DBConnectionException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("unable make Connection with Database");

        } catch (SQLException e) {

            APPLICATIONLOGGER.warning(ApplicationLogger.applicationLogGeneroter(e.getStackTrace()[0].getClassName(), e.getStackTrace()[0].getMethodName(), e.getClass().getName(), e.getMessage()));

            throw new DBOperationException("Error occured while retrieving  Payroll data");
        }

    }


}
