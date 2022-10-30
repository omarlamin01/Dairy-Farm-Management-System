package com.dfms.dairy_farm_management_system.models;

public class User {
    private String password;
    private int roleId;

    public User(String password, int roleId) {
        this.password = password;
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
