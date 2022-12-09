package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.models.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class SettingsController {
    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection = getConnection();
    @FXML
    private TableColumn<Role, String> actions_col;

    @FXML
    private TableColumn<Role, String> added_date_col;

    @FXML
    private TableColumn<Role, String> id_col;

    @FXML
    private TableColumn<Role, String> role_name_col;

    @FXML
    private TextField search_stock_input;

    @FXML
    private TableView<Role> roles_table;

    public void displayStock() {
        ObservableList<Role> roles = getRoles();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        role_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        added_date_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        Callback<TableColumn<Role, String>, TableCell<Role, String>> cellFoctory = (TableColumn<Role, String> param) -> {
            final TableCell<Role, String> cell = new TableCell<Role, String>() {
                Image edit_img = new Image(getClass().getResourceAsStream("/images/edit.png"));
                Image delete_img = new Image(getClass().getResourceAsStream("/images/delete.png"));
                //Image view_details_img = new Image(getClass().getResourceAsStream("/images/eye.png"));

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
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

                        HBox managebtn = new HBox(iv_edit, iv_delete);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(iv_delete, new Insets(1, 1, 0, 3));
                        HBox.setMargin(iv_edit, new Insets(1, 1, 0, 3));

                        setGraphic(managebtn);

                        //delete product
//                        iv_delete.setOnMouseClicked((MouseEvent event) -> {
//                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                            alert.setTitle("Delete Product");
//                            alert.setHeaderText("Are you sure you want to delete this product?");
//                            Stock product = roles_table.getSelectionModel().getSelectedItem();
//                            Optional<ButtonType> result = alert.showAndWait();
//                            if (result.get() == ButtonType.OK) {
//                                try {
//                                    if (product.delete()) {
//                                        displayAlert("Success", "Product deleted successfully", Alert.AlertType.INFORMATION);
//                                        displayStock();
//                                    }
//                                } catch (Exception e) {
//                                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
//                                }
//                            }
//                        });

                        //update employee
//                        iv_edit.setOnMouseClicked((MouseEvent event) -> {
//                            Role role = roles_table.getSelectionModel().getSelectedItem();
//                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/update_product.fxml"));
//                            Scene scene = null;
//                            try {
//                                scene = new Scene(fxmlLoader.load());
//                                UpdateProductController controller = fxmlLoader.getController();
//                                controller.fetchProduct(role);
//                            } catch (IOException e) {
//                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
//                                e.printStackTrace();
//                            }
//                            Stage stage = new Stage();
//                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
//                            stage.setTitle("Update Product");
//                            stage.setResizable(false);
//                            stage.setScene(scene);
//                            centerScreen(stage);
//                            stage.show();
//                        });
                    }
                }
            };
            return cell;
        };
        actions_col.setCellFactory(cellFoctory);
        roles_table.setItems(roles);
    }

    public ObservableList<Role> getRoles() {
        ObservableList<Role> roles = FXCollections.observableArrayList();
        String query = "SELECT * FROM roles";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setName(rs.getString("name"));
                role.setAddedDate(rs.getString("added_date"));
                roles.add(role);
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        return roles;
    }

    @FXML
    void openAddRole(MouseEvent event) throws IOException {
        openNewWindow("Add new role", "add_new_role");
    }

    @FXML
    void refreshList(MouseEvent event) {

    }

    @FXML
    void refreshTable(MouseEvent event) {

    }
}
