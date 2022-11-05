package com.dfms.dairy_farm_management_system.controllers;

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

public class PregnancyController implements Initializable {

    @FXML
    ComboBox<String> cowPregnancyID;
    @FXML
    DatePicker pregnancyStartDate;
    @FXML
    TextArea pregnancyNotes;

    ObservableList<String> cows;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setCows();

        //set cows list
        cowPregnancyID.setItems(cows);
    }

    public void setCows() {
        //get cows ids from db
        this.cows = FXCollections.observableArrayList("cow-1", "cow-2", "cow-3");
    }

    @FXML
    public void addPregnancy(MouseEvent mouseEvent) {
        System.out.println("Pregnancy { " +
                "Cow id: \"" + cowPregnancyID.getValue() + "\", " +
                "Start date: \"" + pregnancyStartDate.getValue() + "\", " +
                "Notes: \"" + pregnancyNotes.getText() + "\" " +
                "},"
        );

        ((Stage)(((Button)mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
