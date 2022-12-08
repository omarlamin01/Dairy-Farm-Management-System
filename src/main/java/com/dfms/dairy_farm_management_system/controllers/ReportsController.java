package com.dfms.dairy_farm_management_system.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.util.ResourceBundle;

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
}
