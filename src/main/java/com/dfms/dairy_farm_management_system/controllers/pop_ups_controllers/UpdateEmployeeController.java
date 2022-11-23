package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;
import static com.dfms.dairy_farm_management_system.helpers.Helper.getRoles;

public class UpdateEmployeeController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setGenderComboItems();
        setRoleComboItems();
        setContractComboItems();
    }

    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection = DBConfig.getConnection();

    @FXML
    private TextField addressInput;

    @FXML
    private TextField cinInput;

    @FXML
    private ComboBox<String> contractCombo;

    @FXML
    private TextField emailInput;

    @FXML
    private TextField firstNameInput;

    @FXML
    private ComboBox<String> genderCombo;

    @FXML
    private DatePicker hireDate;

    @FXML
    private TextField lastNameInput;

    @FXML
    private TextField phoneNumberInput;

    @FXML
    private ComboBox<String> roleCombo;

    @FXML
    private TextField salaryInput;

    ObservableList<String> rolesList;
    String employee_cin = null;

    @FXML
    void updateEmployee(MouseEvent event) {
        if (inputsAreEmpty()) {
            displayAlert("Error", "Please fill all the fields", Alert.AlertType.ERROR);
            return;
        }
        String cin = cinInput.getText();
        String email = emailInput.getText();
        String phone = phoneNumberInput.getText();

        Employee employee = getEmployee(this.employee_cin);
        employee.setFirstName(firstNameInput.getText());
        employee.setLastName(lastNameInput.getText());
        employee.setCin(cinInput.getText());
        employee.setAdress(addressInput.getText());
        employee.setEmail(emailInput.getText());
        employee.setPhone(phoneNumberInput.getText());
        employee.setSalary(Float.parseFloat(salaryInput.getText()));
        employee.setGender(genderCombo.getValue());
        employee.setContractType(contractCombo.getValue());
        employee.setHireDate(java.sql.Date.valueOf(hireDate.getValue()));

        if (employee.update()) {
            displayAlert("Success", "Employee updated successfully", Alert.AlertType.INFORMATION);
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } else {
            displayAlert("Error", "Error while updating employee", Alert.AlertType.ERROR);
        }
    }

    //get current user data
    public void fetchEmployee(Employee employee) {

        //get the employee from the database
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        this.employee_cin = employee.getCin();

        try {
            statement = connection.prepareStatement("SELECT * FROM employees WHERE cin = '" + employee_cin + "' LIMIT 1");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                emailInput.setText(resultSet.getString("email"));
                firstNameInput.setText(resultSet.getString("first_name"));
                lastNameInput.setText(resultSet.getString("last_name"));
                salaryInput.setText(String.valueOf(resultSet.getFloat("salary")));
                addressInput.setText(resultSet.getString("address"));
                cinInput.setText(resultSet.getString("cin"));
                phoneNumberInput.setText(resultSet.getString("phone"));
                contractCombo.setValue(resultSet.getString("contract_type"));
                if (resultSet.getString("gender").equals("M")) {
                    genderCombo.setValue("Male");
                } else {
                    genderCombo.setValue("Female");
                }
                LocalDate date = LocalDate.parse(resultSet.getString("recruitment_date"));
                hireDate.setValue(date);

                //TODO: set role combo value
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRoleName(int id) {
        String roleName = "";
        try {
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("SELECT * FROM role WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roleName;
    }

    public Employee getEmployee(String employee_cin) {
        Employee employee = new Employee();
        String query = "SELECT * FROM `employees` WHERE cin = '" + employee_cin.toUpperCase() + "' LIMIT 1";
        Connection con = getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                //TODO: get role name
                //employee.setId(rs.getInt("id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAdress(rs.getString("address"));
                employee.setCin(rs.getString("cin"));
                employee.setGender(rs.getString("gender"));
                employee.setHireDate(rs.getDate("recruitment_date"));
                employee.setSalary(rs.getFloat("salary"));
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        return employee;
    }

    public void setGenderComboItems() {
        genderCombo.setItems(FXCollections.observableArrayList("Male", "Female"));
    }

    public void setRoleComboItems() {
        this.rolesList = getRoles();
        this.roleCombo.setItems(this.rolesList);
    }

    public void setContractComboItems() {
        this.contractCombo.setItems(FXCollections.observableArrayList("CDI", "CDD", "CTT"));
    }


    public boolean inputsAreEmpty() {
        if (this.firstNameInput.getText().isEmpty() || this.lastNameInput.getText().isEmpty() || this.emailInput.getText().isEmpty() || this.phoneNumberInput.getText().isEmpty() || this.addressInput.getText().isEmpty() || this.cinInput.getText().isEmpty() || this.salaryInput.getText().isEmpty() || this.hireDate.getValue() == null || this.contractCombo.getValue() == null || this.genderCombo.getValue() == null)
            return true;
        return false;
    }
}