package com.dfms.dairy_farm_management_system.models;

public class Stock {
    private int id;
    private String name;
    private String type;
    private int quantity;
    private String addedDate;
    private boolean availability;

    public Stock(int id, String name, String type, int quantity, String addedDate, boolean availability) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.addedDate = addedDate;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
