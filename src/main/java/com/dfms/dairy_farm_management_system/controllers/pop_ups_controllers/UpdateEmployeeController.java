package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.connection.Session;
import com.dfms.dairy_farm_management_system.models.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateEmployeeController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUserData();
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

    @FXML
    void updateEmployee(MouseEvent event) {

    }

    //get current user data
    public void getUserData() {
        User currentUser = Session.getCurrentUser();
        firstNameInput.setText(currentUser.getFirstName());
        lastNameInput.setText(currentUser.getLastName());
        cinInput.setText(currentUser.getCin());
        phoneNumberInput.setText(String.valueOf(currentUser.getPhone()));
        addressInput.setText(currentUser.getAdress());
        emailInput.setText(currentUser.getEmail());
        genderCombo.setValue(currentUser.getGender());
        roleCombo.setValue(getRoleName(currentUser.getRoleId()));
        salaryInput.setText(String.valueOf(currentUser.getSalary()));
        LocalDate date = LocalDate.parse((CharSequence) currentUser.getRecruitmentDate());
        hireDate.setValue(date);
        contractCombo.setValue(currentUser.getContractType());
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
}