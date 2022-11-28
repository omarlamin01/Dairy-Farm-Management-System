package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class MilkSale implements Model{
    private int id;
    private int clientId;
    private String clientName;
    private Float quantity;
    private float price;
    private Date sale_date;
    private Timestamp created_at;
    private Timestamp updated_at;
    public MilkSale(){
        this.created_at = Timestamp.valueOf(LocalDateTime.now());
        this.updated_at = Timestamp.valueOf(LocalDateTime.now());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
        this.clientName = getClientName();
    }

    public String getClientName() {
        String query = "SELECT name FROM clients WHERE id = " + clientId;
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientName;
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

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
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

    @Override
    public boolean save() {
        String query = "INSERT INTO milk_sales (quantity, price, client_id, sale_date, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setFloat(1, quantity);
            statement.setFloat(2, price);
            statement.setInt(3, clientId);
            statement.setDate(4, sale_date);
            statement.setTimestamp(5, created_at);
            statement.setTimestamp(6, updated_at);

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean update() {
        String query = "UPDATE `milk_sales` SET " +
                  "`client_id` = '" + clientId + "', " +
                "`quantity` = '" + quantity + "', " +
                "`price` = '" + price + "', " +
                "`sale_date` = '" + sale_date + "', " +
                "`updated_at` = '" + Timestamp.valueOf(LocalDateTime.now()) + "'" +
                " WHERE `milk_sales`.`id` = " + id;


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
        String deleteQuery = "DELETE FROM `milk_sales` WHERE `milk_sales`.`id` = " + this.id;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
