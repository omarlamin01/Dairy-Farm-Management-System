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

import static com.dfms.dairy_farm_management_system.helpers.Helper.validateNumericInput;

public class NewProductController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validateNumericInput(product_quantity);
    }

    @FXML
    private TextField product_measure_unit;

    @FXML
    private TextField product_name;

    @FXML
    private TextField product_quantity;

    @FXML
    private TextField product_type;

    @FXML
    public void addProduct(MouseEvent mouseEvent) {
        Stock product = new Stock();
        product.setId(new Random().nextInt());
        product.setName(this.product_name.getText());
        product.setQuantity(Float.parseFloat(this.product_quantity.getText()));
        product.setType(this.product_type.getText());
        product.setUnit(this.product_measure_unit.getText());
        System.out.println(product.toString());

        ((Stage) (((Button) mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
