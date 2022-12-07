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
import javafx.scene.layout.VBox;

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

public class UpdateRoutineController {
    @FXML
    TextField routineName;
    @FXML
    TextArea routineNotes;
    @FXML
    VBox foodList;
    @FXML
    Button routineBtn;

    public void initData(Routine routine) {
        this.routineName.setText(routine.getName());
        this.routineNotes.setText(routine.getNote());
        setFoods(routine.getDetails());
        routineBtn.setText("UPDATE");
        routineBtn.setOnMouseClicked((MouseEvent mouseEvent) -> {
            routine.setName(routineName.getText());
            routine.setNote(routineNotes.getText());

            if (routine.update()) {
                deleteDetails(routine.getDetails());
                ArrayList<RoutineDetails> routineDetails = new ArrayList<>();
                boolean detailsAreSaved = true;
                for (Node box : foodList.getChildren()) {
                    CheckBox checkBox = (CheckBox) ((VBox) ((HBox) box).getChildren().get(0)).getChildren().get(1);
                    if (checkBox.isSelected()) {
                        String foodName = checkBox.getText();
                        String foodQuantity = ((TextField) (((VBox) ((HBox) box).getChildren().get(1)).getChildren().get(1))).getText();
                        String foodPeriod = ((ComboBox<String>) (((VBox) ((HBox) box).getChildren().get(2)).getChildren().get(1))).getValue();

                        RoutineDetails routineDetails1 = new RoutineDetails();

//                        routineDetails1.setStock_id(getFoods().get(foodName));
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
                    displayAlert("SUCCESS", "Routine updated successfully", Alert.AlertType.INFORMATION);
                } else {
                    displayAlert("ERROR", "Some error happened while updating!", Alert.AlertType.ERROR);
                }
            } else {
                displayAlert("ERROR", "Some error happened while updating!", Alert.AlertType.ERROR);
            }
        });
    }

    public void setFoods(ArrayList<RoutineDetails> details) {
        this.foodList.getChildren().clear();
        if (details != null) {
            System.out.println("Details != null");
            if (!details.isEmpty()) {
                System.out.println("Details != empty");
                HashMap<String, RoutineDetails> detailsHashMap = new HashMap<>();
                ArrayList<String> routinesFeedsNames = new ArrayList<>();

                for (RoutineDetails routineDetails: details) {
                    detailsHashMap.put(routineDetails.getStock_name(), routineDetails);
                    routinesFeedsNames.add(routineDetails.getStock_name());
                }

                System.out.println(detailsHashMap);
                System.out.println(routinesFeedsNames);
                System.out.println(getFoods());

                for (String foodName: getFoods()) {
                    if (routinesFeedsNames.contains(foodName)) {
                        System.out.println("routine feeds contains " + foodName);
                        addSelectedItem(foodName, detailsHashMap.get(foodName));
                    } else {
                        addItem(foodName);
                    }
                }
            }
        }
        else {
            for (String foodName: getFoods()) {
                addItem(foodName);
            }
        }
    }

    public void addItem(String food) {
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

    public void addSelectedItem(String food, RoutineDetails detail) {
        HBox hBox = new HBox();
        hBox.setSpacing(60);
        VBox foodType = new VBox();
        Label label = new Label("Food type");
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        CheckBox checkBox = new CheckBox(food);
        checkBox.setSelected(true);
        System.out.println(food + " checkbox is selected " + checkBox.isSelected());
        checkBox.getStyleClass().add("main_content");
        VBox.setMargin(checkBox, new Insets(10, 0, 0, 0));
        foodType.getChildren().add(label);
        foodType.getChildren().add(checkBox);
        hBox.getChildren().add(foodType);
        VBox foodQuantity = new VBox();
        Label label1 = new Label("Food Quantity");
        label1.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField quantity = new TextField();
        quantity.setText(String.valueOf(detail.getQuantity()));
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
        period.setValue(detail.getFeeding_time());
        period.setPromptText("Period");
        period.getStyleClass().add("input");
        period.getStyleClass().add("clock_input");
        VBox.setMargin(period, new Insets(10, 0, 0, 8));
        feedingTime.getChildren().add(label2);
        feedingTime.getChildren().add(period);
        hBox.getChildren().add(feedingTime);
        this.foodList.getChildren().add(hBox);
    }

    public ArrayList<String> getFoods() {
        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT * FROM stocks WHERE type = 'feed'";
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void revertChanges(Routine routine, ArrayList<RoutineDetails> details) {
        for (RoutineDetails detail : details) {
            detail.delete();
            details.remove(detail);
        }
        routine.delete();
    }

    public void deleteDetails(ArrayList<RoutineDetails> details) {
        for (RoutineDetails detail : details) {
            detail.delete();
            details.remove(detail);
        }
    }
}