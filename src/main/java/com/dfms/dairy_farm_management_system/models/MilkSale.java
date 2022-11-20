package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class MilkSale implements Model{
    private int id;
    private String id_client;
    private Float quantity;
    private float price;
    private LocalDate operationDate;
    public MilkSale(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
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

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public MilkSale(int id, String id_client, Float quantity, float price, LocalDate operationDate) {
        this.id = id;
        this.id_client = id_client;
        this.quantity = quantity;
        this.price = price;
        this.operationDate = operationDate;
    }

    @Override
    public boolean save() {
        String insertQuery = "INSERT INTO milk_sale (quantity,price,client_id,sale_date) VALUES (?,?,(select id from client where name ='"+getId_client()+"'),?)";
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setFloat(1, quantity);
            preparedStatement.setFloat(2, price);

            preparedStatement.setDate(3, Date.valueOf(operationDate));
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
        String deleteQuery = "DELETE FROM `milk_sale` WHERE `milk_sale`.`id` = " + this.id;
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
