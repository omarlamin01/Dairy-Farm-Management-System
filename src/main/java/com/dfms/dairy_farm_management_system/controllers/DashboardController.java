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

        //fill the charts with real data
        fillPieChart();
        fillBarChart();
        fillLineChart();
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
    private Text total_employees;
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private PieChart pieChart;
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    public void initDashboard() throws SQLException {
        //get total employees
        resultSet = executeQuery("SELECT COUNT(*) FROM employees");
        if (resultSet.next()) {
            total_employees.setText(resultSet.getString(1));
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
        }
        if (resultSet.getString(1) == null) {
            today_sales.setText("0");
        }

        //get today earnings
        resultSet = executeQuery("SELECT SUM(price) FROM animals_sales WHERE sale_date = CURDATE()");
        if (resultSet.next()) {
            today_earnings.setText("$" + resultSet.getString(1));
        }
        if (resultSet.getString(1) == null) {
            today_earnings.setText("$0");
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

    //fill BarChart with sales of each day
    public void fillBarChart() {
        xAxis.setLabel("Days");
        yAxis.setLabel("Sales");

        XYChart.Series<String, Number> animal_sales = new XYChart.Series<>();
        XYChart.Series<String, Number> milk_sales = new XYChart.Series<>();

        animal_sales.setName("Animal Sales");
        milk_sales.setName("Milk Sales");

        //get animal sales
        animal_sales.getData().add(new XYChart.Data<>("Sun", getSalesOfSpecificDay("Sun", "animals_sales")));
        animal_sales.getData().add(new XYChart.Data<>("Mon", getSalesOfSpecificDay("Mon", "animals_sales")));
        animal_sales.getData().add(new XYChart.Data<>("Tue", getSalesOfSpecificDay("Tue", "animals_sales")));
        animal_sales.getData().add(new XYChart.Data<>("Wed", getSalesOfSpecificDay("Wed", "animals_sales")));
        animal_sales.getData().add(new XYChart.Data<>("Thu", getSalesOfSpecificDay("Thu", "animals_sales")));
        animal_sales.getData().add(new XYChart.Data<>("Fri", getSalesOfSpecificDay("Fri", "animals_sales")));
        animal_sales.getData().add(new XYChart.Data<>("Sat", getSalesOfSpecificDay("Sat", "animals_sales")));

        //get milk sales
        milk_sales.getData().add(new XYChart.Data<>("Sun", getSalesOfSpecificDay("Sun", "milk_sales")));
        milk_sales.getData().add(new XYChart.Data<>("Mon", getSalesOfSpecificDay("Mon", "milk_sales")));
        milk_sales.getData().add(new XYChart.Data<>("Tue", getSalesOfSpecificDay("Tue", "milk_sales")));
        milk_sales.getData().add(new XYChart.Data<>("Wed", getSalesOfSpecificDay("Wed", "milk_sales")));
        milk_sales.getData().add(new XYChart.Data<>("Thu", getSalesOfSpecificDay("Thu", "milk_sales")));
        milk_sales.getData().add(new XYChart.Data<>("Fri", getSalesOfSpecificDay("Fri", "milk_sales")));
        milk_sales.getData().add(new XYChart.Data<>("Sat", getSalesOfSpecificDay("Sat", "milk_sales")));

        //set data to bar chart
        barChart.getData().add(animal_sales);
        barChart.getData().add(milk_sales);
    }

    //fill line chart with earnings of each day
    public void fillLineChart() {
        xAxis.setLabel("Days");
        yAxis.setLabel("Count of animals sales");

        XYChart.Series<String, Number> data = new XYChart.Series<>();

        data.getData().add(new XYChart.Data<>("Sun", getEarningsOfSpecificDay("Sun")));
        data.getData().add(new XYChart.Data<>("Mon", getEarningsOfSpecificDay("Mon")));
        data.getData().add(new XYChart.Data<>("Tue", getEarningsOfSpecificDay("Tue")));
        data.getData().add(new XYChart.Data<>("Wed", getEarningsOfSpecificDay("Wed")));
        data.getData().add(new XYChart.Data<>("Thu", getEarningsOfSpecificDay("Thu")));
        data.getData().add(new XYChart.Data<>("Fri", getEarningsOfSpecificDay("Fri")));
        data.getData().add(new XYChart.Data<>("Sat", getEarningsOfSpecificDay("Sat")));

        lineChart.getData().add(data);
    }

    //get sales of specific day
    public int getSalesOfSpecificDay(String day, String table) {
        int sales = 0;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        try {
            //get count of sales of each day
            switch (day) {
                case "Sun" ->
                        resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE DAYNAME(sale_date) = 'Sunday' AND WEEK(sale_date) = WEEK(CURDATE())");
                case "Mon" ->
                        resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE DAYNAME(sale_date) = 'Monday' AND WEEK(sale_date) = WEEK(CURDATE())");
                case "Tue" ->
                        resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE DAYNAME(sale_date) = 'Tuesday' AND WEEK(sale_date) = WEEK(CURDATE())");
                case "Wed" ->
                        resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE DAYNAME(sale_date) = 'Wednesday' AND WEEK(sale_date) = WEEK(CURDATE())");
                case "Thu" ->
                        resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE DAYNAME(sale_date) = 'Thursday' AND WEEK(sale_date) = WEEK(CURDATE())");
                case "Fri" ->
                        resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE DAYNAME(sale_date) = 'Friday' AND WEEK(sale_date) = WEEK(CURDATE())");
                case "Sat" ->
                        resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE DAYNAME(sale_date) = 'Saturday' AND WEEK(sale_date) = WEEK(CURDATE())");
                default -> {
                }

            }
            if (resultSet.next()) {
                sales = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }

    //get earnings of specific day
    public int getEarningsOfSpecificDay(String day) {
        int earnings = 0;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        try {
            //get count of sales of each day
            switch (day) {
                case "Sun" ->
                    //get the sum of price from both tables (animals_sales and milk_sales)
                        resultSet = statement.executeQuery("SELECT SUM(price) FROM animals_sales WHERE DAYNAME(sale_date) = 'Sunday' AND WEEK(sale_date) = WEEK(CURDATE()) UNION SELECT SUM(price) FROM milk_sales WHERE DAYNAME(sale_date) = 'Sunday' AND WEEK(sale_date) = WEEK(CURDATE())");
                case "Mon" ->
                        resultSet = statement.executeQuery("SELECT SUM(price) FROM animals_sales WHERE DAYNAME(sale_date) = 'Monday' AND WEEK(sale_date) = WEEK(CURDATE()) UNION SELECT SUM(price) FROM milk_sales WHERE DAYNAME(sale_date) = 'Monday' AND WEEK(sale_date) = WEEK(CURDATE())");
                case "Tue" ->
                        resultSet = statement.executeQuery("SELECT SUM(price) FROM animals_sales WHERE DAYNAME(sale_date) = 'Tuesday' AND WEEK(sale_date) = WEEK(CURDATE()) UNION SELECT SUM(price) FROM milk_sales WHERE DAYNAME(sale_date) = 'Tuesday' AND WEEK(sale_date) = WEEK(CURDATE())");
                case "Wed" ->
                        resultSet = statement.executeQuery("SELECT SUM(price) FROM animals_sales WHERE DAYNAME(sale_date) = 'Wednesday' AND WEEK(sale_date) = WEEK(CURDATE()) UNION SELECT SUM(price) FROM milk_sales WHERE DAYNAME(sale_date) = 'Wednesday' AND WEEK(sale_date) = WEEK(CURDATE())");
                case "Thu" ->
                        resultSet = statement.executeQuery("SELECT SUM(price) FROM animals_sales WHERE DAYNAME(sale_date) = 'Thursday' AND WEEK(sale_date) = WEEK(CURDATE()) UNION SELECT SUM(price) FROM milk_sales WHERE DAYNAME(sale_date) = 'Thursday' AND WEEK(sale_date) = WEEK(CURDATE())");
                case "Fri" ->
                        resultSet = statement.executeQuery("SELECT SUM(price) FROM animals_sales WHERE DAYNAME(sale_date) = 'Friday' AND WEEK(sale_date) = WEEK(CURDATE()) UNION SELECT SUM(price) FROM milk_sales WHERE DAYNAME(sale_date) = 'Friday' AND WEEK(sale_date) = WEEK(CURDATE())");
                case "Sat" ->
                        resultSet = statement.executeQuery("SELECT SUM(price) FROM animals_sales WHERE DAYNAME(sale_date) = 'Saturday' AND WEEK(sale_date) = WEEK(CURDATE()) UNION SELECT SUM(price) FROM milk_sales WHERE DAYNAME(sale_date) = 'Saturday' AND WEEK(sale_date) = WEEK(CURDATE())");
                default -> {
                }
            }
            if (resultSet.next()) {
                earnings = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return earnings;
    }
}
