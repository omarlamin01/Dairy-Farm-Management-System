package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

import com.dfms.dairy_farm_management_system.models.Client;
import com.dfms.dairy_farm_management_system.models.Supplier;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class SupplierDetailsController implements Initializable {

    @FXML
    private Label header;

    @FXML
    private Label name;

    @FXML
    private Label type;

    @FXML
    private Label email;

    @FXML
    private Label phone;

    Connection connection = getConnection();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void fetchSupplier(Supplier supplier) {
        //get the employee from the database

        try {
            preparedStatement = connection.prepareStatement(
                "SELECT * FROM `suppliers` WHERE id = '" + supplier.getId() + "' LIMIT 1"
            );
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                header.setText("Supplier : " + resultSet.getString("name"));
                name.setText(resultSet.getString("name"));
                email.setText(resultSet.getString("email"));
                type.setText(resultSet.getString("type"));
                phone.setText(resultSet.getString("phone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
