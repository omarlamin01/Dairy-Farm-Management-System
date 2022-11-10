package com.dfms.dairy_farm_management_system.helpers;

import com.dfms.dairy_farm_management_system.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Objects;
import java.util.function.DoubleConsumer;

public class Helper {
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

    //check if the input is empty
    public static void validateInputs(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue) {
                    if (newValue.isEmpty()) {
                        textField.setStyle("-fx-border-color: red");
                    } else {
                        textField.setStyle("-fx-border-color: transparent");
                    }
                }
            });
        }
    }

    //validate email input
    public static void validateEmailInput(TextField textField) {
        // email should be a valid email otherwise error
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
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
}
