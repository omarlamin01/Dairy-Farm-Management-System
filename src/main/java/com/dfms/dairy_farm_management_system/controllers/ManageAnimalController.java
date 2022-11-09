package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.dfms.dairy_farm_management_system.helpers.Helper.openNewWindow;





public class ManageAnimalController implements Initializable {
    @FXML
    private TableView<Animal> animals;

    @FXML
    private TableColumn<Animal,String> colid;

    @FXML
    private TableColumn<Animal,String> coltype;

    @FXML
    private TableColumn<Animal,String> colrace;

    @FXML
    private TableColumn<Animal, Date> colbirth;

    @FXML
    private TableColumn<Animal,String> colroutine;
    @FXML
    private ComboBox<String> combo;

    PreparedStatement st = null;
    ResultSet rs = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");
        combo.setItems(list);
        try {
            afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public void openAddNewAnimal(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add New Animal", "add_new_animal");
    }

    public ObservableList<Animal> getAnimal() throws SQLException, ClassNotFoundException {
        ObservableList<Animal> list = FXCollections.observableArrayList();

        String select_query = "SELECT a.id,a.type,a.birth_date, r.name,ro.name from routine ro,race r, animal a where a.race_id = r.id and a.routine_id=  ro.id ";

            st = DBConfig.getConnection().prepareStatement(select_query);
            rs = st.executeQuery();
            while (rs.next()) {
                Animal animal = new Animal();
                animal.setId_animal(rs.getInt("id"));
                animal.setType(rs.getString("type"));
                animal.setBirth_date(rs.getDate("birth_date"));
                animal.setRace(rs.getString("name"));
                animal.setRoutine(rs.getString("name"));

                list.add(animal);
            }
        return list;
    }

    public void afficher() throws SQLException, ClassNotFoundException {
        ObservableList<Animal> list = getAnimal();
        colid.setCellValueFactory(new PropertyValueFactory<Animal, String>("id_animal"));
        coltype.setCellValueFactory(new PropertyValueFactory<Animal, String>("type"));
        colbirth.setCellValueFactory(new PropertyValueFactory<Animal, Date>("birth_date"));
        colrace.setCellValueFactory(new PropertyValueFactory<Animal, String>("race"));
        colroutine.setCellValueFactory(new PropertyValueFactory<Animal, String>("routine"));

      animals.setItems(list);

    }

}
