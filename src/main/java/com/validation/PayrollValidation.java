package com.validation;

import com.exception.EmployeeTypeException;
import com.model.Payroll;

public class PayrollValidation {
    public static void employeeCheck(Payroll payroll) throws EmployeeTypeException {


        if (payroll.getDate() == null || payroll.getDate().isEmpty()) {

            throw new EmployeeTypeException("Date is empty!");
        }

        if (payroll.getPayrollSalary() < 0) {
            throw new EmployeeTypeException("Salary is negative!");
        }
        if (payroll.getEmpID() < 0) {
            throw new EmployeeTypeException("Ivalid EmployeeID!");
        }


    }

}
