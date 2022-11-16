package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class HealthStatus implements Model {
    private int id;
    private int animal_id;
    private int vaccin_id;
    private float weight;
    private float breathing;
    private float age;
    private LocalDate control_date;
    private String health_score;
    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getVaccin_id() {
        return vaccin_id;
    }

    public void setVaccin_id(int vaccin_id) {
        this.vaccin_id = vaccin_id;
    }

    public LocalDate getControl_date() {
        return control_date;
    }

    public void setControl_date(LocalDate control_date) {
        this.control_date = control_date;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAnimal_id(int animal_id) {
        this.animal_id = animal_id;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setBreathing(float breathing) {
        this.breathing = breathing;
    }

    public void setHealth_score(String health_score) {
        this.health_score = health_score;
    }

    public int getId() {
        return id;
    }

    public int getAnimal_id() {
        return animal_id;
    }

    public float getWeight() {
        return weight;
    }

    public float getBreathing() {
        return breathing;
    }

    public String getHealth_score() {
        return health_score;
    }


    @Override
    public boolean save() {
        String query = "INSERT INTO `health_status` (`animal_id`, `vaccine_id`, `weight`, `breading`, `age`, `control_date`) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, String.valueOf(this.animal_id));
            preparedStatement.setString(2, "1");
            preparedStatement.setString(3, String.valueOf(this.weight));
            preparedStatement.setString(4, String.valueOf(this.breathing));
            preparedStatement.setString(5, String.valueOf(this.age));
            preparedStatement.setString(6, String.valueOf(this.control_date));

            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }
}
