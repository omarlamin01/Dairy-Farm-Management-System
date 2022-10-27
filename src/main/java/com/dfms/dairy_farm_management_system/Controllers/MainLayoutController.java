package com.dfms.dairy_farm_management_system.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String dashboard_view = "profile";
        loadView(dashboard_view);
        profile_btn.setStyle("-fx-background-color: #FFC700, #72ED12;" +
                "-fx-background-insets: 0, 0 0 0 4;");
    }

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button animal_monitor_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button profile_btn;

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
        String stock_view = "employees";
        loadView(stock_view);
    }

    @FXML
    void loadAnimalMonitor(ActionEvent event) {
        String animal_monitor_view = "dashboard";
        loadView(animal_monitor_view);
    }

    @FXML
    void loadClientsSuppliers(ActionEvent event) {
        String clients_suppliers_view = "employees";
        loadView(clients_suppliers_view);
    }

    @FXML
    void loadDashboard(MouseEvent event) {
        String dashboard_view = "dashboard";
        loadView(dashboard_view);
    }

    @FXML
    public void loadProfile(MouseEvent mouseEvent) {
        String profile_view = "profile";
        loadView(profile_view);
    }

    @FXML
    void loadEmployees(ActionEvent event) {
        String employees_view = "employees";
        loadView(employees_view);
    }

    @FXML
    void loadManageAnimal(ActionEvent event) {
        String manage_animals = "dashboard";
        loadView(manage_animals);
    }

    @FXML
    void loadReports(ActionEvent event) {
        String reports_view = "employees";
        loadView(reports_view);
    }

    @FXML
    void loadRoutineMonitor(ActionEvent event) {
        String routine_monitor_view = "dashboard";
        loadView(routine_monitor_view);
    }

    @FXML
    void loadSales(ActionEvent event) {
        String sales_view = "employees";
        loadView(sales_view);
    }

    private void loadView(String fxml) {
        String views_path = "/com/dfms/dairy_farm_management_system/";
        try {
            root = FXMLLoader.load(getClass().getResource(views_path + fxml + ".fxml"));
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
        navLinks.add(profile_btn);
        navLinks.add(employees_btn);
        navLinks.add(manage_animal_btn);
        navLinks.add(manage_clients_suppliers_btn);
        navLinks.add(reports_btn);
        navLinks.add(routine_monitor_btn);
        navLinks.add(sales_btn);
        navLinks.add(stock_btn);

        //add background color to active button
        for (Button button : navLinks) {
            if (button.isFocused()) {
                button.setStyle("-fx-background-color: #FFC700, #72ED12;" +
                        "-fx-background-insets: 0, 0 0 0 4;");
            } else {
                button.setStyle("-fx-background-color: #1B2434, #1E293B;" +
                        "-fx-background-insets: 0, 0 0 1 0;");
            }
        }
    }

}