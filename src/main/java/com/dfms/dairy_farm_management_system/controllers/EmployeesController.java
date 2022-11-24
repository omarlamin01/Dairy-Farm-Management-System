package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.EmployeeDetailsController;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.UpdateEmployeeController;
import com.dfms.dairy_farm_management_system.models.Employee;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.*;

public class EmployeesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BasicConfigurator.configure();
        ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");
        export_combo.setItems(list);
        displayEmployees();
        liveSearch(search_employee_input, employees_table);

        //check what user select in the combo box
        export_combo.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
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
    private TableView<Employee> employees_table;

    @FXML
    private TableColumn<Employee, String> actions_col;

    @FXML
    private TableColumn<Employee, String> col_cin;

    @FXML
    private TableColumn<Employee, String> email_col;

    @FXML
    private TableColumn<Employee, String> first_name_col;

    @FXML
    private TableColumn<Employee, String> last_name_col;

    @FXML
    private TableColumn<Employee, String> salary_col;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_employee_input;
    @FXML
    private ComboBox<String> export_combo;

    @FXML
    private Button openAddNewEmployeeBtn;

    //get all the employees
    public ObservableList<Employee> getEmployees() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        String query = "SELECT * FROM `employees`";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setCin(rs.getString("cin"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setEmail(rs.getString("email"));
                employee.setGender(rs.getString("gender"));
                employee.setSalary(rs.getInt("salary"));
                employees.add(employee);
            }
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        return employees;
    }

    //display all the employees in the table
    public void displayEmployees() {
        ObservableList<Employee> employees = getEmployees();
        col_cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        first_name_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        last_name_col.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        salary_col.setCellValueFactory(new PropertyValueFactory<>("salary"));
        Callback<TableColumn<Employee, String>, TableCell<Employee, String>> cellFoctory = (TableColumn<Employee, String> param) -> {
            final TableCell<Employee, String> cell = new TableCell<Employee, String>() {
                Image edit_img = new Image(getClass().getResourceAsStream("/images/edit.png"));
                Image delete_img = new Image(getClass().getResourceAsStream("/images/delete.png"));
                Image view_details_img = new Image(getClass().getResourceAsStream("/images/eye.png"));

                @Override
                protected void updateItem(String item, boolean empty) {
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

                        //delete employee
                        iv_delete.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Employee");
                            alert.setHeaderText("Are you sure you want to delete this employee?");
                            Employee employee = employees_table.getSelectionModel().getSelectedItem();
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                try {
                                    employee.delete();
                                    displayEmployees();
                                } catch (Exception e) {
                                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                                }
                            }
                        });

                        //update employee
                        iv_edit.setOnMouseClicked((MouseEvent event) -> {
                            Employee employee = employees_table.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/update_employee.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                UpdateEmployeeController controller = fxmlLoader.getController();
                                controller.fetchEmployee(employee);
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
                        iv_view_details.setOnMouseClicked((MouseEvent event) -> {
                            Employee employee = employees_table.getSelectionModel().getSelectedItem();
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/employee_details.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                EmployeeDetailsController controller = fxmlLoader.getController();
                                controller.fetchEmployee(employee);
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
        employees_table.setItems(employees);
    }

    private void deleteEmployee(String cin) {
        String query = "DELETE FROM `employees` WHERE cin = " + cin;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            displayAlert("Success", "Employee deleted successfully", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    //add new employee

    public void openAddEmployee(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add Employee", "add_new_employee");
    }

    @FXML
    public void refreshTable() {
        employees_table.getItems().clear();
        displayEmployees();
    }

    public void liveSearch(TextField search_input, TableView table) {
        search_input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                refreshTable();
            } else {
                ObservableList<Employee> filteredList = FXCollections.observableArrayList();
                ObservableList<Employee> employees = getEmployees();
                for (Employee employee : employees) {
                    if (employee.getFirstName().toLowerCase().contains(newValue.toLowerCase()) || employee.getLastName().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(employee);
                    }
                }
                table.setItems(filteredList);
            }
        });
    }

    @FXML
    void searchEmployee(MouseEvent event) {
        liveSearch(this.search_employee_input, employees_table);
    }

    public void displayEmployeeConsole(Employee employee) {
        System.out.println("Employee First Name: " + employee.getFirstName());
        System.out.println("Employee Last Name: " + employee.getLastName());
        System.out.println("Employee Email: " + employee.getEmail());
        System.out.println("Employee Phone: " + employee.getPhone());
        System.out.println("Employee Address: " + employee.getAdress());
        System.out.println("Employee Cin: " + employee.getCin());
        System.out.println("Employee gender: " + employee.getGender());
        System.out.println("Employee Recrutement Date: " + employee.getHireDate());
    }

    void exportToExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"), new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Employees");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("First Name");
                header.createCell(1).setCellValue("Last Name");
                header.createCell(2).setCellValue("Email");
                header.createCell(3).setCellValue("Phone");
                header.createCell(4).setCellValue("Address");
                header.createCell(5).setCellValue("CIN");
                header.createCell(6).setCellValue("Gender");
                header.createCell(7).setCellValue("Hire Date");
                header.createCell(8).setCellValue("Salary");

                //get all employees from database
                String query = "SELECT * FROM `employees`";
                try {
                    statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    while (rs.next()) {
                        int rowNum = rs.getRow();
                        Row row = sheet.createRow(rowNum);
                        row.createCell(0).setCellValue(rs.getString("first_name"));
                        row.createCell(1).setCellValue(rs.getString("last_name"));
                        row.createCell(2).setCellValue(rs.getString("email"));
                        row.createCell(3).setCellValue(rs.getString("phone"));
                        row.createCell(4).setCellValue(rs.getString("address"));
                        row.createCell(5).setCellValue(rs.getString("cin"));
                        if (rs.getString("gender").equals("M")) {
                            row.createCell(6).setCellValue("Male");
                        } else {
                            row.createCell(6).setCellValue("Female");
                        }
                        row.createCell(7).setCellValue(rs.getString("recruitment_date"));
                        row.createCell(8).setCellValue(rs.getString("salary"));
                    }
                } catch (Exception e) {
                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
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
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                try {
                    document.add(new Paragraph("Employees List"));
                    document.add(new Paragraph(" "));
                } catch (Exception e) {
                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }
                PdfPTable table = new PdfPTable(9);
                table.addCell("First Name");
                table.addCell("Last Name");
                table.addCell("Email");
                table.addCell("Phone");
                table.addCell("Address");
                table.addCell("CIN");
                table.addCell("Gender");
                table.addCell("Hire Date");
                table.addCell("Salary");

                //get all employees from database
                String query = "SELECT * FROM `employees`";
                try {
                    statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    while (rs.next()) {
                        table.addCell(rs.getString("first_name"));
                        table.addCell(rs.getString("last_name"));
                        table.addCell(rs.getString("email"));
                        table.addCell(rs.getString("phone"));
                        table.addCell(rs.getString("address"));
                        table.addCell(rs.getString("cin"));
                        if (rs.getString("gender").equals("M")) {
                            table.addCell("Male");
                        } else {
                            table.addCell("Female");
                        }
                        table.addCell(rs.getString("recruitment_date"));
                        table.addCell(rs.getString("salary"));
                    }

                    document.add(table);
                    document.close();
                    displayAlert("Success", "Employees exported successfully", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
}