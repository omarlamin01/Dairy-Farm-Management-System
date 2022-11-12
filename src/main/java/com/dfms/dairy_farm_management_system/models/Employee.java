package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Employee implements Model {
    private int id;
    private String first_name;
    private String last_name;
    private String gender;
    private String cin;
    private String email;
    private String phone;
    private String adress;
    private float salary;
    private Date recruitment_date;
    private String contract_type;
    private Date updated_at;
    private Date created_at;

    public Employee() {
    }

    public Employee(int id, String first_name, String last_name, String gender, String cin, String email, String phone, String adresse, float salary, Date recruitment_date, String contract_type, Date updated_at, Date created_at) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.cin = cin;
        this.email = email;
        this.phone = phone;
        this.adress = adresse;
        this.salary = salary;
        this.recruitment_date = recruitment_date;
        this.contract_type = contract_type;
        this.updated_at = updated_at;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getRecruitmentDate() {
        return recruitment_date;
    }

    public void setRecruitmentDate(Date recruitment_date) {
        this.recruitment_date = recruitment_date;
    }

    public String getContractType() {
        return contract_type;
    }

    public void setContractType(String contract_type) {
        this.contract_type = contract_type;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public boolean save() {
        String insertQuery = "INSERT INTO employee (first_name, last_name, gender, cin, email, phone, address, salary, recruitment_date, contract_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);

            if (gender.equals("Male")) {
                preparedStatement.setString(3, "M");
            } else {
                preparedStatement.setString(3, "F");
            }
            preparedStatement.setString(4, cin);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, phone);
            preparedStatement.setString(7, adress);
            preparedStatement.setString(8, String.valueOf(salary));
            preparedStatement.setString(9, String.valueOf(recruitment_date));
            preparedStatement.setString(10, contract_type);

            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String updateQuery = "UPDATE `employee` SET `first_name` = '" + first_name +
                "', `last_name` = '" + last_name +
                "', `gender` = '" + (gender.equals("Male") ? 'M' : 'F') +
                "', `cin` = '" + cin +
                "', `email` = '" + email +
                "', `phone` = '" + phone +
                "', `address` = '" + adress +
                "', `salary` = '" + salary +
                "', `recruitment_date` = '" + recruitment_date +
                "', `contract_type` = '" + contract_type +
                "', `updated_at` = '" + dtf.format(now) +"' " +
                "WHERE `employee`.`id` = " + this.id;
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete() {
        String updateQuery = "UPDATE `employee` SET `first_name` = '" + first_name +
                "', `last_name` = '" + last_name +
                "', `gender` = '" + (gender.equals("Male") ? 'M' : 'F') +
                "', `cin` = '" + cin +
                "', `email` = '" + email +
                "', `phone` = '" + phone +
                "', `address` = '" + adress +
                "', `salary` = '" + salary +
                "', `recruitment_date` = '" + recruitment_date +
                "', `contract_type` = '" + contract_type +
                "', `updated_at` = '" + dtf.format(now) +"' " +
                "WHERE `employee`.`id` = " + this.id;
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
