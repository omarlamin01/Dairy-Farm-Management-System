package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Client;
import com.dfms.dairy_farm_management_system.models.Supplier;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class NewSupplierController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setTypeComboItems();
        validatePhoneInput(phoneNumberInput);
    }

    @FXML
    TextField supplierName;
    @FXML
    TextField phoneNumberInput;
    @FXML
    ComboBox<String> typeCombo;
    @FXML
    TextField emailInput;

    public void setTypeComboItems() {
        this.typeCombo.setItems(FXCollections.observableArrayList("Company", "Person"));
    }

    @FXML
    public void addNewSupplier(MouseEvent mouseEvent) {
        Supplier supplier = new Supplier();

        supplier.setName(this.supplierName.getText());
        supplier.setPhone(this.phoneNumberInput.getText());
        supplier.setEmail(this.emailInput.getText());
        supplier.setType(this.typeCombo.getValue());

        if (supplier.save()) {
            closePopUp(mouseEvent);
            displayAlert("SUCCESS", "Supplier saved successfully.", Alert.AlertType.INFORMATION);
        } else {
            displayAlert("ERROR", "Some error happened while saving!", Alert.AlertType.ERROR);
        }

    }
}
