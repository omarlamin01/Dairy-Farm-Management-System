package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class User extends Employee {
    private int id_user;
    private String password;
    private int roleId;
    @Override
    public void setId(int id) {
        this.id_user= id;
    }

    @Override
    public int getId() {
        return id_user;
    }

    public User(int id, String firstName, String lastName, char gender, String cin, String email, int phone, String adresse, int id1, float salary, Date recruitmentDate, String contractType, int id2, String password, int roleId) {
        super(id, firstName, lastName, gender, cin, email, phone, adresse, id1, salary, recruitmentDate, contractType);
        this.id_user = id2;
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
