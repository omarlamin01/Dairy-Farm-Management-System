package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class VaccinationController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setVaccines();
        this.setAnimals();

        //set animals ids list
        animalVaccine.setItems(animals);

        //set vaccines list
        vaccineId.setItems(vaccines);
    }

    @FXML
    ComboBox<String> animalVaccine;
    @FXML
    ComboBox<String> vaccineId;
    @FXML
    DatePicker vaccinationDate;
    @FXML
    TextArea vaccineNotes;

    ObservableList<String> vaccines;
    ObservableList<String> animals;

    public void setVaccines() {
        //get vaccines ids from db
        this.vaccines = FXCollections.observableArrayList("vac-1", "vac-2", "vac-3", "vac-4", "vac-5");
    }

    public void setAnimals() {
        //get animals ids from database
        this.animals = FXCollections.observableArrayList("cow-1", "bull-1", "cow-2", "cow-3", "Bull-1", "cow-calf-1");
    }

    @FXML
    public void addVaccination(MouseEvent mouseEvent) {
        System.out.println("Vaccination { " +
                "Animal id: \"" + animalVaccine.getValue() + "\", " +
                "Vaccine id: \"" + vaccineId.getValue() + "\", " +
                "Vaccination date: \"" + vaccinationDate.getValue() + "\", " +
                "Notes: \"" + vaccineNotes.getText() + "\" " +
                "},"
        );

        ((Stage) (((Button) mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
