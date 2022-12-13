package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.AnimalDetailsController;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.NewAnimalController;
import com.dfms.dairy_farm_management_system.models.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.*;
import static com.dfms.dairy_farm_management_system.helpers.Helper.openNewWindow;

public class ManageAnimalTest implements Initializable {
    @FXML
    private TableView<Animal> animals;

    @FXML
    private TableColumn<Animal, String> colid;

    @FXML
    private TableColumn<Animal, String> coltype;

    @FXML
    private TableColumn<Animal, String> colrace;

    @FXML
    private TableColumn<Animal, Date> colbirth;

    @FXML
    private TableColumn<Animal, String> colroutine;
    @FXML
    private TableColumn<Animal, String> colactions;
    @FXML
    private ComboBox<String> export_combo;
    @FXML
    private TextField textField_search;

    private Connection connection = getConnection();
    private PreparedStatement preparedStatement;
    private Statement statement;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayAnimals();
    }
    public ObservableList<Animal> getAnimals() {
        ObservableList<Animal> listAnimal = FXCollections.observableArrayList();
        String select_query = "SELECT * from `animals`";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select_query);
            while (resultSet.next()) {
                Animal animal = new Animal();
                animal.setId(resultSet.getString("id"));
                animal.setBirth_date(resultSet.getDate("birth_date"));
                animal.setPurchase_date(resultSet.getDate("purchase_date"));
                animal.setRoutineId(resultSet.getInt("routine"));
                animal.setRaceId(resultSet.getInt("race"));
                animal.setType(resultSet.getString("type"));
                animal.setCreated_at(resultSet.getTimestamp("created_at"));
                animal.setUpdated_at(resultSet.getTimestamp("updated_at"));
                listAnimal.add(animal);
            }
        } catch (SQLException e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        return listAnimal;
    }
    public void displayAnimals() {
        ObservableList<Animal> list = getAnimals();
        colid.setCellValueFactory(new PropertyValueFactory<Animal, String>("id"));
        coltype.setCellValueFactory(new PropertyValueFactory<Animal, String>("type"));
        colbirth.setCellValueFactory(new PropertyValueFactory<Animal, Date>("birth_date"));
        colrace.setCellValueFactory(new PropertyValueFactory<Animal, String>("raceName"));
        colroutine.setCellValueFactory(new PropertyValueFactory<Animal, String>("routineName"));
        animals.setItems(list);
    }
    @FXML
    void openAddNewRace(MouseEvent event) throws IOException {
//        openNewWindow("Add New Race", "add_new_race");
    }
//    public void openAddNewAnimal(MouseEvent mouseEvent) throws IOException {
//        openNewWindow("Add New Animal", "add_new_animal");
//    }
    public void refreshTable(MouseEvent mouseEvent) {
        //refreshTableAnimal();
    }
    }
