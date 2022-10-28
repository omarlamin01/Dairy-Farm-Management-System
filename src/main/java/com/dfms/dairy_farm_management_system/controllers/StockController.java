package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.centerScreen;
import static com.dfms.dairy_farm_management_system.helpers.Helper.openNewWindow;

public class StockController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // add data to table
    }


    @FXML
    private TableColumn<?, ?> actionsCol;

    @FXML
    private TableColumn<?, ?> addedDateCol;

    @FXML
    private TableColumn<?, ?> availabilityCol;

    @FXML
    private TableColumn<?, ?> productIDCol;

    @FXML
    private TableColumn<?, ?> productNameCol;

    @FXML
    private TableColumn<?, ?> productTypeCol;

    @FXML
    private TableView<?> stockTable;


    @FXML
    private TableView stockTableView;

    @FXML
    void openAddProduct(MouseEvent event) throws IOException {
        openNewWindow("Add Product", "add_new_product");
    }
}