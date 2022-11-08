package com.dfms.dairy_farm_management_system.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.validatePhoneInput;

public class ProfileController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validatePhoneInput(phone_input);
    }

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

    @FXML
    void updateLoginInfo(MouseEvent event) {

    }

    @FXML
    void updatePersonalInfo(MouseEvent event) {

    }
}
