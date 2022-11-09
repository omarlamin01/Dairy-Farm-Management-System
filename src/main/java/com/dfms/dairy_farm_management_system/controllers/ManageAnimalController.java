package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

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
    private TableColumn<Animal,String> colactions;
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

        Callback<TableColumn<Animal, String>, TableCell<Animal, String>> cellFoctory = (TableColumn<Animal, String> param) -> {
            // make cell containing buttons
            final TableCell<Animal, String> cell = new TableCell<Animal, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                        final Button btnEdit = new Button();
                        btnEdit.setStyle("-fx-background-color: transparent;");
                        ImageView iv = new ImageView();
                        iv.setImage(imgEdit);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        btnEdit.setGraphic(iv);

                        setGraphic(btnEdit);
                        setAlignment(Pos.CENTER);
                        setText(null);
                    }
                }
                public void deleteItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Image imgDelete = new Image(getClass().getResourceAsStream("/images/delete.png"));
                        final Button btnDelete = new Button();
                        btnDelete.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:28px;");
                        ImageView iv = new ImageView();
                        iv.setImage(imgDelete);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        btnDelete.setGraphic(iv);

                        setGraphic(btnDelete);
                        setAlignment(Pos.CENTER);
                        setText(null);
                    }
                }
            };
                 return cell;
            };

      colactions.setCellFactory(cellFoctory);
      animals.setItems(list);

    }

}
