package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Purchase {
    private int id_purchase;
    private int id_supplier;
    private int id_stock;
    private float price;
    private Date operationDate;

    public Purchase(int id_purchase, int id_supplier, int id_stock, float price, Date operationDate) {
        this.id_purchase = id_purchase;
        this.id_supplier = id_supplier;
        this.id_stock = id_stock;
        this.price = price;
        this.operationDate = operationDate;
    }

    public int getId_purchase() {
        return id_purchase;
    }

    public void setId_purchase(int id_purchase) {
        this.id_purchase = id_purchase;
    }

    public int getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(int id_supplier) {
        this.id_supplier = id_supplier;
    }

    public int getId_stock() {
        return id_stock;
    }

    public void setId_stock(int id_stock) {
        this.id_stock = id_stock;
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
}
