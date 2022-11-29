package com.dfms.dairy_farm_management_system.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class PurchasesController  implements Initializable {
    @FXML
    private TableView<?> PurchaseTable;

    @FXML
    private TableColumn<?, ?> action_c;

    @FXML
    private TableColumn<?, ?> date_c;

    @FXML
    private ComboBox<?> export_combo;

    @FXML
    private Button openAddNewEmployeeBtn;

    @FXML
    private Button openAddNewPurchase;

    @FXML
    private TableColumn<?, ?> price_c;

    @FXML
    private TableColumn<?, ?> quantity_c;

    @FXML
    private ImageView refresh_table_table;

    @FXML
    private TextField search_stock_input;

    @FXML
    private TableColumn<?, ?> supplier_c;

    @FXML
    void openAddPurchase(MouseEvent event) {

    }

    @FXML
    void refreshTable(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
