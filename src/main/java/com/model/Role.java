package com.model;

public class Role {

    private long roleID;
    private String roleName;
    private String teamName;


    public long getRoleID() {

        return roleID;
    }

    public void setRoleID(long roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getTeamName() {
        return teamName;

    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }


}
