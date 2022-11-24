package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Client;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class NewClientController implements Initializable {

    @FXML
    private Label add_update;

    @FXML
    private Label head;

    @FXML
    private TextField clientName;

    @FXML
    private TextField phoneNumberInput;

    @FXML
    private ComboBox<String> typeCombo;

    @FXML
    private TextField emailInput;

    @FXML
    private Button add_client_btn;
    private String client_ID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setTypeComboItems();
        validatePhoneInput(phoneNumberInput);
    }


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
            clear();
            displayAlert("SUCESS", "Client added successfully.", Alert.AlertType.INFORMATION);
        } else {
            displayAlert("ERROR", "Some error happened while saving!", Alert.AlertType.ERROR);
        }
    }
    public void fetchClient(String id_client, String Clientname, String email, String clientPhoneNumber,String type) {
        this.client_ID = id_client;
        head.setText("Update Client Num: " + id_client);
        clientName.setText(Clientname);
        emailInput.setText(email);
        phoneNumberInput.setText(clientPhoneNumber);
        typeCombo.setValue(type);
        add_client_btn.setText("Update");
        add_update.setText("Update");

    }
    private void clear() {
        typeCombo.getSelectionModel().clearSelection();
        clientName.setText(null);
       phoneNumberInput.setText(null);
       emailInput.setText(null);


    }

}
