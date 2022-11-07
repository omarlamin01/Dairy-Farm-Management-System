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

public class HealthStatusController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setAnimals();

        //set health status options
        healthStatus.setItems(healthStatusOptions);

        //set health monitor & vaccine animals ids list
        animalId.setItems(animals);
    }

    @FXML
    ComboBox<String> animalId;
    @FXML
    DatePicker monitorDate;
    @FXML
    ComboBox<String> healthStatus;
    @FXML
    TextArea healthStatusNotes;

    ObservableList<String> healthStatusOptions = FXCollections.observableArrayList("excellent", "good", "bad", "very bad");
    ObservableList<String> animals;

    public void setAnimals() {
        //get animals ids from database
        this.animals = FXCollections.observableArrayList("cow-1", "bull-1", "cow-2", "cow-3", "Bull-1", "cow-calf-1");
    }

    @FXML
    public void addHealthStatus(MouseEvent mouseEvent) {
        System.out.println("Health status { " +
                "Animal id: \"" + animalId.getValue() + "\", " +
                "Monitor date: \"" + monitorDate.getValue() + "\", " +
                "Status: \"" + healthStatus.getValue() + "\", " +
                "Notes: \"" + healthStatusNotes.getText() + "\" " +
                "},"
        );

        ((Stage) (((Button) mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
