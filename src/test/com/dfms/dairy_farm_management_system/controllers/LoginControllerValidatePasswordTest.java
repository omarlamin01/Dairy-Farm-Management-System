package com.dfms.dairy_farm_management_system.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import java.sql.Connection;
import java.sql.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginControllerValidatePasswordTest {

    private LoginController controller;

    @BeforeEach
    void setUp() throws Exception {
        controller = new LoginController();

        Connection connection = DBConfig.getConnection();
        Statement statement = connection.createStatement();

        statement.execute("DELETE FROM users");

        statement.execute(
            """
                INSERT INTO users (id, role, password, gender, cin, phone, salary, email)
                VALUES (1, 1, '5f4dcc3b5aa765d61d8327deb882cf99', 'M', 'CIN001', '0000000000', 1000, 'test@example.com')
            """
        ); // hash for "password"

        DBConfig.disconnect();
    }

    @Test
    void testValidEmailAndCorrectPassword() throws Exception {
        boolean result = controller.validatePassword("test@example.com", "password");
        assertTrue(result);
    }

    @Test
    void testValidEmailAndWrongPassword() throws Exception {
        boolean result = controller.validatePassword("test@example.com", "wrongpass");
        assertFalse(result);
    }

    @Test
    void testNonExistingEmail() throws Exception {
        boolean result = controller.validatePassword("nouser@example.com", "password");
        assertFalse(result);
    }

    @Test
    void testEmptyPassword() throws Exception {
        boolean result = controller.validatePassword("test@example.com", "");
        assertFalse(result);
    }

    @Test
    void testEmptyEmail() throws Exception {
        boolean result = controller.validatePassword("", "password");
        assertFalse(result);
    }
}
