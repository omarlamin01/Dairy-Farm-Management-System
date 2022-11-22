package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.models.Stock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private TableColumn<Stock, String> actions_col;

    @FXML
    private TableColumn<Stock, String> added_date_col;

    @FXML
    private TableColumn<Stock, String> availability_col;

    @FXML
    private ComboBox<String> export_combo;

    @FXML
    private TableColumn<Stock, String> id_col;

    @FXML
    private Button openAddNewEmployeeBtn;

    @FXML
    private TableColumn<Stock, String> product_name_col;

    @FXML
    private TableColumn<Stock, String> product_type_col;

    @FXML
    private TextField search_stock_input;

    @FXML
    private TableView<?> stock_table;

    @FXML
    void openAddProduct(MouseEvent event) throws IOException {
        openNewWindow("Add Product", "add_new_product");
    }
}