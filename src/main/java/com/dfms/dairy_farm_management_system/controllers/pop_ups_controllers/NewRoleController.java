package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

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
            try {
                String query = "INSERT INTO roles (name) VALUES (?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, role_name);
                preparedStatement.executeUpdate();
                displayAlert("Success", "Role added successfully", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
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