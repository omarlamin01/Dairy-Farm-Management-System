package com.dfms.dairy_farm_management_system.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;

public class MainLayoutController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button animal_monitor_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button employees_btn;

    @FXML
    private Button manage_animal_btn;

    @FXML
    private Button manage_clients_suppliers_btn;

    @FXML
    private Button reports_btn;

    @FXML
    private Button routine_monitor_btn;

    @FXML
    private Button sales_btn;

    @FXML
    private Button stock_btn;

    private Parent root = null;

    @FXML
    void LoadStock(ActionEvent event) {
        String stock_view = "stock";
        loadView(stock_view);
    }

    @FXML
    void loadAnimalMonitor(ActionEvent event) {
        String animal_monitor_view = "animal_monitor";
        loadView(animal_monitor_view);
    }

    @FXML
    void loadClientsSuppliers(ActionEvent event) {
        String clients_suppliers_view = "manage_clients_suppliers";
        loadView(clients_suppliers_view);
    }

    @FXML
    void loadDashboard(ActionEvent event) {
        String dashboard_view = "dashboard";
        loadView(dashboard_view);
    }

    @FXML
    void loadEmployees(ActionEvent event) {
        String employees_view = "employees";
        loadView(employees_view);
    }

    @FXML
    void loadManageAnimal(ActionEvent event) {
        String manage_animals = "manage_clients_suppliers";
        loadView(manage_animals);
    }

    @FXML
    void loadReports(ActionEvent event) {
        String reports_view = "reports";
        loadView(reports_view);
    }

    @FXML
    void loadRoutineMonitor(ActionEvent event) {
        String routine_monitor_view = "animal_monitor";
        loadView(routine_monitor_view);
    }

    @FXML
    void loadSales(ActionEvent event) {
        String sales_view = "sales";
        loadView(sales_view);
    }

    private void loadView(String fxml) {
        try {
            root = FXMLLoader.load(getClass().getResource(fxml + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane.setCenter(root);
        addActiveClassToNavLink();
    }

    private void addActiveClassToNavLink() {
        ArrayList<Button> navLinks = new ArrayList<>();
        navLinks.add(animal_monitor_btn);
        navLinks.add(dashboard_btn);
        navLinks.add(employees_btn);
        navLinks.add(manage_animal_btn);
        navLinks.add(manage_clients_suppliers_btn);
        navLinks.add(reports_btn);
        navLinks.add(routine_monitor_btn);
        navLinks.add(sales_btn);
        navLinks.add(stock_btn);

        //remove background color from all buttons
        for (Button button : navLinks) {
            button.getStyleClass().remove("active_nav_link");
        }
        //add background color to active button
        for (Button button : navLinks) {
            if (button.isFocused()) {
                button.getStyleClass().add("active_nav_link");
            }
        }
    }
}