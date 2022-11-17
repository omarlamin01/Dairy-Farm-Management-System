package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Animal;
import com.dfms.dairy_farm_management_system.models.Employee;
import com.dfms.dairy_farm_management_system.models.MilkCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;
import static com.dfms.dairy_farm_management_system.helpers.Helper.openNewWindow;

public class MilkCollectionController implements Initializable {
MilkCollection mc;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");
        combo.setItems(list);
        try {
            afficher();
            liveSearch(search_input,MilkCollectionTable);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private ComboBox<String> combo;
    @FXML
    private TableView<MilkCollection> MilkCollectionTable;

    @FXML
    private TableColumn<MilkCollection, String> actions_col;
    @FXML
    private TableColumn<MilkCollection, Date> date_col;

    @FXML
    private TableColumn<MilkCollection, String> id_col;

    @FXML
    private TableColumn<MilkCollection, Float> milk_col;

    @FXML
    private Button openAddNewMilkCollectionBtn;

    @FXML
    private TableColumn<MilkCollection, String> period_col;

    @FXML
    private Button search_button;

    @FXML
    private TextField search_input;
    PreparedStatement st = null;
    ResultSet rs = null;
    ObservableList<MilkCollection> list = FXCollections.observableArrayList();
    public ObservableList<MilkCollection> getMilkCollection() throws SQLException, ClassNotFoundException {
        ObservableList<MilkCollection> list = FXCollections.observableArrayList();

        String select_query = "SELECT mc.cow_id, quantity ,period,mc.created_at from  milk_collection mc ,animal a where mc.cow_id= a.id and a.type='cow' ";

        st = DBConfig.getConnection().prepareStatement(select_query);
        rs = st.executeQuery();
        while (rs.next()) {
            MilkCollection milkCollection = new MilkCollection();
            milkCollection.setCow_id(rs.getString("cow_id"));
            milkCollection.setQuantity(rs.getFloat("quantity"));
            milkCollection.setPeriod(rs.getString("period"));
            milkCollection.setCollection_date(rs.getDate("created_at"));


            list.add(milkCollection);
        }
        return list;
    }
    public void refreshTableMilkCollection() throws SQLException {

        MilkCollectionTable.getItems().clear();
        try {
            afficher();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void afficher() throws SQLException, ClassNotFoundException {
        ObservableList<MilkCollection> list = getMilkCollection();
        id_col.setCellValueFactory(new PropertyValueFactory<MilkCollection, String>("cow_id"));
        milk_col.setCellValueFactory(new PropertyValueFactory<MilkCollection, Float>("quantity"));
        period_col.setCellValueFactory(new PropertyValueFactory<MilkCollection, String>("period"));
        date_col.setCellValueFactory(new PropertyValueFactory<MilkCollection, Date>("collection_date"));


        Callback<TableColumn<MilkCollection, String>, TableCell<MilkCollection, String>> cellFoctory = (TableColumn<MilkCollection, String> param) -> {
            // make cell containing buttons
            final TableCell<MilkCollection, String> cell = new TableCell<MilkCollection, String>() {

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
                           MilkCollection mc = MilkCollectionTable.getSelectionModel().getSelectedItem();
                            String query = "DELETE FROM milk_collection WHERE id = "+mc.getId()+"";
                            Connection connection = DBConfig.getConnection();
                            try {
                                st = connection.prepareStatement(query);
                                st.execute();
                                refreshTableMilkCollection();

                                //displayAlert("Success", "Milk Collection deleted successfully", Alert.AlertType.INFORMATION);
                            } catch (SQLException e) {
                               // displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                throw new RuntimeException(e);
                            }
                        });

                        }}





            };
            return cell;
        };

        actions_col.setCellFactory(cellFoctory);
        MilkCollectionTable.setItems(list);

    }




        public void liveSearch(TextField search_input, TableView table) {
        search_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                try {
                    refreshTableMilkCollection();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                ObservableList<MilkCollection> filteredList = FXCollections.observableArrayList();
                ObservableList<MilkCollection> milkCollections = null;
                try {
                    milkCollections = getMilkCollection();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                for (MilkCollection milkCollection : milkCollections) {
                    if (milkCollection.getPeriod().toLowerCase().contains(newValue.toLowerCase()) || milkCollection.getCow_id().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(milkCollection );
                    }
                }
                MilkCollectionTable.setItems(filteredList);
            }
        });
    }


    @FXML
    void openAddNewMilkCollection(MouseEvent event) throws IOException {
        openNewWindow("Add Milk Collection", "add_new_milk_collection");}
    private Connection con = DBConfig.getConnection();
    private Statement stt;
    private void deleteMilkCollection(int id) {
        String query = "DELETE FROM milk_collection WHERE id = "+id;
        try {

            stt = con.createStatement();
            stt.executeUpdate(query);

            displayAlert("Success", "Milk Collection deleted successfully", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    }