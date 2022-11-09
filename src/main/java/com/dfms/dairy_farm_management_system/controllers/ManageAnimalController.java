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
    private TableColumn<Animal,Integer> colid;

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
    Connection con = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");
        combo.setItems(list);
        affiche();
    }



    public void openAddNewAnimal(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add New Animal", "add_new_animal");
    }

    public ObservableList<Animal> getAnimal() {
        ObservableList<Animal> list = FXCollections.observableArrayList();

        String select = "SELECT * from Animal";
        con = DBConfig.getConnection();
        try {

            while ( DBConfig.executeQuery(select).next()) {
                Animal animal = new Animal();
                animal.setId_animal( DBConfig.executeQuery(select).getInt("ID"));
                animal.setType( DBConfig.executeQuery(select).getString("Type"));
                animal.setBirth_date( DBConfig.executeQuery(select).getDate("Birth Date"));
                list.add(animal);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManageAnimalController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return list;

    }
    public void affiche() {
        ObservableList<Animal> list = getAnimal();
        colid.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("ID"));
        coltype.setCellValueFactory(new PropertyValueFactory<Animal, String>("Type"));
        colbirth.setCellValueFactory(new PropertyValueFactory<Animal, Date>("Birth Date"));

      animals.setItems(list);

    }

}
