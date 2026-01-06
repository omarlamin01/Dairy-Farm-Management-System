package com.dfms.dairy_farm_management_system.helpers;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

//Code Coverage Testing
class HelperValidateEmailInputCoverageTest {

    @BeforeAll
    static void initJavaFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    private static void runFxAndWait(Runnable action) {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                latch.countDown();
            }
        });

        try {
            assertTrue(latch.await(3, TimeUnit.SECONDS), "JavaFX action timed out");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Interrupted while waiting for JavaFX thread");
        }
    }
    @Test
    void validEmail_shouldSetTransparentBorder() {
        TextField tf = new TextField();
        runFxAndWait(() -> Helper.validateEmailInput(tf));

        runFxAndWait(() -> tf.setText("john.doe123@example.com"));

        assertEquals("-fx-border-color: transparent", tf.getStyle());
    }
    @Test
    void invalidEmail_shouldSetRedBorder() {
        TextField tf = new TextField();
        runFxAndWait(() -> Helper.validateEmailInput(tf));

        runFxAndWait(() -> tf.setText("not-an-email"));

        assertEquals("-fx-border-color: red", tf.getStyle());
    }

    @Test
    void invalidThenValid_shouldSwitchRedToTransparent() {
        TextField tf = new TextField();
        runFxAndWait(() -> Helper.validateEmailInput(tf));

        runFxAndWait(() -> tf.setText("bad@"));
        assertEquals("-fx-border-color: red", tf.getStyle());

        runFxAndWait(() -> tf.setText("a_b.c-d@domain.co"));
        assertEquals("-fx-border-color: transparent", tf.getStyle());
    }
}
