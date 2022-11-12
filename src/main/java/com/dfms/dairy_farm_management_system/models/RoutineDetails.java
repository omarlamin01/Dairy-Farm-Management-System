package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class RoutineDetails {
    private  int id_routinedetail;
    private int id_routine;
    private int id_stock;
    private float quantity;
    private Date feed_timing;

    public RoutineDetails(int id, int id_routine, int id_stock, float quantity, Date feed_timing) {
        this.id_routinedetail = id;
        this.id_routine = id_routine;
        this.id_stock = id_stock;
        this.quantity = quantity;
        this.feed_timing = feed_timing;
    }

    public int getId() {
        return id_routinedetail;
    }

    public void setId(int id) {
        this.id_routinedetail = id;
    }

    public int getId_routine() {
        return id_routine;
    }

    public void setId_routine(int id_routine) {
        this.id_routine = id_routine;
    }

    public int getId_stock() {
        return id_stock;
    }

    public void setId_stock(int id_stock) {
        this.id_stock = id_stock;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Date getFeed_timing() {
        return feed_timing;
    }

    public void setFeed_timing(Date feed_timing) {
        this.feed_timing = feed_timing;
    }
}
