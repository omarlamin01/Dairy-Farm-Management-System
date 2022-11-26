package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.AnimalDetailsController;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.MilkCollectionlDetailsController;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.NewAnimalController;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.NewMilkCollectionController;
import com.dfms.dairy_farm_management_system.models.Animal;
import com.dfms.dairy_farm_management_system.models.Employee;
import com.dfms.dairy_farm_management_system.models.MilkCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.geometry.Insets;
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
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class MilkCollectionController implements Initializable {
    MilkCollection mc;
    @FXML
    private Button refresh_table_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BasicConfigurator.configure();

        ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");
        combo.setItems(list);
        combo.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (t1.equals("PDF")) {
                exportToPDF(MilkCollectionTable);
            } else {
                exportToExcel();
            }
        });
        try {
            afficher();
            liveSearch(search_input, MilkCollectionTable);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private ComboBox<String> combo;
    @FXML
    private TableView<MilkCollection> MilkCollectionTable;

    @FXML
    private TableColumn<MilkCollection, String> actions_col;
    @FXML
    private TableColumn<MilkCollection, Date> date_col;

    @FXML
    private TableColumn<MilkCollection, String> id_col;

    @FXML
    private TableColumn<MilkCollection, Float> milk_col;

    @FXML
    private Button openAddNewMilkCollectionBtn;

    @FXML
    private TableColumn<MilkCollection, String> period_col;

    @FXML
    private Button search_button;

    @FXML
    private TextField search_input;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    ObservableList<MilkCollection> list = FXCollections.observableArrayList();

    public ObservableList<MilkCollection> getMilkCollection() throws SQLException, ClassNotFoundException {
        ObservableList<MilkCollection> list = FXCollections.observableArrayList();

        String select_query = "SELECT  mc.id, mc.cow_id, quantity ,period,mc.created_at from  milk_collections mc ,animals a where mc.cow_id= a.id and a.type='cow' ";

        statement = DBConfig.getConnection().prepareStatement(select_query);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            MilkCollection milkCollection = new MilkCollection();
            milkCollection.setId(resultSet.getInt("id"));
            milkCollection.setCow_id(resultSet.getString("cow_id"));
            milkCollection.setQuantity(resultSet.getFloat("quantity"));
            milkCollection.setPeriod(resultSet.getString("period"));
            milkCollection.setCreated_at(resultSet.getTimestamp("created_at"));


            list.add(milkCollection);
        }
        return list;
    }

    public void refreshTableMilkCollection() throws SQLException {

        MilkCollectionTable.getItems().clear();

        try {
            afficher();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void afficher() throws SQLException, ClassNotFoundException {
        ObservableList<MilkCollection> list = getMilkCollection();
        id_col.setCellValueFactory(new PropertyValueFactory<MilkCollection, String>("cow_id"));
        milk_col.setCellValueFactory(new PropertyValueFactory<MilkCollection, Float>("quantity"));
        period_col.setCellValueFactory(new PropertyValueFactory<MilkCollection, String>("period"));
        date_col.setCellValueFactory(new PropertyValueFactory<MilkCollection, Date>("created_at"));


        Callback<TableColumn<MilkCollection, String>, TableCell<MilkCollection, String>> cellFoctory = (TableColumn<MilkCollection, String> param) -> {
            // make cell containing buttons
            final TableCell<MilkCollection, String> cell = new TableCell<MilkCollection, String>() {

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

                            MilkCollection mc = MilkCollectionTable.getSelectionModel().getSelectedItem();
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

                        });
                        btnEdit.setOnMouseClicked((MouseEvent event) -> {

                            MilkCollection milkcollection = MilkCollectionTable.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/add_new_milk_collection.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            NewMilkCollectionController newMilkCollectionController = fxmlLoader.getController();
                            newMilkCollectionController.setUpdate(true);
                            newMilkCollectionController.fetchMilkCollection( milkcollection.getId(),milkcollection.getCow_id(), milkcollection.getPeriod(), milkcollection.getQuantity());
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Update MilkCollection");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });
                        btnViewDetail.setOnMouseClicked((MouseEvent event) -> {
                            MilkCollection mc = MilkCollectionTable.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/milkcollection_details.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                MilkCollectionlDetailsController controller = fxmlLoader.getController();
                                controller.fetchMilkCollection( mc.getId(),mc.getCow_id(), mc.getPeriod(), mc.getQuantity(), mc.getCreated_at());
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Milk Collection  Details");
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

        actions_col.setCellFactory(cellFoctory);
        MilkCollectionTable.setItems(list);

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
                Sheet sheet = workbook.createSheet("Milk Collection");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Milk Collection ID");
                header.createCell(1).setCellValue("Cow ID");
                header.createCell(2).setCellValue("Milk Quantity");
                header.createCell(3).setCellValue("Collection Period");
                header.createCell(4).setCellValue("Collection Date");



                //get all employees from database
                String query = "SELECT * FROM `milk_collections`";
                try {

                    statemeent = connection.createStatement();
                    ResultSet rs = statemeent.executeQuery(query);
                    while (rs.next()) {
                        int rowNum = rs.getRow();
                        Row row = sheet.createRow(rowNum);
                        row.createCell(0).setCellValue(rs.getString("id"));
                        row.createCell(1).setCellValue(rs.getString("cow_id"));
                        row.createCell(2).setCellValue(rs.getString("quantity"));
                        row.createCell(3).setCellValue(rs.getString("period"));
                        row.createCell(4).setCellValue(rs.getString("created_at"));

                    }
                } catch (Exception e) {
                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }


                FileOutputStream fileOutputStream = new FileOutputStream(file);
                workbook.write(fileOutputStream);
                workbook.close();

                displayAlert("Success", "Milk Collection exported successfully", Alert.AlertType.INFORMATION);
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

    public void liveSearch(TextField search_input, TableView table) {
        search_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                try {
                    refreshTableMilkCollection();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                ObservableList<MilkCollection> filteredList = FXCollections.observableArrayList();
                ObservableList<MilkCollection> milkCollections = null;
                try {
                    milkCollections = getMilkCollection();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                for (MilkCollection milkCollection : milkCollections) {
                    if (milkCollection.getPeriod().toLowerCase().contains(newValue.toLowerCase()) || milkCollection.getCow_id().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(milkCollection);
                    }
                }
                MilkCollectionTable.setItems(filteredList);
            }
        });
    }


    @FXML
    void openAddNewMilkCollection(MouseEvent event) throws IOException {
        openNewWindow("Add Milk Collection", "add_new_milk_collection");
    }

    private Connection con = DBConfig.getConnection();
    private Statement stt;

    @FXML
    void refreshTable(MouseEvent event) throws SQLException {

            refreshTableMilkCollection();

    }


}