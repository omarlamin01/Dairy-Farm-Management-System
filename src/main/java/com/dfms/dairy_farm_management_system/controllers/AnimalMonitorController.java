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

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

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
        displayMonitors();
    }

    //get all the HealthStatus
    public ObservableList<HealthStatus> getHealthStatus() {
        ObservableList<HealthStatus> monitors = FXCollections.observableArrayList();
        String query = "SELECT * FROM `health_status`";
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

    //display all the monitors in the table
    public void displayMonitors() {
        ObservableList<HealthStatus> monitors = getHealthStatus();
        animal_id_col.setCellValueFactory(new PropertyValueFactory<>("animal_id"));
        healthMonitorNoteCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
        healthMonitorAddedDateCol.setCellValueFactory(new PropertyValueFactory<>("control_date"));
        Callback<TableColumn<HealthStatus, String>, TableCell<HealthStatus, String>> cellFoctory = (TableColumn<HealthStatus, String> param) -> {
            final TableCell<HealthStatus, String> cell = new TableCell<HealthStatus, String>() {
                Image edit_img = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button edit_btn = new Button();
                Image delete_img = new Image(getClass().getResourceAsStream("/images/delete.png"));
                final Button delete_btn = new Button();
                Image view_details_img = new Image(getClass().getResourceAsStream("/images/eye.png"));
                final Button view_details_btn = new Button();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        view_details_btn.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        ImageView iv1 = new ImageView();
                        iv1.setImage(view_details_img);
                        iv1.setPreserveRatio(true);
                        iv1.setSmooth(true);
                        iv1.setCache(true);
                        view_details_btn.setGraphic(iv1);

                        setGraphic(view_details_btn);
                        setText(null);


                        edit_btn.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        ImageView iv = new ImageView();
                        iv.setImage(edit_img);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        edit_btn.setGraphic(iv);

                        setGraphic(edit_btn);
                        setText(null);

                        delete_btn.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        ImageView iv2 = new ImageView();

                        iv2.setImage(delete_img);
                        iv2.setPreserveRatio(true);
                        iv2.setSmooth(true);
                        iv2.setCache(true);
                        delete_btn.setGraphic(iv2);


                        setGraphic(delete_btn);

                        setText(null);

                        HBox managebtn = new HBox(edit_btn, delete_btn, view_details_btn);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(edit_btn, new Insets(1, 1, 0, 3));
                        HBox.setMargin(delete_btn, new Insets(1, 1, 0, 2));
                        HBox.setMargin(view_details_btn, new Insets(1, 1, 0, 1));

                        setGraphic(managebtn);
                        setText(null);
                        delete_btn.setOnMouseClicked((MouseEvent event) -> {
                            displayAlert("Delete", "Are you sure you want to delete this monitor?", Alert.AlertType.CONFIRMATION);
                        });
                    }
                }
            };
            return cell;
        };
        healthMonitorActionsCol.setCellFactory(cellFoctory);
        healthMonitorTable.setItems(monitors);
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
