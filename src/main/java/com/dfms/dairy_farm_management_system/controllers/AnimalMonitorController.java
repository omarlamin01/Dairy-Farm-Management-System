package com.dfms.dairy_farm_management_system.controllers;

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

    //Health status monitor
    @FXML
    ComboBox<String> animalstatusId;
    @FXML
    DatePicker monitorDate;
    @FXML
    ComboBox healthStatus;
    @FXML
    TextArea healthStatusNotes;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    public void addHealthStatus(MouseEvent mouseEvent) {
        System.out.println("Health status { " +
                "Animal id: \"" + animalstatusId.getAccessibleText() + "\"," +
                "Monitor date: \"" + monitorDate.getAccessibleText() + "\"," +
                "Status: \"" + healthStatus.getAccessibleText() + "\"," +
                "Notes: \"" + healthStatusNotes.getAccessibleText() + "\" " +
                "},"
        );
    }
}
