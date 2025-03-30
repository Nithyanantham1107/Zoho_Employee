package com.model;


public class Employee {


    private Long id;
    private String name;
    private String role;
    private String gender;
    private int salary;
    private String accountNo;
    private String email;
    private String password;
    private String phoneNo;
    private String dob;
    private String place;

    private Long managerID;


    public void setId(long id) {

        this.id = id;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public void setRole(String Role) {
        this.role = Role;
    }

    public void setGender(String Gender) {
        this.gender = Gender;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setAccountNo(String AccountNo) {
        this.accountNo = AccountNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;

    }

    public void setPhoneNo(String PhoneNo) {
        this.phoneNo = PhoneNo;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setPlace(String Place) {
        this.place = Place;
    }

    public void setManagerID(long managerID) {
        this.managerID = managerID;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getGender() {
        return gender;
    }

    public int getSalary() {
        return salary;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getDob() {
        return dob;
    }

    public String getPlace() {
        return place;
    }

    public long getManagerID() {
        return managerID;
    }

}
