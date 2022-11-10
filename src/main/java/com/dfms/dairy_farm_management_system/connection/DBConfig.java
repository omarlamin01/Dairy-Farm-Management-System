package com.dfms.dairy_farm_management_system.connection;

import com.dfms.dairy_farm_management_system.models.Employee;

import java.util.ArrayList;
import java.sql.*;

public class DBConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dairyfarm";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static Connection conn = null;
    private static String current_user = null;


    // Connect to database
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return conn;
    }

    // Disconnect from database
    public static void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Disconnected from database");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
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

    public static void setCurrentUser(String id) {
        current_user = id;
    }

    public static String getCurrentUser() {
        return current_user;
    }
}
