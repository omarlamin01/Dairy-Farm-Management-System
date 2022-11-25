package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Client;
import com.dfms.dairy_farm_management_system.models.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class ClientDetailsController implements Initializable {
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
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void fetchClient(Client client) {

        //get the employee from the database

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `clients` WHERE id = '" +client.getId() + "' LIMIT 1");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                header.setText("Client : "+resultSet.getString("name"));
                name.setText(resultSet.getString("name"));
                email.setText(resultSet.getString("email"));
                type.setText(resultSet.getString("type"));
                phone.setText(String.valueOf(resultSet.getInt("phone")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
