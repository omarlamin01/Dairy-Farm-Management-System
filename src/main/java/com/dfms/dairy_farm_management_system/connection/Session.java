package com.dfms.dairy_farm_management_system.connection;

import com.dfms.dairy_farm_management_system.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Session {
    private Statement st;
    private static final Connection con = DBConfig.getConnection();
    private static User user;
    private static String user_id;

    public Session(String user_id) {
        Session.user_id = user_id;
    }

    public static User getUser() {
        //get user from database
        User user = null;
        String query = "SELECT * FROM users WHERE id = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, user_id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("employee_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("cin"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getFloat("salary"),
                        rs.getDate("recruitment_date"),
                        rs.getString("contract_type"),
                        rs.getDate("updated_at"),
                        rs.getDate("created_at")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void setUser(User user) {
        Session.user = user;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        Session.user_id = user_id;
    }
}
