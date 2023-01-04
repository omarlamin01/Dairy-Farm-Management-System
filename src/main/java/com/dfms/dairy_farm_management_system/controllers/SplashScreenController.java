package com.dfms.dairy_farm_management_system.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.dfms.dairy_farm_management_system.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.centerScreen;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class SplashScreenController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        splash();
    }

    @FXML
    private AnchorPane splash_screen;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private final int SPLASH_TIME = 2000;

    private void splash() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(SPLASH_TIME);
                } catch (Exception e) {
                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            switchToLoginPage();
                        } catch (Exception e) {
                            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                        }
                    }
                });
            }
        }.start();
    }

    private void switchToLoginPage() {
        fxmlLoader = new FXMLLoader(Main.class.getResource("login_screen.fxml"));
        stage = (Stage) splash_screen.getScene().getWindow();
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        centerScreen(stage);
        stage.setScene(scene);
        stage.show();
    }
}