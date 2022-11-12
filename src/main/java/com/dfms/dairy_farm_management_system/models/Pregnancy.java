package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Pregnancy {
    private int id_pregnancy;
    private int id_cow;
    private Date start_date;
    private Date end_date;

    private String pregnancy_type;

    public void setId(int id) {
        this.id_pregnancy = id;
    }

    public void setId_cow(int id_cow) {
        this.id_cow = id_cow;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }


    public void setPregnancy_type(String pregnancy_type) {
        this.pregnancy_type = pregnancy_type;
    }

    public int getId() {
        return id_pregnancy;
    }

    public int getId_cow() {
        return id_cow;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }


    public String getPregnancy_type() {
        return pregnancy_type;
    }

    public Pregnancy(int id, int id_cow, Date start_date, Date end_date, String pregnancy_type) {
        this.id_pregnancy = id;
        this.id_cow = id_cow;
        this.start_date = start_date;
        this.end_date = end_date;

        this.pregnancy_type = pregnancy_type;
    }
}
