package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class HealthStatus implements Model {
    private int id;
    private int animal_id;
    private int vaccin_id;
    private float weight;
    private float breading;
    private float age;
    private Date control_date;
    private String health_score;
    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getVaccin_id() {
        return vaccin_id;
    }

    public void setVaccin_id(int vaccin_id) {
        this.vaccin_id = vaccin_id;
    }

    public Date getControl_date() {
        return control_date;
    }

    public void setControl_date(Date control_date) {
        this.control_date = control_date;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAnimal_id(int animal_id) {
        this.animal_id = animal_id;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setBreading(float breading) {
        this.breading = breading;
    }

    public void setHealth_score(String health_score) {
        this.health_score = health_score;
    }

    public int getId() {
        return id;
    }

    public int getAnimal_id() {
        return animal_id;
    }

    public float getWeight() {
        return weight;
    }

    public float getBreading() {
        return breading;
    }

    public String getHealth_score() {
        return health_score;
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
