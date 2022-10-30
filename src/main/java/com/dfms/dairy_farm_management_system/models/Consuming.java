package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Consuming {
    private int id;
    private int id_product;
    private float quantity;
    private Date date;

    public void setId(int id) {
        this.id = id;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Consuming(int id, int id_product, float quantity, Date date) {
        this.id = id;
        this.id_product = id_product;
        this.quantity = quantity;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getId_product() {
        return id_product;
    }

    public float getQuantity() {
        return quantity;
    }

    public Date getDate() {
        return date;
    }
}
