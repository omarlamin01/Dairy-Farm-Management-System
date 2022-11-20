package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Animal;
import com.dfms.dairy_farm_management_system.models.Race;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;

public class NewAnimalController implements Initializable {

    @FXML
    private ComboBox<String> typeCombo;
    @FXML
    private ComboBox<String> raceCombo;
    @FXML
    private DatePicker birthDate;
    @FXML
    private DatePicker purchaseDate;
    @FXML
    private ComboBox<String> routineCombo;
    @FXML
    private Button btn_add_animal;
    private Boolean update;

    PreparedStatement st = null;
    ResultSet rs = null;

    String animal_ID;
    String query = null;
    private Connection con = DBConfig.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setRaceComboItems();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            setRoutineComboItems();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.setTypeComboItems();
    }

    public void setTypeComboItems() {
        this.typeCombo.setItems(FXCollections.observableArrayList("Cow", "Bull", "Calf"));
    }

    public void setRaceComboItems() throws SQLException {
        //get races from db
        ObservableList<String> races = FXCollections.observableArrayList();

        String select_query = "SELECT name from race;";

        st = con.prepareStatement(select_query);
        rs = st.executeQuery();
        while (rs.next()) {

            races.add(rs.getString("name"));
        }

        raceCombo.setItems(races);
    }

    public void setRoutineComboItems() throws SQLException {
        //get races from db
        ObservableList<String> routines = FXCollections.observableArrayList();

        String select_query = "SELECT name from routine;";

        st = con.prepareStatement(select_query);
        rs = st.executeQuery();
        while (rs.next()) {

            routines.add(rs.getString("name"));
        }

        routineCombo.setItems(routines);
    }

    private void clair() {
        typeCombo.getSelectionModel().clearSelection();
        routineCombo.getSelectionModel().clearSelection();
        raceCombo.getSelectionModel().clearSelection();
        purchaseDate.setValue(null);
        birthDate.setValue(null);

        btn_add_animal.setDisable(false);

    }

    @FXML
    void addAnimal(MouseEvent event) {
        Random random = new Random();
        int rand = random.nextInt();
        animal_ID = "Cow-" + rand;

        try {
            st = con.prepareStatement(query);
            st.setString(1, animal_ID);
            st.setString(2, typeCombo.getSelectionModel().getSelectedItem());
            st.setDate(3, Date.valueOf(birthDate.getValue()));
            st.setDate(4, Date.valueOf(purchaseDate.getValue()));
            st.executeUpdate();
            clair();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    private void getQuery() {

        if (update == false) {
            query = "INSERT INTO animal (id,type,birth_date,purchase_date,routine_id,race_id) VALUES (?,?,?,?,(select id from routine where name ='" + routineCombo.getSelectionModel().getSelectedItem() + "'),(select id from race where name ='" + raceCombo.getSelectionModel().getSelectedItem() + "'))";

    }

    }


    void setUpdate(boolean b) {
        this.update = b;
    }

}
