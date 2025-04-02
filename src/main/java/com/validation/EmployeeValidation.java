package com.validation;

import com.exception.EmployeeDataTypeException;
import com.model.Employee;
import com.model.Role;
import com.utils.tableenum.EmployeeRoleEnum;
import com.utils.tableenum.EmployeeTeamEnum;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class EmployeeValidation {

    public static void employeeCheck(Employee employee) throws EmployeeDataTypeException {
        if (employee.getName() == null || employee.getName().isEmpty()) {

            throw new EmployeeDataTypeException("Employee name is empty!");
        }
        if (employee.getSalary() < 0) {
            throw new EmployeeDataTypeException("Salary is negative!");
        }
        if (employee.getGender() == null || employee.getGender().isEmpty() || !isValidGender(employee.getGender())) {
            throw new EmployeeDataTypeException("Gender is Wrong!");
        }
        employeeRoleCheck(employee.getRole());

        if (employee.getPassword() == null || employee.getPassword().isEmpty()) {
            throw new EmployeeDataTypeException("Password is empty!");
        }
        if (employee.getEmail() == null || employee.getEmail().isEmpty() || !isEmail(employee.getEmail())) {
            throw new EmployeeDataTypeException("Email type is wrong!");
        }
        if (employee.getPhoneNo() == null || employee.getPhoneNo().isEmpty() || !isPhoneNumber(employee.getPhoneNo())) {

            throw new EmployeeDataTypeException("Phone number is wrong!");
        }

        if (employee.getPlace() == null || employee.getPlace().isEmpty()) {
            throw new EmployeeDataTypeException("Place is empty!");
        }
        if (employee.getDob() == null || employee.getDob().isEmpty() || !isDobValid(employee.getDob())) {
            throw new EmployeeDataTypeException("Dob is wrong!");
        }


    }

    public static void employeeRoleCheck(Role role) throws EmployeeDataTypeException {

        if (role == null) {
            throw new EmployeeDataTypeException("Role is Null!");
        }
        if (role.getTeamName().isEmpty() || !(role.getTeamName().equals(EmployeeTeamEnum.DEVELOPER.getTeam()) || role.getTeamName().equals(EmployeeTeamEnum.MANAGEMENT.getTeam()))) {
            throw new EmployeeDataTypeException("Team type is wrong!");

        }

        if (role.getRoleName().isEmpty() || !(role.getRoleName().equals(EmployeeRoleEnum.DEVELOPER.getRole()) || role.getRoleName().equals(EmployeeRoleEnum.ADMIN.getRole()) || role.getRoleName().equals(EmployeeRoleEnum.MANAGER.getRole()))) {
            throw new EmployeeDataTypeException("Role type is wrong!");

        }

    }

    public static boolean isValidGender(String Gender) {
        String gender = Gender.toLowerCase();
        return gender.equals("male") || gender.equals("female");
    }

    public static boolean isDobValid(String dob) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(dob, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }

    }

    public static boolean isEmail(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";


        Pattern p = Pattern.compile(emailRegex);

        return email != null && p.matcher(email).matches();
    }

    public static boolean isPhoneNumber(String phoneNumber) {

        return phoneNumber != null && phoneNumber.matches("^[0-9]{10}$");
    }


}
