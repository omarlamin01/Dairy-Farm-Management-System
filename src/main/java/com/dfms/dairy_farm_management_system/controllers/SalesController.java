package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.MilkCollectionlDetailsController;
import com.dfms.dairy_farm_management_system.models.AnimalSale;
import com.dfms.dairy_farm_management_system.models.MilkCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class SalesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo.setItems(list);
        try {
            afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private ComboBox<String> combo;
    ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");


    @FXML
    private TableColumn<AnimalSale, String> animalis_col;

    @FXML
    private TableColumn<AnimalSale,String > client_col;


    @FXML
    private TableColumn<AnimalSale, String> action_col;
    @FXML
    private TableView<AnimalSale> AnimalSalesTable;

    @FXML
    private TableColumn<AnimalSale, Date> operationdate_col;

    @FXML
    private TableColumn<AnimalSale, Float> price_col;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_input;
    PreparedStatement st = null;
    ResultSet rs = null;
    @FXML
    public void openAddNewAnimalSale(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add New Sale", "add_new_cow_sale");
    }

    @FXML
    public void openAddNewMilkSale(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add New Sale", "add_new_milk_sale");
    }

    public ObservableList<AnimalSale> getAnimalSale() throws SQLException, ClassNotFoundException {
        ObservableList<AnimalSale> list = FXCollections.observableArrayList();

        String select_query = "SELECT ass.id, ass.animal_id, price ,c.name,ass.sale_date from  animal_sale ass ,animal a,client c where ass.animal_id= a.id and ass.client_id= c.id ";

        st = DBConfig.getConnection().prepareStatement(select_query);
        rs = st.executeQuery();
        while (rs.next()) {
            AnimalSale animalSale = new AnimalSale();
            animalSale.setId(rs.getInt("id"));
            animalSale.setId_animal(rs.getString("animal_id"));
            animalSale.setPrice(rs.getFloat("price"));
            animalSale.setId_client(rs.getString("name"));
            animalSale.setOperationDate(rs.getDate("sale_date"));


            list.add(animalSale);
        }
        return list;
    }
    public void refreshTableAnimalSales() throws SQLException {

        AnimalSalesTable.getItems().clear();
        try {
            afficher();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void afficher() throws SQLException, ClassNotFoundException {
        ObservableList<AnimalSale> list = getAnimalSale();
        animalis_col.setCellValueFactory(new PropertyValueFactory<AnimalSale, String>("id_animal"));
        price_col.setCellValueFactory(new PropertyValueFactory<AnimalSale, Float>("price"));
        client_col.setCellValueFactory(new PropertyValueFactory<AnimalSale, String>("id_client"));
        operationdate_col.setCellValueFactory(new PropertyValueFactory<AnimalSale, Date>("operationDate"));


        Callback<TableColumn<AnimalSale, String>, TableCell<AnimalSale, String>> cellFoctory = (TableColumn<AnimalSale, String> param) -> {
            // make cell containing buttons
            final TableCell<AnimalSale, String> cell = new TableCell<AnimalSale, String>() {

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

                        HBox managebtn = new HBox(btnEdit, btnDelete, btnViewDetail);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(btnEdit, new Insets(1, 1, 0, 3));
                        HBox.setMargin(btnDelete, new Insets(1, 1, 0, 2));
                        HBox.setMargin(btnViewDetail, new Insets(1, 1, 0, 1));

                        setGraphic(managebtn);

                        setText(null);


                        btnDelete.setOnMouseClicked((MouseEvent event) -> {

                          /*  MilkCollection mc = MilkCollectionTable.getSelectionModel().getSelectedItem();
                            if (mc.delete()) {

                                displayAlert("success", "Milk Collection deleted successfully", Alert.AlertType.INFORMATION);
                                try {
                                    refreshTableMilkCollection();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    throw new RuntimeException(e);
                                }
                            } else {
                                displayAlert("Error", "Error while deleting!!!", Alert.AlertType.ERROR);
                            }


                            //displayAlert("Success", "Milk Collection deleted successfully", Alert.AlertType.INFORMATION);
*/
                        });
                        btnEdit.setOnMouseClicked((MouseEvent event) -> {

                          /*  MilkCollection mc = MilkCollectionTable.getSelectionModel().getSelectedItem();
                            String path = "/com/dfms/dairy_farm_management_system/popups/update_employee.fxml";
                            FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                displayAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
                                ex.printStackTrace();
                            }
                            if (mc.update()) {

                                displayAlert("success", "Milk Collection Updated successfully", Alert.AlertType.INFORMATION);
                                try {
                                    refreshTableMilkCollection();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    throw new RuntimeException(e);
                                }
                            } else {
                                displayAlert("Error", "Error while deleting!!!", Alert.AlertType.ERROR);
                            }
*/

                            //displayAlert("Success", "Milk Collection deleted successfully", Alert.AlertType.INFORMATION);

                        });
                        btnViewDetail.setOnMouseClicked((MouseEvent event) -> {
                           /* MilkCollection mc = MilkCollectionTable.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/milkcollection_details.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                MilkCollectionlDetailsController controller = fxmlLoader.getController();
                                controller.fetchMilkCollection( mc.getId(),mc.getCow_id(), mc.getPeriod(), mc.getQuantity(), mc.getCollection_date());
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
                            stage.show();*/
                        });
                    }
                }


            };
            return cell;
        };

        action_col.setCellFactory(cellFoctory);
        AnimalSalesTable.setItems(list);

    }

}





