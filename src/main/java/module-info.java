module com.dfms.dairy_farm_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itextpdf;
    requires org.apache.poi.ooxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml.schemas;
    requires log4j.to.slf4j;
    requires log4j;
    requires slf4j.log4j12;
    requires org.apache.logging.log4j;
    requires java.desktop;
    requires layout;

    opens com.dfms.dairy_farm_management_system to javafx.fxml;
    exports com.dfms.dairy_farm_management_system;

    opens com.dfms.dairy_farm_management_system.controllers to javafx.fxml;
    exports com.dfms.dairy_farm_management_system.controllers;
    opens com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers to javafx.fxml;
    exports com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

    opens com.dfms.dairy_farm_management_system.models to javafx.base;
    exports com.dfms.dairy_farm_management_system.models;

}