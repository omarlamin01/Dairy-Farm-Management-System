package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.AnimalSale;
import com.dfms.dairy_farm_management_system.models.Employee;
import com.dfms.dairy_farm_management_system.models.MilkCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
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
    @FXML
    private Label header;

    @FXML
    private Label key;
    @FXML
    private Button add_update;
    private  int AnimalSale_ID;
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
    private boolean update;
    public void setUpdate(boolean b) {
     this.update = b;
    }
    @FXML
    public void addCowSale(MouseEvent mouseEvent) {
        AnimalSale animalSale = new AnimalSale();
        if (this.update) {
            animalSale.setId(this.AnimalSale_ID);
            animalSale.setAnimalId(animalsCombo.getValue());
            animalSale.setPrice(Float.parseFloat(priceOfSale.getText()));
            animalSale.setClientId(clients.get(clientsCombo.getValue()));
            animalSale.setSale_date(Date.valueOf(operationDate.getValue()));
            if (animalSale.update()) {

                if (clientsCombo.getValue() == null || animalsCombo.getValue() == null || priceOfSale.getText().isEmpty() || operationDate.getValue() == null) {
                    displayAlert("Error", "Please Fill all fields ", Alert.AlertType.ERROR);
                } else if (Float.parseFloat(priceOfSale.getText()) == 0) {
                    displayAlert("Error", "Price can't be null ", Alert.AlertType.ERROR);
                } else {

                    clear();
                    closePopUp(mouseEvent);
                    displayAlert("success", "Animal Sale Updated successfully", Alert.AlertType.INFORMATION);

                } }}else {
            animalSale.setAnimalId(animalsCombo.getValue());
            animalSale.setPrice(Float.parseFloat(priceOfSale.getText()));
            animalSale.setClientId(clients.get(clientsCombo.getValue()));
            animalSale.setSale_date(Date.valueOf(operationDate.getValue()));
        if (clientsCombo.getValue() == null || animalsCombo.getValue() == null || priceOfSale.getText().isEmpty() || operationDate.getValue() == null) {
            displayAlert("Error", "Please Fill all fields ", Alert.AlertType.ERROR);
        } else if (Float.parseFloat(priceOfSale.getText()) == 0) {
            displayAlert("Error", "Price can't be null ", Alert.AlertType.ERROR);
        } else {

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
    public  void fetchAnimalSale(int ID, String animal, Float price, String client, Date operationdate){
//        animal = getAnimal(AnimalDetailsController.id_animal);
        this.AnimalSale_ID = ID;
        header.setText("Update Animal Sale num :  " + ID);
        animalsCombo.setValue(animal + "");
        clientsCombo.setValue(client + "");
        priceOfSale.setText(price + "");
        operationDate.setValue(LocalDate.parse(operationdate + ""));
        key.setText("Update");
        add_update.setText("Update");


    }
    private void clear () {
        animalsCombo.getSelectionModel().clearSelection();
        clientsCombo.getSelectionModel().clearSelection();
        priceOfSale.clear();
        operationDate.setValue(null);
        add_update.setDisable(false);
    }
    public AnimalSale getSale(int animalSale_ID) {
        AnimalSale animalSale = new AnimalSale();
        String query = "SELECT * FROM `animals_sales`   where id='" + animalSale_ID + "' LIMIT 1";
        Connection con = getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                animalSale.setId(rs.getInt("id"));
                animalSale.setAnimalId(rs.getString("animal_id"));
                animalSale.setPrice(rs.getFloat("price"));
                animalSale.setClientId(rs.getInt("client_id"));
                animalSale.setSale_date(rs.getDate("sale_date"));


            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        return animalSale;
    }
}
