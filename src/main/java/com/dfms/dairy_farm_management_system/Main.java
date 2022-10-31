package com.dfms.dairy_farm_management_system;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.net.UnknownHostException;

import static com.mongodb.client.model.Filters.eq;

import static com.dfms.dairy_farm_management_system.helpers.Helper.centerScreen;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String first_view = "main_layout";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(first_view + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
        stage.setTitle("Dairy Farm Management System");
        stage.setScene(scene);
        centerScreen(stage);
        stage.show();
    }

    public static void main(String[] args) throws UnknownHostException {
        //launch();

        //test mongo connection
        //DBConfig.connect();
        // DBConfig.getDb().getCollection("dfms");
        // DBObject dbObject = new BasicDBObject();
        // dbObject.put("name", "Dairy Farm Management System");
        // dbObject.put("version", "1.0.0");
        // dbObject.put("author", "Dinithi");
        // dbObject.put("email", "example@gmail.com");

        // DBConfig.getDb().getCollection("dfms").insert(dbObject);

        // DBCollection users = DBConfig.getDb().getCollection("users");
        // DBObject dbObject = new BasicDBObject();
        // dbObject.put("_id", 130);
        // dbObject.put("full_name", "Omar Lamine");
        // dbObject.put("age", 21);
        // dbObject.put("role", "admin");
        // users.insert(dbObject);

        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> collection = database.getCollection("users");
            Bson projectionFields = Projections.fields(
                    Projections.include("role", "age"),
                    Projections.excludeId());
            Document doc = collection.find(eq("role", "admin"))
                    .sort(Sorts.descending("age"))
                    .first();
            if (doc == null) {
                System.out.println("No results found.");
            } else {
                System.out.println(doc.toJson());
            }
        }
    }
}