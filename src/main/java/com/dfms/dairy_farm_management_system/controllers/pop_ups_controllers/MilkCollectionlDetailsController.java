package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Animal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class MilkCollectionlDetailsController implements Initializable {

    @FXML
    private Label collectiondate;

    @FXML
    private Label cow_id;

    @FXML
    private Label header;

    @FXML
    private Label id;

    @FXML
    private Label period;

    @FXML
    private Label quantity;

    public static String id_animal;
    public static Animal animal = new Animal();
    Connection con = DBConfig.getConnection();
    PreparedStatement st = null;
    ResultSet rs = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void fetchMilkCollection(int id, String cow, String periode, Float quantitymilk, Date milkcollection) {
        //        animal = getAnimal(AnimalDetailsController.id_animal);
        header.setText("Here's all the information about MilkCollection Num: " + id);
        cow_id.setText(cow + "");
        period.setText(periode + "");
        quantity.setText(quantitymilk + "");
        collectiondate.setText(milkcollection + " ");
    }
}
