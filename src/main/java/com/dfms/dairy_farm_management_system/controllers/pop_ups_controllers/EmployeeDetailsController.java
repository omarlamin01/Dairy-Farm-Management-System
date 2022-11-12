package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class EmployeeDetailsController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchEmployee();
    }

    private Statement st;
    private PreparedStatement pst;
    private Connection con = getConnection();

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

    public static int employee_id;
    public static Employee employee;


    public void fetchEmployee() {
        employee = getEmployee(EmployeeDetailsController.employee_id);
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

    public Employee getEmployee(int id) {
        Employee employee = new Employee();
        String query = "SELECT * FROM employee WHERE id = " + id;
        con = getConnection();
        try {
            st = con.createStatement();
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

    public void setEmployeeId(int id) {
        EmployeeDetailsController.employee_id = id;
    }
}