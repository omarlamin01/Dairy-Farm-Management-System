package com.dfms.dairy_farm_management_system;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static com.dfms.dairy_farm_management_system.helpers.Helper.centerScreen;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String first_view = "main_layout";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(first_view + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
        stage.setTitle("Dairy Farm Management System");
        stage.setScene(scene);
        centerScreen(stage);
        stage.show();

        //test mongo connection
        DBConfig.connect();
        DBConfig.getDb().getCollection("dfms");
        DBObject dbObject = new BasicDBObject();
        dbObject.put("name", "Dairy Farm Management System");
        dbObject.put("version", "1.0.0");
        dbObject.put("author", "Dinithi");
        dbObject.put("email", "example@gmail.com");

        DBConfig.getDb().getCollection("dfms").insert(dbObject);
    }

    public static void main(String[] args) {
        launch();
    }
}