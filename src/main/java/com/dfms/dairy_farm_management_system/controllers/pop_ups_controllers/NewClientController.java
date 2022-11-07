package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Client;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewClientController implements Initializable {
    @FXML
    TextField clientName;
    @FXML
    TextField phoneNumberInput;
    @FXML
    ComboBox<String> typeCombo;
    @FXML
    TextField emailInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setTypeComboItems();
    }

    public void setTypeComboItems() {
        this.typeCombo.setItems(FXCollections.observableArrayList("Company", "Person"));
    }

    @FXML
    public void addNewClient(MouseEvent mouseEvent) {
        Client client = new Client();
        client.setName(this.clientName.getText());
        client.setPhone(Integer.parseInt(this.phoneNumberInput.getText()));
        client.setEmail(this.emailInput.getText());
        client.setType(this.typeCombo.getValue());
        System.out.println(client.toString());

        ((Stage)(((Button)mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
