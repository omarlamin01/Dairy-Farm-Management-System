package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.*;
import com.dfms.dairy_farm_management_system.models.AnimalSale;
import com.dfms.dairy_farm_management_system.models.MilkCollection;
import com.dfms.dairy_farm_management_system.models.MilkSale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class SalesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo.setItems(list);
        combo1.setItems(list);

        combo.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (t1.equals("PDF")) {
                exportToPDF(AnimalSalesTable);
            } else {
                exportToExcel();
            }
        });
        combo1.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (t1.equals("PDF")) {
                exportToPDF(MilkSaleTable);
            } else {
                exportToExcel2();
            }
        });
        liveSearch(search_input, AnimalSalesTable);
        try {
            afficher();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            afficheer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        liveSearch2(search_inpu, MilkSaleTable);
    }
    @FXML
    private TableView<MilkSale> MilkSaleTable;

    @FXML
    private TableColumn<MilkSale, String> action_c;

    @FXML
    private TableColumn<MilkSale, String> client_c;

    @FXML
    private ComboBox<String> combo1;

    @FXML
    private TableColumn<MilkSale, LocalDate> date_c;

    @FXML
    private TableColumn<MilkSale, Float> price_c;

    @FXML
    private TableColumn<MilkSale, Float> quantity_c;

    @FXML
    private Button search_btn;
    @FXML
    private Button refresh_table_btn;

    @FXML
    private Button refresh_table_btn1;
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
    private TableColumn<AnimalSale, Date> operationdate_col;

    @FXML
    private TableColumn<AnimalSale, Float> price_col;

    @FXML
    private TextField search_input;

    PreparedStatement statement = null;

    ResultSet resultSet = null;

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

        String query = "SELECT * FROM `animals_sales`";

        statement = DBConfig.getConnection().prepareStatement(query);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            AnimalSale animalSale = new AnimalSale();
            
            animalSale.setId(resultSet.getInt("id"));
            animalSale.setClientId(resultSet.getInt("client_id"));
            animalSale.setAnimalId(resultSet.getString("animal_id"));
            animalSale.setPrice(resultSet.getFloat("price"));
            animalSale.setSale_date(resultSet.getDate("sale_date"));
            animalSale.setCreated_at(resultSet.getTimestamp("created_at"));
            animalSale.setUpdated_at(resultSet.getTimestamp("updated_at"));

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
        animalis_col.setCellValueFactory(new PropertyValueFactory<AnimalSale, String>("animalId"));
        price_col.setCellValueFactory(new PropertyValueFactory<AnimalSale, Float>("price"));
        client_col.setCellValueFactory(new PropertyValueFactory<AnimalSale, String>("clientName"));
        operationdate_col.setCellValueFactory(new PropertyValueFactory<AnimalSale, Date>("sale_date"));


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

                            AnimalSale animalSale = AnimalSalesTable.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/add_new_cow_sale.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            CowSalesController cowSalesController = fxmlLoader.getController();
                            cowSalesController.setUpdate(true);
                            cowSalesController.fetchAnimalSale(animalSale.getId(),animalSale.getAnimalId(), animalSale.getPrice(), animalSale.getClientName(),animalSale.getSale_date());
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Update Animal Sale");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });
                        btnViewDetail.setOnMouseClicked((MouseEvent event) -> {
                            AnimalSale animalSale = AnimalSalesTable.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/animal_sale_details.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                               AnimalSaleDetailsController controller = fxmlLoader.getController();
                                controller.fetchAnimalSale( animalSale.getId(),animalSale.getAnimalId(), animalSale.getPrice(), animalSale.getClientName(), animalSale.getSale_date());
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
                    if (Animal.getClientName().toLowerCase().contains(newValue.toLowerCase()) || Animal.getAnimalId().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(Animal);
                    }
                }
                AnimalSalesTable.setItems(filteredList);
            }
        });
    }
    public ObservableList<MilkSale> getMilkSale() throws SQLException {
        ObservableList<MilkSale> list = FXCollections.observableArrayList();

        String select_query = "SELECT * FROM milk_sales";

        statement = DBConfig.getConnection().prepareStatement(select_query);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            MilkSale milkSale = new MilkSale();

            milkSale.setId(resultSet.getInt("id"));
            milkSale.setPrice(resultSet.getFloat("price"));
            milkSale.setQuantity(resultSet.getFloat("quantity"));
            milkSale.setClientId(resultSet.getInt("client_id"));
            milkSale.setSale_date(resultSet.getDate("sale_date"));

            list.add(milkSale);
        }
        return list;
    }
    private void afficheer() throws SQLException {
        ObservableList<MilkSale> list = getMilkSale();
        quantity_c.setCellValueFactory(new PropertyValueFactory<MilkSale, Float>("quantity"));
        price_c.setCellValueFactory(new PropertyValueFactory<MilkSale, Float>("price"));
        client_c.setCellValueFactory(new PropertyValueFactory<MilkSale, String>("clientName"));
        date_c.setCellValueFactory(new PropertyValueFactory<MilkSale, LocalDate>("sale_date"));


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


                            MilkSale mc = MilkSaleTable.getSelectionModel().getSelectedItem();
                            if (mc.delete()) {

                                displayAlert("success", "Milk Sale deleted successfully", Alert.AlertType.INFORMATION);
                                refreshTableMilkSales();
                            } else {
                                displayAlert("Error", "Error while deleting!!!", Alert.AlertType.ERROR);
                            }



                            //displayAlert("Success", "Milk Collection deleted successfully", Alert.AlertType.INFORMATION);

                        });
                        btnEdit.setOnMouseClicked((MouseEvent event) -> {
                            MilkSale milkSale = MilkSaleTable.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/add_new_milk_sale.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            MilkSalesController milkSalesController = fxmlLoader.getController();
                            milkSalesController.setUpdate(true);
                            milkSalesController.fetchMilkSale(milkSale.getId(),milkSale.getQuantity(), milkSale.getPrice(), milkSale.getClientName(),milkSale.getSale_date());
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Update Milk Sale");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });
                        btnViewDetail.setOnMouseClicked((MouseEvent event) -> {
                            MilkSale milkSale = MilkSaleTable.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/milk_sale_details.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                MilkSaleDetailsController controller = fxmlLoader.getController();
                                controller.fetchMilkSale( milkSale.getId(),milkSale.getQuantity(), milkSale.getPrice(), milkSale.getClientName(), milkSale.getSale_date());
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

        action_c.setCellFactory(cellFoctory);
        MilkSaleTable.setItems(list);

    }

    private void refreshTableMilkSales() {
       MilkSaleTable.getItems().clear();
        try {
           afficheer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                Sheet sheet = workbook.createSheet("Animal Sales");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Sale ID");
                header.createCell(1).setCellValue("Animal ID");
                header.createCell(2).setCellValue("Price");
                header.createCell(3).setCellValue("Client");
                header.createCell(4).setCellValue("Date");



                //get all employees from database
                String query = "SELECT ms.id,ms.animal_id,ms.price,c.name,ms.sale_date FROM `animals_sales` ms ,`clients` c where ms.client_id=c.id ";
                try {

                    statemeent = connection.createStatement();
                    ResultSet rs = statemeent.executeQuery(query);
                    while (rs.next()) {
                        int rowNum = rs.getRow();
                        Row row = sheet.createRow(rowNum);
                        row.createCell(0).setCellValue(rs.getString("ms.id"));
                        row.createCell(1).setCellValue(rs.getString("ms.animal_id"));
                        row.createCell(2).setCellValue(rs.getString("ms.price"));
                        row.createCell(3).setCellValue(rs.getString("c.name"));
                        row.createCell(4).setCellValue(rs.getString("ms.sale_date"));

                    }
                } catch (Exception e) {
                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }


                FileOutputStream fileOutputStream = new FileOutputStream(file);
                workbook.write(fileOutputStream);
                workbook.close();

                displayAlert("Success", "Animal Sales exported successfully", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    void exportToPDF(Node node_to_print) {
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
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.EQUAL);

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            job.getJobSettings().setPageLayout(pageLayout);
            //rotate stock table
            boolean success = job.printPage(node_to_print);
            // set orientation to landscape
            if (success) {
                job.endJob();
            } else {
                displayAlert("Error", "Failed to print", Alert.AlertType.ERROR);
            }
        }
    }

    public void liveSearch2(TextField search_input, TableView table) {
        search_inpu.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                refreshTableMilkSales();
            } else {
                ObservableList<MilkSale> filteredList = FXCollections.observableArrayList();
                ObservableList<MilkSale> milkSale = null;
                try {
                    milkSale = getMilkSale();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                for (MilkSale milk: milkSale) {
                    if (milk.getClientName().toLowerCase().contains(newValue.toLowerCase()) ) {
                        filteredList.add(milk);
                    }
                }
                MilkSaleTable.setItems(filteredList);
            }
        });
    }
    void exportToExcel2() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"), new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Milk Sales");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Sale ID");
                header.createCell(1).setCellValue("Quantity");
                header.createCell(2).setCellValue("Price");
                header.createCell(3).setCellValue("Client");
                header.createCell(4).setCellValue("Date");



                //get all employees from database
                String query = "SELECT ms.id,ms.quantity,ms.price,c.name,ms.sale_date FROM `milk_sales` ms ,`clients` c where ms.client_id=c.id ";
                try {

                    statemeent = connection.createStatement();
                    ResultSet rs = statemeent.executeQuery(query);
                    while (rs.next()) {
                        int rowNum = rs.getRow();
                        Row row = sheet.createRow(rowNum);
                        row.createCell(0).setCellValue(rs.getString("ms.id"));
                        row.createCell(1).setCellValue(rs.getString("ms.quantity"));
                        row.createCell(2).setCellValue(rs.getString("ms.price"));
                        row.createCell(3).setCellValue(rs.getString("c.name"));
                        row.createCell(4).setCellValue(rs.getString("ms.sale_date"));

                    }
                } catch (Exception e) {
                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }


                FileOutputStream fileOutputStream = new FileOutputStream(file);
                workbook.write(fileOutputStream);
                workbook.close();

                displayAlert("Success", "Milk Sales exported successfully", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
    @FXML
    void refreshTable(MouseEvent event) throws SQLException {
     refreshTableAnimalSales();
    }

    @FXML
    void refreshTable2(MouseEvent event) {
       refreshTableMilkSales();

    }

}





