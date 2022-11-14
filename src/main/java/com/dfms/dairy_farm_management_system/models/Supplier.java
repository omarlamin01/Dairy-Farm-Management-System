package com.dfms.dairy_farm_management_system.models;

public class Supplier {
    private int id_supplier;
    private String name;
    private String type;
    private String email;
    private int phone;

    public Supplier(int id_supplier, String name, String type, String email, int phone) {
        this.id_supplier = id_supplier;
        this.name = name;
        this.type = type;
        this.email = email;
        this.phone = phone;
    }

    public Supplier() {

    }

    public int getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(int id_supplier) {
        this.id_supplier = id_supplier;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}