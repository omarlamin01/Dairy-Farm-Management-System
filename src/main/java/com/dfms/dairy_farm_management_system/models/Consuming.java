package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Consuming  extends Model{

    private int id_consuming;

    private int id_stock;
    private float quantity;
    private Date date;

    public void setId_consuming(int id) {
        this.id_consuming = id;
    }

    public void setId_stock(int id_stock) {
        this.id_stock = id_stock;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Consuming(int id, int id_stock, float quantity, Date date) {
        this.id_consuming = id;
        this.id_stock= id_stock;
        this.quantity = quantity;
        this.date = date;
    }

    public int getId() {
        return id_consuming;
    }

    public int getId_stock() {
        return id_stock;
    }

    public float getQuantity() {
        return quantity;
    }

    public Date getDate() {
        return date;
    }
}
