package com.dfms.dairy_farm_management_system.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.openNewWindow;

public class StockController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }




    @FXML
    void openAddProduct(MouseEvent event) throws IOException {
        openNewWindow("Add Product", "add_new_product");
    }
}