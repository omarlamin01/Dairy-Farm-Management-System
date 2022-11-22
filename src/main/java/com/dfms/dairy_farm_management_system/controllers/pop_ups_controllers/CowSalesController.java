package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.AnimalSale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class CowSalesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.setAnimalsList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            this.setClientsList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        validateDecimalInput(priceOfSale);
    }

    @FXML
    ComboBox<String> clientsCombo;
    @FXML
    DatePicker operationDate;
    @FXML
    ComboBox<String> animalsCombo;
    @FXML
    TextField priceOfSale;

    HashMap<String, Integer> clients = new HashMap<>();
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    public void setClientsList() throws SQLException {
        ObservableList<String> clientNames = FXCollections.observableArrayList();

        String query = "SELECT id, name from clients ";

        statement = DBConfig.getConnection().prepareStatement(query);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            clients.put(resultSet.getString("name"), resultSet.getInt("id"));
            clientNames.add(resultSet.getString("name"));
        }
        clientsCombo.setItems(clientNames);
    }

    public void setAnimalsList() throws SQLException {
        ObservableList<String> animals = FXCollections.observableArrayList();

        String select_query = "SELECT id from animals ";

        statement = DBConfig.getConnection().prepareStatement(select_query);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {

            animals.add(resultSet.getString("id"));
        }

        animalsCombo.setItems(animals);
    }

    @FXML
    public void addCowSale(MouseEvent mouseEvent) {
        if (clientsCombo.getValue() == null || animalsCombo.getValue() == null || priceOfSale.getText().isEmpty() || operationDate.getValue() == null) {
            displayAlert("Error", "Please Fill all fields ", Alert.AlertType.ERROR);
        } else if (Float.parseFloat(priceOfSale.getText()) == 0) {
            displayAlert("Error", "Price can't be null ", Alert.AlertType.ERROR);
        } else {
            AnimalSale animalSale = new AnimalSale();
            animalSale.setAnimalId(animalsCombo.getValue());
            animalSale.setPrice(Float.parseFloat(priceOfSale.getText()));
            animalSale.setClientId(clients.get(clientsCombo.getValue()));
            animalSale.setSale_date(Date.valueOf(operationDate.getValue()));
            if (animalSale.save()) {
                closePopUp(mouseEvent);
                displayAlert("success", "Sale added successfully", Alert.AlertType.INFORMATION);

            } else {
                displayAlert("Error", "Error while saving!!!", Alert.AlertType.ERROR);
            }


        }
    }
}
