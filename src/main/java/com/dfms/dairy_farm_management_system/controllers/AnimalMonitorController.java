package com.dfms.dairy_farm_management_system.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.*;
import static com.dfms.dairy_farm_management_system.helpers.Helper.openNewWindow;

public class AnimalMonitorController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void oppenAddHealthStatus(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Health status", "add_new_health_status");
    }
}
