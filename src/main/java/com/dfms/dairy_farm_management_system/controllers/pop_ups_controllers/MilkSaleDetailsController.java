package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class MilkSaleDetailsController implements Initializable {

    @FXML
    private Label Operationdate;

    @FXML
    private Label Price;

    @FXML
    private Label quantity;

    @FXML
    private Label header;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void fetchMilkSale(int id, Float quantityy, Float price, String naame, Date Opdate) {
        header.setText("Here's all the information about MilkSale Num: " + id);
        quantity.setText(quantityy + "");
        Price.setText(price + "");
        name.setText(naame + "");
        Operationdate.setText(Opdate + " ");
    }
}
