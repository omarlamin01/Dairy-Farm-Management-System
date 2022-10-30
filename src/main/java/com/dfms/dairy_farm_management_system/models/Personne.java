package com.dfms.dairy_farm_management_system.models;

public class Personne extends Model{
    private int id_personne;
    private String  firstName;
    private String lastName;
    private char gender;
    private String cin;
    private String email;
    private int phone;
    private String adresse;

    public void setId(int id) {
        this.id_personne = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getId() {
        return id_personne;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public char getGender() {
        return gender;
    }

    public String getCin() {
        return cin;
    }

    public String getEmail() {
        return email;
    }

    public int getPhone() {
        return phone;
    }

    public String getAdresse() {
        return adresse;
    }

    public Personne(int id, String firstName, String lastName, char gender, String cin, String email, int phone, String adresse) {
        this.id_personne= id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.cin = cin;
        this.email = email;
        this.phone = phone;
        this.adresse = adresse;
    }
}
