package com.dfms.dairy_farm_management_system.controllers;

import javafx.application.Platform;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class LoginControllerLoginTest {

    private LoginController controller;

    @BeforeAll
    static void initJavaFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
            // already started
        }
    }

    @BeforeEach
    void setUp() {
        controller = new LoginController();
        controller.email_input = new TextField();
        controller.password_input = new PasswordField();
    }

    private MouseEvent mockMouseEvent() {
        return mock(MouseEvent.class);
    }

    @FunctionalInterface
    interface ThrowingRunnable {
        void run() throws Exception;
    }

    /** Runs code on the FX thread, waits, and fails if it throws. */
    private void runOnFxAndWait(ThrowingRunnable action) {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> error = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable t) {
                error.set(t);
            } finally {
                latch.countDown();
            }
        });

        try {
            boolean finished = latch.await(8, TimeUnit.SECONDS);
            assertTrue(finished, "FX task timed out (likely blocked by an Alert.showAndWait())");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Interrupted while waiting for FX task");
        }

        if (error.get() != null) {
            fail("Unexpected exception: " + error.get(), error.get());
        }
    }

    /** Repeatedly closes any open JavaFX windows (Alerts) so showAndWait() can return. */
    private void autoCloseAnyDialogsForAWhile(long millis) {
        long end = System.currentTimeMillis() + millis;

        Thread closer = new Thread(() -> {
            while (System.currentTimeMillis() < end) {
                try {
                    Platform.runLater(() -> {
                        for (Window w : Window.getWindows()) {
                            if (w != null && w.isShowing()) {
                                w.hide(); // closes Alert window
                            }
                        }
                    });
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                    return;
                }
            }
        });

        closer.setDaemon(true);
        closer.start();
    }

    @Test
    void testLoginWithNullEmail() {
        controller.email_input.setText(null);
        controller.password_input.setText("password");

        autoCloseAnyDialogsForAWhile(2000);
        assertDoesNotThrow(() -> runOnFxAndWait(() -> controller.login(mockMouseEvent())));
    }

    @Test
    void testLoginWithNullPassword() {
        controller.email_input.setText("test@example.com");
        controller.password_input.setText(null);

        autoCloseAnyDialogsForAWhile(2000);
        assertDoesNotThrow(() -> runOnFxAndWait(() -> controller.login(mockMouseEvent())));
    }

    @Test
    void testLoginWithEmptyEmailAndPassword() {
        controller.email_input.setText("");
        controller.password_input.setText("");

        autoCloseAnyDialogsForAWhile(2000);
        assertDoesNotThrow(() -> runOnFxAndWait(() -> controller.login(mockMouseEvent())));
    }

    @Test
    void testLoginWithInvalidCredentials() {
        controller.email_input.setText("invalid@example.com");
        controller.password_input.setText("wrong");

        autoCloseAnyDialogsForAWhile(2000);
        assertDoesNotThrow(() -> runOnFxAndWait(() -> controller.login(mockMouseEvent())));
    }

    @Test
    void testLoginWithValidCredentials() {
        controller.email_input.setText("test@example.com");
        controller.password_input.setText("password");

        autoCloseAnyDialogsForAWhile(2000);
        assertDoesNotThrow(() -> runOnFxAndWait(() -> controller.login(mockMouseEvent())));
    }
}