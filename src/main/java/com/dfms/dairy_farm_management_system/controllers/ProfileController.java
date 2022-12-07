package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.connection.Session;
import com.dfms.dairy_farm_management_system.models.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class ProfileController implements Initializable {
    @FXML
    private TextField address_input;

    @FXML
    private TextField cin_input;


    @FXML
    private TextField email_input;

    @FXML
    private TextField first_name_input;

    @FXML
    private TextField last_name_input;

    @FXML
    private TextField phone_input;

    @FXML
    private Button update_login_info_btn;

    @FXML
    private Button update_personal_info_btn;

    @FXML
    private PasswordField new_password_input;

    @FXML
    private PasswordField confirm_new_password_input;

    @FXML
    private PasswordField current_password_input;

    @FXML
    private StackPane new_password_pane;

    @FXML
    private StackPane confirm_new_password_pane;

    @FXML
    private StackPane current_password_pane;

    @FXML
    private GridPane loginInfoGPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validatePhoneInput(phone_input);

        current_password_pane = createPasswordFieldWithEye("Current password", "current_password_input");
        new_password_pane = createPasswordFieldWithEye("New password", "new_password_input");
        confirm_new_password_pane = createPasswordFieldWithEye("Confirm new password", "confirm_new_password_input");

        current_password_input = (PasswordField) current_password_pane.getChildren().get(2);
        new_password_input = (PasswordField) new_password_pane.getChildren().get(2);
        confirm_new_password_input = (PasswordField) confirm_new_password_pane.getChildren().get(2);

        placePasswordFields();
        getUserData();
    }

    public void getUserData() {
        User currentUser = Session.getCurrentUser();
        first_name_input.setText(currentUser.getFirstName());
        last_name_input.setText(currentUser.getLastName());
        cin_input.setText(currentUser.getCin());
        phone_input.setText(String.valueOf(currentUser.getPhone()));
        address_input.setText(currentUser.getAdress());
        email_input.setText(currentUser.getEmail());
    }

    @FXML
    void updateLoginInfo(MouseEvent event) {
        User currentUser = Session.getCurrentUser();
        if (currentUser.validatePassword(current_password_input.getText())) {
            if (new_password_input.getText().equals(confirm_new_password_input.getText())) {
                currentUser.setEmail(email_input.getText());
                currentUser.setPassword(new_password_input.getText());
                if(currentUser.updatePassword()) {
                    displayAlert("Done", "Profile updated successfully", Alert.AlertType.INFORMATION);
                    getUserData();
                    resetPasswordsField();
                } else {
                    displayAlert("Error", "Some error heppened", Alert.AlertType.ERROR);
                }
            } else {
                displayAlert("Error", "New password doesn't match password confirm!", Alert.AlertType.ERROR);
            }
        } else {
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

    public StackPane createPasswordFieldWithEye(String promptext, String id) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(Region.USE_COMPUTED_SIZE);
        passwordField.getStyleClass().add("input");
        passwordField.getStyleClass().add("password_input");
        passwordField.setPromptText(promptext);
        passwordField.setId(id);

        TextField textField = new TextField();
        passwordField.textProperty().bindBidirectional(textField.textProperty());
        textField.setPrefHeight(Region.USE_COMPUTED_SIZE);
        textField.getStyleClass().add("input");
        textField.getStyleClass().add("password_input");
        textField.setPromptText(promptext);

        Image eyeImage = new Image(getClass().getResourceAsStream("/images/eye.png"));
        Image closedEyeImage = new Image(getClass().getResourceAsStream("/images/closed-eye.png"));

        ImageView eyeImageView = new ImageView(eyeImage);
        ImageView closedEyeImageView = new ImageView(closedEyeImage);

        eyeImageView.setCursor(Cursor.HAND);
        closedEyeImageView.setCursor(Cursor.HAND);

        eyeImageView.setFitHeight(32);
        eyeImageView.setFitWidth(32);

        closedEyeImageView.setFitHeight(32);
        closedEyeImageView.setFitWidth(32);
        closedEyeImageView.setStyle("-fx-padding: 5px");

        Button showPassword = new Button();
        Button hidePassword = new Button();

        showPassword.getStyleClass().add("btn-search");
        hidePassword.getStyleClass().add("btn-search");

        showPassword.setGraphic(eyeImageView);
        hidePassword.setGraphic(closedEyeImageView);

        textField.toBack();
        hidePassword.toBack();
        passwordField.toFront();
        showPassword.toFront();

        switch (id) {
            case "current_password_input":
                StackPane.setMargin(textField, new Insets(0, 0, 10, 0));
                StackPane.setMargin(passwordField, new Insets(0, 0, 10, 0));

                StackPane.setMargin(showPassword, new Insets(0, 10, 10, 0));
                StackPane.setAlignment(showPassword, Pos.CENTER_RIGHT);

                StackPane.setMargin(hidePassword, new Insets(0, 10, 10, 0));
                StackPane.setAlignment(hidePassword, Pos.CENTER_RIGHT);
                break;

            case "new_password_input":
                StackPane.setMargin(textField, new Insets(0, 10, 0, 0));
                StackPane.setMargin(passwordField, new Insets(0, 10, 0, 0));

                StackPane.setMargin(showPassword, new Insets(0, 20, 0, 0));
                StackPane.setAlignment(showPassword, Pos.CENTER_RIGHT);

                StackPane.setMargin(hidePassword, new Insets(0, 20, 0, 0));
                StackPane.setAlignment(hidePassword, Pos.CENTER_RIGHT);
                break;

            case "confirm_new_password_input":
                StackPane.setMargin(textField, new Insets(0, 0, 0, 0));
                StackPane.setMargin(passwordField, new Insets(0, 0, 0, 0));

                StackPane.setMargin(showPassword, new Insets(0, 10, 0, 0));
                StackPane.setAlignment(showPassword, Pos.CENTER_RIGHT);

                StackPane.setMargin(hidePassword, new Insets(0, 10, 0, 0));
                StackPane.setAlignment(hidePassword, Pos.CENTER_RIGHT);
                break;
        }

        showPassword.setOnMousePressed((event) -> {
            textField.toFront();
            hidePassword.toFront();
            passwordField.toBack();
            showPassword.toBack();
        });

        hidePassword.setOnMousePressed((event) -> {
            textField.toBack();
            hidePassword.toBack();
            passwordField.toFront();
            showPassword.toFront();
        });

        StackPane root = new StackPane(textField, hidePassword, passwordField, showPassword);

        return root;
    }

    public void placePasswordFields() {
        loginInfoGPane.add(current_password_pane, 1, 0);
        loginInfoGPane.addRow(1);
        loginInfoGPane.add(new_password_pane, 0, 1);
        loginInfoGPane.add(confirm_new_password_pane, 1, 1);
        loginInfoGPane.setHgap(10);
        loginInfoGPane.setVgap(10);
        resetPasswordsField();
    }

    public void resetPasswordsField() {
        current_password_input.setText(null);
        new_password_input.setText(null);
        confirm_new_password_input.setText(null);
    }
}
