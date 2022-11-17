package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class AnimalSale  implements Model{

    private int id;


    private String id_client;

    private String id_animal;
    private float price;
    private LocalDate operationDate;

    public AnimalSale() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id_sales) {
        this.id = id_sales;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }





    public String getId_animal() {
        return id_animal;
    }

    public void setId_animal(String id_animal) {
        this.id_animal = id_animal;
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

    public AnimalSale(int id_sales, String id_client, String id_animal, float price, LocalDate operationDate) {
        this.id= id_sales;
        this.id_client = id_client;
        this.id_animal = id_animal;
        this.price = price;
        this.operationDate = operationDate;
    }
    @Override
    public boolean save() {
        String insertQuery = "INSERT INTO animal_sale (animal_id,price,client_id,sale_date) VALUES (?,?,(select id from client where name ='"+getId_client()+"'),?)";
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, id_animal);
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
        String deleteQuery = "DELETE FROM `animal_sale` WHERE `animal_sale`.`id` = " + this.id;
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
