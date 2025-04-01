package com.utils.tableenum;

public enum EmployeeTeamEnum {

    DEVELOPER("development"),MANAGEMENT("management");
    private String team;
    EmployeeTeamEnum(String team) {
        this.team = team;

    }
    public String getTeam() {
        return team;
    }

}
