package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.Collections;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class NewUserController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setGenderComboItems();
        this.setRoleComboItems();
        validatePhoneInput(phoneNumberInput);
        validateEmailInput(emailInput);
    }

    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection = DBConfig.getConnection();

    @FXML
    TextField lastNameInput;
    @FXML
    TextField firstNameInput;
    @FXML
    TextField adressInput;
    @FXML
    TextField emailInput;
    @FXML
    TextField phoneNumberInput;
    @FXML
    ComboBox<String> genderCombo;
    @FXML
    ComboBox<String> roleCombo;

    ObservableList<String> rolesList;
    @FXML
    private TextField cininput;

    public void setGenderComboItems() {
        this.genderCombo.setItems(FXCollections.observableArrayList("Male", "Female"));
    }

    public void setRoleComboItems() {
        rolesList = FXCollections.observableArrayList();
        String[] names = getRoles().keySet().toArray(new String[0]);
        this.rolesList = FXCollections.observableArrayList();
        Collections.addAll(this.rolesList, names);
        this.roleCombo.setItems(this.rolesList);
    }

    @FXML
    public void addUser(MouseEvent mouseEvent) throws SQLException {
        this.connection = DBConfig.getConnection();

        if (inputesAreEmpty()) {
            displayAlert("Error", "Please fill all the fields", Alert.AlertType.ERROR);
            return;
        }

        String firstName = this.firstNameInput.getText();
        String lastName = this.lastNameInput.getText();
        String email = this.emailInput.getText();
        String phone = this.phoneNumberInput.getText();
        String adress = this.adressInput.getText();
        String cin = this.cininput.getText();
        String gender = this.genderCombo.getValue();
        String role = this.roleCombo.getValue();

        if (!isUnique(email, phone, cin)) {
            displayAlert("Error", "Email, phone or CIN already exists", Alert.AlertType.ERROR);
            return;
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAdress(adress);
        user.setCin(cin);
        user.setGender(gender);
        user.setRole(Integer.parseInt(role));

        //insert into user table
        if (user.save()) {
            displayAlert("Success", "User saved successfully", Alert.AlertType.INFORMATION);
            ((Node) (mouseEvent.getSource())).getScene().getWindow().hide();
        } else {
            displayAlert("Error", "Error while saving user", Alert.AlertType.ERROR);
        }
    }

    public int getRoleID(String role) {
        try {
            this.connection = DBConfig.getConnection();
            this.statement = this.connection.createStatement();
            ResultSet rs = this.statement.executeQuery("SELECT id FROM `roles` WHERE name = '" + role + "'");
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            displayAlert("Error", "Error while getting role id", Alert.AlertType.ERROR);
        }
        return 0;
    }

    //check if all inputs are filled
    public boolean inputesAreEmpty() {
        if (this.firstNameInput.getText().isEmpty()
                || this.lastNameInput.getText().isEmpty()
                || this.emailInput.getText().isEmpty()
                || this.phoneNumberInput.getText().isEmpty()
                || this.adressInput.getText().isEmpty()
                || this.cininput.getText().isEmpty()
                || this.genderCombo.getValue() == null
                || this.roleCombo.getValue() == null)
            return true;
        return false;
    }

    //check if email, cin and phone are unique
    public boolean isUnique(String email, String cin, String phone) {
        String query = "SELECT * FROM `users` WHERE email = '" + email + "' OR cin = '" + cin + "' OR phone = '" + phone + "'";
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
