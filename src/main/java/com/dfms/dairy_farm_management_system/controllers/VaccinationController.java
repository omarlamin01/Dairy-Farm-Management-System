package com.dfms.dairy_farm_management_system.controllers;

import javafx.application.Platform;
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
    @FXML
    ComboBox<String> animalVaccin;
    @FXML
    ComboBox<String> vaccinId;
    @FXML
    DatePicker vaccinationDate;
    @FXML
    TextArea vaccinNotes;

    ObservableList<String> vaccins;
    ObservableList<String> animals;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setVaccins();
        this.setAnimals();

        //set animals ids list
        animalVaccin.setItems(animals);

        //set vaccins list
        vaccinId.setItems(vaccins);
    }

    public void setVaccins() {
        //get vaccins ids from db
        this.vaccins = FXCollections.observableArrayList("vac-1", "vac-2", "vac-3", "vac-4", "vac-5");
    }

    public void setAnimals() {
        //get animals ids from database
        this.animals = FXCollections.observableArrayList("cow-1", "bull-1", "cow-2", "cow-3", "Bull-1", "cow-calf-1");
    }

    @FXML
    public void addVaccination(MouseEvent mouseEvent) {
        System.out.println("Vaccination { " +
                "Animal id: \"" + animalVaccin.getValue() + "\", " +
                "Vaccin id: \"" + vaccinId.getValue() + "\", " +
                "Vaccination date: \"" + vaccinationDate.getValue() + "\", " +
                "Notes: \"" + vaccinNotes.getText() + "\" " +
                "},"
        );

        ((Stage)(((Button)mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
