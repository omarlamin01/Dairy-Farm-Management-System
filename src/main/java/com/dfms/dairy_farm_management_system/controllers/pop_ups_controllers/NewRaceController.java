package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import static com.dfms.dairy_farm_management_system.helpers.Helper.closePopUp;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Animal;
import com.dfms.dairy_farm_management_system.models.Race;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class NewRaceController implements Initializable {

    @FXML
    private TextField nameRace;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    private void clear() {
        nameRace.setText("");
    }

    @FXML
    void addNewRace(MouseEvent event) {
        Race race = new Race();
        if (nameRace.getText().isEmpty()) {
            displayAlert("Error", "Please fill all the fields", Alert.AlertType.ERROR);
            return;
        }
        race.setName(nameRace.getText());
        if (race.add()) {
            clear();
            //closePopUp(event);
            displayAlert("Success", "New race added successfully.", Alert.AlertType.INFORMATION);
        } else {
            displayAlert("Error", "Some error happened while saving!", Alert.AlertType.ERROR);
        }
    }
}
