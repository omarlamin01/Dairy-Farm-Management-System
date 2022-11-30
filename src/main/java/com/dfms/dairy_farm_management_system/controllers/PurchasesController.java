package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.AnimalSaleDetailsController;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.CowSalesController;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.NewPurchaseController;
import com.dfms.dairy_farm_management_system.models.AnimalSale;
import com.dfms.dairy_farm_management_system.models.Purchase;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class PurchasesController  implements Initializable {
    @FXML
    private TableView<Purchase> PurchaseTable;

    @FXML
    private TableColumn<Purchase, String> actions_c;
    @FXML
    private TableColumn<Purchase, Date> date_c;

    @FXML
    private ComboBox<String> export_combo;

    @FXML
    private Button openAddNewEmployeeBtn;

    @FXML
    private Button openAddNewPurchase;

    @FXML
    private TableColumn<Purchase,Float> price_c;

    @FXML
    private TableColumn<Purchase,Float> quantity_c;

    @FXML
    private ImageView refresh_table_table;



    @FXML
    private TableColumn<Purchase, String> supplier_c;


    @FXML
    private TableColumn<Purchase, String> product_c;



    @FXML
    private TextField search_input;


    PreparedStatement statement = null;

    ResultSet resultSet = null;

    @FXML
    void openAddPurchase(MouseEvent event) throws IOException {
        openNewWindow("Add New Purchase", "add_new_purchase");
    }

    @FXML
    void refreshTable(MouseEvent event) {
        PurchaseTable.getItems().clear();
       try {
           afficher();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public ObservableList<Purchase> getPurchase() throws SQLException, ClassNotFoundException {
        ObservableList<Purchase> list = FXCollections.observableArrayList();

        String query = "SELECT * FROM `purchases`";

        statement = DBConfig.getConnection().prepareStatement(query);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Purchase purchase = new Purchase();

            purchase.setId(resultSet.getInt("id"));
            purchase.setSupplier_id(resultSet.getInt("supplier_id"));
            purchase.setStock_id(resultSet.getInt("stock_id"));
            purchase.setQuantity(resultSet.getFloat("quantity"));
            purchase.setPrice(resultSet.getFloat("price"));
            purchase.setPurchase_date(resultSet.getDate("purchase_date"));
            purchase.setCreated_at(resultSet.getTimestamp("created_at"));
            purchase.setUpdated_at(resultSet.getTimestamp("updated_at"));

            list.add(purchase);
        }
        return list;
    }

    public void refreshTablePurchase() throws SQLException {
        PurchaseTable.getItems().clear();
        try {
            afficher();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void afficher() throws SQLException, ClassNotFoundException {
        ObservableList<Purchase> list = getPurchase();
        product_c.setCellValueFactory(new PropertyValueFactory<Purchase, String>("product_name"));
        price_c.setCellValueFactory(new PropertyValueFactory<Purchase, Float>("price"));
        quantity_c.setCellValueFactory(new PropertyValueFactory<Purchase, Float>("quantity"));
        supplier_c.setCellValueFactory(new PropertyValueFactory<Purchase, String>("supplier_name"));
        date_c.setCellValueFactory(new PropertyValueFactory<Purchase, java.sql.Date>("purchase_date"));


        Callback<TableColumn<Purchase, String>, TableCell<Purchase, String>> cellFoctory = (TableColumn<Purchase, String> param) -> {
            // make cell containing buttons
            final TableCell<Purchase, String> cell = new TableCell<Purchase, String>() {

                Image edit_img = new Image(getClass().getResourceAsStream("/images/edit.png"));
                Image delete_img = new Image(getClass().getResourceAsStream("/images/delete.png"));
                Image view_details_img = new Image(getClass().getResourceAsStream("/images/eye.png"));

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        javafx.scene.image.ImageView iv_view_details = new javafx.scene.image.ImageView();
                        iv_view_details.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        iv_view_details.setImage(view_details_img);
                        iv_view_details.setPreserveRatio(true);
                        iv_view_details.setSmooth(true);
                        iv_view_details.setCache(true);


                        javafx.scene.image.ImageView iv_edit = new javafx.scene.image.ImageView();
                        iv_edit.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        iv_edit.setImage(edit_img);
                        iv_edit.setPreserveRatio(true);
                        iv_edit.setSmooth(true);
                        iv_edit.setCache(true);

                        javafx.scene.image.ImageView iv_delete = new javafx.scene.image.ImageView();
                        iv_delete.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");

                        iv_delete.setImage(delete_img);
                        iv_delete.setPreserveRatio(true);
                        iv_delete.setSmooth(true);
                        iv_delete.setCache(true);

                        HBox managebtn = new HBox(iv_view_details, iv_edit, iv_delete);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(iv_view_details, new Insets(1, 1, 0, 3));
                        HBox.setMargin(iv_delete, new Insets(1, 1, 0, 3));
                        HBox.setMargin(iv_edit, new Insets(1, 1, 0, 3));

                        setGraphic(managebtn);

                        iv_delete.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Confirmation");
                            alert.setHeaderText("Are you sure you want to delete this purchase sale?");
                            Purchase mc = PurchaseTable.getSelectionModel().getSelectedItem();
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                try {
                                    if (mc.delete()) {

                                        displayAlert("success", "Purchase deleted successfully", Alert.AlertType.INFORMATION);
                                        refreshTablePurchase();
                                    } else {
                                        displayAlert("Error", "Error while deleting!!!", Alert.AlertType.ERROR);

                                    }
                                } catch (Exception e) {
                                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                }
                            }

                            //displayAlert("Success", "Milk Collection deleted successfully", Alert.AlertType.INFORMATION);

                        });
                        iv_edit.setOnMouseClicked((MouseEvent event) -> {

                            Purchase purchase = PurchaseTable.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/add_new_purchase.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            NewPurchaseController newPurchaseController = fxmlLoader.getController();
                            newPurchaseController.setUpdate(true);
                            newPurchaseController.fetchPurchase(purchase.getId(), purchase.getProduct_name(),purchase.getQuantity(), purchase.getPrice(), purchase.getSupplier_name(), (Date) purchase.getPurchase_date());
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Update Purchase");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });
                        iv_view_details.setOnMouseClicked((MouseEvent event) -> {
                         /* Purchase purchase = PurchaseTable.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/purchase_details.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                AnimalSaleDetailsController controller = fxmlLoader.getController();
                                controller.fetchAnimalSale(animalSale.getId(), animalSale.getAnimalId(), animalSale.getPrice(), animalSale.getClientName(), animalSale.getSale_date());
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Animal Sale Details");
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

        actions_c.setCellFactory(cellFoctory);
        PurchaseTable.setItems(list);

    }

    public void liveSearch(TextField search_input, TableView table) {
       search_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                try {
                    refreshTablePurchase();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                ObservableList<Purchase> filteredList = FXCollections.observableArrayList();
                ObservableList<Purchase> purchase = null;
                try {
                    purchase = getPurchase();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                for (Purchase Purchase : purchase) {
                    if (Purchase.getSupplier_name().toLowerCase().contains(newValue.toLowerCase()) || Purchase.getProduct_name().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(Purchase);
                    }
                }
                PurchaseTable.setItems(filteredList);
            }
        });
    }

}
