package com.dfms.dairy_farm_management_system.models;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.disconnect;
import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Race {

    private int id_race;
    private String name;

    public Race() {}

    public void setId(int id) {
        this.id_race = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id_race;
    }

    public String getName() {
        return name;
    }

    public Race(int id, String name) {
        this.id_race = id;
        this.name = name;
    }

    public boolean add() {
        String query = "INSERT INTO `races` (`name`) VALUES (?)";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            return statement.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }
}
