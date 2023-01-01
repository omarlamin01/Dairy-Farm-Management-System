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
import java.util.stream.Collectors;

import static com.dfms.dairy_farm_management_system.helpers.Helper.centerScreen;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class MainLayoutController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initNavButtons();

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

    private Button animal_monitor_btn; //

    private Button dashboard_btn; //

    private Button profile_btn; //

    private Button employees_btn; //

    private Button manage_animal_btn; //

    private Button manage_clients_suppliers_btn; //

    private Button reports_btn; //

    private Button sales_btn; //

    private Button stock_btn;

    private Button MilkClollection_btn;

    private Button manageUsersBtn;

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
        Image dashboard_img = new Image(getClass().getResourceAsStream("/icons/dashboard.png"));
        ImageView imageView = new ImageView(dashboard_img);
        imageView.getStyleClass().add("nav_link");
        imageView.setFitWidth(32);
        imageView.setFitHeight(28);
        dashboard_btn.setGraphic(imageView);
        dashboard_btn.getStyleClass().add("nav_link");
        dashboard_btn.setPrefWidth(225);
        dashboard_btn.setMaxWidth(Region.USE_COMPUTED_SIZE);
        dashboard_btn.setPrefHeight(40);
        dashboard_btn.setMaxHeight(Region.USE_COMPUTED_SIZE);
        dashboard_btn.setContentDisplay(ContentDisplay.LEFT);
        dashboard_btn.setGraphicTextGap(10);
        dashboard_btn.setOnMouseEntered(this::navLinkMouseEntred);
        dashboard_btn.setOnMouseExited(this::navLinkMouseExited);
        dashboard_btn.setOnMouseClicked(this::loadDashboard);
        menu.getChildren().add(dashboard_btn);

        profile_btn = new Button("Profile");
        Image profile_img = new Image(getClass().getResourceAsStream("/icons/profile.png"));
        ImageView profile_imageView = new ImageView(profile_img);
        profile_imageView.getStyleClass().add("nav_link");
        profile_imageView.setFitWidth(32);
        profile_imageView.setFitHeight(28);
        profile_btn.setGraphic(profile_imageView);
        profile_btn.getStyleClass().add("nav_link");
        profile_btn.setPrefWidth(225);
        profile_btn.setMaxWidth(Region.USE_COMPUTED_SIZE);
        profile_btn.setPrefHeight(40);
        profile_btn.setMaxHeight(Region.USE_COMPUTED_SIZE);
        profile_btn.setContentDisplay(ContentDisplay.LEFT);
        profile_btn.setGraphicTextGap(10);
        profile_btn.setOnMouseEntered(this::navLinkMouseEntred);
        profile_btn.setOnMouseExited(this::navLinkMouseExited);
        profile_btn.setOnMouseClicked(this::loadProfile);
        menu.getChildren().add(profile_btn);

        employees_btn = new Button("Employees");
        Image employees_img = new Image(getClass().getResourceAsStream("/icons/employees.png"));
        ImageView employees_imageView = new ImageView(employees_img);
        employees_imageView.getStyleClass().add("nav_link");
        employees_imageView.setFitWidth(32);
        employees_imageView.setFitHeight(28);
        employees_btn.setGraphic(employees_imageView);
        employees_btn.getStyleClass().add("nav_link");
        employees_btn.setPrefWidth(225);
        employees_btn.setMaxWidth(Region.USE_COMPUTED_SIZE);
        employees_btn.setPrefHeight(40);
        employees_btn.setMaxHeight(Region.USE_COMPUTED_SIZE);
        employees_btn.setContentDisplay(ContentDisplay.LEFT);
        employees_btn.setGraphicTextGap(10);
        employees_btn.setOnMouseEntered(this::navLinkMouseEntred);
        employees_btn.setOnMouseExited(this::navLinkMouseExited);
        employees_btn.setOnAction(this::loadEmployees);
        menu.getChildren().add(employees_btn);

        animal_monitor_btn = new Button("Animal Monitor");
        Image animal_monitor_img = new Image(getClass().getResourceAsStream("/icons/monitor.png"));
        ImageView animal_monitor_imageView = new ImageView(animal_monitor_img);
        animal_monitor_imageView.getStyleClass().add("nav_link");
        animal_monitor_imageView.setFitWidth(32);
        animal_monitor_imageView.setFitHeight(28);
        animal_monitor_btn.setGraphic(animal_monitor_imageView);
        animal_monitor_btn.getStyleClass().add("nav_link");
        animal_monitor_btn.setPrefWidth(225);
        animal_monitor_btn.setMaxWidth(Region.USE_COMPUTED_SIZE);
        animal_monitor_btn.setPrefHeight(40);
        animal_monitor_btn.setMaxHeight(Region.USE_COMPUTED_SIZE);
        animal_monitor_btn.setContentDisplay(ContentDisplay.LEFT);
        animal_monitor_btn.setGraphicTextGap(10);
        animal_monitor_btn.setOnMouseEntered(this::navLinkMouseEntred);
        animal_monitor_btn.setOnMouseExited(this::navLinkMouseExited);
        animal_monitor_btn.setOnAction(this::loadAnimalMonitor);
        menu.getChildren().add(animal_monitor_btn);

        manage_clients_suppliers_btn = new Button("Manage Clients & Suppliers");
        Image manage_clients_suppliers_img = new Image(getClass().getResourceAsStream("/icons/supplier.png"));
        ImageView manage_clients_suppliers_imageView = new ImageView(manage_clients_suppliers_img);
        manage_clients_suppliers_imageView.getStyleClass().add("nav_link");
        manage_clients_suppliers_imageView.setFitWidth(32);
        manage_clients_suppliers_imageView.setFitHeight(28);
        manage_clients_suppliers_btn.setGraphic(manage_clients_suppliers_imageView);
        manage_clients_suppliers_btn.getStyleClass().add("nav_link");
        manage_clients_suppliers_btn.setPrefWidth(225);
        manage_clients_suppliers_btn.setMaxWidth(Region.USE_COMPUTED_SIZE);
        manage_clients_suppliers_btn.setPrefHeight(40);
        manage_clients_suppliers_btn.setMaxHeight(Region.USE_COMPUTED_SIZE);
        manage_clients_suppliers_btn.setContentDisplay(ContentDisplay.LEFT);
        manage_clients_suppliers_btn.setGraphicTextGap(10);
        manage_clients_suppliers_btn.setOnMouseEntered(this::navLinkMouseEntred);
        manage_clients_suppliers_btn.setOnMouseExited(this::navLinkMouseExited);
        manage_clients_suppliers_btn.setOnAction(this::loadClientsSuppliers);
        menu.getChildren().add(manage_clients_suppliers_btn);

        manage_animal_btn = new Button("Manage Animals");
        Image manage_animal_img = new Image(getClass().getResourceAsStream("/icons/animal.png"));
        ImageView manage_animal_imageView = new ImageView(manage_animal_img);
        manage_animal_imageView.getStyleClass().add("nav_link");
        manage_animal_imageView.setFitWidth(32);
        manage_animal_imageView.setFitHeight(28);
        manage_animal_btn.setGraphic(manage_animal_imageView);
        manage_animal_btn.getStyleClass().add("nav_link");
        manage_animal_btn.setPrefWidth(225);
        manage_animal_btn.setMaxWidth(Region.USE_COMPUTED_SIZE);
        manage_animal_btn.setPrefHeight(40);
        manage_animal_btn.setMaxHeight(Region.USE_COMPUTED_SIZE);
        manage_animal_btn.setContentDisplay(ContentDisplay.LEFT);
        manage_animal_btn.setGraphicTextGap(10);
        manage_animal_btn.setOnMouseEntered(this::navLinkMouseEntred);
        manage_animal_btn.setOnMouseExited(this::navLinkMouseExited);
        manage_animal_btn.setOnAction(this::loadManageAnimal);
        menu.getChildren().add(manage_animal_btn);

        sales_btn = new Button("Sales");
        Image sales_img = new Image(getClass().getResourceAsStream("/icons/sales.png"));
        ImageView sales_imageView = new ImageView(sales_img);
        sales_imageView.getStyleClass().add("nav_link");
        sales_imageView.setFitWidth(32);
        sales_imageView.setFitHeight(28);
        sales_btn.setGraphic(sales_imageView);
        sales_btn.getStyleClass().add("nav_link");
        sales_btn.setPrefWidth(225);
        sales_btn.setMaxWidth(Region.USE_COMPUTED_SIZE);
        sales_btn.setPrefHeight(40);
        sales_btn.setMaxHeight(Region.USE_COMPUTED_SIZE);
        sales_btn.setContentDisplay(ContentDisplay.LEFT);
        sales_btn.setGraphicTextGap(10);
        sales_btn.setOnMouseEntered(this::navLinkMouseEntred);
        sales_btn.setOnMouseExited(this::navLinkMouseExited);
        sales_btn.setOnAction(this::loadSales);
        menu.getChildren().add(sales_btn);

        purchase_btn = new Button("Purchase");
        Image purchase_img = new Image(getClass().getResourceAsStream("/icons/purchase.png"));
        ImageView purchase_imageView = new ImageView(purchase_img);
        purchase_imageView.getStyleClass().add("nav_link");
        purchase_imageView.setFitWidth(32);
        purchase_imageView.setFitHeight(28);
        purchase_btn.setGraphic(purchase_imageView);
        purchase_btn.getStyleClass().add("nav_link");
        purchase_btn.setPrefWidth(225);
        purchase_btn.setMaxWidth(Region.USE_COMPUTED_SIZE);
        purchase_btn.setPrefHeight(40);
        purchase_btn.setMaxHeight(Region.USE_COMPUTED_SIZE);
        purchase_btn.setContentDisplay(ContentDisplay.LEFT);
        purchase_btn.setGraphicTextGap(10);
        purchase_btn.setOnMouseEntered(this::navLinkMouseEntred);
        purchase_btn.setOnMouseExited(this::navLinkMouseExited);
        purchase_btn.setOnAction(this::loadPurchases);
        menu.getChildren().add(purchase_btn);

        MilkClollection_btn = new Button("Milk Collection");
        Image MilkClollection_img = new Image(getClass().getResourceAsStream("/icons/milk.png"));
        ImageView MilkClollection_imageView = new ImageView(MilkClollection_img);
        MilkClollection_imageView.getStyleClass().add("nav_link");
        MilkClollection_imageView.setFitWidth(32);
        MilkClollection_imageView.setFitHeight(28);
        MilkClollection_btn.setGraphic(MilkClollection_imageView);
        MilkClollection_btn.getStyleClass().add("nav_link");
        MilkClollection_btn.setPrefWidth(225);
        MilkClollection_btn.setMaxWidth(Region.USE_COMPUTED_SIZE);
        MilkClollection_btn.setPrefHeight(40);
        MilkClollection_btn.setMaxHeight(Region.USE_COMPUTED_SIZE);
        MilkClollection_btn.setContentDisplay(ContentDisplay.LEFT);
        MilkClollection_btn.setGraphicTextGap(10);
        MilkClollection_btn.setOnMouseEntered(this::navLinkMouseEntred);
        MilkClollection_btn.setOnMouseExited(this::navLinkMouseExited);
        MilkClollection_btn.setOnAction(this::loadMilkCollection);
        menu.getChildren().add(MilkClollection_btn);

        manageUsersBtn = new Button("Manage Users");
        Image manageUsersImg = new Image(getClass().getResourceAsStream("/icons/profile.png"));
        ImageView manageUsersImageView = new ImageView(manageUsersImg);
        manageUsersImageView.getStyleClass().add("nav_link");
        manageUsersImageView.setFitWidth(32);
        manageUsersImageView.setFitHeight(28);
        manageUsersBtn.setGraphic(manageUsersImageView);
        manageUsersBtn.getStyleClass().add("nav_link");
        manageUsersBtn.setPrefWidth(225);
        manageUsersBtn.setMaxWidth(Region.USE_COMPUTED_SIZE);
        manageUsersBtn.setPrefHeight(40);
        manageUsersBtn.setMaxHeight(Region.USE_COMPUTED_SIZE);
        manageUsersBtn.setContentDisplay(ContentDisplay.LEFT);
        manageUsersBtn.setGraphicTextGap(10);
        manageUsersBtn.setOnMouseEntered(this::navLinkMouseEntred);
        manageUsersBtn.setOnMouseExited(this::navLinkMouseExited);
        manageUsersBtn.setOnAction(this::loadManageUsers);
        menu.getChildren().add(manageUsersBtn);

        stock_btn = new Button("Stock");
        Image stock_img = new Image(getClass().getResourceAsStream("/icons/stock.png"));
        ImageView stock_imageView = new ImageView(stock_img);
        stock_imageView.getStyleClass().add("nav_link");
        stock_imageView.setFitWidth(32);
        stock_imageView.setFitHeight(28);
        stock_btn.setGraphic(stock_imageView);
        stock_btn.getStyleClass().add("nav_link");
        stock_btn.setPrefWidth(225);
        stock_btn.setMaxWidth(Region.USE_COMPUTED_SIZE);
        stock_btn.setPrefHeight(40);
        stock_btn.setMaxHeight(Region.USE_COMPUTED_SIZE);
        stock_btn.setContentDisplay(ContentDisplay.LEFT);
        stock_btn.setGraphicTextGap(10);
        stock_btn.setOnMouseEntered(this::navLinkMouseEntred);
        stock_btn.setOnMouseExited(this::navLinkMouseExited);
        stock_btn.setOnAction(this::loadStock);
        menu.getChildren().add(stock_btn);

        reports_btn = new Button("Reports");
        Image reports_img = new Image(getClass().getResourceAsStream("/icons/reports.png"));
        ImageView reports_imageView = new ImageView(reports_img);
        reports_imageView.getStyleClass().add("nav_link");
        reports_imageView.setFitWidth(32);
        reports_imageView.setFitHeight(28);
        reports_btn.setGraphic(reports_imageView);
        reports_btn.getStyleClass().add("nav_link");
        reports_btn.setPrefWidth(225);
        reports_btn.setMaxWidth(Region.USE_COMPUTED_SIZE);
        reports_btn.setPrefHeight(40);
        reports_btn.setMaxHeight(Region.USE_COMPUTED_SIZE);
        reports_btn.setContentDisplay(ContentDisplay.LEFT);
        reports_btn.setGraphicTextGap(10);
        reports_btn.setOnMouseEntered(this::navLinkMouseEntred);
        reports_btn.setOnMouseExited(this::navLinkMouseExited);
        reports_btn.setOnAction(this::loadReports);
        menu.getChildren().add(reports_btn);

        logout = new Button("Logout");
        Image logout_img = new Image(getClass().getResourceAsStream("/icons/logout.png"));
        ImageView logout_imageView = new ImageView(logout_img);
        logout_imageView.getStyleClass().add("nav_link");
        logout_imageView.setFitWidth(32);
        logout_imageView.setFitHeight(28);
        logout.setGraphic(logout_imageView);
        logout.getStyleClass().add("nav_link");
        logout.setPrefWidth(225);
        logout.setMaxWidth(Region.USE_COMPUTED_SIZE);
        logout.setPrefHeight(40);
        logout.setMaxHeight(Region.USE_COMPUTED_SIZE);
        logout.setContentDisplay(ContentDisplay.LEFT);
        logout.setGraphicTextGap(10);
        logout.setOnMouseEntered(this::navLinkMouseEntred);
        logout.setOnMouseExited(this::navLinkMouseExited);
        logout.setOnMouseClicked(this::logout);
        menu.getChildren().add(logout);
    }

    void loadStock(ActionEvent event) {
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
        ArrayList<Button> navLinks = menu.getChildren().stream().filter(node -> node instanceof Button).map(node -> (Button) node).collect(Collectors.toCollection(ArrayList::new));

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

    @FXML
    private void logout(MouseEvent event) {
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
            try {
                logoutSystem(login_view, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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