package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Animal {


    private String id;
    private String type;
    private Date birth_date;
    private Date purchase_date;
    private Date created_at;
    private Date updated_at;
    private String routine;
    private String race;


    public Animal(String id, String type, Date birth_date, Date purchase_date, Date created_at, Date updated_at, String routine, String race) {
        this.id = id;
        this.type = type;
        this.birth_date = birth_date;
        this.purchase_date = purchase_date;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.routine = routine;
        this.race = race;
    }

    public Animal() {}

    public Animal(String id, String type, Date birth_date,String routine, String race) {
        this.id = id;
        this.type = type;
        this.birth_date = birth_date;

        this.routine = routine;
        this.race = race;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }
}
