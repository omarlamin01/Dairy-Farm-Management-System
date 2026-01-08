package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Stock;
import java.net.URL;
import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class NewProductController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboBoxes();
        validateNumericInput(product_quantity);
    }

    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection = DBConfig.getConnection();

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
            product.setAvailability(true);
        } else {
            product.setAvailability(false);
        }

        product.setUnit(this.mesure_unit_combo.getValue());

        //save product to database
        if (product.save()) {
            displayAlert("Success", "Product added successfully", Alert.AlertType.INFORMATION);
            clearInputs();
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
        return (
            this.product_name.getText().isEmpty() ||
            this.product_quantity.getText().isEmpty() ||
            this.mesure_unit_combo.getValue() == null ||
            this.product_type_combo.getValue() == null
        );
    }

    public void clearInputs() {
        this.product_name.clear();
        this.product_quantity.clear();
        this.mesure_unit_combo.setValue("Mesure unit");
        this.product_type_combo.setValue("Product type");
    }
}
