package com.dfms.dairy_farm_management_system.helpers;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelperValidatePhoneInputTest {

    @BeforeAll
    static void initJavaFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {

        }
    }

    @Test
    void testValidNumericInput() {
        TextField tf = new TextField();
        Helper.validatePhoneInput(tf);

        tf.setText("1234567890");
        assertEquals("1234567890", tf.getText());
    }

    @Test
    void testInvalidLetters() {
        TextField tf = new TextField();
        Helper.validatePhoneInput(tf);

        tf.setText("12ab34");
        assertEquals("1234", tf.getText());
    }

    @Test
    void testPlusAllowed() {
        TextField tf = new TextField();
        Helper.validatePhoneInput(tf);

        tf.setText("+123");
        assertEquals("+123", tf.getText());
    }
    @Test
    void testEmptyInput() {
        TextField tf = new TextField();
        Helper.validatePhoneInput(tf);

        tf.setText("");
        assertEquals("", tf.getText());
    }

    @Test
    void testOnlyPlus() {
        TextField tf = new TextField();
        Helper.validatePhoneInput(tf);

        tf.setText("+");
        assertEquals("+", tf.getText());
    }

    @Test
    void testSymbolsOnly() {
        TextField tf = new TextField();
        Helper.validatePhoneInput(tf);

        tf.setText("@#$%");
        assertEquals("", tf.getText());
    }

    @Test
    void testPlusInMiddleRemoved() {
        TextField tf = new TextField();
        Helper.validatePhoneInput(tf);

        tf.setText("12+34");
        assertEquals("1234", tf.getText());
    }

}