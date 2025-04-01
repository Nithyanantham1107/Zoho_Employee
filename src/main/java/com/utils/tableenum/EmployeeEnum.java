package com.utils.tableenum;

public enum EmployeeEnum {
    EMPID("empID"), NAME("name"),
    ROLE("role"), GENDER("gender"),
    SALARY("salary"), ACCOUNTNO("accountNo"),
    EMAIL("email"), PASSWORD("password"), PHONENO("phoneNo"),
    BIRTHDATE("dob"),PLACE("place"),MANAGERID("managerID"),;
    private String column;

    EmployeeEnum(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }

}
