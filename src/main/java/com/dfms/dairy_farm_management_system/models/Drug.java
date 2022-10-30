package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Drug {
    private int id;
    private String type;
    private String name;
    private Date expiration_date;
    private int stock;

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public int getStock() {
        return stock;
    }

    public Drug(int id, String type, String name, Date expiration_date, int stock) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.expiration_date = expiration_date;
        this.stock = stock;
    }
}
