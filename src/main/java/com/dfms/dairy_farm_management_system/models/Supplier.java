package com.dfms.dairy_farm_management_system.models;

public class Supplier extends Personne {
    private int id_supplier;

    public Supplier(int id, String firstName, String lastName, char gender, String cin, String email, int phone, String adresse, int id1) {
        super(id, firstName, lastName, gender, cin, email, phone, adresse);
        this.id_supplier = id1;
    }

    @Override
    public int getId() {
        return id_supplier;
    }

    @Override
    public void setId(int id) {
        this.id_supplier = id;
    }
}
