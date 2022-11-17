package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.validateDecimalInput;

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
        System.out.println("Sale: {" + " Client: \"" + this.clientsCombo.getValue() + "\"," + " Animal ID: \"" + this.animalsCombo.getValue() + "\"," + " Operation date: \"" + this.operationDate.getValue() + "\"," + " Price of sale: \"" + this.priceOfSale.getText() + "\" " + "}");

        ((Stage) (((Button) mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
