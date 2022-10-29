package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

import static com.dfms.dairy_farm_management_system.helpers.Helper.centerScreen;

public class LoginController {
    @FXML
    private Circle close_btn;

    @FXML
    private TextField email_input;

    @FXML
    private Label forget_password;

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField password_input;

    @FXML
    private void login(MouseEvent event) {
        //switch to main layout
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main_layout.fxml"));
        Stage stage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        centerScreen(stage);
        stage.setTitle("Dairy Farm Management System");
        stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
        stage.setMaximized(true);
        stage.setScene(scene);
        ((Node) event.getSource()).getScene().getWindow().hide();
        stage.show();
    }

    @FXML
    private void exitApplication(MouseEvent event) {
        System.exit(0);
    }
}
