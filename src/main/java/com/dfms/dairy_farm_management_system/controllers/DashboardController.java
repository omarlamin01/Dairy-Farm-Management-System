package com.dfms.dairy_farm_management_system.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.executeQuery;
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

        //fill the chart with real data
        fillPieChart();
        fillBarChart();

//        xAxis.setLabel("Programming Language");
//
//        yAxis.setLabel("Percent");
//
//        // Series 1 - Data of 2014
//        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
//        dataSeries1.setName("2014");
//
//        dataSeries1.getData().add(new XYChart.Data<String, Number>("Java", 20.973));
//        dataSeries1.getData().add(new XYChart.Data<String, Number>("C#", 4.429));
//        dataSeries1.getData().add(new XYChart.Data<String, Number>("PHP", 2.792));
//
//        // Series 2 - Data of 2015
//        XYChart.Series<String, Number> dataSeries2 = new XYChart.Series<String, Number>();
//        dataSeries2.setName("2015");
//
//        dataSeries2.getData().add(new XYChart.Data<String, Number>("Java", 26.983));
//        dataSeries2.getData().add(new XYChart.Data<String, Number>("C#", 6.569));
//        dataSeries2.getData().add(new XYChart.Data<String, Number>("PHP", 6.619));
//
//        // Add Series to BarChart.
//        barChart.getData().add(dataSeries1);
//        barChart.getData().add(dataSeries2);
    }

    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection = getConnection();
    ResultSet resultSet = null;

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
        resultSet = executeQuery("SELECT COUNT(*) FROM users");
        if (resultSet.next()) {
            total_users.setText(resultSet.getString(1));
        }

        //get total suppliers
        resultSet = executeQuery("SELECT COUNT(*) FROM suppliers");
        if (resultSet.next()) {
            total_suppliers.setText(resultSet.getString(1));
        }

        //get total products
        resultSet = executeQuery("SELECT COUNT(*) FROM stocks");
        if (resultSet.next()) {
            total_products.setText(resultSet.getString(1));
        }

        //get total clients
        resultSet = executeQuery("SELECT COUNT(*) FROM clients");
        if (resultSet.next()) {
            total_clients.setText(resultSet.getString(1));
        }

        //get total cows
        resultSet = executeQuery("SELECT COUNT(*) FROM animals WHERE type = 'cow'");
        if (resultSet.next()) {
            total_cows.setText(resultSet.getString(1));
        }

        //get total calfs
        resultSet = executeQuery("SELECT COUNT(*) FROM animals WHERE type = 'calf'");
        if (resultSet.next()) {
            total_calfs.setText(resultSet.getString(1));
        }

        //get total bulls
        resultSet = executeQuery("SELECT COUNT(*) FROM animals WHERE type = 'bull'");
        if (resultSet.next()) {
            total_bulls.setText(resultSet.getString(1));
        }

        //get today sales
        resultSet = executeQuery("SELECT COUNT(*) FROM animals_sales WHERE sale_date = CURDATE()");
        if (resultSet.next()) {
            today_sales.setText(resultSet.getString(1));
        } else {
            today_sales.setText("0");
        }

        //get today earnings
        resultSet = executeQuery("SELECT SUM(price) FROM animals_sales WHERE sale_date = CURDATE()");
        if (resultSet.next()) {
            today_earnings.setText(resultSet.getString(1));
        } else {
            today_earnings.setText("0");
        }
    }

    //fill PieChart with data
    public void fillPieChart() {
        try {
            String query = "SELECT COUNT(*) FROM animals WHERE type = 'cow'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                total_cows.setText(resultSet.getString(1));
            }

            query = "SELECT COUNT(*) FROM animals WHERE type = 'calf'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                total_calfs.setText(resultSet.getString(1));
            }

            query = "SELECT COUNT(*) FROM animals WHERE type = 'bull'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                total_bulls.setText(resultSet.getString(1));
            }

            PieChart.Data cows = new PieChart.Data("Cows", Integer.parseInt(total_cows.getText()));
            PieChart.Data clafs = new PieChart.Data("Calfs", Integer.parseInt(total_calfs.getText()));
            PieChart.Data bulls = new PieChart.Data("Bulls", Integer.parseInt(total_bulls.getText()));

            pieChart.getData().add(cows);
            pieChart.getData().add(clafs);
            pieChart.getData().add(bulls);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //fill BarChart with data
    public void fillBarChart() {
        // display sales of last 7 days
        try {
            String query = "SELECT COUNT(*) FROM animals_sales WHERE sale_date = CURDATE()";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                today_sales.setText(resultSet.getString(1));
            } else {
                today_sales.setText("0");
            }

            query = "SELECT COUNT(*) FROM animals_sales WHERE sale_date = CURDATE() - INTERVAL 1 DAY";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                today_sales.setText(resultSet.getString(1));
            } else {
                today_sales.setText("0");
            }

            query = "SELECT COUNT(*) FROM animals_sales WHERE sale_date = CURDATE() - INTERVAL 2 DAY";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                today_sales.setText(resultSet.getString(1));
            } else {
                today_sales.setText("0");
            }

            query = "SELECT COUNT(*) FROM animals_sales WHERE sale_date = CURDATE() - INTERVAL 3 DAY";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                today_sales.setText(resultSet.getString(1));
            } else {
                today_sales.setText("0");
            }

            query = "SELECT COUNT(*) FROM animals_sales WHERE sale_date = CURDATE() - INTERVAL 4 DAY";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                today_sales.setText(resultSet.getString(1));
            } else {
                today_sales.setText("0");
            }

            query = "SELECT COUNT(*) FROM animals_sales WHERE sale_date = CURDATE() - INTERVAL 5 DAY";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                today_sales.setText(resultSet.getString(1));
            } else {
                today_sales.setText("0");
            }

            query = "SELECT COUNT(*) FROM animals_sales WHERE sale_date = CURDATE() - INTERVAL 6 DAY";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                today_sales.setText(resultSet.getString(1));
            } else {
                today_sales.setText("0");
            }

            XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
            dataSeries1.setName("Sales");

            dataSeries1.getData().add(new XYChart.Data<String, Number>("Today", Integer.parseInt(today_sales.getText())));
            dataSeries1.getData().add(new XYChart.Data<String, Number>("Yesterday", Integer.parseInt(today_sales.getText())));
            dataSeries1.getData().add(new XYChart.Data<String, Number>("2 days ago", Integer.parseInt(today_sales.getText())));
            dataSeries1.getData().add(new XYChart.Data<String, Number>("3 days ago", Integer.parseInt(today_sales.getText())));
            dataSeries1.getData().add(new XYChart.Data<String, Number>("4 days ago", Integer.parseInt(today_sales.getText())));
            dataSeries1.getData().add(new XYChart.Data<String, Number>("5 days ago", Integer.parseInt(today_sales.getText())));
            dataSeries1.getData().add(new XYChart.Data<String, Number>("6 days ago", Integer.parseInt(today_sales.getText())));
            barChart.getData().add(dataSeries1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
