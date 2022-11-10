package com.dfms.dairy_farm_management_system.models;

public class Race{
    private int id_race;
    private String name;

    public Race() {

    }

    public void setId(int id) {
        this.id_race = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id_race;
    }

    public String getName() {
        return name;
    }

    public Race(int id, String name) {
        this.id_race = id;
        this.name = name;
    }
}
