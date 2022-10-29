package com.dfms.dairy_farm_management_system.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeesController implements Initializable {
    @FXML
    private ComboBox<String> combo;
    ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo.setItems(list);
    }

    public void openAddEmployee(MouseEvent mouseEvent) {

    }
}
