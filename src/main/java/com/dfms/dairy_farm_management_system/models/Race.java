package com.dfms.dairy_farm_management_system.models;

public class Race {
    private int id;
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Race(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
