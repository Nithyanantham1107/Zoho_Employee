package com.model;

public class Attendence {
    private long attendenceID;
    private String date;
    private long empID;
    private String checkin;
    private String checkout;

    public void setAttendenceID(long attendenceID) {
        this.attendenceID = attendenceID;
    }

    public void setEmpID(long empID) {
        this.empID = empID;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public long getAttendenceID() {
        return attendenceID;
    }

    public long getEmpID() {
        return empID;
    }

    public String getCheckin() {
        return checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
