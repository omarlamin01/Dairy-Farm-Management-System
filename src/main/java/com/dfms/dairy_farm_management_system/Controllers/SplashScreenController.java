package com.dfms.dairy_farm_management_system.Controllers;

import com.dfms.dairy_farm_management_system.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {

    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;

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
                            switchToLoginPage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }

    private void switchToLoginPage() {
//        fxmlLoader = new FXMLLoader(Main.class.getResource("login_screen.fxml"));
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        try {
//            scene = new Scene(fxmlLoader.load());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        stage.setScene(scene);
//        stage.show();
    }
}