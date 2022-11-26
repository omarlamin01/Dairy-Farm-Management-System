package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class AnimalSale implements Model {
    private int id;
    private int clientId;
    private String clientName;
    private String animalId;
    private float price;
    private Date sale_date;
    private Timestamp created_at;
    private Timestamp updated_at;

    public AnimalSale() {
        this.updated_at = Timestamp.valueOf(LocalDateTime.now());
        this.created_at = Timestamp.valueOf(LocalDateTime.now());
    }

    public int getId() {
        return id;
    }

    public void setId(int id_sales) {
        this.id = id_sales;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getAnimalId() {
        return animalId;
    }

    public String getClientName() {
        String query = "SELECT `name` FROM `clients` WHERE `id` = " + clientId;
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
        this.clientName = getClientName();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getSale_date() {
        return sale_date;
    }

    public void setSale_date(Date sale_date) {
        this.sale_date = sale_date;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public boolean save() {
        String insertQuery = "INSERT INTO `animals_sales` (client_id, animal_id, price, sale_date, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setInt(1, clientId);
            preparedStatement.setString(2, animalId);
            preparedStatement.setFloat(3, price);
            preparedStatement.setDate(4, sale_date);
            preparedStatement.setTimestamp(5, created_at);
            preparedStatement.setTimestamp(6, updated_at);

            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update() {
        String query = "UPDATE `animals_sales` SET " +
                "`client_id` = '" + clientId + "', " +
                "`animal_id` = '" + animalId + "', " +
                "`price` = '" + price + "', " +
                "`sale_date` = '" + sale_date + "', " +
                "`updated_at` = '" + Timestamp.valueOf(LocalDateTime.now()) + "'" +
                " WHERE `animals_sales`.`id` = " + id;
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete() {
        String query = "DELETE FROM `animals_sales` WHERE `animals_sales`.`id` = " + this.id;
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
