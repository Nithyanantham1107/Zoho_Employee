package com.utils.tableenum;

public enum RoleEnum {

    ROLEID("roleID"),ROLENAME("roleName"),
    TEAMNAME("teamName");

    private String column;

    RoleEnum(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }

}
