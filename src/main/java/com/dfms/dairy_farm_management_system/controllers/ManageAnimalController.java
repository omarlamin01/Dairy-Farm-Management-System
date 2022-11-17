package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.AnimalDetailsController;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.EmployeeDetailsController;
import com.dfms.dairy_farm_management_system.models.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;


public class ManageAnimalController implements Initializable {
    @FXML
    private TableView<Animal> animals;

    @FXML
    private TableColumn<Animal, String> colid;

    @FXML
    private TableColumn<Animal, String> coltype;

    @FXML
    private TableColumn<Animal, String> colrace;

    @FXML
    private TableColumn<Animal, Date> colbirth;

    @FXML
    private TableColumn<Animal, String> colroutine;
    @FXML
    private TableColumn<Animal, String> colactions;
    @FXML
    private ComboBox<String> combo;
    @FXML
    private TextField textField_search;


    Connection con = DBConfig.getConnection();

    PreparedStatement st = null;
    ResultSet rs = null;
    Animal animal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");
        combo.setItems(list);
        try {
            dispalyAnimals();
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
        rs = st.executeQuery();

        while (rs.next()) {
            listAnimal.add(new Animal(rs.getString("id"), rs.getString("type"), rs.getDate("birth_date"), rs.getString("r.name"), rs.getString("ro.name")));
            animals.setItems(listAnimal);


        }

    }

    public void dispalyAnimals() throws SQLException, ClassNotFoundException {
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
                        ImageView iv_viewDetail = new ImageView();
                        iv_viewDetail.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        iv_viewDetail.setImage(imgViewDetail);
                        iv_viewDetail.setPreserveRatio(true);
                        iv_viewDetail.setSmooth(true);
                        iv_viewDetail.setCache(true);
                        btnViewDetail.setGraphic(iv_viewDetail);

                        setGraphic(btnViewDetail);
                        setText(null);


                        ImageView iv_edit = new ImageView();
                        iv_edit.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        iv_edit.setImage(imgEdit);
                        iv_edit.setPreserveRatio(true);
                        iv_edit.setSmooth(true);
                        iv_edit.setCache(true);
                        btnEdit.setGraphic(iv_edit);

                        setGraphic(btnEdit);
                        setText(null);

                        ImageView iv_delete = new ImageView();
                        iv_delete.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");

                        iv_delete.setImage(imgDelete);
                        iv_delete.setPreserveRatio(true);
                        iv_delete.setSmooth(true);
                        iv_delete.setCache(true);
                        btnDelete.setGraphic(iv_delete);


                        setGraphic(btnDelete);

                        setText(null);

                        HBox managebtn = new HBox(iv_edit, iv_delete, iv_viewDetail);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(iv_edit, new Insets(1, 1, 0, 3));
                        HBox.setMargin(iv_delete, new Insets(1, 1, 0, 3));
                        HBox.setMargin(iv_viewDetail, new Insets(1, 1, 0, 3));

                        setGraphic(managebtn);

                        setText(null);


                        iv_delete.setOnMouseClicked((MouseEvent event) -> {

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Confirmation");
                            alert.setHeaderText("Are you sure you want to delete this Cow?");
                            animal = animals.getSelectionModel().getSelectedItem();
                            String delete_query = "DELETE FROM animal WHERE id='" + animal.getId() + "'";
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                try {
                                    st = con.prepareStatement(delete_query);
                                    st.execute();
                                    refreshTableAnimal();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                                alertInfo.setTitle("Delete Cow");
                                alertInfo.setHeaderText("Cow deleted successfully");
                                alertInfo.showAndWait();
                            }

                        });
                        iv_viewDetail.setOnMouseClicked((MouseEvent event) -> {
                            Animal animal = animals.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/animal_details.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                AnimalDetailsController controller = fxmlLoader.getController();
                                controller.fetchAnimal(animal.getId(), animal.getRace(), animal.getBirth_date(), animal.getRoutine(), animal.getPurchase_date(), animal.getType());
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Animal Details");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });

                        iv_edit.setOnMouseClicked((MouseEvent event) -> {
                            Animal animal = animals.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/animal_details.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                AnimalDetailsController controller = fxmlLoader.getController();
                                controller.fetchAnimal(animal.getId(), animal.getRace(), animal.getBirth_date(), animal.getRoutine(), animal.getPurchase_date(), animal.getType());
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Animal Details");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();


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
        String search_text = textField_search.getText();
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
