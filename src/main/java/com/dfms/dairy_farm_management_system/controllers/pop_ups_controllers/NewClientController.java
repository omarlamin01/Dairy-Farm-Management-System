package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Client;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class NewClientController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setTypeComboItems();
        validatePhoneInput(phoneNumberInput);
    }

    @FXML
    TextField clientName;
    @FXML
    TextField phoneNumberInput;
    @FXML
    ComboBox<String> typeCombo;
    @FXML
    TextField emailInput;

    public void setTypeComboItems() {
        this.typeCombo.setItems(FXCollections.observableArrayList("Company", "Person"));
    }

    @FXML
    public void addNewClient(MouseEvent mouseEvent) {
        Client client = new Client();

        client.setName(this.clientName.getText());
        client.setPhone(this.phoneNumberInput.getText());
        client.setEmail(this.emailInput.getText());
        client.setType(this.typeCombo.getValue());

        if (client.save()) {
            closePopUp(mouseEvent);
            displayAlert("SUCESS", "Client added successfully.", Alert.AlertType.INFORMATION);
        } else {
            displayAlert("ERROR", "Some error happened while saving!", Alert.AlertType.ERROR);
        }
    }
}
