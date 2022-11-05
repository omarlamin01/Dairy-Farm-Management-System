package com.dfms.dairy_farm_management_system.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.input.*;
import static com.dfms.dairy_farm_management_system.helpers.Helper.openNewWindow;

public class AnimalMonitorController implements Initializable {

    //Health status tab
    @FXML
    TextField healthStatusSearch;
    @FXML
    Button healthSearchButton;
    @FXML
    Button newStatusButton;

    //Pregnancy tab
    @FXML
    TextField pregnancySearch;
    @FXML
    Button pregnancySearchButton;
    @FXML
    Button newPregnancyButton;

    //Vaccin tab
    @FXML
    TextField vaccinSearch;
    @FXML
    Button vaccinSearchButton;
    @FXML
    Button newVaccintionButton;

    //Pregnancy pop-up
    @FXML
    ComboBox<String> cowPregnancyID;
    @FXML
    DatePicker pregnancyStartDate;
    @FXML
    TextArea pregnancyNotes;

    //Vaccin pop-up
    @FXML
    ComboBox<String> animalVaccin;
    @FXML
    ComboBox<String> vaccinId;
    @FXML
    DatePicker vaccinationDate;
    @FXML
    TextArea vaccinNotes;

    ObservableList<String> cows;
    ObservableList<String> vaccins;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set lists first
        this.setCows();
        this.setVaccins();

        //set health monitor's & vaccin's animals ids list
        animalVaccin.setItems(animals);

        //set cows list
        cowPregnancyID.setItems(cows);

        //set vaccins list
        vaccinId.setItems(vaccins);
    }

    public void setCows() {
        //get cows ids from db
        this.cows = FXCollections.observableArrayList("cow-1", "cow-2", "cow-3");
    }

    public void setVaccins() {
        //get vaccins ids from db
        this.vaccins = FXCollections.observableArrayList("vac-1", "vac-2", "vac-3", "vac-4", "vac-5");
    }

    @FXML
    public void oppenAddHealthStatus(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add health status", "add_new_health_status");
    }

    @FXML
    public void oppenAddPregnancy(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add pregnancy", "add_new_pregnancy");
    }

    @FXML
    public void oppenAddVaccination(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add vaccination", "add_new_vaccination");
    }

    @FXML
    public void addPregnancy(MouseEvent mouseEvent) {
        System.out.println("Pregnancy { " +
                "Cow id: \"" + cowPregnancyID.getTypeSelector() + "\"," +
                "Start date: \"" + pregnancyStartDate.getConverter() + "\"," +
                "Notes: \"" + pregnancyNotes.getText() + "\" " +
                "},"
        );
    }

    @FXML
    public void addVaccination(MouseEvent mouseEvent) {
        System.out.println("Vaccination { " +
                "Animal id: \"" + animalVaccin.getPromptText() + "\"," +
                "Vaccin id: \"" + vaccinId.getPromptText() + "\"," +
                "Vaccination date: \"" + vaccinationDate.getPromptText() + "\"," +
                "Notes: \"" + vaccinNotes.getText() + "\" " +
                "},"
        );
    }

    @FXML
    public void healthStatusSearch(MouseEvent mouseEvent) {
        System.out.println("healthStatusSearch");
    }

    @FXML
    public void pregnancySearch(MouseEvent mouseEvent) {
        System.out.println("pregnancySearch");
    }

    @FXML
    public void vaccinationSearch(MouseEvent mouseEvent) {
        System.out.println("vaccinationSearch");
    }
}
