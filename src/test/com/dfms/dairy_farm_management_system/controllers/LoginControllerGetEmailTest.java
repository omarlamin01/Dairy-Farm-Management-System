package com.dfms.dairy_farm_management_system.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import java.sql.Connection;
import java.sql.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginControllerGetEmailTest {

    private LoginController controller;

    @BeforeEach
    void setUp() throws Exception {
        controller = new LoginController();

        Connection connection = DBConfig.getConnection();
        Statement statement = connection.createStatement();

        statement.execute("DELETE FROM users");

        DBConfig.disconnect();
    }

    @Test
    void testGetEmailWithSingleUser() throws Exception {
        Connection connection = DBConfig.getConnection();
        Statement statement = connection.createStatement();

        statement.execute(
            """
                INSERT INTO users (id, role, password, gender, cin, phone, salary, email)
                VALUES (1, 1, '12345', 'M', 'CIN001', '0000000000', 1000, 'first@example.com')
            """
        );

        DBConfig.disconnect();

        String email = controller.getEmail();
        assertEquals("first@example.com", email);
    }

    @Test
    void testGetEmailReturnsFirstInsertedUser() throws Exception {
        Connection connection = DBConfig.getConnection();
        Statement statement = connection.createStatement();

        statement.execute(
            """
                INSERT INTO users (id, role, password, gender, cin, phone, salary, email)
                VALUES
                    (1, 1, '12345', 'M', 'CIN001', '0000000000', 1000, 'first@example.com'),
                    (2, 2, '12345', 'F', 'CIN002', '1111111111', 1200, 'second@example.com')
            """
        );

        DBConfig.disconnect();

        String email = controller.getEmail();
        assertEquals("first@example.com", email);
    }

    @Test
    void testGetEmailWhenNoUsersExist() {
        String email = controller.getEmail();
        assertNull(email);
    }
}
