package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Cow extends Animal
{
   private int id_cow;

    public Cow(int id_animal, String type, Date birth_date, Date purchase_date, Date created_at, Date updated_at, int id_routine, int id_race, int id_cow) {
        super(id_animal, type, birth_date, purchase_date, created_at, updated_at, id_routine, id_race);
        this.id_cow = id_cow;
    }

    public Cow(int id_cow) {
        this.id_cow = id_cow;
    }
}
