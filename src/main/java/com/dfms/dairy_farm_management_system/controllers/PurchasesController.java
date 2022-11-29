package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.models.Purchase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class PurchasesController  implements Initializable {
    @FXML
    private TableView<Purchase> PurchaseTable;

    @FXML
    private TableColumn<Purchase,String> action_c;

    @FXML
    private TableColumn<Purchase, Date> date_c;

    @FXML
    private ComboBox<String> export_combo;

    @FXML
    private Button openAddNewEmployeeBtn;

    @FXML
    private Button openAddNewPurchase;

    @FXML
    private TableColumn<Purchase,Float> price_c;

    @FXML
    private TableColumn<Purchase,Float> quantity_c;

    @FXML
    private ImageView refresh_table_table;

    @FXML
    private TextField search_stock_input;

    @FXML
    private TableColumn<Purchase, String> supplier_c;

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
