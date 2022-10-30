package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Animal {
   private int id;
   private String type;
   private Date birth_date;
   private  Date purchase_date;
   private  String health_status;
   private int id_regime;
   private int id_race;
   private float weight;
   private boolean pregnancy;

    public Animal(int id, String type, Date birth_date, Date purchase_date, String health_status, int id_regime, int id_race, float weight, boolean pregnancy) {
        this.id = id;
        this.type = type;
        this.birth_date = birth_date;
        this.purchase_date = purchase_date;
        this.health_status = health_status;
        this.id_regime = id_regime;
        this.id_race = id_race;
        this.weight = weight;
        this.pregnancy = pregnancy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public void setHealth_status(String health_status) {
        this.health_status = health_status;
    }

    public void setId_regime(int id_regime) {
        this.id_regime = id_regime;
    }

    public void setId_race(int id_race) {
        this.id_race = id_race;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setPregnancy(boolean pregnancy) {
        this.pregnancy = pregnancy;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public String getHealth_status() {
        return health_status;
    }

    public int getId_regime() {
        return id_regime;
    }

    public int getId_race() {
        return id_race;
    }

    public float getWeight() {
        return weight;
    }

    public boolean isPregnancy() {
        return pregnancy;
    }
}
