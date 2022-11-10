package com.dfms.dairy_farm_management_system.controllers;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import com.dfms.dairy_farm_management_system.models.Animal;
import com.dfms.dairy_farm_management_system.models.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
        String query = "SELECT * FROM employees";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId_employee(rs.getInt("id"));
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
        col_id.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
        first_name_col.setCellValueFactory(new PropertyValueFactory<Employee, String>("first_name"));
        last_name_col.setCellValueFactory(new PropertyValueFactory<Employee, String>("last_name"));
        email_col.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
        salary_col.setCellValueFactory(new PropertyValueFactory<Employee, String>("salary"));
        actions_col.setCellValueFactory(new PropertyValueFactory<Employee, String>("actions"));
    }

    public void openAddEmployee(MouseEvent mouseEvent) throws IOException {
        openNewWindow("Add Employee", "add_new_employee");
    }
}
