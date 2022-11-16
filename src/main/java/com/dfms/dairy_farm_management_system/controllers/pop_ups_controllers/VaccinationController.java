package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Vaccination;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

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
        String query = "SELECT id FROM animal ORDER BY created_at DESC";
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
            System.out.println(i++ + " => " + vaccine_name);
            this.vaccines.add((String) vaccine_name);
        }
        vaccineId.setItems(this.vaccines);
    }

    public HashMap<String, String> getVaccinesIds() {
        HashMap<String, String> vaccinesIds = new HashMap<String, String>();
        String query = "SELECT id, name FROM `stock` WHERE `type` = 'Vaccine' ORDER BY created_at DESC";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                vaccinesIds.put(resultSet.getString("name"), resultSet.getString("id"));
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
        vaccination.setDate(String.valueOf(vaccinationDate.getValue()));
        System.out.println("Vaccination { " +
                "Animal id: \"" + animalVaccine.getValue() + "\", " +
                "Vaccine id: \"" + vaccineId.getValue() + "\", " +
                "Vaccination date: \"" + vaccinationDate.getValue() + "\", " +
                "Notes: \"" + vaccineNotes.getText() + "\" " +
                "},"
        );

        ((Stage) (((Button) mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
