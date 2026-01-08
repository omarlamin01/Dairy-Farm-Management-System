package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.connection.Session;
import com.dfms.dairy_farm_management_system.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.disconnect;
import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class LoginController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email_input.setText(getEmail());
        password_input.setText(DEFAULT_PASSWORD);
    }

    public String getEmail() {
        String query = "SELECT email FROM `users` ORDER BY `users`.`id` ASC LIMIT 1";
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("email");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }
        return null;
    }

    @FXML
    private Circle close_btn;

    @FXML
    TextField email_input;

    @FXML
    private Label forget_password;

    @FXML
    Button login_btn;

    @FXML
    PasswordField password_input;

    @FXML
    void login(MouseEvent event) {
        if (email_input.getText() == null || password_input.getText() == null) {
            displayAlert("Error", "Please fill the required fields!", Alert.AlertType.ERROR);
            return;
        }
        attemptLogin(email_input.getText().trim(), password_input.getText().trim(), (Node) event.getSource());
    }

    @FXML
    void loginWithEnter(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            attemptLogin(email_input.getText().trim(), password_input.getText().trim(), (Node) event.getSource());
        }
    }

    private void attemptLogin(String email, String password, Node sourceNode) {
        try {
            if (validatePassword(email, password)) {
                Connection connection = getConnection();
                User user = new User();
                
                // Secure query for User details
                String userQuery = "SELECT * FROM `users` WHERE email = ?";
                try (PreparedStatement userStmt = connection.prepareStatement(userQuery)) {
                    userStmt.setString(1, email);
                    ResultSet resultSet = userStmt.executeQuery();
                    if (resultSet.next()) {
                        user.setId(resultSet.getInt("id"));
                        user.setFirstName(resultSet.getString("first_name"));
                        user.setLastName(resultSet.getString("last_name"));
                        user.setEmail(resultSet.getString("email"));
                        user.setEncryptedPassword(resultSet.getString("password"));
                        user.setRole(resultSet.getInt("role"));
                        user.setSalary(resultSet.getFloat("salary"));
                        user.setGender(resultSet.getString("gender"));
                        user.setPhone(resultSet.getString("phone"));
                        user.setAdress(resultSet.getString("address"));
                        user.setCin(resultSet.getString("cin"));
                        user.setCreatedAt(resultSet.getTimestamp("created_at"));
                        user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    }
                }

                // Secure query for Employee details
                String empQuery = "SELECT * FROM `employees` WHERE email = ?";
                try (PreparedStatement empStmt = connection.prepareStatement(empQuery)) {
                    empStmt.setString(1, email);
                    ResultSet resultSet = empStmt.executeQuery();
                    if (resultSet.next()) {
                        user.setHireDate(resultSet.getDate("hire_date"));
                        user.setContractType(resultSet.getString("contract_type"));
                    }
                }

                disconnect();
                Session.setCurrentUser(user);
                
                // Switch to main layout
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main_layout.fxml"));
                Stage stage = new Stage();
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setTitle("Dairy Farm Management System");
                stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                stage.setScene(scene);
                
                sourceNode.getScene().getWindow().hide();
                stage.show();
            } else {
                displayAlert("Invalid email or password", "Please check your email and password and try again", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            displayAlert("Database Error", "An error occurred while logging in.", Alert.AlertType.ERROR);
        }
    }

    public boolean validatePassword(String email, String password) throws SQLException {
        //check if user exists limit 1
        try {
            String query = "SELECT `password` FROM `users` WHERE email = ? LIMIT 1";
            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return MD5(resultSet.getString("password"), password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }

    @FXML
    private void exitApplication(MouseEvent event) {
        DBConfig.disconnect();
        System.exit(0);
    }

    public void switchToMainLayout(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main_layout.fxml"));
        Stage stage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("Dairy Farm Management System");
        stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
        stage.setScene(scene);
        // centerScreen(stage);
        ((Node) event.getSource()).getScene().getWindow().hide();
        stage.show();
    }
}
