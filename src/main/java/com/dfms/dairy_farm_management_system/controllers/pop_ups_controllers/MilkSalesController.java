package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.MilkSale;
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

public class MilkSalesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.setClientsList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    ComboBox<String> clientsCombo;
    @FXML
    DatePicker operationDate;
    @FXML
    TextField quantityInput;
    @FXML
    TextField priceOfSale;

    ObservableList<String> clientsList;

    HashMap<String, Integer> clients = new HashMap<>();

    PreparedStatement statement = null;
    ResultSet resultSet = null;

        public void setClientsList() throws SQLException {
            ObservableList<String> client = FXCollections.observableArrayList();

            String select_query = "SELECT name, id from clients ";

            statement = DBConfig.getConnection().prepareStatement(select_query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                clients.put(resultSet.getString("name"), resultSet.getInt("id"));
                client.add(resultSet.getString("name"));
            }

            clientsCombo.setItems(client);
        }


    @FXML
    public void addMilkSale(MouseEvent mouseEvent) {
        if (clientsCombo.getValue() == null || quantityInput.getText().isEmpty() || priceOfSale.getText().isEmpty() || operationDate.getValue() == null) {
            displayAlert("Error", "Please Fill all fields ", Alert.AlertType.ERROR);
        } else if (Float.parseFloat(priceOfSale.getText()) == 0||Float.parseFloat(quantityInput.getText()) == 0) {
            displayAlert("Error", "Price or quantity can not  be null ", Alert.AlertType.ERROR);
        } else {
            MilkSale milkSale = new MilkSale();
            milkSale.setQuantity(Float.parseFloat(quantityInput.getText()));
            milkSale.setPrice(Float.parseFloat(priceOfSale.getText()));
            milkSale.setClientId(clients.get(clientsCombo.getValue()));
            milkSale.setSale_date(Date.valueOf(operationDate.getValue()));
            if (milkSale.save()) {
                closePopUp(mouseEvent);
                displayAlert("success", "Sale added successfully", Alert.AlertType.INFORMATION);
            } else {
                displayAlert("Error", "Error while saving!!!", Alert.AlertType.ERROR);
            }
        }
    }
}
