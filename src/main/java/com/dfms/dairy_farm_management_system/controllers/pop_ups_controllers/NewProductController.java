package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Stock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class NewProductController implements Initializable {
    @FXML
    TextField productName;
    @FXML
    TextField productQuantity;
    @FXML
    TextField productType;
    @FXML
    TextField productMeasureUnit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void addProduct(MouseEvent mouseEvent) {
        Stock product = new Stock();
        product.setId(new Random().nextInt());
        product.setName(this.productName.getText());
        product.setQuantity(Float.parseFloat(this.productQuantity.getText()));
        product.setType(this.productType.getText());
        product.setUnit(this.productMeasureUnit.getText());
        System.out.println(product.toString());

        ((Stage)(((Button)mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
