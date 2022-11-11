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
import java.sql.*;
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
        this.rolesList = getRoles();
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

        String query_emp = "INSERT INTO employee (first_name, last_name, gender, cin, email, phone, address, salary, recruitment_date, contract_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String query_user = "INSERT INTO user (role_id, employee_id, first_name, last_name, email, password, phone, address, gender, cin, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        //insert into employee table
        try {
            this.pst = this.con.prepareStatement(query_emp);
            this.pst.setString(1, firstName);
            this.pst.setString(2, lastName);
            //check gender type
            if (this.genderCombo.getValue().equals("Male")) {
                this.pst.setString(3, "M");
            } else {
                this.pst.setString(3, "F");
            }
            this.pst.setString(4, cin);
            this.pst.setString(5, email);
            this.pst.setString(6, phone);
            this.pst.setString(7, adress);
            this.pst.setString(8, salary);
            this.pst.setString(9, hireDate);
            this.pst.setString(10, contractType);
            this.pst.execute();

            //insert into user table
            try {
                this.con = DBConfig.getConnection();
                this.pst = this.con.prepareStatement(query_user);

                //get role id
                int role_id = getRoleID(role);

                //get last inserted employee id
                this.st = this.con.createStatement();
                ResultSet rs = this.st.executeQuery("SELECT MAX(id) FROM employee");
                rs.next();
                int employee_id = rs.getInt(1);

                //insert into user table
                this.pst.setInt(1, role_id);
                this.pst.setInt(2, employee_id);
                this.pst.setString(3, firstName);
                this.pst.setString(4, lastName);
                this.pst.setString(5, email);
                this.pst.setString(6, cin);
                this.pst.setString(7, phone);
                this.pst.setString(8, adress);
                //check gender type
                if (this.genderCombo.getValue().equals("Male")) {
                    this.pst.setString(9, "M");
                } else {
                    this.pst.setString(9, "F");
                }
                this.pst.setString(10, cin);
                this.pst.setString(11, salary);
                this.pst.execute();

                displayAlert("Done", "Employee added successfully", Alert.AlertType.INFORMATION);

            } catch (SQLException e) {
                displayAlert("Error", "Error while adding employee", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
            closeWindow((Button) mouseEvent.getSource());
        } finally {
            this.pst.close();
            this.con.close();
        }
    }

    public int getRoleID(String role) {
        try {
            this.con = DBConfig.getConnection();
            this.st = this.con.createStatement();
            ResultSet rs = this.st.executeQuery("SELECT id FROM role WHERE name = '" + role + "'");
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            displayAlert("Error", "Error while getting role id", Alert.AlertType.ERROR);
        }
        return 0;
    }
}
