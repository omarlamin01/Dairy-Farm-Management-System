package com.dfms.dairy_farm_management_system.models;

import java.time.LocalDate;

public class MilkSale implements Model{
    private int id;
    private String id_client;
    private Float quantity;
    private float price;
    private LocalDate operationDate;
    public MilkSale(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public MilkSale(int id, String id_client, Float quantity, float price, LocalDate operationDate) {
        this.id = id;
        this.id_client = id_client;
        this.quantity = quantity;
        this.price = price;
        this.operationDate = operationDate;
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }
}
