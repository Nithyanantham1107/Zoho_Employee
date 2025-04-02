package com.utils.tableenum;

public enum AttendanceEnum {

ATTENDENCEID("attendenceID"),DATE("date"),CHECKIN("checkin"),CHECKOUT("checkout"),EMPID("empID");

    private String column;
    AttendanceEnum(String column) {
        this.column = column;
    }
    public String getColumn() {
        return column;
    }
}
