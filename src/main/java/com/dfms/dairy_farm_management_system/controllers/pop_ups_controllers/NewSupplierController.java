package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Client;
import com.dfms.dairy_farm_management_system.models.Supplier;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewSupplierController implements Initializable {
    @FXML
    TextField supplierName;
    @FXML
    TextField phoneNumberInput;
    @FXML
    ComboBox<String> typeCombo;
    @FXML
    TextField emailInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setTypeComboItems();
    }

    public void setTypeComboItems() {
        this.typeCombo.setItems(FXCollections.observableArrayList("Company", "Person"));
    }

    @FXML
    public void addNewClient(MouseEvent mouseEvent) {
        Supplier supplier = new Supplier();
        supplier.setName(this.supplierName.getText());
        supplier.setPhone(Integer.parseInt(this.phoneNumberInput.getText()));
        supplier.setEmail(this.emailInput.getText());
        supplier.setType(this.typeCombo.getValue());
        System.out.println(supplier.toString());

        ((Stage)(((Button)mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
