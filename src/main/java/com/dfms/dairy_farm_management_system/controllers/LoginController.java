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

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class LoginController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email_input.setText(autoConnect()[0]);
        password_input.setText(autoConnect()[1]);
    }

    public String[] autoConnect() {
        String[] arr = new String[2];
        String query = "SELECT email, password FROM `users` ORDER BY `users`.`id` ASC LIMIT 1";
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            arr[0] = resultSet.getString("email");
            arr[1] = resultSet.getString("password");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return arr;
    }

    private Statement st;
    private Connection con = getConnection();
    @FXML
    private Circle close_btn;

    @FXML
    private TextField email_input;

    @FXML
    private Label forget_password;

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField password_input;

    @FXML
    private void login(MouseEvent event) throws SQLException {

        if (email_input.getText() == null || password_input.getText() == null){
            displayAlert("Error", "Please fill the required fields!", Alert.AlertType.ERROR);
            return;
        }

        String email = email_input.getText().trim();
        String password = password_input.getText().trim();

        if (validateLogin(email, password)) {
            //store logged in user in session
            String query = "SELECT * FROM `users` WHERE email = '" + email + "' AND password = '" + password + "'";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getInt("id"));
                user.setSalary(rs.getFloat("salary"));
                user.setGender(rs.getString("gender"));
                user.setPhone(rs.getString("phone"));
                user.setAdress(rs.getString("address"));
                user.setCin(rs.getString("cin"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setUpdatedAt(rs.getTimestamp("updated_at"));

                Session.setCurrentUser(user);
            }
            switchToMainLayout(event);
        } else {
            displayAlert("Invalid email or password", "Please check your email and password and try again", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void loginWithEnter(KeyEvent event) {
        //check if enter key is pressed
        if (event.getCode().toString().equals("ENTER")) {
            try {
                String email = email_input.getText().trim();
                String password = password_input.getText().trim();

                if (validateLogin(email, password)) {
                    //store logged in user in session
                    String query = "SELECT * FROM `users` WHERE email = '" + email + "' AND password = '" + password + "'";
                    st = con.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    if (rs.next()) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setFirstName(rs.getString("first_name"));
                        user.setLastName(rs.getString("last_name"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password"));
                        user.setRole(rs.getInt("id"));
                        user.setSalary(rs.getFloat("salary"));
                        user.setGender(rs.getString("gender"));
                        user.setPhone(rs.getString("phone"));
                        user.setAdress(rs.getString("address"));
                        user.setCin(rs.getString("cin"));
                        user.setCreatedAt(rs.getTimestamp("created_at"));
                        user.setUpdatedAt(rs.getTimestamp("updated_at"));

                        Session.setCurrentUser(user);
                    }
                    //switch to main layout
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
                } else {
                    displayAlert("Invalid email or password", "Please check your email and password and try again", Alert.AlertType.ERROR);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public boolean validateLogin(String email, String password) throws SQLException {
        //check if user exists limit 1
        st = con.createStatement();
        String query = "SELECT email, password FROM `users` WHERE email = '" + email + "' AND password = '" + password + "' LIMIT 1";
        st.executeQuery(query);
        return st.getResultSet().next();
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
