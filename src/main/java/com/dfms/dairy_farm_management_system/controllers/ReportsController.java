package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.models.Purchase;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.log4j.BasicConfigurator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.disconnect;
import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class ReportsController implements Initializable {
//  milk collection part
    @FXML
    private DatePicker from_date_picker;

    @FXML
    private DatePicker to_date_picker;

    @FXML
    private Button search_btn;

    @FXML
    private VBox milk_collection_results_area;

    LocalDate start;
    LocalDate end;
    //Purchase items
    @FXML
    private DatePicker to_date;
    @FXML
    private DatePicker from_date;
    @FXML
    private Button btn_serach;
    @FXML
    private VBox purchase_results_area;
    LocalDate start1;
    LocalDate end1;
    public class DailyMilkCollection {
        private Date collection_date;
        private float total_day_collection;
        private float morning_collection;
        private float evening_collection;

        public Date getCollection_date() {
            return collection_date;
        }

        public void setCollection_date(Date collection_date) {
            this.collection_date = collection_date;
        }

        public float getTotal_day_collection() {
            return total_day_collection;
        }

        public void setTotal_day_collection(float total_day_collection) {
            this.total_day_collection = total_day_collection;
        }

        public float getMorning_collection() {
            return morning_collection;
        }

        public void setMorning_collection(float morning_collection) {
            this.morning_collection = morning_collection;
            total_day_collection += morning_collection;
        }

        public float getEvening_collection() {
            return evening_collection;
        }

        public void setEvening_collection(float evening_collection) {
            this.evening_collection = evening_collection;
            total_day_collection += evening_collection;
        }

        @Override
        public String toString() {
            return "DailyMilkCollection{" +
                    "collection_date=" + collection_date +
                    ", total_day_collection=" + total_day_collection +
                    ", morning_collection=" + morning_collection +
                    ", evening_collection=" + evening_collection +
                    '}';
        }
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BasicConfigurator.configure();
        initView();
        initView2();
        search_btn.setOnMouseClicked(event -> { displayData(); });
        btn_serach.setOnMouseClicked(event -> { displayData2(); });
    }

    private void initView() {
        to_date_picker.setDayCellFactory(d -> new DateCell() {
            /**
             * {@inheritDoc}
             *
             * @param item
             * @param empty
             */
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(LocalDate.now()));
            }
        });

        from_date_picker.setDayCellFactory(d -> new DateCell() {
            /**
             * {@inheritDoc}
             *
             * @param item
             * @param empty
             */
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(LocalDate.now()));
            }
        });

        to_date_picker.setOnAction(event -> {
            LocalDate date = to_date_picker.getValue();
            end = to_date_picker.getValue();
            from_date_picker.setDayCellFactory(d -> new DateCell() {
                /**
                 * {@inheritDoc}
                 *
                 * @param item
                 * @param empty
                 */
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(item.isAfter(date));
                }
            });
        });

        from_date_picker.setOnAction(event -> {
            LocalDate date = from_date_picker.getValue();
            start = from_date_picker.getValue();
            to_date_picker.setDayCellFactory(d -> new DateCell() {
                /**
                 * {@inheritDoc}
                 *
                 * @param item
                 * @param empty
                 */
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(item.isBefore(date) || item.isAfter(LocalDate.now()));
                }
            });
        });

    }

    private ObservableList<DailyMilkCollection> getData() {
        LocalDate min_date = from_date_picker.getValue();
        LocalDate max_date = to_date_picker.getValue();

        ObservableList<DailyMilkCollection> data = FXCollections.observableArrayList();

        try {
            String query =
                    "SELECT `created_at` AS collection_date, " +
                    "sum(`quantity`) AS morning_collection " +
                    "FROM `milk_collections` WHERE `period` = ? AND `created_at` <= ? AND `created_at` >= ? " +
                    "GROUP BY date(created_at) " +
                    "ORDER BY `created_at` DESC";

            PreparedStatement statement = getConnection().prepareStatement(query);

            statement.setString(1, "morning");
            statement.setTimestamp(2, Timestamp.valueOf(max_date.atTime(23, 59, 59)));
            statement.setTimestamp(3, Timestamp.valueOf(min_date.atStartOfDay()));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DailyMilkCollection dailyMilkCollection = new DailyMilkCollection();

                dailyMilkCollection.setCollection_date(resultSet.getDate("collection_date"));
                dailyMilkCollection.setMorning_collection(resultSet.getFloat("morning_collection"));
                try {
                    String query1 = "SELECT sum(`quantity`) AS evening_collection " +
                            "FROM `milk_collections` WHERE `period` = ? AND date(`created_at`) = ? ";

                    PreparedStatement preparedStatement = getConnection().prepareStatement(query1);

                    preparedStatement.setString(1, "evening");
                    preparedStatement.setDate(2, dailyMilkCollection.getCollection_date());

                    ResultSet resultSet1 = preparedStatement.executeQuery();
                    if (resultSet1.next()) {
                        dailyMilkCollection.setEvening_collection(resultSet1.getFloat("evening_collection"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    disconnect();
                }
                data.add(dailyMilkCollection);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        } finally {
            disconnect();
        }
        return data;
    }

    private void displayData() {
        milk_collection_results_area.getChildren().clear();
        milk_collection_results_area.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        ComboBox<String> export_combo = new ComboBox<>(FXCollections.observableArrayList("Excel", "PDF"));
        export_combo.setPromptText("Export");
        export_combo.setPadding(new Insets(8));
        export_combo.getStyleClass().add("combo_box");

        //check what user select in the combo box
        export_combo.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (t1.equals("PDF")) {
                exportToPDF(start, end);
            } else {
                exportToExcel();
            }
        });

        TableColumn<DailyMilkCollection, String> collection_date = new TableColumn<>("Date");
        collection_date.setCellValueFactory(new PropertyValueFactory<DailyMilkCollection, String>("collection_date"));
        collection_date.setPrefWidth(180);

        TableColumn<DailyMilkCollection, String> total_day_collection = new TableColumn<>("Total day collection");
        total_day_collection.setCellValueFactory(new PropertyValueFactory<DailyMilkCollection, String>("total_day_collection"));
        total_day_collection.setPrefWidth(180);

        TableColumn<DailyMilkCollection, String> morning_collection = new TableColumn<>("Morning collection");
        morning_collection.setCellValueFactory(new PropertyValueFactory<DailyMilkCollection, String>("morning_collection"));
        morning_collection.setPrefWidth(180);

        TableColumn<DailyMilkCollection, String> evening_collection = new TableColumn<>("Evening collection");
        evening_collection.setCellValueFactory(new PropertyValueFactory<DailyMilkCollection, String>("evening_collection"));
        evening_collection.setPrefWidth(180);


        TableView<DailyMilkCollection> data_table = new TableView<>();
        data_table.getColumns().addAll(collection_date, total_day_collection, morning_collection, evening_collection);
        data_table.getStyleClass().add("table-view");
        data_table.setItems(getData());
        data_table.setPrefSize(600, 400);

        milk_collection_results_area.getChildren().addAll(export_combo, data_table);
    }

    private void exportToExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"), new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Milk collection");
                Row header = sheet.createRow(1);

                header.createCell(1).setCellValue("Date");
                header.createCell(2).setCellValue("Total day collection");
                header.createCell(3).setCellValue("Morning collection");
                header.createCell(4).setCellValue("Evening collection");

                for (DailyMilkCollection collection : getData()) {
                    Row row = sheet.createRow(sheet.getLastRowNum() + 1);

                    row.createCell(1).setCellValue(collection.getCollection_date().toString());
                    row.createCell(2).setCellValue(collection.getTotal_day_collection());
                    row.createCell(3).setCellValue(collection.getMorning_collection());
                    row.createCell(4).setCellValue(collection.getEvening_collection());
                }

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                workbook.write(fileOutputStream);
                workbook.close();

                displayAlert("Success", "Report exported successfully", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void exportToPDF(LocalDate start, LocalDate end) {
        int COLUMNS_COUNT = 4;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                Document document = new Document();
                //change document orientation to landscape
                document.setPageSize(PageSize.A4);

                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                try {
                    Paragraph title = new Paragraph("Daily milk collection", FontFactory.getFont(FontFactory.COURIER_BOLD, 20, BaseColor.BLACK));
                    Paragraph text = new Paragraph("The milk collection between " + start.toString() + " and " + end.toString() + ".", FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK));

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
                table.addCell(new PdfPCell(new Paragraph("Date", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Total day collection", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Morning collection", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Evening collection", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);

                //add padding to cells
                table.getDefaultCell().setPadding(3);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

                for (DailyMilkCollection collection : getData()) {
                    table.addCell(new PdfPCell(new Paragraph(collection.getCollection_date().toString()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(collection.getTotal_day_collection())))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(collection.getMorning_collection())))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(collection.getEvening_collection())))).setPadding(5);
                }

                document.add(table);
                document.close();
                displayAlert("Success", "Employees exported successfully", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
    //Purchase
    private void initView2() {
        to_date.setDayCellFactory(d -> new DateCell() {
            /**
             * {@inheritDoc}
             *
             * @param item
             * @param empty
             */
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(LocalDate.now()));
            }
        });

        from_date.setDayCellFactory(d -> new DateCell() {
            /**
             * {@inheritDoc}
             *
             * @param item
             * @param empty
             */
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(LocalDate.now()));
            }
        });

        to_date.setOnAction(event -> {
            LocalDate date = to_date.getValue();
            end1= to_date.getValue();
            from_date.setDayCellFactory(d -> new DateCell() {
                /**
                 * {@inheritDoc}
                 *
                 * @param item
                 * @param empty
                 */
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(item.isAfter(date));
                }
            });
        });

        from_date.setOnAction(event -> {
            LocalDate date = from_date.getValue();
            start1 = from_date.getValue();
            to_date.setDayCellFactory(d -> new DateCell() {
                /**
                 * {@inheritDoc}
                 *
                 * @param item
                 * @param empty
                 */
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(item.isBefore(date) || item.isAfter(LocalDate.now()));
                }
            });
        });

    }
    private ObservableList<Purchase> getDataPurchase() {
        LocalDate min_date = from_date.getValue();
        LocalDate max_date = to_date.getValue();

        ObservableList<Purchase> data = FXCollections.observableArrayList();

        try {
            String query =
                   " SELECT * FROM `purchases` WHERE  `purchase_date` <= ? AND `purchase_date` >= ? " +
                            "GROUP BY date(purchase_date) " +
                            "ORDER BY `purchase_date` DESC";

            PreparedStatement statement = getConnection().prepareStatement(query);


            statement.setTimestamp(1, Timestamp.valueOf(max_date.atTime(23, 59, 59)));
            statement.setTimestamp(2, Timestamp.valueOf(min_date.atStartOfDay()));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Purchase purchase = new Purchase();
                purchase.setPurchase_date(resultSet.getDate("purchase_date"));
                purchase.setQuantity(resultSet.getFloat("quantity"));
                purchase.setSupplier_id(resultSet.getInt("supplier_id"));
                purchase.setStock_id(resultSet.getInt("stock_id"));
                data.add(purchase);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        } finally {
            disconnect();
        }
        return data;
    }

    private void displayData2() {
        purchase_results_area.getChildren().clear();
        purchase_results_area.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        ComboBox<String> export_combo = new ComboBox<>(FXCollections.observableArrayList("Excel", "PDF"));
        export_combo.setPromptText("Export");
        export_combo.setPadding(new Insets(8));
        export_combo.getStyleClass().add("combo_box");

        //check what user select in the combo box
        export_combo.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (t1.equals("PDF")) {
                exportToPDF2(start, end);
            } else {
                exportToExcel2();
            }
        });

        TableColumn<Purchase, String> purchase_date = new TableColumn<>("Date");
        purchase_date.setCellValueFactory(new PropertyValueFactory<Purchase, String>("purchase_date"));
        purchase_date.setPrefWidth(180);

        TableColumn<Purchase, String> product = new TableColumn<>("Product");
        product.setCellValueFactory(new PropertyValueFactory<Purchase, String>("product_name"));
        product.setPrefWidth(180);

        TableColumn<Purchase, Float> quantity = new TableColumn<>("Quantity");
        quantity.setCellValueFactory(new PropertyValueFactory<Purchase, Float>("quantity"));
        quantity.setPrefWidth(180);

        TableColumn<Purchase, String> supplier = new TableColumn<>("Supplier");
        supplier.setCellValueFactory(new PropertyValueFactory<Purchase, String>("supplier_name"));
        supplier.setPrefWidth(180);


        TableView<Purchase> data_table = new TableView<>();
        data_table.getColumns().addAll(purchase_date, product, quantity, supplier);
        data_table.getStyleClass().add("table-view");
        data_table.setItems(getDataPurchase());
        data_table.setPrefSize(600, 400);

        purchase_results_area.getChildren().addAll(export_combo, data_table);
    }

    private void exportToExcel2() {
    }

    private void exportToPDF2(LocalDate start, LocalDate end) {

    }
}


