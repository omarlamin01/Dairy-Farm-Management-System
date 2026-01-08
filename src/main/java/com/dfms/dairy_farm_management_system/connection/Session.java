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
