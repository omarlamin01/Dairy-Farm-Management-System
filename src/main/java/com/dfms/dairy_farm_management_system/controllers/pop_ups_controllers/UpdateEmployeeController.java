package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.connection.Session;
import com.dfms.dairy_farm_management_system.models.Employee;
import com.dfms.dairy_farm_management_system.models.User;
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
import java.util.Objects;
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

    private Statement st;
    private PreparedStatement pst;
    private Connection con = DBConfig.getConnection();

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
    int employee_id = -1;

    @FXML
    void updateEmployee(MouseEvent event) {
        if (inputesAreEmpty()) {
            displayAlert("Error", "Please fill all the fields", Alert.AlertType.ERROR);
            return;
        }
        String cin = cinInput.getText();
        String email = emailInput.getText();
        String phone = phoneNumberInput.getText();

        Employee employee = getEmployee(this.employee_id);
        System.out.println("Employee id: " + employee.getId());
        employee.setFirstName(firstNameInput.getText());
        employee.setLastName(lastNameInput.getText());
        employee.setCin(cinInput.getText());
        employee.setAdress(addressInput.getText());
        employee.setEmail(emailInput.getText());
        employee.setPhone(phoneNumberInput.getText());
        employee.setSalary(Float.parseFloat(salaryInput.getText()));
        employee.setGender(genderCombo.getValue());
        employee.setContractType(contractCombo.getValue());
        employee.setRecruitmentDate(java.sql.Date.valueOf(hireDate.getValue()));

        System.out.println(employee.toString());

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
        Connection con = getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        this.employee_id = employee.getId();

        try {
            st = con.prepareStatement("SELECT * FROM employee WHERE id = " + employee.getId());
            rs = st.executeQuery();
            if (rs.next()) {
                addressInput.setText(rs.getString("address"));
                cinInput.setText(rs.getString("cin"));
                phoneNumberInput.setText(rs.getString("phone"));
                contractCombo.setValue(rs.getString("contract_type"));
                if (rs.getString("gender").equals("M")) {
                    genderCombo.setValue("male");
                } else {
                    genderCombo.setValue("Female");
                }
                LocalDate date = LocalDate.parse(rs.getString("recruitment_date"));
                hireDate.setValue(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        emailInput.setText(employee.getEmail());
        firstNameInput.setText(employee.getFirstName());
        lastNameInput.setText(employee.getLastName());
        salaryInput.setText(String.valueOf(employee.getSalary()));
    }

    public String getRoleName(int id) {
        String roleName = "";
        try {
            st = con.createStatement();
            pst = con.prepareStatement("SELECT * FROM role WHERE id = ?");
            pst.setInt(1, id);
            pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roleName;
    }

    public Employee getEmployee(int id) {
        Employee employee = new Employee();
        String query = "SELECT * FROM employee WHERE id = " + id;
        Connection con = getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                employee.setId(rs.getInt("id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAdress(rs.getString("address"));
                employee.setCin(rs.getString("cin"));
                employee.setGender(rs.getString("gender"));
                employee.setRecruitmentDate(rs.getDate("recruitment_date"));
                employee.setSalary(rs.getFloat("salary"));
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
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


    public boolean inputesAreEmpty() {
        if (this.firstNameInput.getText().isEmpty() || this.lastNameInput.getText().isEmpty() || this.emailInput.getText().isEmpty() || this.phoneNumberInput.getText().isEmpty() || this.addressInput.getText().isEmpty() || this.cinInput.getText().isEmpty() || this.salaryInput.getText().isEmpty() || this.hireDate.getValue() == null || this.contractCombo.getValue() == null || this.genderCombo.getValue() == null)
            return true;
        return false;
    }
}