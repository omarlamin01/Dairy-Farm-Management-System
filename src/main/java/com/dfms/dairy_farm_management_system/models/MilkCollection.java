package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class MilkCollection extends Model{
    private int id;
    private int cow_id;
    private float quantity;
    private String period;
    private Date collection_date;

    public MilkCollection( int cow_id,float quantity,String period ,Date d) {
        this.cow_id = cow_id;
        this.quantity = quantity;
        this.period = period;
        this.collection_date=d;
    }
    public MilkCollection(String period ,float quantity, int cow_id) {
        this.cow_id = cow_id;
        this.quantity = quantity;
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public MilkCollection() {

    }

    public MilkCollection(int id, int id_cow, float quantity, Date collection_date) {
        this.id = id;
        this.cow_id = id_cow;
        this.quantity = quantity;
        this.collection_date = collection_date;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setCow_id(int id_cow) {
        this.cow_id = id_cow;
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

    public int getCow_id() {
        return cow_id;
    }

    public float getQuantity() {
        return quantity;
    }

    public Date getCollection_date() {
        return collection_date;
    }


}
