package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class MilkCollection  implements Model{
    private int id;
    private String cow_id;
    private float quantity;
    private String period;
    private Date collection_date;

    public MilkCollection( String cow_id,float quantity,String period ,Date d) {
        this.cow_id = cow_id;
        this.quantity = quantity;
        this.period = period;
        this.collection_date=d;
    }
    public MilkCollection(String period ,float quantity, String cow_id) {
        this.cow_id = cow_id;
        this.quantity = quantity;
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public MilkCollection() {

    }

    public MilkCollection(int id, String id_cow, float quantity, Date collection_date) {
        this.id = id;
        this.cow_id = id_cow;
        this.quantity = quantity;
        this.collection_date = collection_date;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setCow_id(String id_cow) {
        this.cow_id = id_cow;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setCollection_date(Date collection_date) {
        this.collection_date = collection_date;
    }

    public int getId() {
        return id;
    }

    public String getCow_id() {
        return cow_id;
    }

    public float getQuantity() {
        return quantity;
    }

    public Date getCollection_date() {
        return collection_date;
    }
    @Override
    public boolean save() {
        String insertQuery = "INSERT INTO milk_collection (period,quantity,cow_id) VALUES (?,?,?)";
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, period);
            preparedStatement.setFloat(2, quantity);
            preparedStatement.setString(3, cow_id);
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
        String deleteQuery = "DELETE FROM `milk_collection` WHERE `milk_collection`.`id` = " + this.id;
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


