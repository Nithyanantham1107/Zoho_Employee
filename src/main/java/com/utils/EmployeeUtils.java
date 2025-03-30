package com.utils;

import com.model.Employee;

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

        return employee.getRole().toUpperCase().equals(role.toUpperCase());

    }
}
