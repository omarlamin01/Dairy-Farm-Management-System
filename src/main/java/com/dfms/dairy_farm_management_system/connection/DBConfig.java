package com.dfms.dairy_farm_management_system.connection;

import javafx.scene.control.Alert;

import java.sql.*;

import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class DBConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dairyfarm";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static Connection conn = null;


    // Connect to database
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        return conn;
    }

    // Disconnect from database
    public static void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    //Execute a query
    public static ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return resultSet;
    }
}
