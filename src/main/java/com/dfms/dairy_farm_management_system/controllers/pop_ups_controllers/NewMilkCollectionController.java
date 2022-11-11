package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.MilkCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

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


    public void addMilkCollection(javafx.scene.input.MouseEvent mouseEvent) {
    }
}
