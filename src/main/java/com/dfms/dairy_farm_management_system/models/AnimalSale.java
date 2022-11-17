package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class AnimalSale {

    private int id;


    private int id_client;

    private String id_animal;
    private float price;
    private Date operationDate;

    public int getId_sales() {
        return id;
    }

    public void setId_sales(int id_sales) {
        this.id = id_sales;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }





    public String getId_animal() {
        return id_animal;
    }

    public void setId_animal(String id_animal) {
        this.id_animal = id_animal;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public AnimalSale(int id_sales, int id_client, String id_animal, float price, Date operationDate) {
        this.id= id_sales;
        this.id_client = id_client;

        this.id_animal = id_animal;
        this.price = price;
        this.operationDate = operationDate;
    }
}
