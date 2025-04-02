package com.validation;

import com.exception.EmployeeDataTypeException;
import com.model.Payroll;

public class PayrollValidation {
    public static void employeeCheck(Payroll payroll) throws EmployeeDataTypeException {


        if (payroll.getFromDate() == null || payroll.getFromDate().isEmpty()) {

            throw new EmployeeDataTypeException(" from Date is empty!");
        }
        if (payroll.getToDate() == null || payroll.getToDate().isEmpty()) {
            throw new EmployeeDataTypeException("Date is empty!");
        }

        if (payroll.getEmpID() < 0) {
            throw new EmployeeDataTypeException("Ivalid EmployeeID!");
        }


    }

}
