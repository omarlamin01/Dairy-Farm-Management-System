package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Stock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class UpdateProductController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private int product_id;
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

    public void fetchProduct(Stock product) {

    }

    public Stock getProduct(int product_id) {
        Stock product = new Stock();
        String query = "SELECT * FROM `stocks` WHERE id = '" + product_id + "' LIMIT 1";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                product.setType(resultSet.getString("type"));
                product.setName(resultSet.getString("name"));
                product.setQuantity(resultSet.getFloat("quantity"));
                product.setUnit(resultSet.getString("unit"));
                product.setAvailability(resultSet.getString("availability"));
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        return product;
    }

    public void updateProduct(MouseEvent event) {
        if (inputsAreEmpty()) {
            displayAlert("Error", "Please fill all the fields", Alert.AlertType.ERROR);
            return;
        }

        Stock product = getProduct(this.product_id);
        product.setName(this.product_name.getText());
        product.setQuantity(Float.parseFloat(this.product_quantity.getText()));
        product.setUnit(this.mesure_unit_combo.getValue());
        product.setType(this.product_type_combo.getValue());

        if (product.getQuantity() > 0) {
            product.setAvailability("1");
        } else {
            product.setAvailability("0");
        }

        if (product.update()) {
            displayAlert("Success", "Employee updated successfully", Alert.AlertType.INFORMATION);
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } else {
            displayAlert("Error", "Error while updating employee", Alert.AlertType.ERROR);
        }
    }

    public boolean inputsAreEmpty() {
        return (this.product_name.getText().isEmpty()
                || this.product_quantity.getText().isEmpty()
                || this.mesure_unit_combo.getValue() == null
                || this.product_type_combo.getValue() == null);
    }
}
