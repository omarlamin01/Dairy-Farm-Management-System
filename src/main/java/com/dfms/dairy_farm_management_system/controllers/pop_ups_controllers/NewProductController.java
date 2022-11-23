package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Availability;
import com.dfms.dairy_farm_management_system.models.Stock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class NewProductController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboBoxes();
        validateNumericInput(product_quantity);
    }

    @FXML
    private ComboBox<String> mesure_unit_combo;

    @FXML
    private TextField product_name;

    @FXML
    private TextField product_quantity;

    @FXML
    private ComboBox<String> product_type_combo;

    @FXML
    public void addProduct(MouseEvent mouseEvent) {
        if (inputsAreEmpty()) {
            displayAlert("Error", "Please fill all the fields", Alert.AlertType.ERROR);
            return;
        }

        Stock product = new Stock();
        product.setId(new Random().nextInt());
        product.setName(this.product_name.getText());
        product.setQuantity(Float.parseFloat(this.product_quantity.getText()));
        product.setUnit(this.mesure_unit_combo.getValue());
        product.setType(this.product_type_combo.getValue());

        //check availability of product
        if (product.getQuantity() > 0) {
            product.setAvailability(Availability.AVAILABLE);
        } else {
            product.setAvailability(Availability.NOT_AVAILABLE);
        }

        product.setUnit(this.mesure_unit_combo.getValue());

        //save product to database
        if (product.save()) {
            displayAlert("Success", "Product added successfully", Alert.AlertType.INFORMATION);
            closeWindow(mouseEvent);
        } else {
            displayAlert("Error", "An error occurred while adding the product", Alert.AlertType.ERROR);
        }

        System.out.println(product.toString());
    }

    public void initializeComboBoxes() {
        this.product_type_combo.getItems().addAll("Machine", "Vaccine", "Feed", "Drug");
        this.mesure_unit_combo.getItems().addAll("Kg", "Litre", "Unit");
    }

    public boolean inputsAreEmpty() {
        return this.product_name.getText().isEmpty() || this.product_quantity.getText().isEmpty() || this.mesure_unit_combo.getValue().isEmpty() || this.product_type_combo.getValue().isEmpty();
    }
}
