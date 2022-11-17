package com.dfms.dairy_farm_management_system;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Date;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String first_view = "login_screen";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(first_view + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
        stage.setTitle("Dairy Farm Management System");
        stage.setScene(scene);
        centerScreen(stage);
        stage.show();

        String password = "abdellatif.laghjaj";
        String enc_pass = encryptPassword(password);
        System.out.println("Encrypted password: " + enc_pass);

        System.out.println(MD5(enc_pass, password));
    }

    public static void main(String[] args) {
        launch();
    }
}