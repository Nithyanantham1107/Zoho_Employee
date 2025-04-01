package com.utils;

import com.exception.DBOperationException;
import com.model.Employee;
import com.threadlocal.EmployeeThreadLocal;
import com.utils.tableenum.EmployeeRoleEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeUtils {

    public static long convertEmployeeDobToLong(String dob) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            Date date = (Date) formatter.parse(dob);
            return date.getTime();

        } catch (ParseException e) {

            return 0;
        }


    }

    public static String convertEmployeeDobToString(long dob) {
        return new SimpleDateFormat("dd/MM/yyyy").format(dob);

    }

    public static Boolean employeeRoleCheck(Employee employee, String role) {

        return employee.getRole().getRoleName().equals(role);

    }

    public static Boolean ValidRoleToUpdate(Employee employee) throws DBOperationException {

        Employee currentEmployee = EmployeeThreadLocal.getEmployeeThreadLocal();


        if (currentEmployee.getRole().getRoleName().equals(EmployeeRoleEnum.ADMIN.getRole())) {
            return true;
        }

        if (currentEmployee.getRole().getRoleName().equals(EmployeeRoleEnum.MANAGER.getRole()) && currentEmployee.getEmpID() != employee.getEmpID()) {
            return true;
        }


        return false;


    }
}
