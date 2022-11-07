package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

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

import static com.dfms.dairy_farm_management_system.helpers.Helper.validateDecimalInput;

public class CowSalesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setAnimalsList();
        this.setClientsList();
        validateDecimalInput(priceOfSale);
    }

    @FXML
    ComboBox<String> clientsCombo;
    @FXML
    DatePicker operationDate;
    @FXML
    ComboBox<String> animalsCombo;
    @FXML
    TextField priceOfSale;

    ObservableList<String> clientsList;
    ObservableList<String> animalsList;

    public void setClientsList() {
        this.clientsList = FXCollections.observableArrayList("client-1", "client-2", "client-3", "client-4", "client-5");
        this.clientsCombo.setItems(this.clientsList);
    }

    public void setAnimalsList() {
        this.animalsList = FXCollections.observableArrayList("cow-1", "bull-1", "cow-2", "cow-3", "Bull-1", "cow-calf-1");
        this.animalsCombo.setItems(this.animalsList);
    }

    @FXML
    public void addCowSale(MouseEvent mouseEvent) {
        System.out.println("Sale: {" + " Client: \"" + this.clientsCombo.getValue() + "\"," + " Animal ID: \"" + this.animalsCombo.getValue() + "\"," + " Operation date: \"" + this.operationDate.getValue() + "\"," + " Price of sale: \"" + this.priceOfSale.getText() + "\" " + "}");

        ((Stage) (((Button) mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
