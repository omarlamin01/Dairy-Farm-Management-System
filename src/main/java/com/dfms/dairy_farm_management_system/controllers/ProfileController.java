package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.models.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.validatePhoneInput;

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
        User currentUser = new User(1, "lamine", "omar", 'M', "JT90918", "omarlamin272@gmail.com", "0616944666", "Taroudant", 5000, new Date(), "CDD", new Date(), new Date());
        first_name_input.setText(currentUser.getFirstName());
        last_name_input.setText(currentUser.getLastName());
        cin_input.setText(currentUser.getCin());
        phone_input.setText(String.valueOf(currentUser.getPhone()));
        address_input.setText(currentUser.getAddress());
        email_input.setText(currentUser.getEmail());
    }

    @FXML
    void updateLoginInfo(MouseEvent event) {
        User currentUser = new User(1, "lamine", "omar", 'M', "JT90918", "omarlamin272@gmail.com", "0616944666", "Taroudant", 5000, new Date(), "CDD", new Date(), new Date());
        currentUser.setPassword("samepasswordeverywhere");
        if (currentUser.getPassword().equals(current_password_input.getText())) {
            if (new_password_input.getText().equals(confirm_new_password_input.getText())) {
                currentUser.setEmail(email_input.getText());
                currentUser.setPassword(new_password_input.getText());
                currentUser.update();
            } else {
                System.out.println("New password doesn't match password confirm!");
            }
        } else {
            System.out.println("Wrong password!");
        }
    }

    @FXML
    void updatePersonalInfo(MouseEvent event) {
        User currentUser = new User(1, "lamine", "omar", 'M', "JT90918", "omarlamin272@gmail.com", "0616944666", "Taroudant", 5000, new Date(), "CDD", new Date(), new Date());
        currentUser.setFirstName(first_name_input.getText());
        currentUser.setLastName(last_name_input.getText());
        currentUser.setCin(cin_input.getText());
        currentUser.setPhone(phone_input.getText());
        currentUser.setAddress(address_input.getText());
        currentUser.update();
        System.out.println(currentUser);
    }
}
