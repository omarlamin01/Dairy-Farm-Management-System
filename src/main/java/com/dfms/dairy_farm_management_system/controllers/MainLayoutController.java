package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.connection.Session;
import com.dfms.dairy_farm_management_system.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.centerScreen;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class MainLayoutController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String first_view = "dashboard";
        loadView(first_view);
        dashboard_btn.setStyle("-fx-background-color: #FFC700, #00A300;" +
                "-fx-background-insets: 0, 0 0 0 4;");

        nav_scroll_pane.getStyleClass().clear();

        // load user details
        getCurrentUser();
    }

    private PreparedStatement pst;
    private Connection con = DBConfig.getConnection();
    private User user;


    @FXML
    private BorderPane borderPane;


//    private Button animal_monitor_btn; //
//
//
    private Button dashboard_btn; //
//
//
//    private Button profile_btn; //
//
//
//    private Button employees_btn; //
//
//
//    private Button manage_animal_btn; //
//
//
//    private Button manage_clients_suppliers_btn; //
//
//
//    private Button reports_btn; //
//
//
//    private Button sales_btn; //
//
//
//    private Button stock_btn;
//
//
//    private Button MilkClollection_btn;
//
//
//    private Button manageUsersBtn;


    @FXML
    private Button logout;


    @FXML
    private Label user_name;


    @FXML
    private ScrollPane nav_scroll_pane;


    @FXML
    private VBox menu;


    private Parent root = null;


    private Button purchase_btn; //


    private void initNavButtons() {
        dashboard_btn = new Button("Dashboard");
        Image dashboard_img = new Image(getClass().getResourceAsStream("/images/edit.png"));
        dashboard_btn.setGraphic(new ImageView(dashboard_img));
        dashboard_btn.getStyleClass().add("nav_link");
//        dashboard_btn.setPrefWidth(600);
//        dashboard_btn.setMaxWidth(Region.USE_COMPUTED_SIZE);
//        dashboard_btn.setPrefWidth(40);
//        dashboard_btn.setMaxHeight(Region.USE_COMPUTED_SIZE);
        menu.getChildren().add(dashboard_btn);
    }


    void LoadStock(ActionEvent event) {
        String stock_view = "stock";
        loadView(stock_view);
    }


    void loadAnimalMonitor(ActionEvent event) {
        String animal_monitor_view = "animal_monitor";
        loadView(animal_monitor_view);
    }


    void loadClientsSuppliers(ActionEvent event) {
        String clients_suppliers_view = "clients_suppliers";
        loadView(clients_suppliers_view);
    }


    void loadDashboard(MouseEvent event) {
        String dashboard_view = "dashboard";
        loadView(dashboard_view);
    }


    public void loadProfile(MouseEvent mouseEvent) {
        String profile_view = "profile";
        loadView(profile_view);
    }


    void loadEmployees(ActionEvent event) {
        String employees_view = "employees";
        loadView(employees_view);
    }


    void loadManageAnimal(ActionEvent event) {
        String manage_animals = "manage_animal";
        loadView(manage_animals);
    }


    void loadReports(ActionEvent event) {
        String reports_view = "reports";
        loadView(reports_view);
    }


    void loadSales(ActionEvent event) {
        String sales_view = "sales";
        loadView(sales_view);
    }


    void loadMilkCollection(ActionEvent event) {
        String milkcollection_view = "milk_collection";
        loadView(milkcollection_view);
    }


    void loadPurchases(ActionEvent event) {
        String purchase_view = "purchases";
        loadView(purchase_view);
    }


    void loadManageUsers(ActionEvent event) {
        String manageUsersView = "users";
        loadView(manageUsersView);
    }

    private void loadView(String fxml) {
        String views_path = "/com/dfms/dairy_farm_management_system/";
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(views_path + fxml + ".fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane.setCenter(root);
        addActiveClassToNavLink();
    }

    private void addActiveClassToNavLink() {
        ArrayList<Button> navLinks = navLinks();

        //add background color to active button
        for (Button button : navLinks) {
            if (button.isFocused()) {
                button.setStyle("-fx-background-color: #FFC700, #00A300;" +
                        "-fx-background-insets: 0, 0 0 0 4;");
            } else {
                button.setStyle("-fx-background-color: #1B2434, #1E293B;" +
                        "-fx-background-insets: 0, 0 0 1 0;");
            }
        }
    }


    @FXML
    void navLinkMouseEntred(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #FFC700,#00A300;" +
                "-fx-background-insets: 0, 0 0 0 4;");
    }


    @FXML
    void navLinkMouseExited(MouseEvent event) {
        Button button = (Button) event.getSource();
        if (!button.isFocused()) {
            button.setStyle("-fx-background-color: #1B2434, #1E293B;" +
                    "-fx-background-insets: 0, 0 0 1 0;");
        }
    }

    private ArrayList<Button> navLinks() {
        initNavButtons();
        ArrayList<Button> navLinks = new ArrayList<>();
//        navLinks.add(animal_monitor_btn);
        navLinks.add(dashboard_btn);
//        navLinks.add(profile_btn);
//        navLinks.add(employees_btn);
//        navLinks.add(manage_animal_btn);
//        navLinks.add(manage_clients_suppliers_btn);
//        navLinks.add(reports_btn);
//        navLinks.add(sales_btn);
//        navLinks.add(stock_btn);
//        navLinks.add(MilkClollection_btn);
//        navLinks.add(manageUsersBtn);
        navLinks.add(logout);
        return navLinks;
    }


    @FXML
    private void logout(MouseEvent event) throws IOException {
        String login_view = "login_screen";
        //show alert dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("Click ok to logout");

        //logout if user clicks ok
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Session.logoutUser();
            logoutSystem(login_view, event);
        }
    }

    //logout method
    private void logoutSystem(String view, MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(view + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
        stage.setTitle("Dairy Farm Management System");
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        centerScreen(stage);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    //get current logged in user
    private void getCurrentUser() {
        User user = Session.getCurrentUser();
        //capitalize first letter of first name
        try {
            String first_name = user.getFirstName().toLowerCase();
            String first_letter = first_name.substring(0, 1).toUpperCase();
            String rest_of_name = first_name.substring(1);
            String name = first_letter + rest_of_name;
            user_name.setText(name);
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}