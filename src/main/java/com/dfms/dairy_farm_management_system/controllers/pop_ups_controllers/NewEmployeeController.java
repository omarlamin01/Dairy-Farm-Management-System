package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.Collections;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class NewEmployeeController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setGenderComboItems();
        this.setContractComboItems();
        this.setRoleComboItems();
        validatePhoneInput(phoneNumberInput);
        validateDecimalInput(salaryInput);
        validateEmailInput(emailInput);
    }

    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection = DBConfig.getConnection();
    @FXML
    TextField lastNameInput;
    @FXML
    TextField firstNameInput;
    @FXML
    TextField adressInput;
    @FXML
    TextField emailInput;
    @FXML
    TextField phoneNumberInput;
    @FXML
    TextField salaryInput;
    @FXML
    ComboBox<String> genderCombo;
    @FXML
    ComboBox<String> roleCombo;
    @FXML
    DatePicker hireDate;
    @FXML
    ComboBox<String> contractCombo;

    ObservableList<String> rolesList;
    @FXML
    private TextField cininput;

    public void setGenderComboItems() {
        this.genderCombo.setItems(FXCollections.observableArrayList("Male", "Female"));
    }

    public void setContractComboItems() {
        this.contractCombo.setItems(FXCollections.observableArrayList("CDI", "CDD", "CTT"));
    }

    public void setRoleComboItems() {
        rolesList = FXCollections.observableArrayList();
        String[] names = getRoles().keySet().toArray(new String[0]);
        this.rolesList = FXCollections.observableArrayList();
        Collections.addAll(this.rolesList, names);
        this.roleCombo.setItems(this.rolesList);
    }

    @FXML
    public void addEmployee(MouseEvent mouseEvent) throws SQLException {
        this.connection = DBConfig.getConnection();
        System.out.println("Employee: { " + "First name: \"" + this.firstNameInput.getText() + "\", " + "Last name: \"" + this.lastNameInput.getText() + "\", " + "Email: \"" + this.emailInput.getText() + "\", " + "Phone: \"" + this.phoneNumberInput.getText() + "\", " + "Adress: \"" + this.adressInput.getText() + "\", " + "CIN: \"" + this.cininput.getText() + "\", " + "Salary: \"" + this.salaryInput.getText() + "\", " + "Hire date: \"" + this.hireDate.getValue() + "\", " + "Contract type: \"" + this.contractCombo.getValue() + "\", " + "Gender: \"" + this.genderCombo.getValue() + "\", " + "Role: \"" + this.roleCombo.getValue() + "\"" + " }");

        if (inputesAreEmpty()) {
            displayAlert("Error", "Please fill all the fields", Alert.AlertType.ERROR);
            return;
        }

        String firstName = this.firstNameInput.getText();
        String lastName = this.lastNameInput.getText();
        String email = this.emailInput.getText();
        String phone = this.phoneNumberInput.getText();
        String adress = this.adressInput.getText();
        String cin = this.cininput.getText();
        String salary = this.salaryInput.getText();
        String hireDate = this.hireDate.getValue().toString();
        String contractType = this.contractCombo.getValue();
        String gender = this.genderCombo.getValue();
        String role = this.roleCombo.getValue();

        if (!isUnique(email, phone, cin)) {
            displayAlert("Error", "Email, phone or CIN already exists", Alert.AlertType.ERROR);
            return;
        }

        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setAdress(adress);
        employee.setCin(cin);
        employee.setSalary(Float.parseFloat(salary));
        Date date = Date.valueOf(hireDate);
        employee.setHireDate(date);
        employee.setContractType(contractType);
        employee.setGender(gender);

        //insert into employee table
        if (employee.save()) {
            displayAlert("Success", "Employee added successfully", Alert.AlertType.INFORMATION);
            ((Node) (mouseEvent.getSource())).getScene().getWindow().hide();
        } else {
            displayAlert("Error", "Error while adding employee", Alert.AlertType.ERROR);
        }
    }

    public int getRoleID(String role) {
        try {
            this.connection = DBConfig.getConnection();
            this.statement = this.connection.createStatement();
            ResultSet rs = this.statement.executeQuery("SELECT id FROM `roles` WHERE name = '" + role + "'");
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            displayAlert("Error", "Error while getting role id", Alert.AlertType.ERROR);
        }
        return 0;
    }

    //check if all inputs are filled
    public boolean inputesAreEmpty() {
        if (this.firstNameInput.getText().isEmpty()
                || this.lastNameInput.getText().isEmpty()
                || this.emailInput.getText().isEmpty()
                || this.phoneNumberInput.getText().isEmpty()
                || this.adressInput.getText().isEmpty()
                || this.cininput.getText().isEmpty()
                || this.salaryInput.getText().isEmpty()
                || this.hireDate.getValue() == null
                || this.contractCombo.getValue() == null
                || this.genderCombo.getValue() == null
                || this.roleCombo.getValue() == null)
            return true;
        return false;
    }

    //check if email, cin and phone are unique
    public boolean isUnique(String email, String cin, String phone) {
        String query = "SELECT * FROM `employees` WHERE email = '" + email + "' OR cin = '" + cin + "' OR phone = '" + phone + "'";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return false;
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        return true;
    }
}
