package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

import com.dfms.dairy_farm_management_system.models.User;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class UserDetailsController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    private Label header;

    @FXML
    private Label address;

    @FXML
    private Label cin;

    @FXML
    private Label email;

    @FXML
    private Label first_name;

    @FXML
    private Label gender;

    @FXML
    private Label last_name;

    @FXML
    private Label phone;

    @FXML
    private Label role;

    public void initData(User user) {
        header.setText(user.getFirstName() + " " + user.getLastName());
        email.setText(user.getEmail());
        first_name.setText(user.getFirstName());
        last_name.setText(user.getLastName());
        address.setText(user.getAddress());
        cin.setText(user.getCin());
        phone.setText(user.getPhone());
        role.setText(getRole(user.getRole()));

        //TODO: get the role name from the database
    }

    public String getRole(int id) {
        String role = "";
        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM `roles` WHERE id = " + id);
            while (rs.next()) {
                role = rs.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }
}
