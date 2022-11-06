package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewAnimalController implements Initializable {
    @FXML
    TextField weightInput;
    @FXML
    ComboBox<String> typeCombo;
    @FXML
    ComboBox<String> raceCombo;
    @FXML
    CheckBox isPregnant;
    @FXML
    DatePicker birthDate;
    @FXML
    DatePicker purchaseDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setRaceComboItems();
        this.setTypeComboItems();
    }

    public void setTypeComboItems() {
        this.typeCombo.setItems(FXCollections.observableArrayList("Cow", "Bull", "Calf"));
    }

    public void setRaceComboItems() {
        //get races from db
        ObservableList<String> races = FXCollections.observableArrayList();
        races.add("race-1");
        races.add("race-2");
        races.add("race-3");
        races.add("race-4");

        this.raceCombo.setItems(races);
    }

    @FXML
    public void addAnimal(MouseEvent mouseEvent) {
        System.out.println("new animal added");

        ((Stage)(((Button)mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
