package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.AnimalSale;
import com.dfms.dairy_farm_management_system.models.MilkSale;
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

public class MilkSalesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.setClientsList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        validateDecimalInput(priceOfSale);
        validateDecimalInput(quantityInput);
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

    @FXML
    private Button add_update;


    @FXML
    private Label header;

    @FXML
    private Label key;
    private  int MilkSale_ID;

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

    private boolean update;
    public void setUpdate(boolean b) {
        this.update = b;
    }
    @FXML
    public void addMilkSale(MouseEvent mouseEvent) {
        MilkSale milkSale = new MilkSale();
        if (this.update) {

            if (milkSale.update()) {

                if (clientsCombo.getValue() == null || quantityInput.getText().isEmpty() || priceOfSale.getText().isEmpty() || operationDate.getValue() == null) {
                    displayAlert("Error", "Please Fill all fields ", Alert.AlertType.ERROR);
                } else if (Float.parseFloat(priceOfSale.getText()) == 0) {
                    displayAlert("Error", "Price can't be null ", Alert.AlertType.ERROR);
                } else {
                    milkSale.setId(this.MilkSale_ID);
                    milkSale.setQuantity(Float.parseFloat(quantityInput.getText()));
                    milkSale.setPrice(Float.parseFloat(priceOfSale.getText()));
                    milkSale.setClientId(clients.get(clientsCombo.getValue()));
                    milkSale.setSale_date(Date.valueOf(operationDate.getValue()));
                    clear();
                    closePopUp(mouseEvent);
                    displayAlert("success", "Milk Sale Updated successfully", Alert.AlertType.INFORMATION);

                } }}else {

        if (clientsCombo.getValue() == null || quantityInput.getText().isEmpty() || priceOfSale.getText().isEmpty() || operationDate.getValue() == null) {
            displayAlert("Error", "Please Fill all fields ", Alert.AlertType.ERROR);
        } else if (Float.parseFloat(priceOfSale.getText()) == 0||Float.parseFloat(quantityInput.getText()) == 0) {
            displayAlert("Error", "Price or quantity can not  be null ", Alert.AlertType.ERROR);
        } else {

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
        }}
    }
    public  void fetchMilkSale(int ID, Float Quantity, Float price, String client, Date operationdate){
//        animal = getAnimal(AnimalDetailsController.id_animal);
        this.MilkSale_ID = ID;
        header.setText("Update Milk Sale num :  " + ID);
        quantityInput.setText(Quantity + "");
        clientsCombo.setValue(client + "");
        priceOfSale.setText(price + "");
        operationDate.setValue(LocalDate.parse(operationdate + ""));
        key.setText("Update");
        add_update.setText("Update");


    }
    private void clear () {
        quantityInput.clear();
        clientsCombo.getSelectionModel().clearSelection();
        priceOfSale.clear();
        operationDate.setValue(null);
        add_update.setDisable(false);
    }
    public MilkSale getSale(int milkSale_ID) {
        MilkSale milkSale = new MilkSale();
        String query = "SELECT * FROM `milk_sales`   where id='" + milkSale_ID + "' LIMIT 1";
        Connection con = getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                milkSale.setId(rs.getInt("id"));
                milkSale.setQuantity(rs.getFloat("quantity"));
                milkSale.setPrice(rs.getFloat("price"));
                milkSale.setClientId(rs.getInt("client_id"));
                milkSale.setSale_date(rs.getDate("sale_date"));


            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        return milkSale;
    }
}
