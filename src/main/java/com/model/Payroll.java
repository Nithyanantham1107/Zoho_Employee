package com.model;

public class Payroll {

    private long payRollID;
    private String date;
    private int payrollSalary;
    private long empID;


    public String getDate() {
        return date;
    }

    public int getPayrollSalary() {
        return payrollSalary;
    }

    public long getEmpID() {

        return empID;
    }

    public void setPayRollID(long payRollID) {
        this.payRollID = payRollID;
    }

    public long getPayRollID() {
        return payRollID;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public void setPayrollSalary(int payrollSalary) {
        this.payrollSalary = payrollSalary;
    }

    public void setEmpID(long empID) {
        this.empID = empID;
    }

}
