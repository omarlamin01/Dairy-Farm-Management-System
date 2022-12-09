package com.dfms.dairy_farm_management_system.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SettingsController {

    @FXML
    private TableColumn<?, ?> actions_col;

    @FXML
    private TableColumn<?, ?> added_date_col;

    @FXML
    private TableColumn<?, ?> id_col;

    @FXML
    private Button openAddNewEmployeeBtn;

    @FXML
    private Button openAddNewEmployeeBtn1;

    @FXML
    private ImageView refresh_table_table;

    @FXML
    private TableColumn<?, ?> role_name_col;

    @FXML
    private TextField search_stock_input;

    @FXML
    private TableView<?> stock_table;

    @FXML
    void openAddRole(MouseEvent event) {

    }

    @FXML
    void openNewRole(MouseEvent event) {

    }

    @FXML
    void refreshList(MouseEvent event) {

    }

    @FXML
    void refreshTable(MouseEvent event) {

    }
}
