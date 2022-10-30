package com.dfms.dairy_farm_management_system.models;

public class HealthStatus {
    private int id;
    private int id_animal;
    private float weight;
    private float breading;
    private String health_score;

    public void setId(int id) {
        this.id = id;
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
        return id;
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

    public HealthStatus(int id, int id_animal, float weight, float breading, String health_score) {
        this.id = id;
        this.id_animal = id_animal;
        this.weight = weight;
        this.breading = breading;
        this.health_score = health_score;
    }
}
