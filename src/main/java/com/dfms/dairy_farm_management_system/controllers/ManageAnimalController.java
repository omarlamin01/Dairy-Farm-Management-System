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

public class ManageAnimalController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");
        combo.setItems(list);
    }

    @FXML
    private ComboBox<String> combo;

    public void openAddNewAnimal(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add New Animal", "add_new_animal");
    }
}
