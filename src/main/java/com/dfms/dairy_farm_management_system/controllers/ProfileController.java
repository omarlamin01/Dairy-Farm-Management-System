package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.connection.Session;
import com.dfms.dairy_farm_management_system.models.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class ProfileController implements Initializable {
    @FXML
    private TextField address_input;

    @FXML
    private TextField cin_input;

    @FXML
    private TextField confirm_new_password_input;

    @FXML
    private TextField current_password_input;

    @FXML
    private TextField email_input;

    @FXML
    private TextField first_name_input;

    @FXML
    private TextField last_name_input;

    @FXML
    private TextField new_password_input;

    @FXML
    private TextField phone_input;

    @FXML
    private Button update_login_info_btn;

    @FXML
    private Button update_personal_info_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUserData();
        validatePhoneInput(phone_input);
    }

    public void getUserData() {
        User currentUser = Session.getCurrentUser();
        first_name_input.setText(currentUser.getFirstName());
        last_name_input.setText(currentUser.getLastName());
        cin_input.setText(currentUser.getCin());
        phone_input.setText(String.valueOf(currentUser.getPhone()));
        address_input.setText(currentUser.getAdress());
        email_input.setText(currentUser.getEmail());
        current_password_input.setText(null);
        new_password_input.setText(null);
        confirm_new_password_input.setText(null);
    }

    @FXML
    void updateLoginInfo(MouseEvent event) {
        User currentUser = Session.getCurrentUser();
        System.out.println("user password ==> " + currentUser.password);
        if (currentUser.validatePassword(current_password_input.getText())) {
            if (new_password_input.getText().equals(confirm_new_password_input.getText())) {
                currentUser.setEmail(email_input.getText());
                currentUser.setPassword(new_password_input.getText());
                if(currentUser.updatePassword()) {
                    displayAlert("Done", "Profile updated successfully", Alert.AlertType.INFORMATION);
                    getUserData();
                } else {
                    displayAlert("Error", "Some error heppened", Alert.AlertType.ERROR);
                }
            } else {
                displayAlert("Error", "New password doesn't match password confirm!", Alert.AlertType.ERROR);
            }
        } else {
            System.out.println("current ==> " + current_password_input.getText());
            System.out.println("encrpt ==> " + encryptPassword(current_password_input.getText()));
            displayAlert("Error", "Wrong password!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void updatePersonalInfo(MouseEvent event) {
        User currentUser = Session.getCurrentUser();
        currentUser.setFirstName(first_name_input.getText());
        currentUser.setLastName(last_name_input.getText());
        currentUser.setCin(cin_input.getText());
        currentUser.setPhone(phone_input.getText());
        currentUser.setAdress(address_input.getText());
        if(currentUser.update()) {
            displayAlert("Done", "Profile updated successfully", Alert.AlertType.INFORMATION);
            getUserData();
        } else {
            displayAlert("Error", "Some error heppened", Alert.AlertType.ERROR);
        }
    }
}
