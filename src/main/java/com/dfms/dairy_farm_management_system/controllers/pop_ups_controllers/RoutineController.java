package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RoutineController implements Initializable {

    @FXML
    TextField routineName;
    @FXML
    TextArea routineNotes;
    @FXML
    Pane foodList;

    String[] foods;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setFoods();
    }

    public void setFoods() {
        //get foods from db
        this.foods = new String[]{"Corn", "Weed", "Grass"};

        //
        for (String food : foods) {
            HBox hBox = new HBox();
            hBox.setSpacing(50);
                VBox foodType = new VBox();
                    Label label = new Label("Food type");
                    CheckBox checkBox = new CheckBox(food);
                    checkBox.getStyleClass().add("main_content");
                foodType.getChildren().add(label);
                foodType.getChildren().add(checkBox);
            hBox.getChildren().add(foodType);
                VBox foodQuantity = new VBox();
                    Label label1 = new Label("Food quantity");
                    TextField quantity = new TextField("Quantity");
                    quantity.getStylesheets().add("../../../../../resources/style/style.css");
                    quantity.getStyleClass().add("input");
                    quantity.getStyleClass().add("quantity_input");
                foodQuantity.getChildren().add(label1);
                foodQuantity.getChildren().add(quantity);
            hBox.getChildren().add(foodQuantity);
                VBox feedingTime = new VBox();
                    Label label2 = new Label("Feeding Time");
                    ObservableList<String> periods = FXCollections.observableArrayList("Morning", "Evening");
                    ComboBox<String> period = new ComboBox<String>(periods);
                    period.getStylesheets().add("../../../../../resources/style/style.css");
                    period.getStyleClass().add("input");
                    period.getStyleClass().add("clock_input");
                feedingTime.getChildren().add(label2);
                feedingTime.getChildren().add(period);
            hBox.getChildren().add(feedingTime);
            this.foodList.getChildren().add(hBox);
        }
    }

    @FXML
    public void addRoutine(MouseEvent mouseEvent) {
        System.out.println("Routine { " +
                "Name: \"" + routineName.getText() + "\", " +
                "Notes: \"" + routineNotes.getText() + "\" " +
                "},"
        );

        ((Stage)(((Button)mouseEvent.getSource()).getScene().getWindow())).close();
    }
}