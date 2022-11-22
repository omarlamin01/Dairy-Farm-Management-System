package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.closePopUp;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class NewAnimalController implements Initializable {
    @FXML
    private Label Add_Update;

    @FXML
    private Label Head;
    @FXML
    private ComboBox<String> typeCombo;
    @FXML
    private ComboBox<String> raceCombo;
    @FXML
    private DatePicker birthDate;
    @FXML
    private DatePicker purchaseDate;
    @FXML
    private ComboBox<String> routineCombo;
    @FXML
    private Button btn_add_animal;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    String animal_ID;
    private Connection connection = DBConfig.getConnection();
    private boolean update;
    String query = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setRaceComboItems();
        try {
            setRoutineComboItems();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.setTypeComboItems();
    }

    public void setTypeComboItems() {
        this.typeCombo.setItems(FXCollections.observableArrayList("Cow", "Bull", "Calf"));
    }

    public HashMap<String, Integer> getRaces() {
        HashMap<String, Integer> races = new HashMap<>();
        String query = "SELECT `name`, `id` FROM `races`";
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                races.put(resultSet.getString("name"), resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return races;
    }

    public void setRaceComboItems() {
        ObservableList<String> races = FXCollections.observableArrayList();
        String[] names = getRaces().keySet().toArray(new String[0]);
        for (String race : names) {
            races.add(race);
        }
        raceCombo.setItems(races);
    }

    public HashMap<String, Integer> getRoutines() {
        HashMap<String, Integer> routines = new HashMap<>();
        String query = "SELECT `name`, `id` FROM `routines`";
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                routines.put(resultSet.getString("name"), resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routines;
    }

    public void setRoutineComboItems() throws SQLException {
        ObservableList<String> routines = FXCollections.observableArrayList();
        String[] names = getRoutines().keySet().toArray(new String[0]);
        for (String routine : names) {
            routines.add(routine);
        }
        routineCombo.setItems(routines);
    }

    private void clear() {
        typeCombo.getSelectionModel().clearSelection();
        routineCombo.getSelectionModel().clearSelection();
        raceCombo.getSelectionModel().clearSelection();
        purchaseDate.setValue(null);
        birthDate.setValue(null);
        btn_add_animal.setDisable(false);
    }

    @FXML
    void addAnimal(MouseEvent event) {
        Animal animal = new Animal();
        if (this.update) {
            animal.setId(this.animal_ID);
            animal.setBirth_date(Date.valueOf(birthDate.getValue()));
            animal.setPurchase_date(Date.valueOf(purchaseDate.getValue()));
            animal.setRaceId(getRaces().get(raceCombo.getValue()) == null ? 0 : getRaces().get(raceCombo.getValue()));
            animal.setRoutineId(getRoutines().get(routineCombo.getValue()) == null ? 0 : getRoutines().get(routineCombo.getValue()));
            animal.setType(typeCombo.getValue());
            if (animal.update()) {
                clear();
                closePopUp(event);
                displayAlert("Success", "animal updated successfully.", Alert.AlertType.INFORMATION);
            } else {
                displayAlert("Warning", "animal not updated", Alert.AlertType.WARNING);
            }
        } else {
            Random random = new Random();
            animal.setBirth_date(Date.valueOf(birthDate.getValue()));
            animal.setPurchase_date(Date.valueOf(purchaseDate.getValue()));
            animal.setRaceId(getRaces().get(raceCombo.getValue()) == null ? 0 : getRaces().get(raceCombo.getValue()));
            animal.setRoutineId(getRoutines().get(routineCombo.getValue()) == null ? 0 : getRoutines().get(routineCombo.getValue()));
            animal.setType(typeCombo.getValue());
            String id = animal.getType() + random.nextInt();
            animal.setId(id);
            if (animal.add()) {
                clear();
                //closePopUp(event);
                displayAlert("Success", "New animal added successfully.", Alert.AlertType.INFORMATION);
            } else {
                displayAlert("Error", "Some error happened while saving!", Alert.AlertType.ERROR);
            }
        }

    }

    public void setUpdate(boolean b) {
        this.update = b;
    }

    public void fetchAnimal(String id_animal, String race_animal, LocalDate birthdate, String
            routine_animal, LocalDate purchasedate, String type_animal) {
        this.animal_ID = id_animal;
        Head.setText("Update Cow Num: " + id_animal);
        birthDate.setValue(birthdate);
        raceCombo.setValue(race_animal);
        routineCombo.setValue(routine_animal);
        typeCombo.setValue(type_animal);
        purchaseDate.setValue(purchasedate);
        btn_add_animal.setText("Update");
        Add_Update.setText("Update");

    }
}
