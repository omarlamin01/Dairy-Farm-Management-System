package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.models.HealthStatus;
import com.dfms.dairy_farm_management_system.models.Pregnancy;
import com.dfms.dairy_farm_management_system.models.Vaccination;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.input.*;

import static com.dfms.dairy_farm_management_system.helpers.Helper.openNewWindow;

public class AnimalMonitorController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //Health status tab
    @FXML
    TextField healthStatusSearch;
    @FXML
    Button healthSearchButton;
    @FXML
    Button newStatusButton;
    @FXML
    TableView<HealthStatus> healthMonitorTable;
    @FXML
    TableColumn<HealthStatus, String> animal_id_col;
    @FXML
    TableColumn<HealthStatus, String> healthMonitorNoteCol;
    @FXML
    TableColumn<HealthStatus, String> healthMonitorAddedDateCol;
    @FXML
    TableColumn<HealthStatus, String> healthMonitorActionsCol;


    //Pregnancy tab
    @FXML
    TextField pregnancySearch;
    @FXML
    Button pregnancySearchButton;
    @FXML
    Button newPregnancyButton;
    @FXML
    TableView<Pregnancy> pregnancyTable;
    @FXML
    TableColumn<Pregnancy, String> cow_id_col;
    @FXML
    TableColumn<Pregnancy, String> pregnancyStartDateCol;
    @FXML
    TableColumn<Pregnancy, String> pregnancyEndCol;
    @FXML
    TableColumn<Pregnancy, String> pregnancyTypeCol;
    @FXML
    TableColumn<Pregnancy, String> pregnancyActionsCol;

    //Vaccine tab
    @FXML
    TextField vaccineSearch;
    @FXML
    Button vaccineSearchButton;
    @FXML
    Button newVaccinationButton;
    @FXML
    TableView<Vaccination> vaccinationTable;
    @FXML
    TableColumn<Vaccination, String> vaccineNameCol;
    @FXML
    TableColumn<Vaccination, String> vaccineDoseCol;
    @FXML
    TableColumn<Vaccination, String> vaccinationNotesCol;
    @FXML
    TableColumn<Vaccination, String> vaccinationDateCol;
    @FXML
    TableColumn<Vaccination, String> vaccinationActions;

    //Routine monitor tab
    @FXML
    private Button RoutineSearchButton;
    @FXML
    private Button newRoutineButton;
    @FXML
    private TextField routineSearch;

    //Health status monitor
    @FXML
    ComboBox<String> animalStatusId;

    @FXML
    DatePicker monitorDate;

    @FXML
    ComboBox healthStatus;

    @FXML
    TextArea healthStatusNotes;

    @FXML
    public void openAddHealthStatus(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add health status", "add_new_health_status");
    }

    @FXML
    public void openAddPregnancy(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add pregnancy", "add_new_pregnancy");
    }

    @FXML
    void openAddRoutine(MouseEvent event) throws IOException {
        openNewWindow("Add routine", "add_new_routine");
    }

    @FXML
    public void openAddVaccination(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add vaccination", "add_new_vaccination");
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

    @FXML
    public void routineSearch(MouseEvent mouseEvent) {
        System.out.println("routineSearch");
    }
}
