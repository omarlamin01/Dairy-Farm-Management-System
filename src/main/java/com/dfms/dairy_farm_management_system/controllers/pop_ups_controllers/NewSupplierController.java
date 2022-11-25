package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Client;
import com.dfms.dairy_farm_management_system.models.Supplier;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;
import static com.dfms.dairy_farm_management_system.helpers.Helper.validatePhoneInput;

public class NewSupplierController implements Initializable {

    @FXML
    private Label add_update;

    @FXML
    private Label head;

    @FXML
    private TextField SupplierName;

    @FXML
    private TextField phoneNumberInput;

    @FXML
    private ComboBox<String> typeCombo;

    @FXML
    private TextField emailInput;

    @FXML
    private Button add_supplier_btn;
    private int supplier_ID;
    private boolean update;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setTypeComboItems();
        validatePhoneInput(phoneNumberInput);
    }


    public void setTypeComboItems() {
        this.typeCombo.setItems(FXCollections.observableArrayList("Company", "Person"));
    }

    @FXML
    public void addNewSupplier(MouseEvent mouseEvent) {
        Supplier supplier = new Supplier();
        if (this.update) {
            supplier.setId(this.supplier_ID);
            supplier.setNameSupplier(this.SupplierName.getText());
            supplier.setPhoneSupplier(this.phoneNumberInput.getText());
            supplier.setEmailSupplier(this.emailInput.getText());
            supplier.setTypeSupplier(this.typeCombo.getValue());
            if (supplier.update()) {
                displayAlert("Success", "Supplier updated successfully.", Alert.AlertType.INFORMATION);
                clear();
            } else {
                displayAlert("Warning", "Supplier not updated", Alert.AlertType.WARNING);
            }
        }else{
            supplier.setNameSupplier(this.SupplierName.getText());
            supplier.setPhoneSupplier(this.phoneNumberInput.getText());
            supplier.setEmailSupplier(this.emailInput.getText());
            supplier.setTypeSupplier(this.typeCombo.getValue());

        if (supplier.save()) {
            displayAlert("SUCESS", "Supplier added successfully.", Alert.AlertType.INFORMATION);
            this.clear();
        } else {
            displayAlert("ERROR", "Some error happened while saving!", Alert.AlertType.ERROR);
        }
    }}
    public void fetchSupplier(Supplier supplier) {
        this.supplier_ID = supplier.getId();
        head.setText("Update Supplier Num: " +supplier.getId());
        SupplierName.setText(supplier.getNameSupplier());
        emailInput.setText(supplier.getEmailSupplier());
        phoneNumberInput.setText(supplier.getPhoneSupplier());
        typeCombo.setValue(supplier.getTypeSupplier());
        add_supplier_btn.setText("Update");
        add_update.setText("Update");

    }
    private void clear() {
        typeCombo.getSelectionModel().clearSelection();
        SupplierName.setText("");
        phoneNumberInput.setText("");
        emailInput.setText("");


    }
    public void setUpdate(boolean b) {
        this.update = b;
    }
}
