package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Cow extends Animal
{
   private int id_cow;

    public Cow(int id, Date birth_date, Date purchase_date, int id_regime, int id_race, int id1) {
        super(id, birth_date, purchase_date, id_regime, id_race);
        this.id_cow= id1;
    }

    @Override
    public int getId() {
        return id_cow;
    }

    @Override
    public void setId(int id) {
        this.id_cow= id;
    }
}
