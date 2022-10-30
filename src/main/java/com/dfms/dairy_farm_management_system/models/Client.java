package com.dfms.dairy_farm_management_system.models;

public class Client {
    private String firstName;
    private String lastName;
    private int phone ;
    private String emai;

    public Client(String firstName, String lastName, int phone, String emai) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.emai = emai;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmai() {
        return emai;
    }

    public void setEmai(String emai) {
        this.emai = emai;
    }
}
