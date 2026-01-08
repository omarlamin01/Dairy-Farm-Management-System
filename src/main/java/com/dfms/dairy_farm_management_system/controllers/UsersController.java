package com.dfms.dairy_farm_management_system.controllers;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.EmployeeDetailsController;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.UpdateEmployeeController;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.UpdateUserController;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.UserDetailsController;
import com.dfms.dairy_farm_management_system.models.Employee;
import com.dfms.dairy_farm_management_system.models.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
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

public class UsersController implements Initializable {

    private static final int COLUMNS_COUNT = 9;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //this line of code is so important for the export !!!!
        BasicConfigurator.configure();

        ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");
        export_combo.setItems(list);
        displayUsers();
        liveSearch(search_user_input, users_table);

        //check what user select in the combo box
        export_combo
            .getSelectionModel()
            .selectedItemProperty()
            .addListener((observableValue, s, t1) -> {
                if (t1.equals("PDF")) {
                    exportToPDF();
                } else {
                    exportToExcel();
                }
            });
    }

    private Statement statement;
    private PreparedStatement preparedStatement;
    private Connection connection = getConnection();

    @FXML
    private TableView<User> users_table;

    @FXML
    private TableColumn<User, String> actions_col;

    @FXML
    private TableColumn<User, String> col_id;

    @FXML
    private TableColumn<User, String> email_col;

    @FXML
    private TableColumn<User, String> first_name_col;

    @FXML
    private TableColumn<User, String> last_name_col;

    @FXML
    private TableColumn<User, String> role_col;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_user_input;

    @FXML
    private ComboBox<String> export_combo;

    @FXML
    private Button openAddNewEmployeeBtn;

    @FXML
    private Button new_role_btn;

    //get all the employees
    public ObservableList<User> getUsers() {
        ObservableList<User> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM `users`";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setCin(resultSet.getString("cin"));
                user.setEmail(resultSet.getString("email"));
                user.setGender(resultSet.getString("gender"));
                user.setPhone(resultSet.getString("phone"));
                user.setSalary(resultSet.getInt("salary"));
                user.setAdress(resultSet.getString("address"));
                user.setRole(resultSet.getInt("role"));
                user.setCreatedAt(resultSet.getTimestamp("created_at"));
                list.add(user);
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        return list;
    }

    //display all the employees in the table
    public void displayUsers() {
        ObservableList<User> users = getUsers();
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        first_name_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        last_name_col.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        role_col.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        Callback<TableColumn<User, String>, TableCell<User, String>> cellFoctory = (TableColumn<
            User,
            String
        > param) -> {
            final TableCell<User, String> cell = new TableCell<User, String>() {
                Image edit_img = new Image(getClass().getResourceAsStream("/images/edit.png"));
                Image delete_img = new Image(getClass().getResourceAsStream("/images/delete.png"));
                Image view_details_img = new Image(getClass().getResourceAsStream("/images/eye.png"));

                Button edit_btn = new Button();
                Button delete_btn = new Button();
                Button view_details_btn = new Button();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        ImageView iv_view_details = new ImageView();
                        iv_view_details.setImage(view_details_img);
                        iv_view_details.setPreserveRatio(true);
                        iv_view_details.setSmooth(true);
                        iv_view_details.setCache(true);

                        view_details_btn.setGraphic(iv_view_details);
                        view_details_btn.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");

                        ImageView iv_edit = new ImageView();
                        iv_edit.setImage(edit_img);
                        iv_edit.setPreserveRatio(true);
                        iv_edit.setSmooth(true);
                        iv_edit.setCache(true);

                        edit_btn.setGraphic(iv_edit);
                        edit_btn.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");

                        ImageView iv_delete = new ImageView();
                        iv_delete.setImage(delete_img);
                        iv_delete.setPreserveRatio(true);
                        iv_delete.setSmooth(true);
                        iv_delete.setCache(true);

                        delete_btn.setGraphic(iv_delete);
                        delete_btn.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");

                        HBox managebtn = new HBox(view_details_btn, edit_btn, delete_btn);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(iv_view_details, new Insets(1, 1, 0, 3));
                        HBox.setMargin(iv_delete, new Insets(1, 1, 0, 3));
                        HBox.setMargin(iv_edit, new Insets(1, 1, 0, 3));

                        setGraphic(managebtn);

                        //delete user
                        delete_btn.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete user");
                            alert.setHeaderText("Are you sure you want to delete this user?");
                            User user = users.get(getRowIndex(event));
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                try {
                                    user.delete();
                                    displayUsers();
                                } catch (Exception e) {
                                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                }
                            }
                        });

                        //update user
                        edit_btn.setOnMouseClicked((MouseEvent event) -> {
                            User user = users.get(getRowIndex(event));
                            FXMLLoader fxmlLoader = new FXMLLoader(
                                Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/update_user.fxml")
                            );
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                UpdateUserController controller = fxmlLoader.getController();
                                controller.initData(user);
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Update Employee");
                            stage.setResizable(false);
                            stage.setScene(scene);
                            centerScreen(stage);
                            stage.show();
                        });

                        //view employee details
                        view_details_btn.setOnMouseClicked((MouseEvent event) -> {
                            User user = users.get(getRowIndex(event));
                            FXMLLoader fxmlLoader = new FXMLLoader(
                                Main.class.getResource(
                                    "/com/dfms/dairy_farm_management_system/popups/user_details.fxml"
                                )
                            );
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                UserDetailsController controller = fxmlLoader.getController();
                                controller.initData(user);
                            } catch (IOException e) {
                                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                e.printStackTrace();
                            }
                            Stage stage = new Stage();
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Employee Details");
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
        users_table.setItems(users);
    }

    public void openAddUser(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add user", "add_new_user");
    }

    @FXML
    public void refreshTable() {
        users_table.getItems().clear();
        displayUsers();
    }

    public void liveSearch(TextField search_input, TableView table) {
        search_input
            .textProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (newValue == null || newValue.isEmpty()) {
                    refreshTable();
                } else {
                    ObservableList<User> filteredList = FXCollections.observableArrayList();
                    ObservableList<User> users = getUsers();
                    for (User user : users) {
                        if (
                            user.getFirstName().toLowerCase().contains(newValue.toLowerCase()) ||
                            user.getLastName().toLowerCase().contains(newValue.toLowerCase())
                        ) {
                            filteredList.add(user);
                        }
                    }
                    table.setItems(filteredList);
                }
            });
    }

    @FXML
    void searchUser(MouseEvent event) {
        liveSearch(this.search_user_input, users_table);
    }

    void exportToExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser
            .getExtensionFilters()
            .addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"),
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
            );
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Employees");
                Row header = sheet.createRow(1);
                header.createCell(1).setCellValue("First Name");
                header.createCell(2).setCellValue("Last Name");
                header.createCell(3).setCellValue("Email");
                header.createCell(4).setCellValue("Phone");
                header.createCell(5).setCellValue("Address");
                header.createCell(6).setCellValue("CIN");
                header.createCell(7).setCellValue("Gender");
                header.createCell(8).setCellValue("Salary");

                //get users displayed in table
                ObservableList<User> users = users_table.getItems();

                //get employee of each row
                //used a method in my updateEmplyeeController to get the employee of each row based on the cin
                UpdateEmployeeController controller = new UpdateEmployeeController();

                for (Employee employee : users) {
                    Employee emp = controller.getEmployee(employee.getCin());
                    Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                    row.createCell(1).setCellValue(emp.getFirstName());
                    row.createCell(2).setCellValue(emp.getLastName());
                    row.createCell(3).setCellValue(emp.getEmail());
                    row.createCell(4).setCellValue(emp.getPhone());
                    row.createCell(5).setCellValue(emp.getAddress());
                    row.createCell(6).setCellValue(emp.getCin());
                    if (emp.getGender().equals("M")) {
                        row.createCell(7).setCellValue("Male");
                    } else {
                        row.createCell(7).setCellValue("Female");
                    }
                    row.createCell(8).setCellValue(String.valueOf(emp.getSalary()));
                }

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                workbook.write(fileOutputStream);
                workbook.close();

                displayAlert("Success", "Employees exported successfully", Alert.AlertType.INFORMATION);
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
                    Paragraph title = new Paragraph(
                        "Employees List",
                        FontFactory.getFont(FontFactory.COURIER_BOLD, 20, BaseColor.BLACK)
                    );
                    Paragraph text = new Paragraph(
                        "This is the list of the users",
                        FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK)
                    );

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
                table
                    .addCell(
                        new PdfPCell(
                            new Paragraph(
                                "First Name",
                                FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)
                            )
                        )
                    )
                    .setPadding(5);
                table
                    .addCell(
                        new PdfPCell(
                            new Paragraph(
                                "Last Name",
                                FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)
                            )
                        )
                    )
                    .setPadding(5);
                table
                    .addCell(
                        new PdfPCell(
                            new Paragraph("Email", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK))
                        )
                    )
                    .setPadding(5);
                table
                    .addCell(
                        new PdfPCell(
                            new Paragraph("Phone", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK))
                        )
                    )
                    .setPadding(5);
                table
                    .addCell(
                        new PdfPCell(
                            new Paragraph("Address", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK))
                        )
                    )
                    .setPadding(5);
                table
                    .addCell(
                        new PdfPCell(
                            new Paragraph("CIN", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK))
                        )
                    )
                    .setPadding(5);
                table
                    .addCell(
                        new PdfPCell(
                            new Paragraph("Gender", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK))
                        )
                    )
                    .setPadding(5);
                table
                    .addCell(
                        new PdfPCell(
                            new Paragraph(
                                "Hire Date",
                                FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK)
                            )
                        )
                    )
                    .setPadding(5);
                table
                    .addCell(
                        new PdfPCell(
                            new Paragraph("Salary", FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK))
                        )
                    )
                    .setPadding(5);

                //add padding to cells
                table.getDefaultCell().setPadding(3);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

                //get users displayed in table
                ObservableList<User> users = users_table.getItems();

                //get employee of each row
                //used a method in my updateEmplyeeController to get the employee of each row based on the cin
                UpdateEmployeeController controller = new UpdateEmployeeController();

                for (Employee employee : users) {
                    Employee emp = controller.getEmployee(employee.getCin());

                    table.addCell(new PdfPCell(new Paragraph(emp.getFirstName()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(emp.getLastName()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(emp.getEmail()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(emp.getPhone()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(emp.getAddress()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(emp.getCin()))).setPadding(5);
                    if (emp.getGender().equals("M")) {
                        table.addCell(new PdfPCell(new Paragraph("Male"))).setPadding(5);
                    } else {
                        table.addCell(new PdfPCell(new Paragraph("Female"))).setPadding(5);
                    }
                    table.addCell(new PdfPCell(new Paragraph(emp.getHireDate().toString()))).setPadding(5);
                    table.addCell(new PdfPCell(new Paragraph(String.valueOf(emp.getSalary())))).setPadding(5);
                }

                document.add(table);
                document.close();
                displayAlert("Success", "Employees exported successfully", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void openNewRole(MouseEvent event) throws IOException {
        openNewWindow("Add new role", "add_new_role");
    }
}
