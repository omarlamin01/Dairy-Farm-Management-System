package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.HealthStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.dfms.dairy_farm_management_system.helpers.Helper.*;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.closeWindow;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class HealthStatusController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setAnimals();

        //set health status options
        healthStatus.setItems(healthStatusOptions);

        //set health monitor & vaccine animals ids list
        animalId.setItems(animals);
    }

    @FXML
    ComboBox<String> animalId;
    @FXML
    DatePicker monitorDate;
    @FXML
    ComboBox<String> healthStatus;
    @FXML
    TextArea healthStatusNotes;

    ObservableList<String> healthStatusOptions = FXCollections.observableArrayList("excellent", "good", "bad", "very bad");
    ObservableList<String> animals;

    public void setAnimals() {
        //get animals ids from database
        ArrayList<String> list = getAnimalsIds();
        ObservableList<String> collection = FXCollections.observableArrayList();
        for (String id : list) {
            collection.add(id);
        }
        this.animals = collection;
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

    @FXML
    public void addHealthStatus(MouseEvent mouseEvent) {
        HealthStatus monitor = new HealthStatus();
        monitor.setAnimal_id(Integer.parseInt(animalId.getValue()));
        monitor.setControl_date(monitorDate.getValue());
        monitor.setHealth_score(healthStatus.getValue());
        monitor.setNotes(healthStatusNotes.getText());
        if (monitor.save()) {
            displayAlert("Success", "Health monitor added successfully.", Alert.AlertType.INFORMATION);
            closeWindow(mouseEvent);
        } else {
            displayAlert("Error", "Some error happened while saving!", Alert.AlertType.ERROR);
        }
    }
}
