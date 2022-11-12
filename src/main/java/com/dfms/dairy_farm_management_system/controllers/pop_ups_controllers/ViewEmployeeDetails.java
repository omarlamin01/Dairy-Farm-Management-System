package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewEmployeeDetails implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeLabels();
        getEmployee();
    }

    private Statement st;
    private PreparedStatement pst;
    private Connection con = DBConfig.getConnection();

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

    public void getEmployee() {
        //header_label.setText("Here's the details of " + employee.getFirstName() + " " + employee.getLastName());
        first_name_label.setText(employee.getFirstName());
        last_name_label.setText(employee.getLastName());
        email_label.setText(employee.getEmail());
        phone_label.setText(employee.getPhone());
        address_label.setText(employee.getAdress());
        cin_label.setText(employee.getCin());
        salary_label.setText(String.valueOf(employee.getSalary()));
        contract_type_label.setText(employee.getContractType());
        gender_label.setText(employee.getGender());
    }

    public void setEmployee(Employee selectedEmployee) {
        employee = selectedEmployee;
    }

    public void initializeLabels() {
        header_label = new Label();
        first_name_label = new Label();
        last_name_label = new Label();
        email_label = new Label();
        phone_label = new Label();
        address_label = new Label();
        cin_label = new Label();
        salary_label = new Label();
        contract_type_label = new Label();
        gender_label = new Label();
        role_label = new Label();
    }
}
