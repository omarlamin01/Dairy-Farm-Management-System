package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Bull extends Animal{
    private int id_bull;

    public Bull(int id, Date birth_date, Date purchase_date, int id_routine, int id_race, int id_bull) {
        super(id, birth_date, purchase_date, id_routine, id_race);
        this.id_bull = id_bull;
    }

    public int getId_bull() {
        return id_bull;
    }

    public void setId_bull(int id_bull) {
        this.id_bull = id_bull;
    }
}
