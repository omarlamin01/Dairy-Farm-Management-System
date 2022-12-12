package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.AnimalSale;
import com.dfms.dairy_farm_management_system.models.MilkCollection;
import com.dfms.dairy_farm_management_system.models.Purchase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.disconnect;
import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

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
    HashMap<String, Integer> stocks = new HashMap<>();
    @FXML
    private ComboBox<String> stockCombo;
    @FXML
    private Button openAddNewProduct;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
       @FXML
    void addPurchase(MouseEvent event) {
        Purchase purchase = new Purchase();
        if (this.update) {

            if (purchase.update()) {

                if (suppliersCombo.getValue() == null || stockCombo.getValue() == null || priceOfSale.getText().isEmpty() || quantityInput.getText().isEmpty()|| operationDate.getValue() == null) {
                    displayAlert("Error", "Please Fill all fields ", Alert.AlertType.ERROR);
                } else if (Float.parseFloat(priceOfSale.getText()) == 0) {
                    displayAlert("Error", "Price can't be null ", Alert.AlertType.ERROR);
                }
                else if (Float.parseFloat(quantityInput.getText()) == 0) {
                    displayAlert("Error", "Quantity can't be null ", Alert.AlertType.ERROR);
                }
                else {
                    purchase.setId(this.Purchase_ID);
                    purchase.setStock_id(stocks.get(stockCombo.getValue()));
                    purchase.setPrice(Float.parseFloat(priceOfSale.getText()));
                    purchase.setQuantity(Float.parseFloat(quantityInput.getText()));
                    purchase.setSupplier_id(suppliers.get(suppliersCombo.getValue()));
                    purchase.setPurchase_date(Date.valueOf(operationDate.getValue()));
                    clear();
                    closePopUp(event);
                    displayAlert("success", "Purchase  Updated successfully", Alert.AlertType.INFORMATION);

                } }}else {

            if (suppliersCombo.getValue() == null || suppliersCombo.getValue() == null || priceOfSale.getText().isEmpty() || quantityInput.getText().isEmpty()|| operationDate.getValue() == null) {
                displayAlert("Error", "Please Fill all fields ", Alert.AlertType.ERROR);
            } else if (Float.parseFloat(priceOfSale.getText()) == 0) {
                displayAlert("Error", "Price can't be null ", Alert.AlertType.ERROR);
            }
            else if (Float.parseFloat(quantityInput.getText()) == 0) {
                displayAlert("Error", "Quantity can't be null ", Alert.AlertType.ERROR);
            }else {

                purchase.setStock_id(stocks.get(stockCombo.getValue()));
                purchase.setPrice(Float.parseFloat(priceOfSale.getText()));
                purchase.setQuantity(Float.parseFloat(quantityInput.getText()));
                purchase.setSupplier_id(suppliers.get(suppliersCombo.getValue()));
                purchase.setPurchase_date(Date.valueOf(operationDate.getValue()));
                if (purchase.save()) {
                    closePopUp(event);
                    displayAlert("success", "Purchase added successfully", Alert.AlertType.INFORMATION);

                } else {
                    displayAlert("Error", "Error while saving!!!", Alert.AlertType.ERROR);
                }

            }
        }
    }
    private void clear () {
        stockCombo.getSelectionModel().clearSelection();
        suppliersCombo.getSelectionModel().clearSelection();
        priceOfSale.clear();
        quantityInput.clear();
        operationDate.setValue(null);
        add_update.setDisable(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            this.setSupplierList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            this.setProuctsList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        validateDecimalInput(priceOfSale);
        validateDecimalInput(quantityInput);
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
        disconnect();
        suppliersCombo.setItems(supplierNames);
    }
    public void setProuctsList() throws SQLException {
        ObservableList<String> products = FXCollections.observableArrayList();

        String select_query = "SELECT id,name from stocks ";

        statement = DBConfig.getConnection().prepareStatement(select_query);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {

            stocks.put(resultSet.getString("name"), resultSet.getInt("id"));
            products.add(resultSet.getString("name"));
        }
        disconnect();
        stockCombo.setItems(products);
    }
    private boolean update;
    public void setUpdate(boolean b) {
        this.update = b;
    }
    public  void fetchPurchase(int ID, String typeProduct, Float quantity, Float price, String supplier, Date operationdate){
//        animal = getAnimal(AnimalDetailsController.id_animal);
        this.Purchase_ID = ID;
        header.setText("Update Purchase num :  " + ID);
        stockCombo.setValue(typeProduct + "");
        suppliersCombo.setValue(supplier + "");
        priceOfSale.setText(price + "");
        quantityInput.setText(quantity + "");
        operationDate.setValue(LocalDate.parse(operationdate + ""));
        key.setText("Update");
        add_update.setText("Update");


    }
    @FXML
    void openAddProduct(MouseEvent event) throws IOException {
        openNewWindow("Add Product", "add_new_product");
    }
    @FXML
    void refreshTable(MouseEvent event) throws SQLException {
        setProuctsList();
    }
    public Purchase getPurchase(int purchase_ID) {
        Purchase purchase = new Purchase();
        String query = "SELECT * FROM `purchases`   where id='" + purchase_ID + "' LIMIT 1";
        Connection con = getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                purchase.setId(rs.getInt("id"));
                purchase.setQuantity(rs.getFloat("quantity"));
                purchase.setStock_id(rs.getInt("stock_id"));
                purchase.setSupplier_id(rs.getInt("supplier_id"));
                purchase.setPrice(rs.getInt("price"));
                purchase.setPurchase_date(rs.getDate("purchase_date"));



            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return purchase;
    }
}
