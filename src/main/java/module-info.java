module com.dfms.dairy_farm_management_system {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.dfms.dairy_farm_management_system to javafx.fxml;
    exports com.dfms.dairy_farm_management_system;
    exports com.dfms.dairy_farm_management_system.controllers;
    opens com.dfms.dairy_farm_management_system.controllers to javafx.fxml;
}