package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Employee;
import com.dfms.dairy_farm_management_system.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class UpdateUserController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setRoleComboItems();
        updateBtn.setOnMouseClicked(mouseEvent -> {
            updateUser(mouseEvent);
        });
    }

    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection = DBConfig.getConnection();

    @FXML
    private TextField addressInput;

    @FXML
    private TextField cinInput;

    @FXML
    private TextField emailInput;

    @FXML
    private TextField firstNameInput;

    @FXML
    private TextField lastNameInput;

    @FXML
    private TextField phoneNumberInput;

    @FXML
    private ComboBox<String> roleCombo;

    @FXML
    private Button updateBtn;

    ObservableList<String> rolesList;

    public void updateUser(MouseEvent event) {
        if (inputsAreEmpty()) {
            displayAlert("Error", "Please fill all the fields", Alert.AlertType.ERROR);
            return;
        }
        String cin = cinInput.getText();
        String email = emailInput.getText();
        String phone = phoneNumberInput.getText();

        User user = getUser(cin);
        user.setFirstName(firstNameInput.getText());
        user.setLastName(lastNameInput.getText());
        user.setCin(cinInput.getText());
        user.setAdress(addressInput.getText());
        user.setEmail(emailInput.getText());
        user.setPhone(phoneNumberInput.getText());
        // TODO: don't know why selected item doesn't work
        user.setRole(getRoles().get(roleCombo.getValue()));
        if (user.update()) {
            displayAlert("Success", "Employee updated successfully", Alert.AlertType.INFORMATION);
            closePopUp(event);
        } else {
            displayAlert("Error", "Error while updating employee", Alert.AlertType.ERROR);
        }
    }

    private User getUser(String cin) {
        User user = new User();
        String query = "SELECT * FROM `users` WHERE cin = '" + cin.toUpperCase() + "' LIMIT 1";
        Connection con = getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setRole(rs.getInt("role"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setCin(rs.getString("cin"));
                user.setGender(rs.getString("gender"));
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        roleCombo.setValue(getRoleName(user.getRole()));
        return user;
    }

    //get current user data
    public void initData(User user) {
        emailInput.setText(user.getEmail());
        firstNameInput.setText(user.getFirstName());
        lastNameInput.setText(user.getLastName());
        addressInput.setText(user.getAddress());
        cinInput.setText(user.getCin());
        phoneNumberInput.setText(user.getPhone());
    }

    public String getRoleName(int id) {
        String roleName = "";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM `roles` WHERE `id` = '" + id + "' LIMIT 1").executeQuery();
            if (resultSet.next()) {
                roleName = resultSet.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roleName;
    }

    public void setRoleComboItems() {
        this.rolesList = FXCollections.observableArrayList();
        String[] names = getRoles().keySet().toArray(new String[0]);
        for (String name : names) {
            this.rolesList.add(name);
        }
        this.roleCombo.setItems(this.rolesList);
    }

    public boolean inputsAreEmpty() {
        if (this.firstNameInput.getText().isEmpty()
                || this.lastNameInput.getText().isEmpty()
                || this.emailInput.getText().isEmpty()
                || this.phoneNumberInput.getText().isEmpty()
                || this.addressInput.getText().isEmpty()
                || this.cinInput.getText().isEmpty())
            return true;
        return false;
    }
}