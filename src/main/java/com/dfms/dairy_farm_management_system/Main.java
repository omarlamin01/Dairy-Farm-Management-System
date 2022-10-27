package com.dfms.dairy_farm_management_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    public static Stage getStage() {
        //get current stage


        return new Stage();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main_layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        // stage.initStyle(StageStyle.UNDECORATED);
        // stage.setMaximized(true);
        stage.setTitle("Dairy Farm Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}