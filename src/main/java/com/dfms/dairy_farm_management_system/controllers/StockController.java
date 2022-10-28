package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.models.Product;
import com.dfms.dairy_farm_management_system.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.openNewWindow;

public class StockController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // add data to table
        productIDCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        productTypeCol.setCellValueFactory(new PropertyValueFactory<User, String>("type"));
        addedDateCol.setCellValueFactory(new PropertyValueFactory<User, String>("addedDate"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<User, Boolean>("availability"));
        stockTable.setItems(products);
    }


    @FXML
    private TableColumn<User, ?> actionsCol;

    @FXML
    private TableColumn<User, String> addedDateCol;

    @FXML
    private TableColumn<User, Boolean> availabilityCol;

    @FXML
    private TableColumn<User, Integer> productIDCol;

    @FXML
    private TableColumn<User, String> productNameCol;

    @FXML
    private TableColumn<User, String> productTypeCol;

    @FXML
    private TableView<Product> stockTable;

    ObservableList<Product> products = FXCollections.observableArrayList(
            new Product(1, "Milk", "Liquid", "2021-05-01", true),
            new Product(2, "Butter", "Solid", "2021-05-01", true),
            new Product(3, "Cheese", "Solid", "2021-05-01", true),
            new Product(4, "Yogurt", "Liquid", "2021-05-01", true),
            new Product(5, "Cream", "Liquid", "2021-05-01", true)
    );


    @FXML
    private TableView<Product> stockTableView;

    @FXML
    void openAddProduct(MouseEvent event) throws IOException {
        openNewWindow("Add Product", "add_new_product");
    }
}