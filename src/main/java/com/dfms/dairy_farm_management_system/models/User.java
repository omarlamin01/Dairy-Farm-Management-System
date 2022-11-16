package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class User extends Employee {
    private int id_user;
    private String password;
    private int roleId;


    public User(int id_employee, String firstName, String lastName, String gender, String cin, String email, String password, String phone, String address, float salary, Date recruitmentDate, String contractType, Date updated_at, Date created_at) {
        super(id_employee, firstName, lastName, gender, cin, email, phone, address, salary, recruitmentDate, contractType, updated_at, created_at);
        this.password = password;
    }

    public User() {
        super();
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean save() {
        int emp_id = 0;
        boolean empIsSaved = super.save();
        if(!empIsSaved) {
            return false;
        } else {
            try {
                String query = "SELECT id FROM `employee` WHERE `cin` = '" + super.getCin() + "'";
                Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    emp_id = Integer.parseInt(resultSet.getString("id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        String insertQuery = "INSERT INTO `user` (`role_id`, `employee_id`, `password`, `first_name`, `last_name`, `gender`, `cin`, `phone`, `salary`, `email`, `address`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, String.valueOf(roleId));
            preparedStatement.setString(2, String.valueOf(emp_id));
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, super.getFirstName());
            preparedStatement.setString(5, super.getLastName());

            if (super.getGender().equalsIgnoreCase("Male")) {
                preparedStatement.setString(6, "M");
            } else {
                preparedStatement.setString(6, "F");
            }
            preparedStatement.setString(7, super.getCin());
            preparedStatement.setString(8, super.getPhone());
            preparedStatement.setString(9, String.valueOf(super.getSalary()));
            preparedStatement.setString(10, super.getEmail());
            preparedStatement.setString(11, super.getAdress());

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

        String updateQuery = "UPDATE `user` SET `password` = '" + password +
                "', `role_id` = '" + roleId +
                "', `employee_id` = '" + super.getId() +
                "', `updated_at` = '" + dtf.format(now) +
                "', `first_name` = '" + super.getFirstName() +
                "', `last_name` = '" + super.getLastName() +
                "', `gender` = '" + (super.getGender().equalsIgnoreCase("Male") ? 'M' : 'F') +
                "', `cin` = '" + super.getCin() +
                "', `phone` = '" + super.getPhone() +
                "', `salary` = '" + super.getSalary() +
                "', `email` = '" + super.getEmail() +
                "', `address` = '" + super.getAdress() +
                "' WHERE `user`.`id` = " + this.id_user;
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            return (preparedStatement.executeUpdate() != 0) && super.update();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete() {
        String deleteQuery = "DELETE FROM `user` WHERE `user`.`id` = " + this.id_user;
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
