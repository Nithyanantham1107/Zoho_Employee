package com.validation;

import com.exception.EmployeeDataTypeException;
import com.model.Attendence;
import com.utils.EmployeeUtils;

public class AttendenceValidation {

    public static void attendenceCheck(Attendence attendence) throws EmployeeDataTypeException {

        if (attendence.getDate() == null || attendence.getDate().isEmpty() || EmployeeValidation.isDobValid(attendence.getDate())) {
            throw new EmployeeDataTypeException("Attendence Date Type is inValid");
        }
        if(attendence.getEmpID()<=0 ){

            throw new EmployeeDataTypeException("Attendence EmpID is invalid");
        }


    }
}
