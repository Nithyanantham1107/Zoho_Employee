package com.utils.tableenum;

public enum PayrollEnum {


   PAYROLLID("payrollID"),FROMDATE("fromdate"),TODATE("todate"),PAYROLLSALARY(("payrollSalary")),EMPID("empID");

    private String column;

    PayrollEnum(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
