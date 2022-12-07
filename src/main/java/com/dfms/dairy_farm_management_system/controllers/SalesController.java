package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.*;
import com.dfms.dairy_farm_management_system.models.AnimalSale;
import com.dfms.dairy_farm_management_system.models.MilkSale;
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
import javafx.scene.image.ImageView;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class SalesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BasicConfigurator.configure();
        combo.setItems(list);
        combo1.setItems(list);

        combo.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (t1.equals("PDF")) {
                exportToPDF();
            } else {
                exportToExcel();
            }
        });
        combo1.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (t1.equals("PDF")) {
                exportToPDF2();
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
    private TableColumn<AnimalSale, String> client_col;

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

                        iv_delete.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Confirmation");
                            alert.setHeaderText("Are you sure you want to delete this animal sale?");
                            AnimalSale mc = AnimalSalesTable.getSelectionModel().getSelectedItem();
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                try {
                                    if (mc.delete()) {
                                        displayAlert("success", "Animal Sale deleted successfully", Alert.AlertType.INFORMATION);
                                        refreshTableAnimalSales();
                                    } else {
                                        displayAlert("Error", "Error while deleting!!!", Alert.AlertType.ERROR);
                                    }
                                } catch (Exception e) {
                                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                }
                            }
                        });
                        iv_edit.setOnMouseClicked((MouseEvent event) -> {

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
                            cowSalesController.fetchAnimalSale(animalSale.getId(), animalSale.getAnimalId(), animalSale.getPrice(), animalSale.getClientName(), animalSale.getSale_date());
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Update Animal Sale");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });
                        iv_view_details.setOnMouseClicked((MouseEvent event) -> {
                            AnimalSale animalSale = AnimalSalesTable.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/animal_sale_details.fxml"));
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
                for (AnimalSale Animal : animalSale) {
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


                        iv_delete.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Confirmation");
                            alert.setHeaderText("Are you sure you want to delete this cow sale?");

                            MilkSale mc = MilkSaleTable.getSelectionModel().getSelectedItem();
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                try {
                                    if (mc.delete()) {

                                        displayAlert("success", "Milk Sale deleted successfully", Alert.AlertType.INFORMATION);
                                        refreshTableMilkSales();
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
                            milkSalesController.fetchMilkSale(milkSale.getId(), milkSale.getQuantity(), milkSale.getPrice(), milkSale.getClientName(), milkSale.getSale_date());
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Update Milk Sale");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });
                        iv_view_details.setOnMouseClicked((MouseEvent event) -> {
                            MilkSale milkSale = MilkSaleTable.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/milk_sale_details.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                MilkSaleDetailsController controller = fxmlLoader.getController();
                                controller.fetchMilkSale(milkSale.getId(), milkSale.getQuantity(), milkSale.getPrice(), milkSale.getClientName(), milkSale.getSale_date());
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

    private static int COLUMNS_COUNT = 4;

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
                    Paragraph title = new Paragraph("Animal Sales List", FontFactory.getFont(FontFactory.COURIER_BOLD, 20, BaseColor.BLACK));
                    Paragraph text = new Paragraph("This is the list of the animal sales", FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK));

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
                table.addCell(new PdfPCell(new Paragraph("Animal ID", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Price", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Client", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Sale's date", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);

                //add padding to cells
                table.getDefaultCell().setPadding(3);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);


                //get employees displayed in table
                ObservableList<AnimalSale> animalsales = AnimalSalesTable.getItems();

                //get employee of each row
                //used a method in my updateEmplyeeController to get the employee of each row based on the cin
                CowSalesController controller = new CowSalesController();

                for (AnimalSale animalSale : animalsales) {
                    AnimalSale animalsa = controller.getSale(animalSale.getId());

                    table.addCell(new PdfPCell(new Paragraph(animalsa.getAnimalId()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(animalsa.getPrice())))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(animalsa.getClientName()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(animalsa.getSale_date())))).setPadding(5);

                }

                document.add(table);
                document.close();
                displayAlert("Success", "Animal Sales exported successfully", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
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
                for (MilkSale milk : milkSale) {
                    if (milk.getClientName().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(milk);
                    }
                }
                MilkSaleTable.setItems(filteredList);
            }
        });
    }

    void exportToPDF2() {
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
                    Paragraph title = new Paragraph("Milk Sales List", FontFactory.getFont(FontFactory.COURIER_BOLD, 20, BaseColor.BLACK));
                    Paragraph text = new Paragraph("This is the list of the milk sales", FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK));

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
                table.addCell(new PdfPCell(new Paragraph("Quantity", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Price", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Client", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Sale's date", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);

                //add padding to cells
                table.getDefaultCell().setPadding(3);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);


                //get employees displayed in table
                ObservableList<MilkSale> milkSales = MilkSaleTable.getItems();

                //get employee of each row
                //used a method in my updateEmplyeeController to get the employee of each row based on the cin
                MilkSalesController controller = new MilkSalesController();

                for (MilkSale MilkSale : milkSales) {
                    MilkSale milksa = controller.getSale(MilkSale.getId());

                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(milksa.getQuantity())))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(milksa.getPrice())))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(milksa.getClientName()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(milksa.getSale_date())))).setPadding(5);

                }

                document.add(table);
                document.close();
                displayAlert("Success", "Milk Sales exported successfully", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
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





