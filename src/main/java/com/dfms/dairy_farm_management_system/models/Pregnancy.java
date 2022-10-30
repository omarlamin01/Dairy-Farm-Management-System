package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Pregnancy {
    private int id;
    private int id_animal;
    private Date start_date;
    private Date end_date;
    private String birth_status;
    private String pregnancy_type;

    public void setId(int id) {
        this.id = id;
    }

    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setBirth_status(String birth_status) {
        this.birth_status = birth_status;
    }

    public void setPregnancy_type(String pregnancy_type) {
        this.pregnancy_type = pregnancy_type;
    }

    public int getId() {
        return id;
    }

    public int getId_animal() {
        return id_animal;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public String getBirth_status() {
        return birth_status;
    }

    public String getPregnancy_type() {
        return pregnancy_type;
    }

    public Pregnancy(int id, int id_animal, Date start_date, Date end_date, String birth_status, String pregnancy_type) {
        this.id = id;
        this.id_animal = id_animal;
        this.start_date = start_date;
        this.end_date = end_date;
        this.birth_status = birth_status;
        this.pregnancy_type = pregnancy_type;
    }
}
