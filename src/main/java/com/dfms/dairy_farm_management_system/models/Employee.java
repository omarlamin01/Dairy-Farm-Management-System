package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

public class Employee implements Model {
    private String first_name;
    private String last_name;
    private String gender;
    private String cin;
    private String email;
    private String phone;
    private String adress;
    private float salary;
    private Date hire_date;
    private String contract_type;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Employee() {
        this.created_at = Timestamp.valueOf(LocalDateTime.now());
        this.updated_at = Timestamp.valueOf(LocalDateTime.now());
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public Date getHireDate() {
        return hire_date;
    }

    public void setHireDate(Date hire_date) {
        this.hire_date = hire_date;
    }

    public String getContractType() {
        return contract_type;
    }

    public void setContractType(String contract_type) {
        this.contract_type = contract_type;
    }

    public Timestamp getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public boolean save() {
        String insertQuery = "INSERT INTO `employees` (first_name, last_name, gender, cin, email, phone, address, salary, hire_date, contract_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);

            if (gender.equalsIgnoreCase("Male")) {
                preparedStatement.setString(3, "M");
            } else {
                preparedStatement.setString(3, "F");
            }
            preparedStatement.setString(4, cin.toUpperCase());
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, phone);
            preparedStatement.setString(7, adress);
            preparedStatement.setString(8, String.valueOf(salary));
            preparedStatement.setDate(9, hire_date);
            preparedStatement.setString(10, contract_type);

            //TODO: add created_at and updated_at

            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String updateQuery = "UPDATE `employees` SET " +
                "`first_name` = '" + first_name +
                "', `last_name` = '" + last_name +
                "', `gender` = '" + (gender.equalsIgnoreCase("Male") ? 'M' : 'F') +
                "', `cin` = '" + cin +
                "', `email` = '" + email +
                "', `phone` = '" + phone +
                "', `address` = '" + adress +
                "', `salary` = '" + salary +
                "', `contract_type` = '" + contract_type +
                "', `updated_at` = '" + dtf.format(now) + "' " +
                "WHERE `employees`.`cin` = '" + cin.toUpperCase() + "'";
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete() {
        String deleteQuery = "DELETE FROM `employees` WHERE `employees`.`cin` = '" + cin.toUpperCase() + "'";
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
