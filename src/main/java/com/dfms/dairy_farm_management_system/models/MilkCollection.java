package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class MilkCollection extends Model{
    private int id_milkcollection;
    private int id_cow;
    private float quantity;
    private Date collection_date;
    public MilkCollection(int id, int id_cow, float quantity, Date collection_date) {
        this.id_milkcollection = id;
        this.id_cow = id_cow;
        this.quantity = quantity;
        this.collection_date = collection_date;
    }
    public void setId(int id) {
        this.id_milkcollection = id;
    }

    public void setId_cow(int id_cow) {
        this.id_cow = id_cow;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setCollection_date(Date collection_date) {
        this.collection_date = collection_date;
    }

    public int getId() {
        return id_milkcollection;
    }

    public int getId_cow() {
        return id_cow;
    }

    public float getQuantity() {
        return quantity;
    }

    public Date getCollection_date() {
        return collection_date;
    }


}
