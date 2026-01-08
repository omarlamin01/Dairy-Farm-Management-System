package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class NewRoleController {

    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection = DBConfig.getConnection();

    @FXML
    private Label Add_Update;

    @FXML
    private Label Head;

    @FXML
    private TextField role_name;

    @FXML
    void addNewRole(MouseEvent event) {
        String role_name = this.role_name.getText();
        if (!isUnique(role_name)) {
            displayAlert("Error", "Role name already exists", Alert.AlertType.ERROR);
            return;
        } else if (role_name.isEmpty()) {
            displayAlert("Error", "Please fill all the fields", Alert.AlertType.ERROR);
        } else {
            Role role = new Role();
            role.setName(role_name);
            if (role.save()) {
                displayAlert("Success", "Role added successfully", Alert.AlertType.INFORMATION);
            } else {
                displayAlert("Error", "Something went wrong", Alert.AlertType.ERROR);
            }
        }
    }

    public boolean isUnique(String name) {
        String query = "SELECT * FROM `roles` WHERE name = '" + name + "'";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return false;
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        return true;
    }
}
