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
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class EmployeeDetailsController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private Label header;
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

    public void fetchEmployee(Employee employee) {

        //get the employee from the database
        Connection con = getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = con.prepareStatement("SELECT * FROM `employees` WHERE cin = " + employee.getCin());
            rs = st.executeQuery();
            if (rs.next()) {
                address.setText(rs.getString("address"));
                cin.setText(rs.getString("cin"));
                phone.setText(rs.getString("phone"));
                contract_type.setText(rs.getString("contract_type"));
                recruitment_date.setText(rs.getString("recruitment_date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        header.setText(employee.getFirstName() + " " + employee.getLastName());
        email.setText(employee.getEmail());
        first_name.setText(employee.getFirstName());
        last_name.setText(employee.getLastName());
        salary.setText(String.valueOf(employee.getSalary()));
    }

    public String getRole(int id) {
        String role = "";
        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM `roles` WHERE id = " + id);
            while (rs.next()) {
                role = rs.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }
}