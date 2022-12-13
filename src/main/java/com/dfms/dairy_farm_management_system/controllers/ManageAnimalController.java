package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.AnimalDetailsController;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.NewAnimalController;
import com.dfms.dairy_farm_management_system.models.Animal;
import com.dfms.dairy_farm_management_system.models.Client;
import com.dfms.dairy_farm_management_system.models.Employee;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
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


public class ManageAnimalController implements Initializable {
    @FXML
    private TableView<Animal> animals;

    @FXML
    private TableColumn<Animal, String> colid;

    @FXML
    private TableColumn<Animal, String> coltype;

    @FXML
    private TableColumn<Animal, String> colrace;

    @FXML
    private TableColumn<Animal, Date> colbirth;

    @FXML
    private TableColumn<Animal, String> colroutine;
    @FXML
    private TableColumn<Animal, String> colactions;
    @FXML
    private ComboBox<String> export_combo;
    @FXML
    private TextField textField_search;


    private  Connection connection = getConnection();
    private PreparedStatement preparedStatement;
    private  Statement statement;
    private static int COLUMNS_COUNT = 6;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BasicConfigurator.configure();
        displayAnimals();
        ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");
        export_combo.setItems(list);
//        liveSearch();
        export_combo.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (t1.equals("PDF")) {
                exportToPDF();
            } else {
                exportToExcel();
            }
        });
    }

    public ObservableList<Animal> getAnimals() {
        ObservableList<Animal> listAnimal = FXCollections.observableArrayList();
        String select_query = "SELECT * from `animals`";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select_query);
            while(resultSet.next()) {
                Animal animal = new Animal();
                animal.setId(resultSet.getString("id"));
                animal.setBirth_date(resultSet.getDate("birth_date"));
                animal.setPurchase_date(resultSet.getDate("purchase_date"));
                animal.setRoutineId(resultSet.getInt("routine"));
                animal.setRaceId(resultSet.getInt("race"));
                animal.setType(resultSet.getString("type"));
                animal.setCreated_at(resultSet.getTimestamp("created_at"));
                animal.setUpdated_at(resultSet.getTimestamp("updated_at"));

                listAnimal.add(animal);
            }
        } catch (SQLException e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);

        }
        return listAnimal;
    }
    public void displayAnimals() {
        ObservableList<Animal> list = getAnimals();
        colid.setCellValueFactory(new PropertyValueFactory<Animal, String>("id"));
        coltype.setCellValueFactory(new PropertyValueFactory<Animal, String>("type"));
        colbirth.setCellValueFactory(new PropertyValueFactory<Animal, Date>("birth_date"));
        colrace.setCellValueFactory(new PropertyValueFactory<Animal, String>("raceName"));
        colroutine.setCellValueFactory(new PropertyValueFactory<Animal, String>("routineName"));

        Callback<TableColumn<Animal, String>, TableCell<Animal, String>> cellFoctory = (TableColumn<Animal, String> param) -> {
            // make cell containing buttons
            final TableCell<Animal, String> cell = new TableCell<>() {

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
                            alert.setHeaderText("Are you sure you want to delete this Cow?");
                            Animal animal = animals.getSelectionModel().getSelectedItem();
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                try {

                                    if(animal.delete()){
                                    Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                                    alertInfo.setTitle("Delete Cow");
                                    alertInfo.setHeaderText("Cow deleted successfully");
                                    alertInfo.showAndWait();
                                    refreshTableAnimal();}
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        iv_view_details.setOnMouseClicked((MouseEvent event) -> {
                            Animal animal = animals.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/animal_details.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                AnimalDetailsController controller = fxmlLoader.getController();
                                controller.fetchAnimal(animal.getId(), animal.getRaceName(), animal.getBirth_date(), animal.getRoutineName(), animal.getPurchase_date(), animal.getType());
                            } catch (IOException e) {
                                e.printStackTrace();
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                            }
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Animal Details");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });

                        iv_edit.setOnMouseClicked((MouseEvent event) -> {
                            Animal animal = animals.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/add_new_animal.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            NewAnimalController newAnimalController = fxmlLoader.getController();
                            newAnimalController.setUpdate(true);
                            newAnimalController.fetchAnimal(animal.getId(), animal.getRaceName(), animal.getBirth_date().toLocalDate(), animal.getRoutineName(), animal.getPurchase_date().toLocalDate(), animal.getType());
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Update Animal");
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

        colactions.setCellFactory(cellFoctory);
        animals.setItems(list);
    }
    public void liveSearch() {
        textField_search.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                refreshTableAnimal();
            } else {
                ObservableList<Animal> filteredList = FXCollections.observableArrayList();
                ObservableList<Animal> listanimals = getAnimals();
                for (Animal animal : listanimals) {
                    if (animal.getType().toLowerCase().contains(newValue.toLowerCase()) || animal.getRaceName().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(animal);
                    }else{
                        return;
                    }
                }
                animals.setItems(filteredList);
            }
        });
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
                    Paragraph title = new Paragraph("Animal List", FontFactory.getFont(FontFactory.COURIER_BOLD, 20, BaseColor.BLACK));
                    Paragraph text = new Paragraph("This is the list of the animals", FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK));
                    title.setAlignment(Element.ALIGN_CENTER);
                    text.setAlignment(Element.ALIGN_CENTER);
                    title.setSpacingAfter(30);
                    text.setSpacingAfter(30);
                    document.add(title);
                    document.add(text);

                } catch (Exception e) {
                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }
                PdfPTable table = new PdfPTable(COLUMNS_COUNT);
                table.setWidthPercentage(100);
                table.setSpacingBefore(11f);
                table.setSpacingAfter(11f);
                float[] colWidth = new float[COLUMNS_COUNT];
                for (int i = 0; i < COLUMNS_COUNT; i++) {
                    colWidth[i] = 2f;
                }

                table.addCell(new PdfPCell(new Paragraph("Cow ID", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Race", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Birth Date", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Type", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Routine", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);
                table.addCell(new PdfPCell(new Paragraph("Purchase Date", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)))).setPadding(5);

                //add padding to cells
                table.getDefaultCell().setPadding(3);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

                //get animals displayed in table
                ObservableList<Animal> list_animals = animals.getItems();

                NewAnimalController controller = new NewAnimalController();

                for (Animal animal : list_animals) {
                    Animal an = controller.getAnimal(animal.getId());

                    table.addCell(new PdfPCell(new Paragraph(an.getId()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(an.getRaceName()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(an.getBirth_date())))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(an.getType()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(an.getRoutineName()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(an.getPurchase_date())))).setPadding(5);

                }

                    document.add(table);
                    document.close();
                    displayAlert("Success", "Animals exported successfully", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }

        }
    }

    void exportToExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"), new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Animals");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Cow ID");
                header.createCell(1).setCellValue("Race");
                header.createCell(2).setCellValue("Birth Date");
                header.createCell(3).setCellValue("Type");
                header.createCell(4).setCellValue("Routine");
                header.createCell(5).setCellValue("Purchase Date");

                ObservableList<Animal> list_animals = animals.getItems();

                NewAnimalController controller = new NewAnimalController();

                for (Animal animal : list_animals) {
                    Animal an = controller.getAnimal(animal.getId());
                    Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                    row.createCell(0).setCellValue(animal.getId());
                    row.createCell(1).setCellValue(animal.getRaceName());
                    row.createCell(2).setCellValue(animal.getBirth_date());
                    row.createCell(3).setCellValue(animal.getType());
                    row.createCell(4).setCellValue(animal.getRoutineName());
                    row.createCell(5).setCellValue(animal.getPurchase_date());
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                workbook.write(fileOutputStream);
                workbook.close();

                displayAlert("Success", "Animals exported successfully", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
    public void liveSearchClient() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        ObservableList<Animal> listAnimal = getAnimals();
        FilteredList<Animal> filteredData = new FilteredList<>(listAnimal, p->true);

        // 2. Set the filter Predicate whenever the filter changes.
        textField_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(animal -> {
                // If filter text is empty, display all clients.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (animal.getType().toLowerCase().contains(newValue.toLowerCase()) || animal.getRaceName().toLowerCase().contains(newValue.toLowerCase())) {
                    return true; // Filter matches first name.
                }return false;
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Animal> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(animals.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        animals.setItems(sortedData);
    }

    public void refreshTable(MouseEvent mouseEvent) {
        refreshTableAnimal();
    }
    public void refreshTableAnimal() {
        animals.getItems().clear();
        displayAnimals();
    }
    @FXML
    void openAddNewRace(MouseEvent event) throws IOException {
        openNewWindow("Add New Race", "add_new_race");
    }
    public void openAddNewAnimal(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add New Animal", "add_new_animal");
    }
    @FXML
    void search_animal(MouseEvent event) {
        liveSearchClient();
    }
}