package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.connection.Session;
import com.dfms.dairy_farm_management_system.models.Employee;
import com.dfms.dairy_farm_management_system.models.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class UpdateEmployeeController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchEmployee();
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

    public static int employee_id;
    public static Employee employee;

    @FXML
    void updateEmployee(MouseEvent event) {

    }

    //get current user data
    public void fetchEmployee() {
        employee = getEmployee(1);
        firstNameInput.setText(employee.getFirstName());
        lastNameInput.setText(employee.getLastName());
        emailInput.setText(employee.getEmail());
        phoneNumberInput.setText(employee.getPhone());
        addressInput.setText(employee.getAdress());
        cinInput.setText(employee.getCin());
        salaryInput.setText(String.valueOf(employee.getSalary()));
        LocalDate date = LocalDate.parse((CharSequence) employee.getRecruitmentDate());
        hireDate.setValue(date);
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

    public void setEmplyeeId(int id) {
        UpdateEmployeeController.employee_id = id;
    }
}