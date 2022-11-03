package com.dfms.dairy_farm_management_system.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.openNewWindow;

public class ClientsSuppliersController implements Initializable {
    @FXML
    private ComboBox<String> combo;
    ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo.setItems(list);
    }

    @FXML
    void openAddClient(MouseEvent event) throws IOException {
        openNewWindow("Add client", "add_new_client");
    }

    @FXML
    void openAddSupplier(MouseEvent event) throws IOException {
        openNewWindow("Add supplier", "add_new_supplier");
    }
}
