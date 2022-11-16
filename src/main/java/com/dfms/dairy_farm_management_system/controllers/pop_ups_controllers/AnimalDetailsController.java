package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Animal;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class AnimalDetailsController implements Initializable {
    @FXML
    private Label header;

    @FXML
    private Label id;

    @FXML
    private Label birth_date;

    @FXML
    private Label race;

    @FXML
    private Label type;

    @FXML
    private Label routine;

    @FXML
    private Label purchase_date;
    public static String id_animal;
    public static Animal animal;

    Connection con = DBConfig.getConnection();
    PreparedStatement st = null;
    ResultSet rs = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fetchAnimal();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Animal getAnimal(String id) throws SQLException {
         animal =new Animal();

        String select_query = "SELECT a.id,a.type,a.birth_date, r.name,ro.name,a.purchase_date from routine ro,race r, animal a where a.race_id = r.id and a.routine_id=ro.id  where id='"+id+"'";

        st = con.prepareStatement(select_query);
        rs = st.executeQuery();
        while (rs.next()) {
            animal.setId(rs.getString("id"));
            animal.setType(rs.getString("type"));
            animal.setBirth_date(rs.getDate("birth_date"));
            animal.setRace(rs.getString("r.name"));
            animal.setRoutine(rs.getString("ro.name"));
            animal.setPurchase_date(rs.getDate("purchase_date"));
        }
        return animal;
    }

    public void fetchAnimal() throws SQLException {
        animal = getAnimal(AnimalDetailsController.id_animal);
        header.setText("Here's all the information about Cow Num: " + animal.getId());
        id.setText(animal.getId());
        birth_date.setText(animal.getBirth_date()+"");
        race.setText(animal.getRace());
        routine.setText(animal.getRace());
        type.setText(animal.getType());
        purchase_date.setText(animal.getPurchase_date()+" ");

    }
    public void setAnimalId(String id) {
        AnimalDetailsController.id_animal = id;
    }
}
