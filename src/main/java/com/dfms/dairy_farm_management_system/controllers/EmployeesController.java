package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;
import static com.dfms.dairy_farm_management_system.helpers.Helper.openNewWindow;

public class EmployeesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        export_cb.setItems(list);
        displayEmployees();
        liveSearch(search_employee_input, employees_table);
    }

    private Statement st;
    private PreparedStatement pst;
    private Connection con = DBConfig.getConnection();
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
    ObservableList<String> list = FXCollections.observableArrayList("PDF", "Excel");

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
                final Button edit_btn = new Button();
                Image delete_img = new Image(getClass().getResourceAsStream("/images/delete.png"));
                final Button delete_btn = new Button();
                Image view_details_img = new Image(getClass().getResourceAsStream("/images/eye.png"));
                final Button view_details_btn = new Button();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        view_details_btn.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        ImageView iv1 = new ImageView();
                        iv1.setImage(view_details_img);
                        iv1.setPreserveRatio(true);
                        iv1.setSmooth(true);
                        iv1.setCache(true);
                        view_details_btn.setGraphic(iv1);

                        setGraphic(view_details_btn);
                        setText(null);


                        edit_btn.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        ImageView iv = new ImageView();
                        iv.setImage(edit_img);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        edit_btn.setGraphic(iv);

                        setGraphic(edit_btn);
                        setText(null);

                        delete_btn.setStyle("-fx-background-color: transparent;-fx-cursor: hand;-fx-size:15px;");
                        ImageView iv2 = new ImageView();

                        iv2.setImage(delete_img);
                        iv2.setPreserveRatio(true);
                        iv2.setSmooth(true);
                        iv2.setCache(true);
                        delete_btn.setGraphic(iv2);


                        setGraphic(delete_btn);

                        setText(null);

                        HBox managebtn = new HBox(edit_btn, delete_btn, view_details_btn);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(edit_btn, new Insets(1, 1, 0, 3));
                        HBox.setMargin(delete_btn, new Insets(1, 1, 0, 2));
                        HBox.setMargin(view_details_btn, new Insets(1, 1, 0, 1));

                        setGraphic(managebtn);
                        setText(null);
                        delete_btn.setOnMouseClicked((MouseEvent event) -> {
                            displayAlert("Delete", "Are you sure you want to delete this employee?", Alert.AlertType.CONFIRMATION);
                        });
                    }
                }
            };
            return cell;
        };
        actions_col.setCellFactory(cellFoctory);
        employees_table.setItems(employees);
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
}
