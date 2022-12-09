package com.dfms.dairy_farm_management_system.models;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Date;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.disconnect;
import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class Purchase implements Model {
    private int id;
    private int supplier_id;
    private String supplier_name;
    private int stock_id;
    private float price;
    private String product_name;
    private float quantity;
    private Date purchase_date;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Purchase() {
        this.updated_at = Timestamp.valueOf(LocalDateTime.now());
        this.created_at = Timestamp.valueOf(LocalDateTime.now());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
        this.supplier_name = getSupplier_name();
    }

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
        this.product_name = getProduct_name();
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }


    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
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


    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getSupplier_name() {
        String query = "SELECT `name` FROM `suppliers` WHERE `id` = " + supplier_id;
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return null;
    }

    public String getProduct_name() {
        String query = "SELECT `name` FROM `stocks` WHERE `id` = " + stock_id;
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return null;
    }

    @Override
    public boolean save() {
        String insertQuery = "INSERT INTO `purchases` (supplier_id,quantity, stock_id, price, purchase_date, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?,?)";
        updateQuantity();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setInt(1, supplier_id);
            preparedStatement.setFloat(2, quantity);
            preparedStatement.setInt(3, stock_id);
            preparedStatement.setFloat(4, price);
            preparedStatement.setDate(5, (java.sql.Date) purchase_date);
            preparedStatement.setTimestamp(6, created_at);
            preparedStatement.setTimestamp(7, updated_at);
            return preparedStatement.executeUpdate() != 0;


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            disconnect();
        }
    }

    public boolean updateQuantity() {
        String query = "Update stocks set quantity=quantity + '" + quantity + "' where id=  '" + stock_id + "'";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }

    @Override
    public boolean update() {
        String query = "UPDATE `purchases` SET " +
                "`supplier_id` = '" + supplier_id + "', " +
                "`quantity` = '" + quantity + "', " +
                "`stock_id` = '" + stock_id + "', " +
                "`price` = '" + price + "', " +
                "`purchase_date` = '" + purchase_date + "', " +
                "`updated_at` = '" + Timestamp.valueOf(LocalDateTime.now()) + "'" +
                " WHERE `purchases`.`id` = " + id;
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }

    @Override
    public boolean delete() {
        String query = "DELETE FROM `purchases` WHERE `purchases`.`id` = " + this.id;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            disconnect();
        }
    }


}

