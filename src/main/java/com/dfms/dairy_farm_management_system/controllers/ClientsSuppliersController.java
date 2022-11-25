package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.*;
import com.dfms.dairy_farm_management_system.models.Animal;
import com.dfms.dairy_farm_management_system.models.Client;
import com.dfms.dairy_farm_management_system.models.Employee;
import com.dfms.dairy_farm_management_system.models.Supplier;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class ClientsSuppliersController implements Initializable {

    @FXML
    private TableView<Client> TableClient;

    @FXML
    private TableColumn<Client,Integer> colidClient;

    @FXML
    private TableColumn<Client,String> colnameClient;

    @FXML
    private TableColumn<Client,String>coltypeClient;

    @FXML
    private TableColumn<Client,String>colemailClient;

    @FXML
    private TableColumn<Client,String> colphoneClient;

    @FXML
    private TableColumn<Client,String> actionClient;
    @FXML
    private ComboBox<String> export_combo;
    @FXML
    private TextField search_input;

    @FXML
    private ComboBox<String> export_combo_sup;

    @FXML
    private TableView<Supplier> TableSupplier;

    @FXML
    private TableColumn<Supplier,Integer> colidSupplier;

    @FXML
    private TableColumn<Supplier,String> colnameSupplier;

    @FXML
    private TableColumn<Supplier,String>coltypeSupplier;

    @FXML
    private TableColumn<Supplier,String> colemailSupplier;

    @FXML
    private TableColumn<Supplier,String> colphoneSupplier;

    @FXML
    private TableColumn<Supplier,String> colactionSupplier;
    PreparedStatement statement =null;
    ResultSet resultSet = null;
    Connection connection = DBConfig.getConnection();
    ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        export_combo.setItems(list);
        export_combo_sup.setItems(list);
        try {
            displayClients();
            displaySuppliers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        export_combo.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            String query = "SELECT * FROM `clients`";
            if (t1.equals("PDF")) {
                exportToPDF("Clients List",query);
            } else {
                exportToExcel("Clients",query,"Clients List");
            }
        });

        export_combo_sup.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            String query = "SELECT * FROM `suppliers`";
            if (t1.equals("PDF")) {
                exportToPDF("Suppliers list",query);
            } else {
                exportToExcel("Suppliers",query,"Suppliers List");
            }
        });



    }

    @FXML
    void openAddClient(MouseEvent event) throws IOException {
        openNewWindow("Add client", "add_new_client");
    }

    @FXML
    void openAddSupplier(MouseEvent event) throws IOException {
        openNewWindow("Add supplier", "add_new_supplier");
    }

    ObservableList<Client> listClient = FXCollections.observableArrayList();

    public ObservableList<Client> getClients() {
        String select_query = "SELECT * from `clients`";

        try {
            statement = connection.prepareStatement(select_query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Client client = new Client();

                client.setId(resultSet.getInt("id"));
                client.setName((resultSet.getString("name")));
                client.setType(resultSet.getString("type"));
                client.setPhone(resultSet.getString("phone"));
                client.setEmail(resultSet.getString("email"));
                client.setCreated_at(resultSet.getTimestamp("created_at"));
                client.setUpdated_at(resultSet.getTimestamp("updated_at"));

                listClient.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return listClient;
    }

    public void displayClients() throws SQLException, ClassNotFoundException {
        ObservableList<Client> list = getClients();
        colidClient.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
        colnameClient.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        coltypeClient.setCellValueFactory(new PropertyValueFactory<Client, String>("type"));
        colemailClient.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        colphoneClient.setCellValueFactory(new PropertyValueFactory<Client, String>("phone"));

        Callback<TableColumn<Client, String>, TableCell<Client, String>> cellFoctory = (TableColumn<Client, String> param) -> {
            // make cell containing buttons
            final TableCell<Client, String> cell = new TableCell<Client, String>() {

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
                            Client client =TableClient.getSelectionModel().getSelectedItem();
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                client.delete();
                                refreshTableClient();
                                Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                                alertInfo.setTitle("Delete Cow");
                                alertInfo.setHeaderText("Cow deleted successfully");
                                alertInfo.showAndWait();
                            }

                        });
                        iv_viewDetail.setOnMouseClicked((MouseEvent event) -> {
                            Client client= TableClient.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/client_details.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                ClientDetailsController  clientDetailsController = fxmlLoader.getController();
                                 clientDetailsController.fetchClient(client);
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Clients Details");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });

                        iv_edit.setOnMouseClicked((MouseEvent event) -> {
                            Client client = TableClient.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/add_new_client.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            NewClientController newClientController = fxmlLoader.getController();
                            newClientController.setUpdate(true);
                            newClientController.fetchClient(client.getId(), client.getName(), client.getEmail(), client.getPhone(),client.getType());
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Update Client");
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

        actionClient.setCellFactory(cellFoctory);
        TableClient.setItems(list);
    }
    public void refreshTableClient() {
        listClient.clear();
        listClient = getClients();
        TableClient.setItems(listClient);
    }
    public void refreshTable(MouseEvent mouseEvent) {
        refreshTableClient();
    }
    public void liveSearchClient() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Client> filteredData = new FilteredList<>(listClient,p->true);

        // 2. Set the filter Predicate whenever the filter changes.
        search_input.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                // If filter text is empty, display all clients.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (client.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }return false;
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Client> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(TableClient.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
         TableClient.setItems(sortedData);
    }

    public void liveSearchSupplier() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Supplier> filteredData = new FilteredList<>(listSupplier,p->true);

        // 2. Set the filter Predicate whenever the filter changes.
        search_input.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(supplier -> {
                // If filter text is empty, display all clients.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (supplier.getNameSupplier().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }return false;
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Supplier> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(TableSupplier.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        TableSupplier.setItems(sortedData);
    }

    @FXML
    void search_client(MouseEvent event) {
     liveSearchClient();
    }
    @FXML
    void search_supplier(MouseEvent event) {
        liveSearchSupplier();
    }
    void exportToPDF(String typeList,String query) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                try {
                    document.add(new Paragraph(typeList));
                    document.add(new Paragraph(" "));
                } catch (Exception e) {
                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }
                PdfPTable table = new PdfPTable(9);
                table.addCell("ID");
                table.addCell("Name");
                table.addCell("Type");
                table.addCell("Phone");
                table.addCell("Email");

                //get all clients from database
//                String query = "SELECT * FROM `clients`";
                try {
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    while (rs.next()) {
                        table.addCell(rs.getString("id"));
                        table.addCell(rs.getString("name"));
                        table.addCell(rs.getString("type"));
                        table.addCell(rs.getString("phone"));
                        table.addCell(rs.getString("email"));


                    }

                    document.add(table);
                    document.close();
                    displayAlert("Success", typeList+" exported successfully", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
    void exportToExcel(String NameSheet,String query,String typeList) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"), new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet(NameSheet);
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("ID");
                header.createCell(1).setCellValue("Name");
                header.createCell(2).setCellValue("Type");
                header.createCell(3).setCellValue("Phone");
                header.createCell(4).setCellValue("Email");

                //get all clients from database

                try {
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    while (rs.next()) {
                        int rowNum = rs.getRow();
                        Row row = sheet.createRow(rowNum);
                        row.createCell(0).setCellValue(rs.getString("id"));
                        row.createCell(1).setCellValue(rs.getString("name"));
                        row.createCell(2).setCellValue(rs.getString("type"));
                        row.createCell(3).setCellValue(rs.getString("phone"));
                        row.createCell(4).setCellValue(rs.getString("email"));


                    }
                } catch (Exception e) {
                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }


                FileOutputStream fileOutputStream = new FileOutputStream(file);
                workbook.write(fileOutputStream);
                workbook.close();

                displayAlert("Success", typeList+"exported successfully", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    ObservableList<Supplier> listSupplier = FXCollections.observableArrayList();
    public ObservableList<Supplier> getSuppliers() {
        String select_query = "SELECT * from `suppliers`";

        try {
            statement = connection.prepareStatement(select_query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Supplier supplier = new Supplier();
                supplier.setId(resultSet.getInt("id"));
                supplier.setNameSupplier((resultSet.getString("name")));
                supplier.setTypeSupplier(resultSet.getString("type"));
                supplier.setPhoneSupplier(resultSet.getString("phone"));
                supplier.setEmailSupplier(resultSet.getString("email"));
                supplier.setCreated_at(resultSet.getTimestamp("created_at"));
                supplier.setUpdated_at(resultSet.getTimestamp("updated_at"));

                listSupplier.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return listSupplier;
    }

    public void displaySuppliers() throws SQLException, ClassNotFoundException {
        ObservableList<Supplier> list = getSuppliers();
        colidSupplier.setCellValueFactory(new PropertyValueFactory<Supplier, Integer>("id"));
        colnameSupplier.setCellValueFactory(new PropertyValueFactory<Supplier, String>("nameSupplier"));
        coltypeSupplier.setCellValueFactory(new PropertyValueFactory<Supplier, String>("typeSupplier"));
        colemailSupplier.setCellValueFactory(new PropertyValueFactory<Supplier, String>("emailSupplier"));
        colphoneSupplier.setCellValueFactory(new PropertyValueFactory<Supplier, String>("phoneSupplier"));

        Callback<TableColumn<Supplier, String>, TableCell<Supplier, String>> cellFoctory = (TableColumn<Supplier, String> param) -> {
            // make cell containing buttons
            final TableCell<Supplier, String> cell = new TableCell<Supplier, String>() {

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
                            alert.setHeaderText("Are you sure you want to delete this supplier?");
                            Supplier supplier =TableSupplier.getSelectionModel().getSelectedItem();
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                supplier.delete();
                                refreshTableSupplier();
                                Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                                alertInfo.setTitle("Delete supplier");
                                alertInfo.setHeaderText("supplier deleted successfully");
                                alertInfo.showAndWait();
                            }

                        });
                        iv_viewDetail.setOnMouseClicked((MouseEvent event) -> {
                            Supplier supplier= TableSupplier.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/supplier_details.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                SupplierDetailsController supplierDetailsController = fxmlLoader.getController();
                                supplierDetailsController.fetchSupplier(supplier);
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Clients Details");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });

                        iv_edit.setOnMouseClicked((MouseEvent event) -> {
                           Supplier supplier = TableSupplier.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/add_new_supplier.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            NewSupplierController newSupplierController = fxmlLoader.getController();
                            newSupplierController.setUpdate(true);
                            newSupplierController.fetchSupplier(supplier);
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Update Supplier");
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

        colactionSupplier.setCellFactory(cellFoctory);
        TableSupplier.setItems(list);
    }
    @FXML
    void refreshTableSupplier(MouseEvent event) {
       refreshTableSupplier();
    }
    void refreshTableSupplier(){
        listSupplier.clear();
        listSupplier = getSuppliers();
        TableSupplier.setItems(listSupplier);
    }

}

