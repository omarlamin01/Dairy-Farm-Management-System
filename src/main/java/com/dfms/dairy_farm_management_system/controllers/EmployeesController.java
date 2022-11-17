package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.Main;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.EmployeeDetailsController;
import com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers.UpdateEmployeeController;
import com.dfms.dairy_farm_management_system.models.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

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
        ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");
        export_cb.setItems(list);
        displayEmployees();
        liveSearch(search_employee_input, employees_table);
    }

    private Statement st;
    private PreparedStatement pst;
    private Connection con = getConnection();

    @FXML
    private TableView<Employee> employees_table;
    @FXML
    private TableColumn<Employee, String> actions_col;

    @FXML
    private TableColumn<Employee, String> col_id;

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
    private ComboBox<String> export_cb;

    @FXML
    private Button openAddNewEmployeeBtn;

    //get all the employees
    public ObservableList<Employee> getEmployees() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        String query = "SELECT * FROM employee";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setEmail(rs.getString("email"));
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
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
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
                            //mark row as selected
                            TableRow<Employee> currentRow = getTableRow();
                            employees_table.getSelectionModel().select(currentRow.getItem());
                            Employee employee = employees_table.getSelectionModel().getSelectedItem();
                            if (employee != null) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Are you sure you want to delete this employee?");
                                alert.setContentText("Click ok to confirm");
                                Optional<ButtonType> action = alert.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    deleteEmployee(employee.getId());
                                    displayEmployees();
                                }
                            } else {
                                displayAlert("Error", "Please select an employee to delete", Alert.AlertType.ERROR);
                            }
                        });

                        //update employee
                        iv_edit.setOnMouseClicked((MouseEvent event) -> {
                            //mark row as selected
                            TableRow<Employee> currentRow = getTableRow();
                            employees_table.getSelectionModel().select(currentRow.getItem());
                            int id = employees_table.getSelectionModel().getSelectedItem().getId();
                            String path = "/com/dfms/dairy_farm_management_system/popups/update_employee.fxml";
                            FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                displayAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
                                ex.printStackTrace();
                            }
                            UpdateEmployeeController updateEmployeeController = loader.getController();
                            updateEmployeeController.setEmplyeeId(id);
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
                            stage.setTitle("Update Employee");
                            stage.setResizable(false);
                            centerScreen(stage);
                            stage.show();
                        });

                        //view employee details
                        iv_view_details.setOnMouseClicked((MouseEvent event) -> {
                            //mark row as selected
                            TableRow<Employee> currentRow = getTableRow();
                            employees_table.getSelectionModel().select(currentRow.getItem());
                            int id = employees_table.getSelectionModel().getSelectedItem().getId();
                            String url = "popups/employee_details.fxml";
                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/dfms/dairy_farm_management_system/popups/employee_details.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load());
                                EmployeeDetailsController controller = fxmlLoader.getController();
                                System.out.println("id: " + id);
                                controller.setEmployeeId(id);
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

    private void deleteEmployee(int id) {
        String query = "DELETE FROM employee WHERE id = " + id;
        try {
            st = con.createStatement();
            st.executeUpdate(query);
            displayAlert("Success", "Employee deleted successfully", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    //add new employee

    public void openAddEmployee(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add Employee", "add_new_employee");
    }

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
        System.out.println("Employee ID: " + employee.getId());
        System.out.println("Employee First Name: " + employee.getFirstName());
        System.out.println("Employee Last Name: " + employee.getLastName());
        System.out.println("Employee Email: " + employee.getEmail());
        System.out.println("Employee Phone: " + employee.getPhone());
        System.out.println("Employee Address: " + employee.getAdress());
        System.out.println("Employee Cin: " + employee.getCin());
        System.out.println("Employee gender: " + employee.getGender());
        System.out.println("Employee Recrutement Date: " + employee.getRecruitmentDate());
    }

//    public Employee getEmployee(int id) {
//        Employee employee = new Employee();
//        String query = "SELECT * FROM employee WHERE id = " + id;
//        con = getConnection();
//        try {
//            st = con.createStatement();
//            ResultSet rs = st.executeQuery(query);
//            while (rs.next()) {
//                employee.setId(rs.getInt("id"));
//                employee.setFirstName(rs.getString("first_name"));
//                employee.setLastName(rs.getString("last_name"));
//                employee.setEmail(rs.getString("email"));
//                employee.setPhone(rs.getString("phone"));
//                employee.setAdress(rs.getString("address"));
//                employee.setCin(rs.getString("cin"));
//                employee.setGender(rs.getString("gender"));
//                employee.setRecruitmentDate(rs.getDate("recruitment_date"));
//                employee.setSalary(rs.getFloat("salary"));
//            }
//        } catch (Exception e) {
//            displayAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
//        }
//        return employee;
//    }
}
