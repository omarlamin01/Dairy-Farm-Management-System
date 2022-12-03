package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.UpdateProductController;
import com.dfms.dairy_farm_management_system.models.Employee;
import com.dfms.dairy_farm_management_system.models.Stock;
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
import javafx.print.*;

import java.awt.print.*;

import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.log4j.BasicConfigurator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class StockController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //this line of code is so important for the export !!!!
        BasicConfigurator.configure();

        ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");
        export_combo.setItems(list);
        displayStock();

        //check what user select in the combo box
        export_combo.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (t1.equals("PDF")) {
                exportToPDF();
            } else {
                exportToExcel();
            }
        });

        liveSearch(search_stock_input, stock_table);
    }

    private static int COLUMNS_COUNT = 8;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection = getConnection();
    @FXML
    private TableColumn<Stock, String> actions_col;


    @FXML
    private TableColumn<Stock, String> product_qunatity_col;

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
        String query = "SELECT * FROM stocks";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Stock product = new Stock();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setType(rs.getString("type"));
                if (rs.getInt("quantity") > 0) {
                    product.setAvailability(true);
                } else {
                    product.setAvailability(false);
                }
                product.setQuantity(rs.getFloat("quantity"));
                product.setUnit(rs.getString("unit"));
                products.add(product);
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        return products;
    }

    public void displayStock() {
        ObservableList<Stock> products = getProducts();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        product_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        product_type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        product_qunatity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        availability_col.setCellValueFactory(new PropertyValueFactory<>("availability"));
        Callback<TableColumn<Stock, String>, TableCell<Stock, String>> cellFoctory = (TableColumn<Stock, String> param) -> {
            final TableCell<Stock, String> cell = new TableCell<Stock, String>() {
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
                        //ImageView iv_view_details = new ImageView();
                        //iv_view_details.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        //iv_view_details.setImage(view_details_img);
                        //iv_view_details.setPreserveRatio(true);
                        //iv_view_details.setSmooth(true);
                        //iv_view_details.setCache(true);


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
                        //HBox.setMargin(iv_view_details, new Insets(1, 1, 0, 3));
                        HBox.setMargin(iv_delete, new Insets(1, 1, 0, 3));
                        HBox.setMargin(iv_edit, new Insets(1, 1, 0, 3));

                        setGraphic(managebtn);

                        //delete employee
                        iv_delete.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Product");
                            alert.setHeaderText("Are you sure you want to delete this product?");
                            Stock product = stock_table.getSelectionModel().getSelectedItem();
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                try {
                                    product.delete();
                                    displayStock();
                                } catch (Exception e) {
                                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                }
                            }
                        });

                        //update employee
                        iv_edit.setOnMouseClicked((MouseEvent event) -> {
                            Stock product = stock_table.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/update_product.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                UpdateProductController controller = fxmlLoader.getController();
                                controller.fetchProduct(product);
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Update Product");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });

                        //view employee details
//                        iv_view_details.setOnMouseClicked((MouseEvent event) -> {
//                            Stock product = stock_table.getSelectionModel().getSelectedItem();
//                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/employee_details.fxml"));
//                            Scene scene = null;
//                            try {
//                                scene = new Scene(fxmlLoader.load());
//                                ProductDetailsController controller = fxmlLoader.getController();
//                                controller.fetchProduct(product);
//                            } catch (IOException e) {
//                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
//                                e.printStackTrace();
//                            }
//                            Stage stage = new Stage();
//                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
//                            stage.setTitle("Product Details");
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
        stock_table.setItems(products);
    }

    @FXML
    void openAddProduct(MouseEvent event) throws IOException {
        openNewWindow("Add Product", "add_new_product");
    }

    @FXML
    public void refreshTable() {
        stock_table.getItems().clear();
        displayStock();
    }

    void exportToExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"), new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Stock");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Product ID");
                header.createCell(1).setCellValue("Product Name");
                header.createCell(2).setCellValue("Product Type");
                header.createCell(3).setCellValue("Quantity");
                header.createCell(4).setCellValue("Availability");
                header.createCell(5).setCellValue("Unit");
                header.createCell(6).setCellValue("Added Date");

                //get all employees from database
                String query = "SELECT * FROM `stocks`";
                try {
                    statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    while (rs.next()) {
                        int rowNum = rs.getRow();
                        Row row = sheet.createRow(rowNum);
                        row.createCell(0).setCellValue(rs.getString("id"));
                        row.createCell(1).setCellValue(rs.getString("name"));
                        row.createCell(2).setCellValue(rs.getString("type"));
                        row.createCell(3).setCellValue(rs.getString("quantity"));
                        row.createCell(4).setCellValue(rs.getString("availability"));
                        row.createCell(5).setCellValue(rs.getString("unit"));
                        row.createCell(6).setCellValue(rs.getString("created_at"));
                    }
                } catch (Exception e) {
                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }


                FileOutputStream fileOutputStream = new FileOutputStream(file);
                workbook.write(fileOutputStream);
                workbook.close();

                displayAlert("Success", "Stock exported successfully", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    void exportToPDF() {
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
                    Paragraph title = new Paragraph("Stock List", FontFactory.getFont(FontFactory.COURIER_BOLD, 20, BaseColor.BLACK));
                    Paragraph text = new Paragraph("This is the list of the products", FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK));

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
                float[] colWidth = {2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
                table.setWidths(colWidth);

                //add table headers
                table.addCell(new PdfPCell(new Paragraph("Product ID", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Product Name", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Product Type", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Quantity", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Availability", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Unit", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Added Date", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);

                //add padding to cells
                table.getDefaultCell().setPadding(3);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

                //get employees displayed in table
                ObservableList<Stock> stock = stock_table.getItems();

                //get product of each row
                //used a method in my updateProductController to get the product of each row based on the id
                UpdateProductController controller = new UpdateProductController();

                for (Stock s : stock) {
                    Stock product = controller.getProduct(s.getId());
                    System.out.println(product.toString());
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(product.getId())))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(product.getName()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(product.getType()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(product.getQuantity())))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(product.getAvailability()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(product.getUnit()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(product.getCreatedAt())))).setPadding(5);
                }

                document.add(table);
                document.close();
                displayAlert("Success", "Stock exported successfully", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    public void liveSearch(TextField search_input, TableView table) {
        search_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                refreshTable();
            } else {
                ObservableList<Stock> filteredList = FXCollections.observableArrayList();
                ObservableList<Stock> products = getProducts();
                for (Stock product : products) {
                    if (product.getName().toLowerCase().contains(newValue.toLowerCase()) || product.getType().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(product);
                    }
                }
                table.setItems(filteredList);
            }
        });
    }
}