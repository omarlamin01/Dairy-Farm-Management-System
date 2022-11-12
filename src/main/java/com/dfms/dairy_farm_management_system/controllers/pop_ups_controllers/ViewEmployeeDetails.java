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
        getEmployee(this.employeeId);
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
    private int employeeId;

    public void getEmployee(int employeeId) {

        //get employee from db and store it in employee object
        con = DBConfig.getConnection();
        try {
            pst = con.prepareStatement("SELECT * FROM employee WHERE id = ?");
            pst.setInt(1, employeeId);
            pst.execute();

            ResultSet rs = pst.getResultSet();

            employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setFirstName(rs.getString("first_name"));
            employee.setLastName(rs.getString("last_name"));
            employee.setEmail(rs.getString("email"));
            employee.setAdress(rs.getString("address"));
            employee.setCin(rs.getString("cin"));
            employee.setPhone(rs.getString("phone"));
            employee.setGender(rs.getString("gender"));
            employee.setSalary(rs.getFloat("salary"));
            employee.setRecruitmentDate(rs.getDate("recruitment_date"));
            employee.setContractType(rs.getString("contract_type"));

            DBConfig.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }


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

    public void setEmployeeId(int id) {
        this.employeeId = id;
    }
}
