package com.utils.tableenum;

public enum PayrollEnum {


   PAYROLLID("payrollID"),DATE("date"),PAYROLLSALARY(("payrollSalary")),EMPID("empID");

    private String column;

    PayrollEnum(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
