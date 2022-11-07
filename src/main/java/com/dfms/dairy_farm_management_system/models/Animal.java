package com.dfms.dairy_farm_management_system.models;

import java.util.Date;

public class Animal  extends Model{
   private int id_animal;

   private Date birth_date;
   private  Date purchase_date;


   private int id_routine;
   private int id_race;

    public Animal(int id, Date birth_date, Date purchase_date,  int id_routine, int id_race) {
        this.id_animal = id;
        this.birth_date = birth_date;
        this.purchase_date = purchase_date;

        this.id_routine = id_routine;
        this.id_race = id_race;
    }

    public Animal() {

    }

    public void setId(int id) {
        this.id_animal = id;
    }



    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }


    public void setId_routine(int id_routine) {
        this.id_routine= id_routine;
    }

    public void setId_race(int id_race) {
        this.id_race = id_race;
    }





    public int getId() {
        return id_animal;
    }



    public Date getBirth_date() {
        return birth_date;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }



    public int getId_routine() {
        return id_routine;
    }

    public int getId_race() {
        return id_race;
    }


}
