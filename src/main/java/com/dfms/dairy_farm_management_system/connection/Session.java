package com.dfms.dairy_farm_management_system.connection;

import com.dfms.dairy_farm_management_system.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class Session {
    private Statement st;
    private static final Connection con = DBConfig.getConnection();
    private static User current_user;
    private static String current_user_id;

    public Session(String user_id) {
        Session.current_user_id = user_id;
    }

    public static User getCurrentUser() {
        //get user from database
        String query = "SELECT * FROM `users` WHERE id = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, "1");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                current_user = new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("cin"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getFloat("salary"),
                        new Date(),
                        "CDD",
                        rs.getDate("updated_at"),
                        rs.getDate("created_at")
                );
                current_user.setId(1);
                current_user.setRoleId(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return current_user;
    }

    public static void logoutUser() {
        current_user = null;
        current_user_id = null;
    }

    public static void setCurrentUser(User user) {
        Session.current_user = user;
    }

    public String getCurrentUserId() {
        return current_user_id;
    }

    public void setCurrentUserId(String user_id) {
        Session.current_user_id = user_id;
    }
}
