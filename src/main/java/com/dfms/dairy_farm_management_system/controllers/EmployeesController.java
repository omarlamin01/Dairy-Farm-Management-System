package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.helpers.Helper;
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

public class EmployeesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo.setItems(list);
    }

    @FXML
    private ComboBox<String> combo;
    ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");

    public void openAddEmployee(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add Employee", "add_new_employee");
    }
}
