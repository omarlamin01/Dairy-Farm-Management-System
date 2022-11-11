package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
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
    @FXML
    private TextField textField_search;


    Connection con = DBConfig.getConnection();

    PreparedStatement st = null;
    ResultSet rs = null;
    Animal animal ;
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
        goSearch();
    }



    public void openAddNewAnimal(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add New Animal", "add_new_animal");
    }
    ObservableList<Animal> listAnimal = FXCollections.observableArrayList();
    public ObservableList<Animal> getAnimal() throws SQLException, ClassNotFoundException {


        String select_query = "SELECT a.id,a.type,a.birth_date, r.name,ro.name from routine ro,race r, animal a where a.race_id = r.id and a.routine_id=ro.id ";

            st = con.prepareStatement(select_query);
            rs = st.executeQuery();
            while (rs.next()) {
                Animal animal = new Animal();
                animal.setId(rs.getString("id"));
                animal.setType(rs.getString("type"));
                animal.setBirth_date(rs.getDate("birth_date"));
                animal.setRace(rs.getString("r.name"));
                animal.setRoutine(rs.getString("ro.name"));

                listAnimal.add(animal);
            }
        return listAnimal;
    }
        public void refreshTableAnimal() throws SQLException {

           listAnimal.clear();
            String query_refresh = "SELECT a.id,a.type,a.birth_date, r.name,ro.name from routine ro,race r, animal a where a.race_id = r.id and a.routine_id=  ro.id ";
            st = con.prepareStatement(query_refresh);
            rs= st.executeQuery();

            while (rs.next()){
                listAnimal.add(new  Animal(
                        rs.getString("id"),
                       rs.getString("type"),
                       rs.getDate("birth_date"),
                       rs.getString("r.name"),
                     rs.getString("ro.name")));
              animals.setItems(listAnimal);


            }

      }
    public void  afficher() throws SQLException, ClassNotFoundException {
        ObservableList<Animal> list = getAnimal();
        colid.setCellValueFactory(new PropertyValueFactory<Animal, String>("id"));
        coltype.setCellValueFactory(new PropertyValueFactory<Animal, String>("type"));
        colbirth.setCellValueFactory(new PropertyValueFactory<Animal, Date>("birth_date"));
        colrace.setCellValueFactory(new PropertyValueFactory<Animal, String>("race"));
        colroutine.setCellValueFactory(new PropertyValueFactory<Animal, String>("routine"));

        Callback<TableColumn<Animal, String>, TableCell<Animal, String>> cellFoctory = (TableColumn<Animal, String> param) -> {
            // make cell containing buttons
            final TableCell<Animal, String> cell = new TableCell<Animal, String>() {

                Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button btnEdit = new Button();
                Image imgDelete = new Image(getClass().getResourceAsStream("/images/delete.png"));
                final Button btnDelete = new Button();
                Image imgViewDetail = new Image(getClass().getResourceAsStream("/images/eye.png"));
                final Button btnViewDetail = new Button();
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        btnViewDetail.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        ImageView iv1 = new ImageView();
                        iv1.setImage(imgViewDetail);
                        iv1.setPreserveRatio(true);
                        iv1.setSmooth(true);
                        iv1.setCache(true);
                        btnViewDetail.setGraphic(iv1);

                        setGraphic(btnViewDetail);
                        setText(null);


                        btnEdit.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        ImageView iv = new ImageView();
                        iv.setImage(imgEdit);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        btnEdit.setGraphic(iv);

                        setGraphic(btnEdit);
                        setText(null);

                        btnDelete.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        ImageView iv2 = new ImageView();

                        iv2.setImage(imgDelete);
                        iv2.setPreserveRatio(true);
                        iv2.setSmooth(true);
                        iv2.setCache(true);
                        btnDelete.setGraphic(iv2);


                        setGraphic(btnDelete);

                        setText(null);

                        HBox managebtn = new HBox(btnEdit, btnDelete,btnViewDetail);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(btnEdit, new Insets(1, 1, 0, 3));
                        HBox.setMargin(btnDelete, new Insets(1, 1, 0, 2));
                        HBox.setMargin(btnViewDetail, new Insets(1, 1, 0, 1));

                        setGraphic(managebtn);

                        setText(null);


                        btnDelete.setOnMouseClicked((MouseEvent event) -> {
                            animal = animals.getSelectionModel().getSelectedItem();
                            String delete_query = "DELETE FROM animal WHERE id='"+animal.getId()+"'";
                            Connection connection = DBConfig.getConnection();
                            try {
                                st = connection.prepareStatement(delete_query);
                                st.execute();
                                refreshTableAnimal();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }


                        });

                    }
                }





            };
                 return cell;
            };

      colactions.setCellFactory(cellFoctory);
      animals.setItems(list);

    }

    public void goSearch() {
      String  search_text = textField_search.getText();
        FilteredList<Animal> filteredData = new FilteredList<>(listAnimal, p -> true);
        textField_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(animal -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (animal.getType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (animal.getRace().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (animal.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        });
        SortedList<Animal> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(animals.comparatorProperty());
        animals.setItems(sortedData);

    }


}
