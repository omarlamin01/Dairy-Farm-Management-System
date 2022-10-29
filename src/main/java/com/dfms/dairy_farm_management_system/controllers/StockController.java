package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.models.Product;
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
    private TableColumn<Product, ?> actionsCol;

    @FXML
    private TableColumn<Product, String> addedDateCol;

    @FXML
    private TableColumn<Product, Boolean> availabilityCol;

    @FXML
    private TableColumn<Product, Integer> productIDCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, String> productTypeCol;

    @FXML
    private TableView<Product> stockTable;

    @FXML
    private TableView<Product> stockTableView;

    @FXML
    void openAddProduct(MouseEvent event) throws IOException {
        //chi t5rbi9a
    }
}