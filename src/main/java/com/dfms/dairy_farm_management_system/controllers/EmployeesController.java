package com.dfms.dairy_farm_management_system.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize the combo box
        ArrayList<String> exportOptions = new ArrayList<>();
        exportOptions.add("Export to PDF");
        exportOptions.add("Export to Excel");
        exportOptions.add("Export to CSV");

        exportComboBox.getItems().addAll(exportOptions);
    }

    @FXML
    private ComboBox<String> exportComboBox;

    @FXML
    private Button openAddNewEmployeeBtn;


    @FXML
    public void openAddEmployee(MouseEvent mouseEvent) {

    }
}
