package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.connection.DBConfig;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class LoginController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private Statement st;
    private Connection con = DBConfig.getConnection();
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
        String email = email_input.getText().trim();
        String password = password_input.getText().trim();

        System.out.println("email: " + email + " password: " + password);

        if (validateLogin(email, password)) {
            switchToMainLayout(event);
        } else {
            displayAlert("Invalid email or password", "Please check your email and password and try again", Alert.AlertType.ERROR);
        }
    }

    public boolean validateLogin(String email, String password) throws SQLException {
        //check if user exists limit 1
        st = con.createStatement();
        String query = "SELECT email, password FROM user WHERE email = '" + email + "' AND password = '" + password + "' LIMIT 1";
        st.executeQuery(query);
        return st.getResultSet().next();
    }

    @FXML
    private void exitApplication(MouseEvent event) {
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
