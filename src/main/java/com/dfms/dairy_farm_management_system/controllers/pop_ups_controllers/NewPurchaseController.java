package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class NewPurchaseController implements Initializable {
    @FXML
    private Button add_update;

    @FXML
    private Label header;

    @FXML
    private Label key;

    @FXML
    private DatePicker operationDate;

    @FXML
    private TextField priceOfSale;

    @FXML
    private TextField quantityInput;

    @FXML
    private ComboBox<String> suppliersCombo;
    private  int Purchase_ID;
    HashMap<String, Integer> suppliers = new HashMap<>();
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    @FXML
    void addPurchase(MouseEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setSupplierList() throws SQLException {
        ObservableList<String> supplierNames = FXCollections.observableArrayList();

        String query = "SELECT id, name from suppliers ";

        statement = DBConfig.getConnection().prepareStatement(query);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            suppliers.put(resultSet.getString("name"), resultSet.getInt("id"));
            supplierNames.add(resultSet.getString("name"));
        }
        suppliersCombo.setItems(supplierNames);
    }
    public void setProuctsList() throws SQLException {
        ObservableList<String> products = FXCollections.observableArrayList();

        String select_query = "SELECT type from purchase ";

        statement = DBConfig.getConnection().prepareStatement(select_query);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {

            products.add(resultSet.getString("type"));
        }

        p.setItems(animals);
    }
}
