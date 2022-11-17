package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.MilkCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.closePopUp;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;


public class NewMilkCollectionController implements Initializable {
    @FXML
    private ComboBox<String> cowid;

    @FXML
    private TextField milkquantity_input;

    @FXML
    private ComboBox<String> period_input;
    PreparedStatement st = null;
    ResultSet rs = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            setCowComboItems();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.setPeriodComboItems();


    }

    public void setPeriodComboItems() {
        this.period_input.setItems(FXCollections.observableArrayList("morning", "evening"));
    }

    public void setCowComboItems() throws SQLException {

        ObservableList<String> cows = FXCollections.observableArrayList();

        String select_query = "SELECT id from animal  where type='cow';";

        st = DBConfig.getConnection().prepareStatement(select_query);
        rs = st.executeQuery();
        while (rs.next()) {

            cows.add(rs.getString("id"));
        }

        cowid.setItems(cows);
    }

    @FXML


    public void addMilkCollection(MouseEvent mouseEvent) {
        if (period_input.getValue() == null || cowid.getValue() == null || milkquantity_input.getText().isEmpty()) {
            displayAlert("Error", "Please Fill all field ", Alert.AlertType.ERROR);
        } else if (Float.parseFloat(milkquantity_input.getText()) == 0) {
            displayAlert("Error", "Quantity can't be null ", Alert.AlertType.ERROR);
        } else {
            MilkCollection milkCollection = new MilkCollection();
            milkCollection.setPeriod(period_input.getValue());
            milkCollection.setQuantity(Float.parseFloat(milkquantity_input.getText()));
            milkCollection.setCow_id(cowid.getValue());
            if (milkCollection.save()) {
                closePopUp(mouseEvent);
                displayAlert("success", "Milk Collection added successfull", Alert.AlertType.INFORMATION);

            } else {
                displayAlert("Error", "Error while saving!!!", Alert.AlertType.ERROR);
            }


        }
    }
}

