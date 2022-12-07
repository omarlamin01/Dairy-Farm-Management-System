package com.dfms.dairy_farm_management_system.controllers.pop_ups_controllers;

import com.dfms.dairy_farm_management_system.models.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;

public class EmployeeDetailsController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private Button print_btn;
    @FXML
    private Label header;
    @FXML
    private Label address;

    @FXML
    private Label cin;

    @FXML
    private Label contract_type;

    @FXML
    private Label email;

    @FXML
    private Label first_name;

    @FXML
    private Label gender;

    @FXML
    private Label last_name;

    @FXML
    private Label phone;

    @FXML
    private Label recruitment_date;

    @FXML
    private Label role;

    @FXML
    private Label salary;

    public void fetchEmployee(Employee employee) {

        //get the employee from the database
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `employees` WHERE cin = '" + employee.getCin() + "' LIMIT 1");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                header.setText(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                email.setText(resultSet.getString("email"));
                first_name.setText(resultSet.getString("first_name"));
                last_name.setText(resultSet.getString("last_name"));
                salary.setText(String.valueOf(resultSet.getInt("salary")));
                address.setText(resultSet.getString("address"));
                cin.setText(resultSet.getString("cin"));
                phone.setText(resultSet.getString("phone"));
                contract_type.setText(resultSet.getString("contract_type"));
                recruitment_date.setText(resultSet.getString("hire_date"));

                //TODO: get the role name from the database
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRole(int id) {
        String role = "";
        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM `roles` WHERE id = " + id);
            while (rs.next()) {
                role = rs.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }

    @FXML
    void printEmployeeDetails(MouseEvent event) {
        Printer printer = Printer.getDefaultPrinter();
        if (printer != null) {
            System.out.println("Printer: " + printer.getName());

            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null) {
                System.out.println(job.jobStatusProperty().asString());

                boolean printed = job.printPage(header);
                if (printed) {
                    job.endJob();
                } else {
                    System.out.println("Printing failed.");
                }
            } else {
                System.out.println("Could not create a printer job.");
            }
        } else {
            System.out.println("No printer found.");
        }
    }

    //print the employee details
    public void printEmployeeToConsole(Employee employee) {
        System.out.println("Employee Details");
        System.out.println("First Name: " + employee.getFirstName());
        System.out.println("Last Name: " + employee.getLastName());
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Phone: " + employee.getPhone());
        System.out.println("Address: " + employee.getAdress());
        System.out.println("CIN: " + employee.getCin());
        System.out.println("Salary: " + employee.getSalary());
        System.out.println("Recruitment Date: " + employee.getHireDate());
        System.out.println("Contract Type: " + employee.getContractType());
    }
}