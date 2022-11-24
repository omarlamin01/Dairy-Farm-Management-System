package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Routine;
import com.dfms.dairy_farm_management_system.models.RoutineDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.closePopUp;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

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

    public void setFoods() {
        //get foods from db
        String[] foods = getFoods().keySet().toArray(new String[0]);
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
        String query = "SELECT id, name FROM stocks WHERE type = 'feed'";
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.put(resultSet.getString("name"), resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @FXML
    public void addRoutine(MouseEvent mouseEvent) {
        Routine routine = new Routine();

        routine.setName(routineName.getText());
        routine.setNote(routineNotes.getText());

        if (routine.save()) {
            routine.setId(Routine.getLastId());
            ArrayList<RoutineDetails> routineDetails = new ArrayList<>();
            boolean detailsAreSaved = true;
            for (Node box : foodList.getChildren()) {
                CheckBox checkBox = (CheckBox) ((VBox) ((HBox) box).getChildren().get(0)).getChildren().get(1);
                if (checkBox.isSelected()) {
                    String foodName = checkBox.getText();
                    String foodQuantity = ((TextField) (((VBox) ((HBox) box).getChildren().get(1)).getChildren().get(1))).getText();
                    String foodPeriod = ((ComboBox<String>) (((VBox) ((HBox) box).getChildren().get(2)).getChildren().get(1))).getValue();

                    RoutineDetails routineDetails1 = new RoutineDetails();

                    routineDetails1.setStock_id(getFoods().get(foodName));
                    routineDetails1.setRoutine_id(routine.getId());
                    routineDetails1.setQuantity(Float.parseFloat(foodQuantity));
                    routineDetails1.setFeeding_time(foodPeriod);

                    if (routineDetails1.save()) {
                        routineDetails1.setId(RoutineDetails.getLastId());
                        routineDetails.add(routineDetails1);
                    } else {
                        revertChanges(routine, routineDetails);
                        detailsAreSaved = false;
                        break;
                    }
                }
            }
            if (detailsAreSaved) {
                closePopUp(mouseEvent);
                displayAlert("SUCCESS", "Routine saved successfully", Alert.AlertType.INFORMATION);
            } else {
                displayAlert("ERROR", "Some error happened while saving!", Alert.AlertType.ERROR);
            }
        } else {
            displayAlert("ERROR", "Some error happened while saving!", Alert.AlertType.ERROR);
        }
    }

    public void revertChanges(Routine routine, ArrayList<RoutineDetails> details) {
        for (RoutineDetails detail : details) {
            detail.delete();
            details.remove(detail);
        }
        routine.delete();
    }
}