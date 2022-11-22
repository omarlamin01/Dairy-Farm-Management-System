package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.models.Employee;
import com.dfms.dairy_farm_management_system.models.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class StockController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection = getConnection();
    @FXML
    private TableColumn<Stock, String> actions_col;

    @FXML
    private TableColumn<Stock, String> added_date_col;

    @FXML
    private TableColumn<Stock, String> availability_col;

    @FXML
    private ComboBox<String> export_combo;

    @FXML
    private TableColumn<Stock, String> id_col;

    @FXML
    private Button openAddNewEmployeeBtn;

    @FXML
    private TableColumn<Stock, String> product_name_col;

    @FXML
    private TableColumn<Stock, String> product_type_col;

    @FXML
    private TextField search_stock_input;

    @FXML
    private TableView<Stock> stock_table;

    public ObservableList<Stock> getProducts() {
        ObservableList<Stock> products = FXCollections.observableArrayList();
        String query = "SELECT * FROM stock";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Stock product = new Stock();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setType(rs.getString("type"));
                product.setQuantity(rs.getFloat("quantity"));
                product.setUnit(rs.getString("unit"));
                product.setAddedDate(Date.from(rs.getDate("added_date").toInstant()));
                products.add(product);
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        return products;
    }

    public void displayStock() {
        ObservableList<Stock> products = getProducts();
        id_col.setCellValueFactory(new PropertyValueFactory<>("cin"));
        product_name_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        product_type_col.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        Callback<TableColumn<Employee, String>, TableCell<Employee, String>> cellFoctory = (TableColumn<Employee, String> param) -> {
            final TableCell<Employee, String> cell = new TableCell<Employee, String>() {
                Image edit_img = new Image(getClass().getResourceAsStream("/images/edit.png"));
                Image delete_img = new Image(getClass().getResourceAsStream("/images/delete.png"));
                Image view_details_img = new Image(getClass().getResourceAsStream("/images/eye.png"));

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        ImageView iv_view_details = new ImageView();
                        iv_view_details.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        iv_view_details.setImage(view_details_img);
                        iv_view_details.setPreserveRatio(true);
                        iv_view_details.setSmooth(true);
                        iv_view_details.setCache(true);


                        ImageView iv_edit = new ImageView();
                        iv_edit.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        iv_edit.setImage(edit_img);
                        iv_edit.setPreserveRatio(true);
                        iv_edit.setSmooth(true);
                        iv_edit.setCache(true);

                        ImageView iv_delete = new ImageView();
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

                        //delete employee
                        iv_delete.setOnMouseClicked((MouseEvent event) -> {
//                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                            alert.setTitle("Delete Employee");
//                            alert.setHeaderText("Are you sure you want to delete this employee?");
//                            Employee employee = employees_table.getSelectionModel().getSelectedItem();
//                            Optional<ButtonType> result = alert.showAndWait();
//                            if (result.get() == ButtonType.OK) {
//                                try {
//                                    employee.delete();
//                                    displayEmployees();
//                                } catch (Exception e) {
//                                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
//                                }
//                            }
                        });

                        //update employee
                        iv_edit.setOnMouseClicked((MouseEvent event) -> {
//                            Employee employee = employees_table.getSelectionModel().getSelectedItem();
//                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/update_employee.fxml"));
//                            Scene scene = null;
//                            try {
//                                scene = new Scene(fxmlLoader.load());
//                                UpdateEmployeeController controller = fxmlLoader.getController();
//                                controller.fetchEmployee(employee);
//                            } catch (IOException e) {
//                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
//                                e.printStackTrace();
//                            }
//                            Stage stage = new Stage();
//                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
//                            stage.setTitle("Update Employee");
//                            stage.setResizable(false);
//                            stage.setScene(scene);
//                            centerScreen(stage);
//                            stage.show();
                        });

                        //view employee details
                        iv_view_details.setOnMouseClicked((MouseEvent event) -> {
//                            Employee employee = employees_table.getSelectionModel().getSelectedItem();
//                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/employee_details.fxml"));
//                            Scene scene = null;
//                            try {
//                                scene = new Scene(fxmlLoader.load());
//                                EmployeeDetailsController controller = fxmlLoader.getController();
//                                controller.fetchEmployee(employee);
//                            } catch (IOException e) {
//                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
//                                e.printStackTrace();
//                            }
//                            Stage stage = new Stage();
//                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
//                            stage.setTitle("Employee Details");
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
        actions_col.setCellFactory(cellFoctory);
        employees_table.setItems(employees);
    }

    @FXML
    void openAddProduct(MouseEvent event) throws IOException {
        openNewWindow("Add Product", "add_new_product");
    }
}