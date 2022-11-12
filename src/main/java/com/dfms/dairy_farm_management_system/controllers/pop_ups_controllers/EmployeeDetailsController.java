package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeDetailsController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchEmployee();
    }

    @FXML
    private Label address;

    @FXML
    private Label cin;

    @FXML
    private Label contract_type;

    @FXML
    private Label email;

    @FXML
    private Label first_name;

    @FXML
    private Label gender;

    @FXML
    private Label last_name;

    @FXML
    private Label phone;

    @FXML
    private Label recruitment_date;

    @FXML
    private Label role;

    @FXML
    private Label salary;

    private static Employee employee = new Employee();


    public void fetchEmployee() {
        //header_label.setText("Here's the details of " + employee.getFirstName() + " " + employee.getLastName());
        first_name.setText(employee.getFirstName());
        last_name.setText(employee.getLastName());
        email.setText(employee.getEmail());
        phone.setText(employee.getPhone());
        address.setText(employee.getAdress());
        cin.setText(employee.getCin());
        salary.setText(String.valueOf(employee.getSalary()));
        contract_type.setText(employee.getContractType());
        recruitment_date.setText(String.valueOf(employee.getRecruitmentDate()));
        gender.setText(employee.getGender());
    }


    public void setEmployee(Employee employee) {
        EmployeeDetailsController.employee = employee;
    }
}