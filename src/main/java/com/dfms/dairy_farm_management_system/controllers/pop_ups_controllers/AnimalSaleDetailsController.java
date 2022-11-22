package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AnimalSaleDetailsController implements Initializable {
    @FXML
    private Label Operationdate;

    @FXML
    private Label Price;

    @FXML
    private Label cow_id;

    @FXML
    private Label header;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void fetchAnimalSale(int id, String cow, Float price, String naame, Date Opdate)  {

        header.setText("Here's all the information about AnimalSale Num: " + id);
        cow_id.setText(cow+"");
        Price.setText(price+"");
        name.setText(naame+"");
        Operationdate.setText(Opdate+" ");

    }
}
