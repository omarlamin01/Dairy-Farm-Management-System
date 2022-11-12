package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewEmployeeDetails {

    @FXML
    private Label address_label;

    @FXML
    private Label cin_label;

    @FXML
    private Label contract_type_label;

    @FXML
    private Label email_label;

    @FXML
    private Label first_name_label;

    @FXML
    private Label gender_label;

    @FXML
    private Label header_label;

    @FXML
    private Label last_name_label;

    @FXML
    private Label phone_label;

    @FXML
    private Label role_label;

    @FXML
    private Label salary_label;

    private Employee employee;

    public void setEmployee(Employee selectedItem) {
        this.employee = selectedItem;
    }
}
