package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Animal;
import com.dfms.dairy_farm_management_system.models.MilkCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.Random;
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
    @FXML
    private Label header;

    @FXML
    private Label key;


    @FXML
    private Button Add_Update;
    private int MilkCollection_ID;
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

        String select_query = "SELECT id from animals  where type='cow';";

        st = DBConfig.getConnection().prepareStatement(select_query);
        rs = st.executeQuery();
        while (rs.next()) {

            cows.add(rs.getString("id"));
        }

        cowid.setItems(cows);
    }

    private boolean update;
    public void setUpdate(boolean b) {

        this.update = b;
    }
    @FXML
 public void addMilkCollection(MouseEvent mouseEvent) throws SQLException{
       /* MilkCollection   milkCollection=new  MilkCollection();
        if (this.update) {
            milkCollection.setCow_id(milkCollection.getCow_id());
            milkCollection.setPeriod(milkCollection.getPeriod());

            milkCollection.setQuantity(milkCollection.getQuantity());
            }
        if (milkCollection.update()) {

        if (period_input.getValue() == null || cowid.getValue() == null || milkquantity_input.getText().isEmpty()) {
            displayAlert("Error", "Please Fill all field ", Alert.AlertType.ERROR);
        } else if (Float.parseFloat(milkquantity_input.getText()) == 0) {
            displayAlert("Error", "Quantity can't be null ", Alert.AlertType.ERROR);
        } else {
            milkCollection.setPeriod(period_input.getValue());
            milkCollection.setQuantity(Float.parseFloat(milkquantity_input.getText()));
            milkCollection.setCow_id(cowid.getValue());
                clear();
                closePopUp(mouseEvent);
                displayAlert("success", "Milk Collection Updated successfully", Alert.AlertType.INFORMATION);

            } else {
                displayAlert("Error", "Error while saving!!!", Alert.AlertType.ERROR);
            }}
        } else {
                milkCollection.setCow_id(milkCollection.getCow_id());
                milkCollection.setPeriod(milkCollection.getPeriod());

                milkCollection.setQuantity(milkCollection.getQuantity());

                if (milkCollection.save()) {
                if (period_input.getValue() == null || cowid.getValue() == null || milkquantity_input.getText().isEmpty()) {
                    displayAlert("Error", "Please Fill all field ", Alert.AlertType.ERROR);
                } else if (Float.parseFloat(milkquantity_input.getText()) == 0) {
                    displayAlert("Error", "Quantity can't be null ", Alert.AlertType.ERROR);
                } else {
                    milkCollection.setPeriod(period_input.getValue());
                    milkCollection.setQuantity(Float.parseFloat(milkquantity_input.getText()));
                    milkCollection.setCow_id(cowid.getValue());
                    if (milkCollection.save()) {
                        clear();
                        closePopUp(mouseEvent);
                        displayAlert("success", "Milk Collection Added successfully", Alert.AlertType.INFORMATION);

                    } else {
                        displayAlert("Error", "Error while saving!!!", Alert.AlertType.ERROR);
                    }

        }}*/

        MilkCollection milkCollection = new MilkCollection();
       if (this.update) {
            milkCollection.setId(this.MilkCollection_ID);
            milkCollection.setCow_id(cowid.getValue());
            milkCollection.setPeriod(period_input.getValue());
            milkCollection.setQuantity(Float.parseFloat(milkquantity_input.getText()));
            if (milkCollection.update()) {
              if (period_input.getValue() == null || cowid.getValue() == null || milkquantity_input.getText().isEmpty()) {
                    displayAlert("Error", "Please Fill all field ", Alert.AlertType.ERROR);
                } else if (Float.parseFloat(milkquantity_input.getText()) == 0) {
                    displayAlert("Error", "Quantity can't be null ", Alert.AlertType.ERROR);
                } else {

                clear();
                closePopUp(mouseEvent);
                displayAlert("success", "Milk Collection Updated successfully", Alert.AlertType.INFORMATION);

        } }}else {
        milkCollection.setPeriod(period_input.getValue());
        milkCollection.setQuantity(Float.parseFloat(milkquantity_input.getText()));
        milkCollection.setCow_id(cowid.getValue());
           if (milkCollection.save()) {
               if (period_input.getValue() == null || cowid.getValue() == null || milkquantity_input.getText().isEmpty()) {
                   displayAlert("Error", "Please Fill all field ", Alert.AlertType.ERROR);
               } else if (Float.parseFloat(milkquantity_input.getText()) == 0) {
                   displayAlert("Error", "Quantity can't be null ", Alert.AlertType.ERROR);
               } else {
                   clear();
                   closePopUp(mouseEvent);
                   displayAlert("success", "Milk Collection Added successfully", Alert.AlertType.INFORMATION);

               }
               }}}



           public void fetchMilkCollection ( int ID, String cow, String periode, Float quantitymilk){
//        animal = getAnimal(AnimalDetailsController.id_animal);
               this.MilkCollection_ID = ID;
               header.setText("Update Milk collection :  " + ID);
               cowid.setValue(cow + "");
               period_input.setValue(periode + "");
               milkquantity_input.setText(quantitymilk + "");
               key.setText("Update");
               Add_Update.setText("Update");


           }


           private void clear () {
               cowid.getSelectionModel().clearSelection();
               period_input.getSelectionModel().clearSelection();
               milkquantity_input.clear();
               Add_Update.setDisable(false);
           }
       }

