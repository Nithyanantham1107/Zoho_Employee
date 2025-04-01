package com.utils.tableenum;

public enum EmployeeRoleEnum {


    DEVELOPER("developer"), ADMIN("admin"), MANAGER("manager");
    private String role;

    EmployeeRoleEnum(String role) {
        this.role = role;

    }

    public String getRole() {
        return role;
    }


}
