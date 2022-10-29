package com.dfms.dairy_farm_management_system.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private PieChart pieChart;
    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;
}
