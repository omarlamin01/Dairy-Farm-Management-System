package com.dfms.dairy_farm_management_system.models;

public class Stock extends Model {
    private int id_stock;
    private String name;
    private String type;

    private String unit;
    private float quantity;
    private String addedDate;


    public Stock(int id, String name,String unit, String type, float quantity, String addedDate) {
        this.id_stock = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.addedDate = addedDate;
        this.unit=unit;

    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getId() {
        return id_stock;
    }

    public void setId(int id) {
        this.id_stock = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

   }
