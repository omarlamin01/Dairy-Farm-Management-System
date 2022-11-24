package com.dfms.dairy_farm_management_system.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class DashboardController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initDashboard();
        } catch (SQLException e) {
            displayAlert("Error", "Error occurred while loading dashboard", Alert.AlertType.ERROR);
        }
        PieChart.Data slice1 = new PieChart.Data("Desktop", 213);
        PieChart.Data slice2 = new PieChart.Data("Phone", 67);
        PieChart.Data slice3 = new PieChart.Data("Tablet", 36);

        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
        pieChart.getData().add(slice3);

        xAxis.setLabel("Programming Language");

        yAxis.setLabel("Percent");

        // Series 1 - Data of 2014
        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
        dataSeries1.setName("2014");

        dataSeries1.getData().add(new XYChart.Data<String, Number>("Java", 20.973));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("C#", 4.429));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("PHP", 2.792));

        // Series 2 - Data of 2015
        XYChart.Series<String, Number> dataSeries2 = new XYChart.Series<String, Number>();
        dataSeries2.setName("2015");

        dataSeries2.getData().add(new XYChart.Data<String, Number>("Java", 26.983));
        dataSeries2.getData().add(new XYChart.Data<String, Number>("C#", 6.569));
        dataSeries2.getData().add(new XYChart.Data<String, Number>("PHP", 6.619));

        // Add Series to BarChart.
        barChart.getData().add(dataSeries1);
        barChart.getData().add(dataSeries2);
    }

    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection = getConnection();

    @FXML
    private Text today_earnings;

    @FXML
    private Text today_sales;

    @FXML
    private Text total_bulls;

    @FXML
    private Text total_calfs;

    @FXML
    private Text total_clients;

    @FXML
    private Text total_cows;

    @FXML
    private Text total_products;

    @FXML
    private Text total_suppliers;

    @FXML
    private Text total_users;
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private PieChart pieChart;
    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    public void initDashboard() throws SQLException {
        //get total users
        statement = connection.createStatement();
        preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM users");
        total_users.setText(preparedStatement.executeQuery().toString());

        //get total suppliers
        statement = connection.createStatement();
        preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM suppliers");
        total_suppliers.setText(preparedStatement.executeQuery().toString());

        //get total products
        statement = connection.createStatement();
        preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM products");
        total_products.setText(preparedStatement.executeQuery().toString());

        //get total clients
        statement = connection.createStatement();
        preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM clients");
        total_clients.setText(preparedStatement.executeQuery().toString());

        //get total cows
        statement = connection.createStatement();
        preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM animals WHERE type = 'cow'");
        total_cows.setText(preparedStatement.executeQuery().toString());

        //get total calfs
        statement = connection.createStatement();
        preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM animals WHERE type = 'calf'");
        total_calfs.setText(preparedStatement.executeQuery().toString());

        //get total bulls
        statement = connection.createStatement();
        preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM animals WHERE type = 'bull'");
        total_bulls.setText(preparedStatement.executeQuery().toString());

        //get today sales
        statement = connection.createStatement();
        preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM sales WHERE date = CURDATE()");
        today_sales.setText(preparedStatement.executeQuery().toString());

        //get today earnings
        statement = connection.createStatement();
        preparedStatement = connection.prepareStatement("SELECT SUM(total) FROM sales WHERE date = CURDATE()");
        today_earnings.setText(preparedStatement.executeQuery().toString() + " $");
    }
}
