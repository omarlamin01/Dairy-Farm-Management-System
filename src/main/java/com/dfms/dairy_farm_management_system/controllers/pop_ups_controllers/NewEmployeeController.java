package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.helpers.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.validateDecimalNumbers;
import static com.dfms.dairy_farm_management_system.helpers.Helper.validateOnlyNumbers;

public class NewEmployeeController implements Initializable {
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setGenderComboItems();
        this.setContractComboItems();
        this.setRoleComboItems();
        validateOnlyNumbers(phoneNumberInput);
        validateDecimalNumbers(salaryInput);
    }

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
    public void addEmployee(MouseEvent mouseEvent) {
        System.out.println("Employee: { " +
                "First name: \"" + this.firstNameInput.getText() + "\", " +
                "Last name: \"" + this.lastNameInput.getText() + "\", " +
                "Email: \"" + this.emailInput.getText() + "\", " +
                "Phone: \"" + this.phoneNumberInput.getText() + "\", " +
                "Adress: \"" + this.adressInput.getText() + "\", " +
                "CIN: \"" + this.cininput.getText() + "\", " +
                "Salary: \"" + this.salaryInput.getText() + "\", " +
                "Hire date: \"" + this.hireDate.getValue() + "\", " +
                "Contract type: \"" + this.contractCombo.getValue() + "\", " +
                "Gender: \"" + this.genderCombo.getValue() + "\", " +
                "Role: \"" + this.roleCombo.getValue() + "\"" +
                " }");

        ((Stage) (((Button) mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
