package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
    }

    private Statement st;
    private PreparedStatement pst;
    private Connection con = DBConfig.getConnection();
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
        this.contractCombo.setItems(FXCollections.observableArrayList("CDI", "CDD", "Anapec"));
    }

    public void setRoleComboItems() {
        //get roles from db
        this.rolesList = FXCollections.observableArrayList("Admin", "HR", "Sales agent", "Production manager", "Veterinare");

        this.roleCombo.setItems(this.rolesList);
    }

    @FXML
    public void addEmployee(MouseEvent mouseEvent) throws SQLException {
        System.out.println("Employee: { " + "First name: \"" + this.firstNameInput.getText() + "\", " + "Last name: \"" + this.lastNameInput.getText() + "\", " + "Email: \"" + this.emailInput.getText() + "\", " + "Phone: \"" + this.phoneNumberInput.getText() + "\", " + "Adress: \"" + this.adressInput.getText() + "\", " + "CIN: \"" + this.cininput.getText() + "\", " + "Salary: \"" + this.salaryInput.getText() + "\", " + "Hire date: \"" + this.hireDate.getValue() + "\", " + "Contract type: \"" + this.contractCombo.getValue() + "\", " + "Gender: \"" + this.genderCombo.getValue() + "\", " + "Role: \"" + this.roleCombo.getValue() + "\"" + " }");

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

        String query = "INSERT INTO employee (first_name, last_name, gender, cin, email, phone, adress, salary, recruitment_date, contract_type, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            this.pst = this.con.prepareStatement(query);
            this.pst.setString(1, firstName);
            this.pst.setString(2, lastName);
            this.pst.setString(3, gender);
            this.pst.setString(4, cin);
            this.pst.setString(5, email);
            this.pst.setString(6, phone);
            this.pst.setString(7, adress);
            this.pst.setString(8, salary);
            this.pst.setString(9, hireDate);
            this.pst.setString(10, contractType);
            this.pst.setString(11, role);
            this.pst.execute();
            this.pst.close();
            this.con.close();
            displayAlert("Done", "Employee added successfully", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            displayAlert("Error", "Error while adding employee", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        ((Stage) (((Button) mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
