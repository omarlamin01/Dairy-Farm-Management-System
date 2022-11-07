package com.dfms.dairy_farm_management_system.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.openNewWindow;

public class MilkCollectionController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private ComboBox<?> combo;

    @FXML
    private Button openAddNewMilkCollectionBtn;

    @FXML
    void openAddNewMilkCollection(MouseEvent event) throws IOException {
        openNewWindow("Add Milk Collection", "add_new_milk_collection");

    }

}
