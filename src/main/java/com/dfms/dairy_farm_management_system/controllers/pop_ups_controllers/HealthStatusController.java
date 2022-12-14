package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.HealthStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class HealthStatusController implements Initializable {
    @FXML
    ComboBox<String> animalId;
    @FXML
    DatePicker monitorDate;
    @FXML
    ComboBox<String> healthStatusCombo;
    @FXML
    TextArea healthStatusNotes;
    @FXML
    Button addHealthMonitorBtn;

    ObservableList<String> healthStatusOptions = FXCollections.observableArrayList("excellent", "good", "bad", "very bad");
    ObservableList<String> animals;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setAnimals();
        healthStatusCombo.setItems(healthStatusOptions);
        animalId.setItems(animals);
        addHealthMonitorBtn.setOnMouseClicked((MouseEvent mouseEvent) -> {
            HealthStatus monitor = new HealthStatus();
            monitor.setAnimal_id(animalId.getValue());
            monitor.setControl_date(Date.valueOf(monitorDate.getValue()));
            monitor.setHealth_score(healthStatusCombo.getValue());
            monitor.setNotes(healthStatusNotes.getText());
            if (monitor.save()) {
                closePopUp(mouseEvent);
                displayAlert("Success", "Health monitor added successfully.", Alert.AlertType.INFORMATION);
            } else {
                displayAlert("Error", "Some error happened while saving!", Alert.AlertType.ERROR);
            }
        });
    }

    public void initData(HealthStatus healthStatus) {
        animalId.setValue(healthStatus.getAnimal_id());
        monitorDate.setValue(healthStatus.getControl_date().toLocalDate());
        healthStatusCombo.setValue(healthStatus.getHealth_score());
        healthStatusNotes.setText(healthStatus.getNotes());
        addHealthMonitorBtn.setText("UPDATE");
        addHealthMonitorBtn.setOnMouseClicked((MouseEvent event) -> {
            healthStatus.setAnimal_id(animalId.getValue());
            healthStatus.setControl_date(Date.valueOf(monitorDate.getValue()));
            healthStatus.setHealth_score(healthStatusCombo.getValue());
            healthStatus.setNotes(healthStatusNotes.getText());
            if (healthStatus.update()) {
                closePopUp(event);
                displayAlert("Success", "Health monitor updated successfully.", Alert.AlertType.INFORMATION);
            } else {
                displayAlert("Error", "Some error happened while updating!", Alert.AlertType.ERROR);
            }
        });
    }

    public void setAnimals() {
        //get animals ids from database
        ObservableList<String> collection = FXCollections.observableArrayList();
        for (String id : getAnimalsIds()) {
            collection.add(id);
        }
        this.animals = collection;
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
}
