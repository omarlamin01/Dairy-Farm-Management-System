package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.closePopUp;

public class RoutineController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setFoods();
    }

    public RoutineController() {
    }

    @FXML
    TextField routineName;
    @FXML
    TextArea routineNotes;
    @FXML
    VBox foodList;

    String[] foods;

    public void setFoods() {
        //get foods from db
        this.foods = getFoods().keySet().toArray(new String[0]);

        //
        for (String food : foods) {
            HBox hBox = new HBox();
            hBox.setSpacing(60);
                VBox foodType = new VBox();
                    Label label = new Label("Food type");
                    label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                    CheckBox checkBox = new CheckBox(food);
                    checkBox.getStyleClass().add("main_content");
                VBox.setMargin(checkBox, new Insets(10, 0, 0, 0));
                foodType.getChildren().add(label);
                foodType.getChildren().add(checkBox);
            hBox.getChildren().add(foodType);
                VBox foodQuantity = new VBox();
                    Label label1 = new Label("Food Quantity");
                    label1.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                    TextField quantity = new TextField();
                    quantity.setPromptText("Quantity");
                    quantity.getStyleClass().add("input");
                    quantity.getStyleClass().add("quantity_input");
                VBox.setMargin(quantity, new Insets(10, 0, 0, 0));
                foodQuantity.getChildren().add(label1);
                foodQuantity.getChildren().add(quantity);
            hBox.getChildren().add(foodQuantity);
                VBox feedingTime = new VBox();
                    Label label2 = new Label("Feeding Time");
                    label2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                    ObservableList<String> periods = FXCollections.observableArrayList("Morning", "Evening");
                    ComboBox<String> period = new ComboBox<String>(periods);
                    period.setPromptText("Period");
                    period.getStyleClass().add("input");
                    period.getStyleClass().add("clock_input");
                VBox.setMargin(period, new Insets(10, 0, 0, 8));
                feedingTime.getChildren().add(label2);
                feedingTime.getChildren().add(period);
            hBox.getChildren().add(feedingTime);
            this.foodList.getChildren().add(hBox);
        }
    }

    public HashMap<String, Integer> getFoods() {
        HashMap<String, Integer> list = new HashMap<>();
        String query = "SELECT id, name FROM stock WHERE type = 'feed'";
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                list.put(resultSet.getString("name"), resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @FXML
    public void addRoutine(MouseEvent mouseEvent) {
        System.out.println("Routine { " +
                "Name: \"" + routineName.getText() + "\", " +
                "Notes: \"" + routineNotes.getText() + "\" " +
                "},"
        );

        closePopUp(mouseEvent);
    }
}