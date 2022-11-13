package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Routine implements Model {
    private int id_routine;
    private String name;
    private String note;

    public void setId(int id) {
        this.id_routine = id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public int getId() {
        return id_routine;
    }


    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }


    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }
}
