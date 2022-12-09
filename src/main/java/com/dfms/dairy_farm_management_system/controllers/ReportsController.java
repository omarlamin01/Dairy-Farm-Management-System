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
import javafx.scene.layout.Region;
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

    public class DailyMilkCollection {
        private Date collection_date;
        private float total_day_collection;
        private float morning_collection;
        private float evening_collection;

        public Date getCollection_date() {
            return collection_date;
        }

        public void setCollection_date(Date collection_date) {
            this.collection_date = collection_date;
        }

        public float getTotal_day_collection() {
            return total_day_collection;
        }

        public void setTotal_day_collection(float total_day_collection) {
            this.total_day_collection = total_day_collection;
        }

        public float getMorning_collection() {
            return morning_collection;
        }

        public void setMorning_collection(float morning_collection) {
            this.morning_collection = morning_collection;
            total_day_collection += morning_collection;
        }

        public float getEvening_collection() {
            return evening_collection;
        }

        public void setEvening_collection(float evening_collection) {
            this.evening_collection = evening_collection;
            total_day_collection += evening_collection;
        }

        @Override
        public String toString() {
            return "DailyMilkCollection{" +
                    "collection_date=" + collection_date +
                    ", total_day_collection=" + total_day_collection +
                    ", morning_collection=" + morning_collection +
                    ", evening_collection=" + evening_collection +
                    '}';
        }
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
                    "sum(`quantity`) AS morning_collection " +
                    "FROM `milk_collections` WHERE `period` = ? AND `created_at` <= ? AND `created_at` >= ? " +
                    "GROUP BY date(created_at) " +
                    "ORDER BY `created_at` DESC";

            PreparedStatement statement = getConnection().prepareStatement(query);

            statement.setString(1, "morning");
            statement.setTimestamp(2, Timestamp.valueOf(max_date.atTime(23, 59, 59)));
            statement.setTimestamp(3, Timestamp.valueOf(min_date.atStartOfDay()));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DailyMilkCollection dailyMilkCollection = new DailyMilkCollection();

                dailyMilkCollection.setCollection_date(resultSet.getDate("collection_date"));
                dailyMilkCollection.setMorning_collection(resultSet.getFloat("morning_collection"));
                try {
                    String query1 = "SELECT sum(`quantity`) AS evening_collection " +
                            "FROM `milk_collections` WHERE `period` = ? AND date(`created_at`) = ? ";

                    PreparedStatement preparedStatement = getConnection().prepareStatement(query1);

                    preparedStatement.setString(1, "evening");
                    preparedStatement.setDate(2, dailyMilkCollection.getCollection_date());

                    ResultSet resultSet1 = preparedStatement.executeQuery();
                    if (resultSet1.next()) {
                        dailyMilkCollection.setEvening_collection(resultSet1.getFloat("evening_collection"));
                    }
                } catch (SQLException e) {}
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
        milk_collection_results_area.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);



        TableColumn<DailyMilkCollection, String> collection_date = new TableColumn<>("Date");
        collection_date.setCellValueFactory(new PropertyValueFactory<DailyMilkCollection, String>("collection_date"));
        collection_date.setPrefWidth(180);

        TableColumn<DailyMilkCollection, String> total_day_collection = new TableColumn<>("Total day collection");
        total_day_collection.setCellValueFactory(new PropertyValueFactory<DailyMilkCollection, String>("total_day_collection"));
        total_day_collection.setPrefWidth(180);

        TableColumn<DailyMilkCollection, String> morning_collection = new TableColumn<>("Morning collection");
        morning_collection.setCellValueFactory(new PropertyValueFactory<DailyMilkCollection, String>("morning_collection"));
        morning_collection.setPrefWidth(180);

        TableColumn<DailyMilkCollection, String> evening_collection = new TableColumn<>("Evening collection");
        evening_collection.setCellValueFactory(new PropertyValueFactory<DailyMilkCollection, String>("evening_collection"));
        evening_collection.setPrefWidth(180);


        TableView<DailyMilkCollection> data_table = new TableView<>();
        data_table.getColumns().addAll(collection_date, total_day_collection, morning_collection, evening_collection);
        data_table.getStyleClass().add("table-view");
        data_table.setItems(getData());
        data_table.setPrefSize(600, 400);

        milk_collection_results_area.getChildren().add(data_table);
    }
}
