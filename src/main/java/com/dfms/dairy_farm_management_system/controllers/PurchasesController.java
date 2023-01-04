package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.NewPurchaseController;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.PurchaseDetailsController;
import com.dfms.dairy_farm_management_system.models.Purchase;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.log4j.BasicConfigurator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
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
        BasicConfigurator.configure();

        ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");
        export_combo.setItems(list);


        //check what user select in the combo box
        export_combo.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (t1.equals("PDF")) {
                exportToPDF();
            } else {
                exportToExcel();
            }
        });

        liveSearch(search_input, PurchaseTable);
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
                            newPurchaseController.fetchPurchase(purchase);
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Update Purchase");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });
                        iv_view_details.setOnMouseClicked((MouseEvent event) -> {
                         Purchase purchase = PurchaseTable.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/purchase_details.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                PurchaseDetailsController controller = fxmlLoader.getController();
                                controller.fetchPurchase(purchase.getId(), purchase.getProduct_name(),  purchase.getQuantity(), purchase.getPrice(), purchase.getSupplier_name(), (Date) purchase.getPurchase_date());
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
    private Statement statemeent;
    private Connection connection = getConnection();
    void exportToExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"), new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Purchases");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Purchase ID");
                header.createCell(1).setCellValue("Product");
                header.createCell(2).setCellValue("Price");
                header.createCell(3).setCellValue("Quantity");
                header.createCell(4).setCellValue("Supplier");
                header.createCell(5).setCellValue("Date");


                //get all employees from database
                String query = "SELECT pur.id,st.name,pur.price,s.name,pur.purchase_date,pur.quantity FROM `purchases` pur ,`suppliers` s , `stocks` st where pur.supplier_id=s.id and pur.stock_id=st.id  ";
                try {

                    statemeent = connection.createStatement();
                    ResultSet rs = statemeent.executeQuery(query);
                    while (rs.next()) {
                        int rowNum = rs.getRow();
                        Row row = sheet.createRow(rowNum);
                        row.createCell(0).setCellValue(rs.getString("pur.id"));
                        row.createCell(1).setCellValue(rs.getString("st.name"));
                        row.createCell(2).setCellValue(rs.getString("pur.price"));
                        row.createCell(3).setCellValue(rs.getString("pur.quantity"));
                        row.createCell(4).setCellValue(rs.getString("s.name"));
                        row.createCell(5).setCellValue(rs.getString("pur.purchase_date"));

                    }
                } catch (Exception e) {
                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }


                FileOutputStream fileOutputStream = new FileOutputStream(file);
                workbook.write(fileOutputStream);
                workbook.close();

                displayAlert("Success", "Purchases exported successfully", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
    private static int COLUMNS_COUNT = 5;
    void exportToPDF() {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Save As");
//        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
//        File file = fileChooser.showSaveDialog(null);
//        if (file != null) {
//            try {
//                Document document = new Document();
//                PdfWriter.getInstance(document, new FileOutputStream(file));
//                document.open();
//                try {
//                    document.add(new Paragraph(Element.ALIGN_CENTER, "Stock Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD)));
//                    document.add(new Paragraph(" "));
//                } catch (Exception e) {
//                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
//                }
//                PdfPTable table = new PdfPTable(9);
//                table.addCell("Product ID");
//                table.addCell("Product Name");
//                table.addCell("Product Type");
//                table.addCell("Quantity");
//                table.addCell("Availability");
//                table.addCell("Unit");
//                table.addCell("Added Date");
//
//                //make pdf page width bigger
//                table.setWidthPercentage(100);
//                table.setSpacingBefore(10f);
//                table.setSpacingAfter(10f);
//
//                //get all employees from database
//                String query = "SELECT * FROM `stocks`";
//                try {
//                    statement = connection.createStatement();
//                    ResultSet rs = statement.executeQuery(query);
//                    while (rs.next()) {
//                        table.addCell(rs.getString("id"));
//                        table.addCell(rs.getString("name"));
//                        table.addCell(rs.getString("type"));
//                        table.addCell(rs.getString("quantity"));
//                        table.addCell(rs.getString("availability"));
//                        table.addCell(rs.getString("unit"));
//                        table.addCell(rs.getString("created_at"));
//                    }
//
//                    document.add(table);
//                    document.close();
//                    displayAlert("Success", "Stcok exported successfully", Alert.AlertType.INFORMATION);
//                } catch (Exception e) {
//                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
//                }
//            } catch (Exception e) {
//                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
//            }
//        }
//    }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                Document document = new Document();
                //change document orientation to landscape
                document.setPageSize(PageSize.A4.rotate());

                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                try {
                    Paragraph title = new Paragraph("Purchases List", FontFactory.getFont(FontFactory.COURIER_BOLD, 20, BaseColor.BLACK));
                    Paragraph text = new Paragraph("This is the list of the purchases", FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK));

                    //center paragraph
                    title.setAlignment(Element.ALIGN_CENTER);
                    text.setAlignment(Element.ALIGN_CENTER);
                    title.setSpacingAfter(30);
                    text.setSpacingAfter(30);

                    document.add(title);
                    document.add(text);
                } catch (Exception e) {
                    e.printStackTrace();
                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }
                PdfPTable table = new PdfPTable(COLUMNS_COUNT);

                //change pdf orientation to landscape
                table.setWidthPercentage(100);
                table.setSpacingBefore(11f);
                table.setSpacingAfter(11f);
                float[] colWidth = new float[COLUMNS_COUNT];
                for (int i = 0; i < COLUMNS_COUNT; i++) {
                    colWidth[i] = 2f;
                }

                //add table header
                table.addCell(new PdfPCell(new Paragraph("Product ", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Price", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Quantity", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Supplier", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Date", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);

                //add padding to cells
                table.getDefaultCell().setPadding(3);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);


                //get employees displayed in table
                ObservableList<Purchase> purchases = PurchaseTable.getItems();

                //get employee of each row
                //used a method in my updateEmplyeeController to get the employee of each row based on the cin
                NewPurchaseController controller = new NewPurchaseController();

                for (Purchase purchase : purchases) {
                    Purchase pur = controller.getPurchase(purchase.getId());

                    table.addCell(new PdfPCell(new Paragraph(pur.getProduct_name()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(pur.getPrice())))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(pur.getQuantity())))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(pur.getSupplier_name()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(pur.getPurchase_date())))).setPadding(5);

                }

                document.add(table);
                document.close();
                displayAlert("Success", "Purchases exported successfully", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

}
