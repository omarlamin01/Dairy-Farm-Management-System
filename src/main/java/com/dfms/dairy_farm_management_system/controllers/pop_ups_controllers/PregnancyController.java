package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Pregnancy;
import javafx.collections.FXCollections;
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
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class PregnancyController implements Initializable {
    @FXML
    ComboBox<String> cowPregnancyID;

    @FXML
    DatePicker pregnancyStartDate;
    @FXML
    TextArea pregnancyNotes;
    ObservableList<String> cows;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setCows();

        //set cows list
        cowPregnancyID.setItems(cows);
    }

    public void setCows() {
        //get cows ids from db
        ObservableList<String> collection = FXCollections.observableArrayList();
        for (String cow : getCows()) {
            collection.add(cow);
        }
        this.cows = collection;
    }

    public ArrayList<String> getCows() {
        ArrayList<String> cows = new ArrayList<String>();
        String query = "SELECT id FROM `animal` WHERE `type` = 'cow' ORDER BY created_at DESC";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cows.add(resultSet.getString("id"));
            }
        } catch (SQLException e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        return cows;
    }

    @FXML
    public void addPregnancy(MouseEvent mouseEvent) {
        Pregnancy pregnancy = new Pregnancy();
        pregnancy.setCow_id(Integer.parseInt(cowPregnancyID.getValue()));
        pregnancy.setStart_date(pregnancyStartDate.getValue());
        pregnancy.setNotes(pregnancyNotes.getText());
        pregnancy.setType("natural");
        if (pregnancy.save()) {
            closePopUp(mouseEvent);
            displayAlert("Success", "Pregnancy added successfully.", Alert.AlertType.INFORMATION);
        } else {
            displayAlert("Error", "Some error happened while saving!", Alert.AlertType.ERROR);
        }
    }
}
