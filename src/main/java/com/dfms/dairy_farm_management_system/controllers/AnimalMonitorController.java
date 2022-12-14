package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.*;
import com.dfms.dairy_farm_management_system.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;
import static com.dfms.dairy_farm_management_system.connection.DBConfig.*;

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
    TableColumn<HealthStatus, String> health_score_col;
    @FXML
    TableColumn<HealthStatus, String> control_date_col;
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
    TableColumn<Vaccination, String> animalIdCol;
    @FXML
    TableColumn<Vaccination, String> vaccineNameCol;
    @FXML
    TableColumn<Vaccination, String> ResponsibleNameCol;
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

    boolean isSearchingForMonitors = false;
    boolean isSearchingForPregnancies = false;
    boolean isSearchingForVaccinations = false;
    boolean isSearchingForRoutines = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayMonitors(getHealthStatus());
        displayPregnancies(getPregnancies());
        displayVaccinations(getVaccinations());
        displayRoutines(getRoutines());
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
                monitor.setAnimal_id(resultSet.getString("animal_id"));
                monitor.setWeight(resultSet.getInt("weight"));
                monitor.setBreathing(resultSet.getInt("breathing"));
                monitor.setControl_date(resultSet.getDate("control_date"));
                monitor.setHealth_score(resultSet.getString("health_score"));
                monitor.setNotes(resultSet.getString("notes"));
                monitors.add(monitor);
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        } finally {
            disconnect();
        }
        return monitors;
    }

    //display all the monitors in the table
    public void displayMonitors(ObservableList<HealthStatus> monitors) {
        animal_id_col.setCellValueFactory(new PropertyValueFactory<>("animal_id"));
        healthMonitorNoteCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
        health_score_col.setCellValueFactory(new PropertyValueFactory<>("health_score"));
        control_date_col.setCellValueFactory(new PropertyValueFactory<>("control_date"));
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
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete");
                            alert.setHeaderText("Are you sure you want to delete this monitor?");
                            int index = ((TableCell<HealthStatus, String>) ((Button) event.getSource()).getParent().getParent()).getTableRow().getIndex();
                            HealthStatus monitor = monitors.get(index);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                try {
                                    monitor.delete();
                                    displayMonitors(isSearchingForMonitors ? monitors : getHealthStatus());
                                } catch (Exception e) {
                                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                }
                            }
                        });
                        edit_btn.setOnMouseClicked((MouseEvent event) -> {
                            int index = ((TableCell<HealthStatus, String>) ((HBox) ((Button) event.getSource()).getParent()).getParent()).getTableRow().getIndex();
                            HealthStatus monitor = monitors.get(index);
                            String url = "popups/add_new_health_status.fxml";
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(url));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            HealthStatusController controller = fxmlLoader.getController();
                            controller.initData(monitor);
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });
                    }
                }
            };
            return cell;
        };
        healthMonitorActionsCol.setCellFactory(cellFoctory);
        healthMonitorTable.setItems(monitors);
    }

    //get all the pregnancies
    public ObservableList<Pregnancy> getPregnancies() {
        ObservableList<Pregnancy> pregnancies = FXCollections.observableArrayList();
        String query = "SELECT * FROM `pregnancies`";
        try {
            Connection connection = DBConfig.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Pregnancy pregnancy = new Pregnancy();
                pregnancy.setId(resultSet.getInt("id"));
                pregnancy.setCow_id(resultSet.getString("cow_id"));
                pregnancy.setStart_date(resultSet.getDate("start_date"));
                pregnancy.setDelivery_date(resultSet.getDate("delivery_date"));
                pregnancy.setPregnancy_status(resultSet.getString("pregnancy_status"));
                pregnancy.setNotes(resultSet.getString("notes"));
                pregnancies.add(pregnancy);
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        } finally {
            disconnect();
        }
        return pregnancies;
    }

    //display all the pregnancies in the table
    public void displayPregnancies(ObservableList<Pregnancy> pregnancies) {
        cow_id_col.setCellValueFactory(new PropertyValueFactory<>("cow_id"));
        pregnancyStartDateCol.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        pregnancyEndCol.setCellValueFactory(new PropertyValueFactory<>("delivery_date"));
        pregnancyTypeCol.setCellValueFactory(new PropertyValueFactory<>("pregnancy_status"));
        Callback<TableColumn<Pregnancy, String>, TableCell<Pregnancy, String>> cellFoctory = (TableColumn<Pregnancy, String> param) -> {
            final TableCell<Pregnancy, String> cell = new TableCell<Pregnancy, String>() {
                Image edit_img = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button edit_btn = new Button();
                Image delete_img = new Image(getClass().getResourceAsStream("/images/delete.png"));
                final Button delete_btn = new Button();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
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

                        HBox managebtn = new HBox(edit_btn, delete_btn);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(edit_btn, new Insets(1, 1, 0, 3));
                        HBox.setMargin(delete_btn, new Insets(1, 1, 0, 2));

                        setGraphic(managebtn);
                        setText(null);
                        delete_btn.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete");
                            alert.setHeaderText("Are you sure you want to delete this pregnancy?");
                            int index = ((TableCell<Pregnancy, String>) ((HBox) ((Button) event.getSource()).getParent()).getParent()).getTableRow().getIndex();
                            Pregnancy pregnancy = pregnancies.get(index);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                try {
                                    pregnancy.delete();
                                    displayPregnancies(isSearchingForPregnancies ? pregnancies : getPregnancies());
                                } catch (Exception e) {
                                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                }
                            }
                        });
                        edit_btn.setOnMouseClicked((MouseEvent event) -> {
                            int index = ((TableCell<HealthStatus, String>) ((HBox) ((Button) event.getSource()).getParent()).getParent()).getTableRow().getIndex();
                            Pregnancy pregnancy = pregnancies.get(index);
                            String url = "popups/add_new_pregnancy.fxml";
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(url));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            PregnancyController controller = fxmlLoader.getController();
                            controller.initData(pregnancy);
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });
                    }
                }
            };
            return cell;
        };
        pregnancyActionsCol.setCellFactory(cellFoctory);
        pregnancyTable.setItems(pregnancies);
    }

    //get all the vaccinations
    public ObservableList<Vaccination> getVaccinations() {
        ObservableList<Vaccination> vaccinations = FXCollections.observableArrayList();
        String query = "SELECT * FROM `vaccination`";
        try {
            Connection connection = DBConfig.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Vaccination vaccination = new Vaccination();
                vaccination.setId(resultSet.getInt("id"));
                vaccination.setAnimal_id(resultSet.getString("animal_id"));
                vaccination.setResponsible_id(resultSet.getInt("responsible_id"));
                vaccination.setVaccine_id(resultSet.getInt("vaccine_id"));
                vaccination.setVaccination_date(resultSet.getDate("vaccination_date"));
                vaccinations.add(vaccination);
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        } finally {
            disconnect();
        }
        return vaccinations;
    }

    //display all the vaccinations in the table
    public void displayVaccinations(ObservableList<Vaccination> vaccinations) {
        animalIdCol.setCellValueFactory(new PropertyValueFactory<>("animal_id"));
        vaccineNameCol.setCellValueFactory(new PropertyValueFactory<>("vaccine_name"));
        ResponsibleNameCol.setCellValueFactory(new PropertyValueFactory<>("responsible_name"));
        vaccinationDateCol.setCellValueFactory(new PropertyValueFactory<>("vaccination_date"));
        Callback<TableColumn<Vaccination, String>, TableCell<Vaccination, String>> cellFoctory = (TableColumn<Vaccination, String> param) -> {
            final TableCell<Vaccination, String> cell = new TableCell<Vaccination, String>() {
                Image edit_img = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button edit_btn = new Button();
                Image delete_img = new Image(getClass().getResourceAsStream("/images/delete.png"));
                final Button delete_btn = new Button();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
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

                        HBox managebtn = new HBox(edit_btn, delete_btn);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(edit_btn, new Insets(1, 1, 0, 3));
                        HBox.setMargin(delete_btn, new Insets(1, 1, 0, 2));

                        setGraphic(managebtn);
                        setText(null);
                        delete_btn.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete");
                            alert.setHeaderText("Are you sure you want to delete this vaccination?");
                            int index = getRowIndex(event);
                            Vaccination vaccination = vaccinations.get(index);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                try {
                                    vaccination.delete();
                                    displayVaccinations(isSearchingForVaccinations ? vaccinations : getVaccinations());
                                } catch (Exception e) {
                                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                }
                            }
                        });
                        edit_btn.setOnMouseClicked((MouseEvent event) -> {
                            int index = getRowIndex(event);
                            Vaccination vaccination = vaccinations.get(index);
                            String url = "popups/add_new_vaccination.fxml";
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(url));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            VaccinationController controller = fxmlLoader.getController();
                            controller.initData(vaccination);
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });
                    }
                }
            };
            return cell;
        };
        vaccinationActions.setCellFactory(cellFoctory);
        vaccinationTable.setItems(vaccinations);
    }

    //get all the routines
    public ObservableList<Routine> getRoutines() {
        ObservableList<Routine> routines = FXCollections.observableArrayList();
        String query = "SELECT * FROM `routines`";
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Routine routine = new Routine();
                routine.setId(resultSet.getInt("id"));
                routine.setName(resultSet.getString("name"));
                routine.setNote(resultSet.getString("note"));
                routine.setCreated_at(resultSet.getTimestamp("created_at"));
                routines.add(routine);
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        } finally {
            disconnect();
        }
        return routines;
    }

    //display all the routines in the table
    public void displayRoutines(ObservableList<Routine> routines) {
        routineNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        routineNotesCol.setCellValueFactory(new PropertyValueFactory<>("note"));
        routineAdditionDateCol.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        Callback<TableColumn<Routine, String>, TableCell<Routine, String>> cellFoctory = (TableColumn<Routine, String> param) -> {
            final TableCell<Routine, String> cell = new TableCell<Routine, String>() {
                Image edit_img = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button edit_btn = new Button();
                Image delete_img = new Image(getClass().getResourceAsStream("/images/delete.png"));
                final Button delete_btn = new Button();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
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

                        HBox managebtn = new HBox(edit_btn, delete_btn);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(edit_btn, new Insets(1, 1, 0, 3));
                        HBox.setMargin(delete_btn, new Insets(1, 1, 0, 2));

                        setGraphic(managebtn);
                        setText(null);
                        delete_btn.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete");
                            alert.setHeaderText("Are you sure you want to delete this routine?");
                            int index = getRowIndex(event);
                            Routine routine = routines.get(index);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                try {
                                    routine.delete();
                                    displayRoutines(isSearchingForRoutines ? routines : getRoutines());
                                } catch (Exception e) {
                                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                }
                            }
                        });
                        edit_btn.setOnMouseClicked((MouseEvent event) -> {
                            int index = getRowIndex(event);
                            Routine routine = routines.get(index);
                            String url = "popups/add_new_routine.fxml";
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(url));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            RoutineController controller = fxmlLoader.getController();
                            controller.initData(routine);
                            stage.show();
                        });
                    }
                }
            };
            return cell;
        };
        routineActionsCol.setCellFactory(cellFoctory);
        routineTable.setItems(routines);
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

    public ObservableList<HealthStatus> searchForHealthStatus(String searchClause) {
        ObservableList<HealthStatus> monitors = FXCollections.observableArrayList();
        String query = "SELECT * FROM `health_status` WHERE `animal_id` LIKE '%" + searchClause + "%' OR `health_score` LIKE '%" + searchClause + "%' OR `notes` LIKE '%" + searchClause + "%' ORDER BY `created_at` DESC ";
        try {
            ResultSet resultSet = getConnection().prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                HealthStatus healthStatus = new HealthStatus();

                healthStatus.setId(resultSet.getInt("id"));
                healthStatus.setAnimal_id(resultSet.getString("animal_id"));
                healthStatus.setWeight(resultSet.getInt("weight"));
                healthStatus.setBreathing(resultSet.getInt("breathing"));
                healthStatus.setHealth_score(resultSet.getString("health_score"));
                healthStatus.setControl_date(resultSet.getDate("control_date"));
                healthStatus.setNotes(resultSet.getString("notes"));
                healthStatus.setCreated_at(resultSet.getTimestamp("created_at"));
                healthStatus.setUpdated_at(resultSet.getTimestamp("updated_at"));

                monitors.add(healthStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return monitors;
    }

    public ObservableList<Pregnancy> searchForPregnancies(String searchClause) {
        ObservableList<Pregnancy> pregnancies = FXCollections.observableArrayList();
        String query = "SELECT * FROM `pregnancies` WHERE `cow_id` LIKE '%" + searchClause + "%' OR `notes` LIKE '%" + searchClause + "%' ORDER BY `created_at` DESC ";
        try {
            ResultSet resultSet = getConnection().prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                Pregnancy pregnancy = new Pregnancy();

                pregnancy.setId(resultSet.getInt("id"));
                pregnancy.setCow_id(resultSet.getString("cow_id"));
                pregnancy.setStart_date(resultSet.getDate("start_date"));
                pregnancy.setDelivery_date(resultSet.getDate("delivery_date"));
                pregnancy.setPregnancy_status(resultSet.getString("pregnancy_status"));
                pregnancy.setNotes(resultSet.getString("notes"));
                pregnancy.setCreated_at(resultSet.getTimestamp("created_at"));
                pregnancy.setUpdated_at(resultSet.getTimestamp("updated_at"));

                pregnancies.add(pregnancy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return pregnancies;
    }

    public ObservableList<Vaccination> searchForVaccinations(String searchClause) {
        ObservableList<Vaccination> vaccinations = FXCollections.observableArrayList();
        String query = "SELECT * FROM `vaccination` WHERE `animal_id` LIKE '%" + searchClause + "%' OR `vaccine_id` IN (SELECT id FROM stocks WHERE name LIKE '%" + searchClause + "%') OR `responsible_id` IN (SELECT id FROM users WHERE first_name LIKE '%" + searchClause + "%' OR last_name LIKE '%" + searchClause + "%')";
        try {
            ResultSet resultSet = getConnection().prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                Vaccination vaccination = new Vaccination();

                vaccination.setId(resultSet.getInt("id"));
                vaccination.setAnimal_id(resultSet.getString("animal_id"));
                vaccination.setResponsible_id(resultSet.getInt("responsible_id"));
                vaccination.setVaccine_id(resultSet.getInt("vaccine_id"));
                vaccination.setVaccination_date(resultSet.getDate("vaccination_date"));
                vaccination.setUpdated_at(resultSet.getTimestamp("updated_at"));
                vaccination.setCreated_at(resultSet.getTimestamp("created_at"));

                vaccinations.add(vaccination);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return vaccinations;
    }

    public ObservableList<Routine> searchForRoutines(String searchClause) {
        ObservableList<Routine> routines = FXCollections.observableArrayList();
        String query = "SELECT * FROM `routines` WHERE `name` LIKE '%" + searchClause + "%' OR `note` LIKE '%" + searchClause + "'";
        try {
            ResultSet resultSet = getConnection().prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                Routine routine = new Routine();

                routine.setId(resultSet.getInt("id"));
                routine.setName(resultSet.getString("name"));
                routine.setNote(resultSet.getString("note"));
                routine.setCreated_at(resultSet.getTimestamp("created_at"));
                routine.setUpdated_at(resultSet.getTimestamp("updated_at"));

                routines.add(routine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return routines;
    }

    @FXML
    public void healthStatusSearch(MouseEvent mouseEvent) {
        String searchClause = healthStatusSearch.getText().trim();
        if (!searchClause.isEmpty() && searchClause != "") {
            isSearchingForMonitors = true;
            displayMonitors(searchForHealthStatus(searchClause));
        } else {
            isSearchingForMonitors = false;
            displayMonitors(getHealthStatus());
        }
    }

    @FXML
    public void pregnancySearch(MouseEvent mouseEvent) {
        String searchClause = pregnancySearch.getText().trim();
        if (!searchClause.isEmpty() && searchClause != "") {
            isSearchingForPregnancies = true;
            displayPregnancies(searchForPregnancies(searchClause));
        } else {
            isSearchingForPregnancies = false;
            displayPregnancies(getPregnancies());
        }
    }

    @FXML
    public void vaccinationSearch(MouseEvent mouseEvent) {
        String searchClause = vaccineSearch.getText().trim();
        if (!searchClause.isEmpty() && searchClause != "") {
            isSearchingForVaccinations = true;
            displayVaccinations(searchForVaccinations(searchClause));
        } else {
            isSearchingForVaccinations = false;
            displayVaccinations(getVaccinations());
        }
    }

    @FXML
    public void routineSearch(MouseEvent mouseEvent) {
        String searchClause = routineSearch.getText().trim();
        if (!searchClause.isEmpty() && searchClause != "") {
            isSearchingForRoutines = true;
            displayRoutines(searchForRoutines(searchClause));
        } else {
            isSearchingForRoutines = false;
            displayRoutines(getRoutines());
        }
    }
}
