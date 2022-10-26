package com.dfms.dairy_farm_management_system.Controllers;

import com.dfms.dairy_farm_management_system.HelloApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {

    @FXML
    AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        splash();
    }

    protected void splash() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Switch to login
                            System.out.println("Switch to login");
                            
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }

    public void switchScene(ActionEvent event) throws IOException {
    }

}