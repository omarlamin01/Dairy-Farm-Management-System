package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Routine extends Model{
    private int id_routine;

    private String name;
    private String note;
    public Routine(int id,String name, String note) {
        this.id_routine = id;

        this.name = name;
        this.note = note;

    }

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



}
