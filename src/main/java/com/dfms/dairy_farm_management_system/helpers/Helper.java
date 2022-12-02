package com.dfms.dairy_farm_management_system.helpers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Employee;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.w3c.dom.Text;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.DoubleConsumer;

public class Helper {
    private static Connection con = DBConfig.getConnection();

    public static void centerScreen(Stage stage) {
        Screen screen = Screen.getPrimary();
        Rectangle2D sbounds = screen.getBounds();

        double sw = sbounds.getWidth();
        double sh = sbounds.getHeight();

        listenToSizeInitialization(stage.widthProperty(), w -> stage.setX((sw - w) / 2));
        listenToSizeInitialization(stage.heightProperty(), h -> stage.setY((sh - h) / 2));
    }

    public static void listenToSizeInitialization(ObservableDoubleValue size, DoubleConsumer handler) {
        ChangeListener<Number> listener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> obs, Number oldSize, Number newSize) {
                if (newSize.doubleValue() != Double.NaN) {
                    handler.accept(newSize.doubleValue());
                    size.removeListener(this);
                }
            }
        };
        size.addListener(listener);
    }

    public static void openNewWindow(String title, String view) throws IOException {
        String url = "popups/" + view + ".fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(url));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        // stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(scene);
        centerScreen(stage);
        stage.show();
    }

    //close window
    public static void closeWindow(Object event) {
        Stage stage = (Stage) ((javafx.scene.Node) event).getScene().getWindow();
        stage.close();
    }

    public static void closePopUp(MouseEvent mouseEvent) {
        ((Stage) (((Button) mouseEvent.getSource()).getScene().getWindow())).close();
    }

    //validate inputs to accept only numbers
    public static void validateNumericInput(TextField textField) {
        // force the field to be numeric only
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("\\D", ""));
                }
            }
        });
    }

    //validate inputs to accept only decimal numbers and one dot
    public static void validateDecimalInput(TextField textField) {
        // force the field to be numeric only
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                //accept only numbers and only one dot
                if (!newValue.matches("\\d*\\.?\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d.]", ""));
                }
            }
        });
    }

    //validate inputs to accept only + and numbers
    public static void validatePhoneInput(TextField textField) {
        // force the field to be numeric only
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                //accept only phone numbers format
                if (!newValue.matches("\\+?\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d+]", ""));
                }
            }
        });
    }

    public static void setErrorOnInput(TextField textField, String error) {
        textField.getStyleClass().add("error");
        textField.setTooltip(new Tooltip(error));
    }

    public static void setErrorOnInput(DatePicker datePicker, String error) {
        datePicker.getStyleClass().add("error");
        datePicker.setTooltip(new Tooltip(error));
    }

    public static void setErrorOnInput(ComboBox comboBox, String error) {
        comboBox.getStyleClass().add("error");
        comboBox.setTooltip(new Tooltip(error));
    }

    public static void removeErrorOnInput(TextField textField) {
        textField.getStyleClass().remove("error");
        textField.setTooltip(null);
    }

    public static void removeErrorOnInput(DatePicker datePicker) {
        datePicker.getStyleClass().remove("error");
        datePicker.setTooltip(null);
    }

    public static void removeErrorOnInput(ComboBox comboBox) {
        comboBox.getStyleClass().remove("error");
        comboBox.setTooltip(null);
    }

    //validate email input
    public static void validateEmailInput(TextField textField) {
        // email should be a valid email otherwise error
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
                        + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$")) {
                    textField.setStyle("-fx-border-color: red");
                } else {
                    textField.setStyle("-fx-border-color: transparent");
                }
            }
        });
    }

    //validate password input
    public static void validatePasswordInput(TextField textField) {
        //password must be at least 8 characters long
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (newValue.length() < 8) {
                    textField.setStyle("-fx-border-color: red");
                } else {
                    textField.setStyle("-fx-border-color: transparent");
                }
            }
        });
    }

    //display alert message
    public static void displayAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static HashMap<String, Integer> getRoles() {
        HashMap<String, Integer> rolesList = new HashMap<>();
        Statement st = null;
        try {
            con = DBConfig.getConnection();
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `roles`");
            while (rs.next()) {
                rolesList.put(rs.getString("name"), rs.getInt("id"));
            }
        } catch (SQLException e) {
            displayAlert("Error", "Error while getting roles", Alert.AlertType.ERROR);
        }
        return rolesList;
    }

    //encrypt password
    public static String encryptPassword(String password) {
        String generatedPassword = null;
        // Create MessageDigest instance for MD5
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        md.update(password.getBytes(), 0, password.length());
        generatedPassword = new BigInteger(1, md.digest()).toString(16);
        return generatedPassword;
    }

    public static boolean MD5(String encrypted_password, String password) {
        String md5 = null;
        boolean isEquals = false;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            md5 = new BigInteger(1, digest).toString(16);
            isEquals = md5.equals(encrypted_password);
        } catch (NoSuchAlgorithmException e) {
            isEquals = false;
        }

        return isEquals;
    }
}
