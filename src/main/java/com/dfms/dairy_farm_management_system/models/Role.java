package com.dfms.dairy_farm_management_system.models;

public class Role {
    private int id;
    private String name;
    private String added_date;

    public Role() {
    }

    public Role(int id, String role_name, String added_date) {
        this.id = id;
        this.name = role_name;
        this.added_date = added_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String role_name) {
        this.name = role_name;
    }

    public String getAddedDate() {
        return added_date;
    }

    public void setAddedDate(String added_date) {
        this.added_date = added_date;
    }
}
