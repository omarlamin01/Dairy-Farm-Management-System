package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class PurchaseDetailsController implements Initializable {
    @FXML
    private Label Operationdate;

    @FXML
    private Label Price;

    @FXML
    private Label header;

    @FXML
    private Label id;

    @FXML
    private Label id1;

    @FXML
    private Label item;

    @FXML
    private Label name;

    @FXML
    private Label quantity;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void fetchPurchase(int id, String product,Float quantityy, Float price, String naame, Date Opdate)  {

        header.setText("Here's all the information about Purchase Num: " + id);
        item.setText(product+"");
        Price.setText(price+"");
        quantity.setText(quantityy+"");

        name.setText(naame+"");
        Operationdate.setText(Opdate+" ");

    }
}
