package com.dfms.dairy_farm_management_system.Controllers;

<<<<<<<< HEAD:src/main/java/com/dfms/dairy_farm_management_system/Controllers/SplashController.java
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
========
import com.dfms.dairy_farm_management_system.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
>>>>>>>> abdellatif:src/main/java/com/dfms/dairy_farm_management_system/Controllers/SplashScreenController.java

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

<<<<<<<< HEAD:src/main/java/com/dfms/dairy_farm_management_system/Controllers/SplashController.java
public class SplashController implements Initializable {

    @FXML
    AnchorPane anchorPane;
========
public class SplashScreenController implements Initializable {

    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
>>>>>>>> abdellatif:src/main/java/com/dfms/dairy_farm_management_system/Controllers/SplashScreenController.java

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
<<<<<<<< HEAD:src/main/java/com/dfms/dairy_farm_management_system/Controllers/SplashController.java
                            // Switch to login
                            System.out.println("Switch to login");
========
                            switchToLoginPage();
>>>>>>>> abdellatif:src/main/java/com/dfms/dairy_farm_management_system/Controllers/SplashScreenController.java
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }

<<<<<<<< HEAD:src/main/java/com/dfms/dairy_farm_management_system/Controllers/SplashController.java
    public void switchScene(ActionEvent event) throws IOException {
    }

========
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
>>>>>>>> abdellatif:src/main/java/com/dfms/dairy_farm_management_system/Controllers/SplashScreenController.java
}