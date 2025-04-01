package com.dao;

import com.dbconnection.DBConnection;
import com.exception.DBConnectionException;
import com.exception.DBOperationException;
import com.logger.ApplicationLogger;
import com.model.Payroll;
import com.utils.EmployeeUtils;
import com.utils.tableenum.EmployeeEnum;
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
                + PayrollEnum.DATE.getColumn() + ","
                + PayrollEnum.PAYROLLSALARY.getColumn() + ","
                + PayrollEnum.EMPID.getColumn() + ")  values (?,?,?);";

        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setLong(1, EmployeeUtils.convertEmployeeDobToLong(payroll.getDate()));
            ps.setInt(2, payroll.getPayrollSalary());
            ps.setLong(3, payroll.getEmpID());
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

        System.out.println("here teh Query is "+query);
        List<Payroll> payrolls = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(query);


            ps.setLong(1, empID);
            ResultSet result = ps.executeQuery();
            while (result.next()) {

                Payroll payroll = new Payroll();
                payroll.setEmpID(result.getLong(PayrollEnum.EMPID.getColumn()));
                payroll.setPayrollSalary(result.getInt(PayrollEnum.PAYROLLSALARY.getColumn()));
                payroll.setDate(EmployeeUtils.convertEmployeeDobToString(result.getLong(PayrollEnum.DATE.getColumn())));

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
