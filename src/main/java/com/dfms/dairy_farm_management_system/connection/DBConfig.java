package com.dfms.dairy_farm_management_system.connection;

import java.util.ArrayList;
import java.sql.*;

public class DBConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dairyfarm";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static Connection conn = null;

    public static Connection getConnection() {
        return conn;
    }

    // Connect to database
    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
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
}
