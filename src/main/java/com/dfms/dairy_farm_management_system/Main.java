package com.dfms.dairy_farm_management_system;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
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
        String first_view = "splash_screen";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(first_view + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.initStyle(StageStyle.TRANSPARENT);
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

        DBObject dbObject1 = new BasicDBObject();
        dbObject1.put("name", "Ecommerce");
        dbObject1.put("version", "1.0.0");
        dbObject1.put("author", "Abdellatif");
        dbObject1.put("email", "abdelatiflaghjaj@gmail.com");

        DBObject dbObject2 = new BasicDBObject();

        dbObject2.put("name", "Clinic Management System");
        dbObject2.put("version", "1.0.0");
        dbObject2.put("author", "Omar");
        dbObject2.put("email", "omarlamine@gmail.com");

        //insert multiple documents
        DBConfig.getDb().getCollection("dfms").insert(dbObject);
        DBConfig.getDb().getCollection("dfms").insert(dbObject1);
        DBConfig.getDb().getCollection("dfms").insert(dbObject2);


        //get data
        System.out.println("===================================");
        System.out.println("Get all data from dfms collection");
        System.out.println("===================================");
        DBCursor cursor = DBConfig.getDb().getCollection("dfms").find();
        while (cursor.hasNext()) {
            if (cursor.next() != null) {
                //printing the whole document
                System.out.println(cursor.curr());
                //convert document to object and access fields
                DBObject obg = cursor.curr();
                System.out.println(obg.get("name"));
            }
        }

        //get specific data
        System.out.println("===================================");
        System.out.println("Get specific data from MongoDB");
        System.out.println("===================================");
        DBObject query = new BasicDBObject();
        query.put("name", "Clinic Management System");
        DBCursor cursor1 = DBConfig.getDb().getCollection("dfms").find(query);
        while (cursor1.hasNext()) {
            if (cursor1.next() != null) {
                System.out.println(cursor1.curr());
            }
        }

        //update data
        System.out.println("===================================");
        System.out.println("Update data in MongoDB");
        System.out.println("===================================");
        DBObject query1 = new BasicDBObject();
        query1.put("name", "Clinic Management System");
        DBObject newDocument = new BasicDBObject();
        newDocument.put("name", "Clinic Management System v999");
        newDocument.put("version", "1.0.1");
        newDocument.put("author", "Omar Updated");
        newDocument.put("email", "omar_updated@gmail.com");
        DBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);
        DBConfig.getDb().getCollection("dfms").update(query1, updateObj);


        //get updated data
        System.out.println("===================================");
        System.out.println("Get updated data from MongoDB");
        System.out.println("===================================");
        DBObject query2 = new BasicDBObject();
        query2.put("name", "Clinic Management System v999");
        DBCursor cursor2 = DBConfig.getDb().getCollection("dfms").find(query2);
        while (cursor2.hasNext()) {
            if (cursor2.next() != null) {
                System.out.println(cursor2.curr());
            }
        }

        //delete data
        System.out.println("===================================");
        System.out.println("Delete data from MongoDB");
        System.out.println("===================================");
        DBObject query3 = new BasicDBObject();
        query3.put("name", "Clinic Management System v999");
        DBConfig.getDb().getCollection("dfms").remove(query3);


        //get data after delete
        System.out.println("===================================");
        System.out.println("Get data after delete from MongoDB");
        System.out.println("===================================");
        DBCursor cursor3 = DBConfig.getDb().getCollection("dfms").find();
        while (cursor3.hasNext()) {
            if (cursor3.next() != null) {
                System.out.println(cursor3.curr());
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}