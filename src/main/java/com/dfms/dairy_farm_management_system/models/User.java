package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class User extends Employee{
    private int id_user;
    private String password;
    private int roleId;


    public User(int id_employee, String firstName, String lastName, char gender, String cin, String email, String phone, String adresse, float salary, Date recruitmentDate, String contractType, Date updated_at, Date created_at) {
        super(id_employee, firstName, lastName, gender, cin, email, phone, adresse, salary, recruitmentDate, contractType, updated_at, created_at);
    }

    public User() {
        super();
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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
