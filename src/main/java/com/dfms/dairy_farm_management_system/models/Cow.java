package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Cow
{
   private int id_cow;

    public Cow(int id_animal, String type, Date birth_date, Date purchase_date, Date created_at, Date updated_at, int id_routine, int id_race, int id_cow) {

        this.id_cow = id_cow;
    }

    public Cow(int id_cow) {
        this.id_cow = id_cow;
    }
}
