package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;

import javafx.scene.control.*;
import javafx.scene.input.*;

import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;
import static com.dfms.dairy_farm_management_system.helpers.Helper.openNewWindow;

public class AnimalMonitorController implements Initializable {

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
    Button RoutineSearchButton;
    @FXML
    Button newRoutineButton;
    @FXML
    TextField routineSearch;
    @FXML
    TableView<Routine> routineTable;
    @FXML
    TableColumn<Routine, String> routineNameCol;
    @FXML
    TableColumn<Routine, String> routineNotesCol;
    @FXML
    TableColumn<Routine, String> routineAdditionDateCol;
    @FXML
    TableColumn<Routine, String> routineActionsCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //get all the HealthStatus
    public ObservableList<HealthStatus> getHealthStatus() {
        ObservableList<HealthStatus> monitors = FXCollections.observableArrayList();
        String query = "SELECT * FROM employee";
        try {
            Connection connection = DBConfig.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                HealthStatus monitor = new HealthStatus();
                monitor.setId(resultSet.getInt("id"));
                monitor.setAnimal_id(resultSet.getInt("animal_id"));
                monitor.setVaccin_id(resultSet.getInt("vaccine_id"));
                monitor.setWeight(resultSet.getFloat("weight"));
                monitor.setBreading(resultSet.getFloat("breading"));
                monitor.setAge(resultSet.getFloat("age"));
                monitor.setControl_date(resultSet.getDate("control_date"));
                monitors.add(monitor);
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        return monitors;
    }

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
