package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class NewPurchaseController implements Initializable {
    @FXML
    private Button add_update;

    @FXML
    private Label header;

    @FXML
    private Label key;

    @FXML
    private DatePicker operationDate;

    @FXML
    private TextField priceOfSale;

    @FXML
    private TextField quantityInput;

    @FXML
    private ComboBox<String> suppliersCombo;

    @FXML
    void addPurchase(MouseEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
