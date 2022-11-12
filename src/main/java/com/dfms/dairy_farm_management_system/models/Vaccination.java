package com.dfms.dairy_farm_management_system.models;

public class Vaccination {

    private int id_vaccin;

    private String name;
    private String dose;
    private String note;
    private String date;

    public int getId_vaccin() {
        return id_vaccin;
    }

    public void setId_vaccin(int id_vaccin) {
        this.id_vaccin = id_vaccin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
