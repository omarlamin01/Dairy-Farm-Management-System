package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Sales extends Model{

    private int id_sales;


    private int id_client;
    private int id_milkcollection;
    private int id_animal;
    private float price;
    private Date operationDate;

    public int getId_sales() {
        return id_sales;
    }

    public void setId_sales(int id_sales) {
        this.id_sales = id_sales;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_milkcollection() {
        return id_milkcollection;
    }

    public void setId_milkcollection(int id_milkcollection) {
        this.id_milkcollection = id_milkcollection;
    }

    public int getId_animal() {
        return id_animal;
    }

    public void setId_animal(int id_animal) {
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

    public Sales(int id_sales, int id_client, int id_milkcollection, int id_animal, float price, Date operationDate) {
        this.id_sales = id_sales;
        this.id_client = id_client;
        this.id_milkcollection = id_milkcollection;
        this.id_animal = id_animal;
        this.price = price;
        this.operationDate = operationDate;
    }
}
