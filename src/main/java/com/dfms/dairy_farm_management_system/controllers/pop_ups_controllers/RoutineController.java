package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.disconnect;
import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.closePopUp;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

import com.dfms.dairy_farm_management_system.models.Routine;
import com.dfms.dairy_farm_management_system.models.RoutineDetails;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
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

public class RoutineController implements Initializable {

    private static final String ALERT_ERROR = "ERROR";
    private static final String ALERT_SUCCESS = "SUCCESS";
    private static final String CSS_INPUT = "input";
    private static final String LABEL_STYLE_BOLD_14 = "-fx-font-size: 14px; -fx-font-weight: bold;";

    @FXML
    TextField routineName;

    @FXML
    TextArea routineNotes;

    @FXML
    VBox foodList;

    @FXML
    Button routineBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFoods(null);

        routineBtn.setOnMouseClicked((MouseEvent mouseEvent) -> {
            Routine routine = new Routine();
            handleRoutinePersist(mouseEvent, routine, false);
        });
    }

    public void initData(Routine routine) {
        this.routineName.setText(routine.getName());
        this.routineNotes.setText(routine.getNote());
        setFoods(routine.getDetails());

        routineBtn.setText("UPDATE");
        routineBtn.setOnMouseClicked((MouseEvent mouseEvent) -> handleRoutinePersist(mouseEvent, routine, true));
    }

    private void handleRoutinePersist(MouseEvent mouseEvent, Routine routine, boolean isUpdate) {
        routine.setName(routineName.getText());
        routine.setNote(routineNotes.getText());

        boolean routineOk = isUpdate ? routine.update() : routine.save();
        if (!routineOk) {
            displayAlert(
                ALERT_ERROR,
                isUpdate ? "Some error happened while updating!" : "Some error happened while saving!",
                Alert.AlertType.ERROR
            );
            return;
        }

        if (!isUpdate) {
            routine.setId(Routine.getLastId());
        } else {
            deleteDetails(routine.getDetails());
        }

        ArrayList<RoutineDetails> savedDetails = new ArrayList<>();
        boolean detailsOk = buildAndSaveDetails(routine, savedDetails);

        if (!detailsOk) {
            revertChanges(routine, savedDetails);
            displayAlert(
                ALERT_ERROR,
                isUpdate ? "Some error happened while updating!" : "Some error happened while saving!",
                Alert.AlertType.ERROR
            );
            return;
        }

        closePopUp(mouseEvent);
        displayAlert(
            ALERT_SUCCESS,
            isUpdate ? "Routine updated successfully" : "Routine saved successfully",
            Alert.AlertType.INFORMATION
        );
    }

    private boolean buildAndSaveDetails(Routine routine, ArrayList<RoutineDetails> savedDetails) {
        for (Node box : foodList.getChildren()) {
            CheckBox checkBox = (CheckBox) ((VBox) ((HBox) box).getChildren().get(0)).getChildren().get(1);
            if (!checkBox.isSelected()) {
                continue;
            }

            String foodQuantity = (
                (TextField) (((VBox) ((HBox) box).getChildren().get(1)).getChildren().get(1))
            ).getText();
            String foodPeriod = (
                (ComboBox<String>) (((VBox) ((HBox) box).getChildren().get(2)).getChildren().get(1))
            ).getValue();

            RoutineDetails detail = new RoutineDetails();
            // detail.setStock_id(getFoods().get(checkBox.getText())); // keep commented as in your original
            detail.setRoutine_id(routine.getId());
            detail.setQuantity(Float.parseFloat(foodQuantity));
            detail.setFeeding_time(foodPeriod);

            if (!detail.save()) {
                return false;
            }

            detail.setId(RoutineDetails.getLastId());
            savedDetails.add(detail);
        }
        return true;
    }

    public void setFoods(ArrayList<RoutineDetails> details) {
        this.foodList.getChildren().clear();

        if (details != null && !details.isEmpty()) {
            HashMap<String, RoutineDetails> detailsHashMap = new HashMap<>();
            ArrayList<String> routinesFeedsNames = new ArrayList<>();

            for (RoutineDetails routineDetails : details) {
                detailsHashMap.put(routineDetails.getStock_name(), routineDetails);
                routinesFeedsNames.add(routineDetails.getStock_name());
            }

            for (String foodName : getFoods()) {
                if (routinesFeedsNames.contains(foodName)) {
                    addFoodRow(foodName, detailsHashMap.get(foodName));
                } else {
                    addFoodRow(foodName, null);
                }
            }
            return;
        }

        for (String foodName : getFoods()) {
            addFoodRow(foodName, null);
        }
    }

    private void addFoodRow(String food, RoutineDetails detailOrNull) {
        HBox hBox = new HBox();
        hBox.setSpacing(60);

        VBox foodType = new VBox();
        Label label = new Label("Food type");
        label.setStyle(LABEL_STYLE_BOLD_14);

        CheckBox checkBox = new CheckBox(food);
        checkBox.getStyleClass().add("main_content");
        if (detailOrNull != null) {
            checkBox.setSelected(true);
        }
        VBox.setMargin(checkBox, new Insets(10, 0, 0, 0));
        foodType.getChildren().addAll(label, checkBox);
        hBox.getChildren().add(foodType);

        VBox foodQuantity = new VBox();
        Label label1 = new Label("Food Quantity");
        label1.setStyle(LABEL_STYLE_BOLD_14);

        TextField quantity = new TextField();
        quantity.setPromptText("Quantity");
        quantity.getStyleClass().add(CSS_INPUT);
        quantity.getStyleClass().add("quantity_input");
        if (detailOrNull != null) {
            quantity.setText(String.valueOf(detailOrNull.getQuantity()));
        }
        VBox.setMargin(quantity, new Insets(10, 0, 0, 0));
        foodQuantity.getChildren().addAll(label1, quantity);
        hBox.getChildren().add(foodQuantity);

        VBox feedingTime = new VBox();
        Label label2 = new Label("Feeding Time");
        label2.setStyle(LABEL_STYLE_BOLD_14);

        ObservableList<String> periods = FXCollections.observableArrayList("Morning", "Evening");
        ComboBox<String> period = new ComboBox<>(periods);
        period.setPromptText("Period");
        period.getStyleClass().add(CSS_INPUT);
        period.getStyleClass().add("clock_input");
        if (detailOrNull != null) {
            period.setValue(detailOrNull.getFeeding_time());
        }
        VBox.setMargin(period, new Insets(10, 0, 0, 8));
        feedingTime.getChildren().addAll(label2, period);
        hBox.getChildren().add(feedingTime);

        this.foodList.getChildren().add(hBox);
    }

    public ArrayList<String> getFoods() {
        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT * FROM stocks WHERE type = 'feed'";

        try {
            Connection connection = getConnection();
            try (
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    list.add(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return list;
    }

    public void revertChanges(Routine routine, ArrayList<RoutineDetails> details) {
        deleteDetails(details);
        routine.delete();
    }

    public void deleteDetails(ArrayList<RoutineDetails> details) {
        if (details == null) return;

        for (RoutineDetails detail : new ArrayList<>(details)) {
            detail.delete();
        }
        details.clear();
    }
}
