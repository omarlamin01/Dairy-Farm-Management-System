package com.dfms.dairy_farm_management_system.models;

public class Client extends Personne {
    private int id_client;

    public Client(int id, String firstName, String lastName, char gender, String cin, String email, int phone, String adresse, int id1) {
        super(id, firstName, lastName, gender, cin, email, phone, adresse);
        this.id_client = id1;
    }

    @Override
    public int getId() {
        return id_client;
    }

    @Override
    public void setId(int id) {
        this.id_client = id;
    }
}
