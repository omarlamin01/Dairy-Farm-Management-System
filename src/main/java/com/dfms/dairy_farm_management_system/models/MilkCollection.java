package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class MilkCollection {
    private int id;
    private int id_animal;
    private float quantity;
    private Date collection_date;

    public void setId(int id) {
        this.id = id;
    }

    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setCollection_date(Date collection_date) {
        this.collection_date = collection_date;
    }

    public int getId() {
        return id;
    }

    public int getId_animal() {
        return id_animal;
    }

    public float getQuantity() {
        return quantity;
    }

    public Date getCollection_date() {
        return collection_date;
    }

    public MilkCollection(int id, int id_animal, float quantity, Date collection_date) {
        this.id = id;
        this.id_animal = id_animal;
        this.quantity = quantity;
        this.collection_date = collection_date;
    }
}
