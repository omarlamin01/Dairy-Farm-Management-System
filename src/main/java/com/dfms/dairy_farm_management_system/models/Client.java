package com.dfms.dairy_farm_management_system.models;

public class Client extends Model {
    private int id_client;

    private String name;
    private String type;
    private String email;
    private int phone;

    public Client(int id_client, String name, String type, String email, int phone) {
        this.id_client = id_client;
        this.name = name;
        this.type = type;
        this.email = email;
        this.phone = phone;
    }

    public Client() {}

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
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
