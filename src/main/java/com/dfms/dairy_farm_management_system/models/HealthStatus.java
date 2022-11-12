package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class HealthStatus {
    private int id_healthstatus;
    private int id_animal;
    private int id_vaccin;
    private float weight;
    private float breading;
    private float age;
    private Date date_control;

    public int getId_vaccin() {
        return id_vaccin;
    }

    public void setId_vaccin(int id_vaccin) {
        this.id_vaccin = id_vaccin;
    }

    public HealthStatus(int id, int id_animal, int id_vaccin, float weight, float breading, float age, Date date_control, String health_score) {
        this.id_healthstatus = id;
        this.id_animal = id_animal;
        this.id_vaccin = id_vaccin;
        this.weight = weight;
        this.breading = breading;
        this.age = age;
        this.date_control = date_control;
        this.health_score = health_score;
    }

    public Date getDate_control() {
        return date_control;
    }

    public void setDate_control(Date date_control) {
        this.date_control = date_control;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }


    private String health_score;

    public void setId(int id) {
        this.id_healthstatus = id;
    }

    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
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
        return id_healthstatus;
    }

    public int getId_animal() {
        return id_animal;
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


}
