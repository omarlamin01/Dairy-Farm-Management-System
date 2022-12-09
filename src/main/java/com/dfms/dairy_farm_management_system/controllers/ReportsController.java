package com.dfms.dairy_farm_management_system.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Style;
import javafx.css.Stylesheet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.StyleSheetDocument;

import javax.swing.text.html.StyleSheet;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class ReportsController implements Initializable {
//  milk collection part
    @FXML
    private DatePicker from_date_picker;

    @FXML
    private DatePicker to_date_picker;

    @FXML
    private Button search_btn;

    @FXML
    private VBox milk_collection_results_area;

    private class DailyMilkCollection {
        Date collection_date;
        float total_day_collection;
        float morning_collection;
        float evening_collection;
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
        search_btn.setOnMouseClicked(event -> { displayData(); });
    }

    private void initView() {
        to_date_picker.setDayCellFactory(d -> new DateCell() {
            /**
             * {@inheritDoc}
             *
             * @param item
             * @param empty
             */
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(LocalDate.now()));
            }
        });

        from_date_picker.setDayCellFactory(d -> new DateCell() {
            /**
             * {@inheritDoc}
             *
             * @param item
             * @param empty
             */
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(LocalDate.now()));
            }
        });

        to_date_picker.setOnAction(event -> {
            LocalDate date = to_date_picker.getValue();
            from_date_picker.setDayCellFactory(d -> new DateCell() {
                /**
                 * {@inheritDoc}
                 *
                 * @param item
                 * @param empty
                 */
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(item.isAfter(date));
                }
            });
        });

        from_date_picker.setOnAction(event -> {
            LocalDate date = from_date_picker.getValue();
            to_date_picker.setDayCellFactory(d -> new DateCell() {
                /**
                 * {@inheritDoc}
                 *
                 * @param item
                 * @param empty
                 */
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(item.isBefore(date) || item.isAfter(LocalDate.now()));
                }
            });
        });

    }

    private ObservableList<DailyMilkCollection> getData() {
        LocalDate min_date = from_date_picker.getValue();
        LocalDate max_date = to_date_picker.getValue();

        ObservableList<DailyMilkCollection> data = FXCollections.observableArrayList();

        try {
            String query =
                    "SELECT `created_at` AS collection_date, " +
                    "sum(`quantity`) AS total_day_collection " +
                    "FROM `milk_collections` WHERE `created_at` <= ? AND `created_at` >= ? " +
                    "ORDER BY `created_at`";

            PreparedStatement statement = getConnection().prepareStatement(query);

            statement.setTimestamp(1, Timestamp.valueOf(max_date.atStartOfDay()));
            statement.setTimestamp(2, Timestamp.valueOf(min_date.atStartOfDay()));

            System.out.println(statement);
            System.out.println(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DailyMilkCollection dailyMilkCollection = new DailyMilkCollection();

                dailyMilkCollection.collection_date = resultSet.getDate("collection_date");
                dailyMilkCollection.total_day_collection = resultSet.getFloat("total_day_collection");
//                dailyMilkCollection.morning_collection = resultSet.getFloat("morning_collection");
//                dailyMilkCollection.evening_collection = resultSet.getFloat("evening_collection");

                data.add(dailyMilkCollection);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
        return data;
    }

    private void displayData() {
        milk_collection_results_area.getChildren().clear();
        TableColumn<DailyMilkCollection, String> collection_date = new TableColumn<>("Date");
        collection_date.setCellValueFactory(new PropertyValueFactory<DailyMilkCollection, String>("collection_date"));

        TableColumn<DailyMilkCollection, String> total_day_collection = new TableColumn<>("Total day collection");
        total_day_collection.setCellValueFactory(new PropertyValueFactory<DailyMilkCollection, String>("total_day_collection"));

        TableColumn<DailyMilkCollection, String> morning_collection = new TableColumn<>("Morning collection");
        morning_collection.setCellValueFactory(new PropertyValueFactory<DailyMilkCollection, String>("morning_collection"));

        TableColumn<DailyMilkCollection, String> evening_collection = new TableColumn<>("Evening collection");
        evening_collection.setCellValueFactory(new PropertyValueFactory<DailyMilkCollection, String>("evening_collection"));

        TableView<DailyMilkCollection> data_table = new TableView<>();
        data_table.getColumns().addAll(collection_date, total_day_collection, morning_collection, evening_collection);
        data_table.getStylesheets().add("/style/table_view.css");
        data_table.getStyleClass().add("table-view");
        data_table.setItems(getData());
        milk_collection_results_area.getChildren().add(data_table);
    }
}
