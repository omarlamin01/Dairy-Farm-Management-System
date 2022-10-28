package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

import static com.dfms.dairy_farm_management_system.helpers.Helper.centerScreen;

public class StockController {

    @FXML
    private Button addNewProductBtn;

    @FXML
    void openAddProduct(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("popups/add_new_product.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        // stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
        stage.setTitle("Add New Product");
        stage.setResizable(false);
        stage.setScene(scene);
        centerScreen(stage);
        stage.show();
    }
}