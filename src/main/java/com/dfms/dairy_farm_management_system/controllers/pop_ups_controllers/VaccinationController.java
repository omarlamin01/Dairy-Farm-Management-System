package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Vaccination;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.connection.Session.getCurrentUser;
import static com.dfms.dairy_farm_management_system.helpers.Helper.closePopUp;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class VaccinationController implements Initializable {
    @FXML
    ComboBox<String> animalVaccine;
    @FXML
    ComboBox<String> vaccineId;
    @FXML
    DatePicker vaccinationDate;
    @FXML
    TextArea vaccineNotes;

    ObservableList<String> vaccines;
    ObservableList<String> animals;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setVaccines();
        this.setAnimals();
    }

    public void setAnimals() {
        this.animals = FXCollections.observableArrayList();
        for (String id : getAnimalsIds()) {
            animals.add(id);
        }
        animalVaccine.setItems(animals);
    }

    public ArrayList<String> getAnimalsIds() {
        ArrayList<String> ids = new ArrayList<String>();
        String query = "SELECT id FROM animals ORDER BY created_at DESC";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public void setVaccines() {
        this.vaccines = FXCollections.observableArrayList();
        Object[] strings = getVaccinesIds().keySet().toArray();
        System.out.println(strings);
        int i = 0;
        for (Object vaccine_name : strings) {
            this.vaccines.add((String) vaccine_name);
        }
        vaccineId.setItems(this.vaccines);
    }

    public HashMap<String, Integer> getVaccinesIds() {
        HashMap<String, Integer> vaccinesIds = new HashMap<>();
        String query = "SELECT id, name FROM `stocks` WHERE `type` = 'Vaccine' ORDER BY created_at DESC";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                vaccinesIds.put(resultSet.getString("name"), resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(vaccinesIds);
        return vaccinesIds;
    }

    @FXML
    public void addVaccination(MouseEvent mouseEvent) {
        Vaccination vaccination = new Vaccination();
        vaccination.setAnimal_id(animalVaccine.getValue());
        vaccination.setVaccine_id(getVaccinesIds().get(vaccineId.getValue()));
        vaccination.setVaccination_date(Date.valueOf(vaccinationDate.getValue()));
        vaccination.setResponsible_id(getCurrentUser().getId());
        if (vaccination.save()) {
            displayAlert("SUCCESS", "vaccination saved successfully.", Alert.AlertType.INFORMATION);
            closePopUp(mouseEvent);
        } else
            displayAlert("ERROR", "some error happened while saving!", Alert.AlertType.ERROR);
    }
}
