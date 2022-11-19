package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.AnimalSaleDetailsController;
import com.dfms.dairy_farm_management_system.models.AnimalSale;
import com.dfms.dairy_farm_management_system.models.MilkSale;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class SalesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo.setItems(list);
        liveSearch(search_input, AnimalSalesTable);
        try {
            afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            afficheer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private TableView<MilkSale> MilkSaleTable;

    @FXML
    private TableColumn<MilkSale, String> action_c;



    @FXML
    private TableColumn<MilkSale, String> client_c;


    @FXML
    private ComboBox<?> combo1;

    @FXML
    private TableColumn<MilkSale, LocalDate> date_c;



    @FXML
    private TableColumn<MilkSale, Float> price_c;



    @FXML
    private TableColumn<MilkSale, Float> quantity_c;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_inpu;


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
    private TableColumn<AnimalSale, LocalDate> operationdate_col;

    @FXML
    private TableColumn<AnimalSale, Float> price_col;



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
            animalSale.setOperationDate(rs.getDate("sale_date").toLocalDate());


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
        operationdate_col.setCellValueFactory(new PropertyValueFactory<AnimalSale, LocalDate>("operationDate"));


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

                            AnimalSale mc = AnimalSalesTable.getSelectionModel().getSelectedItem();
                            if (mc.delete()) {

                                displayAlert("success", "Animal Sale deleted successfully", Alert.AlertType.INFORMATION);
                                try {
                                    refreshTableAnimalSales();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    throw new RuntimeException(e);
                                }
                            } else {
                                displayAlert("Error", "Error while deleting!!!", Alert.AlertType.ERROR);
                            }


                            //displayAlert("Success", "Milk Collection deleted successfully", Alert.AlertType.INFORMATION);

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
                            AnimalSale animalSale = AnimalSalesTable.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/animal_sale_details.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                               AnimalSaleDetailsController controller = fxmlLoader.getController();
                                controller.fetchAnimalSale( animalSale.getId(),animalSale.getId_animal(), animalSale.getPrice(), animalSale.getId_client(), animalSale.getOperationDate());
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
                            stage.show();
                        });
                    }
                }


            };
            return cell;
        };

        action_col.setCellFactory(cellFoctory);
        AnimalSalesTable.setItems(list);

    }
    public void liveSearch(TextField search_input, TableView table) {
        search_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                try {
                    refreshTableAnimalSales();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                ObservableList<AnimalSale> filteredList = FXCollections.observableArrayList();
                ObservableList<AnimalSale> animalSale = null;
                try {
                    animalSale = getAnimalSale();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                for (AnimalSale Animal: animalSale) {
                    if (Animal.getId_client().toLowerCase().contains(newValue.toLowerCase()) || Animal.getId_animal().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(Animal);
                    }
                }
                AnimalSalesTable.setItems(filteredList);
            }
        });
    }
    public ObservableList<MilkSale> getMilkSale() throws SQLException, ClassNotFoundException {
        ObservableList<MilkSale> list = FXCollections.observableArrayList();

        String select_query = "SELECT ms.id, price ,quantity,c.name,ms.sale_date from  milk_sale ms ,client c where   ms.client_id= c.id ";

        st = DBConfig.getConnection().prepareStatement(select_query);
        rs = st.executeQuery();
        while (rs.next()) {
            MilkSale milkSale = new MilkSale();
            milkSale.setId(rs.getInt("id"));

            milkSale.setPrice(rs.getFloat("price"));
            milkSale.setQuantity(rs.getFloat("quantity"));
            milkSale.setId_client(rs.getString("name"));
            milkSale.setOperationDate(rs.getDate("sale_date").toLocalDate());


            list.add(milkSale);
        }
        return list;
    }
    private void afficheer() throws SQLException, ClassNotFoundException {
        ObservableList<MilkSale> list = getMilkSale();
        quantity_c.setCellValueFactory(new PropertyValueFactory<MilkSale, Float>("quantity"));
        price_c.setCellValueFactory(new PropertyValueFactory<MilkSale, Float>("price"));
        client_c.setCellValueFactory(new PropertyValueFactory<MilkSale, String>("id_client"));
        date_c.setCellValueFactory(new PropertyValueFactory<MilkSale, LocalDate>("operationDate"));


        Callback<TableColumn<MilkSale, String>, TableCell<MilkSale, String>> cellFoctory = (TableColumn<MilkSale, String> param) -> {
            // make cell containing buttons
            final TableCell<MilkSale, String> cell = new TableCell<MilkSale, String>() {

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

//                            AnimalSale mc = AnimalSalesTable.getSelectionModel().getSelectedItem();
//                            if (mc.delete()) {
//
//                                displayAlert("success", "Animal Sale deleted successfully", Alert.AlertType.INFORMATION);
//                                try {
//                                    refreshTableAnimalSales();
//                                } catch (SQLException e) {
//                                    e.printStackTrace();
//                                    throw new RuntimeException(e);
//                                }
//                            } else {
//                                displayAlert("Error", "Error while deleting!!!", Alert.AlertType.ERROR);
//                            }


                            //displayAlert("Success", "Milk Collection deleted successfully", Alert.AlertType.INFORMATION);

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
//                            AnimalSale animalSale = AnimalSalesTable.getSelectionModel().getSelectedItem();
//                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/animal_sale_details.fxml"));
//                            Scene scene = null;
//                            try {
//                                scene = new Scene(fxmlLoader.load());
//                                AnimalSaleDetailsController controller = fxmlLoader.getController();
//                                controller.fetchAnimalSale( animalSale.getId(),animalSale.getId_animal(), animalSale.getPrice(), animalSale.getId_client(), animalSale.getOperationDate());
//                            } catch (IOException e) {
//                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
//                                e.printStackTrace();
//                            }
//                            Stage stage = new Stage();
//                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
//                            stage.setTitle("Animal Sale Details");
//                            stage.setResizable(false);
//                            stage.setScene(scene);
//                            centerScreen(stage);
//                            stage.show();
                        });
                    }
                }


            };
            return cell;
        };

        action_c.setCellFactory(cellFoctory);
        MilkSaleTable.setItems(list);

    }

}





