package com.model;

public class Payroll {

    private long payRollID;
    private String fromDate;
    private String toDate;
    private int payrollSalary;
    private long empID;


    public String getFromDate() {
        return fromDate;
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

    public void setFromDate(String fromDate) {

        this.fromDate = fromDate;
    }

    public void setPayrollSalary(int payrollSalary) {
        this.payrollSalary = payrollSalary;
    }

    public void setEmpID(long empID) {
        this.empID = empID;
    }

    public String getToDate() {
        return toDate;
    }
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

}
