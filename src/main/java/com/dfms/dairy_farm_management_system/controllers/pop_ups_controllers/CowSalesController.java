package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.AnimalSale;
import com.dfms.dairy_farm_management_system.models.MilkCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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


    PreparedStatement st = null;
    ResultSet rs = null;
    public void setClientsList() throws SQLException {
        ObservableList<String> client = FXCollections.observableArrayList();

        String select_query = "SELECT name from client ";

        st = DBConfig.getConnection().prepareStatement(select_query);
        rs = st.executeQuery();
        while (rs.next()) {

            client.add(rs.getString("name"));
        }

        clientsCombo.setItems(client);
    }

    public void setAnimalsList() throws SQLException {
        ObservableList<String> animals = FXCollections.observableArrayList();

        String select_query = "SELECT id from animal ";

        st = DBConfig.getConnection().prepareStatement(select_query);
        rs = st.executeQuery();
        while (rs.next()) {

            animals.add(rs.getString("id"));
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
            animalSale.setId_animal(animalsCombo.getValue());
            animalSale.setPrice(Float.parseFloat(priceOfSale.getText()));
            animalSale.setId_client(clientsCombo.getValue());
            animalSale.setOperationDate(operationDate.getValue());
            if (animalSale.save()) {
                closePopUp(mouseEvent);
                displayAlert("success", "Sale added successfully", Alert.AlertType.INFORMATION);

            } else {
                displayAlert("Error", "Error while saving!!!", Alert.AlertType.ERROR);
            }


        }
    }
}
