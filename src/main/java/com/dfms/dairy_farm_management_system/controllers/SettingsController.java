package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.models.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import static com.dfms.dairy_farm_management_system.helpers.Helper.openNewWindow;

public class SettingsController {

    @FXML
    private TableColumn<Role, String> actions_col;

    @FXML
    private TableColumn<Role, String> added_date_col;

    @FXML
    private TableColumn<Role, String> id_col;

    @FXML
    private TableColumn<Role, String> role_name_col;

    @FXML
    private TextField search_stock_input;

    @FXML
    private TableView<Role> stock_table;

    @FXML
    void openAddRole(MouseEvent event) throws IOException {
        openNewWindow("Add new role", "add_new_role");
    }

    @FXML
    void refreshList(MouseEvent event) {

    }

    @FXML
    void refreshTable(MouseEvent event) {

    }
}
