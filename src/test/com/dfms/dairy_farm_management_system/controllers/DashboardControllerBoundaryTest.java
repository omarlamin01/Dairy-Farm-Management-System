package com.dfms.dairy_farm_management_system.controllers;

import javafx.scene.control.Alert;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.dfms.dairy_farm_management_system.helpers.Helper.displayAlert;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DashboardControllerBoundaryTest {

    DashboardController controller;

    Connection connection;
    Statement statement;
    ResultSet resultSet;

    @BeforeEach
    void setUp() throws Exception {
        controller = spy(new DashboardController());

        connection = mock(Connection.class);
        statement = mock(Statement.class);
        resultSet = mock(ResultSet.class);

        inject(controller, "connection", connection);
        inject(controller, "statement", statement);
        inject(controller, "resultSet", resultSet);
    }

    // -------- Boundary tests for "day" --------

    @Test
    void dayLowerBoundary_validSun_returnsCount() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(contains("Sunday"))).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(5);

        int sales = controller.getSalesOfSpecificDay("Sun", "animals_sales");

        assertEquals(5, sales);
    }

    @Test
    void dayUpperBoundary_validSat_returnsCount() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(contains("Saturday"))).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(9);

        int sales = controller.getSalesOfSpecificDay("Sat", "milk_sales");

        assertEquals(9, sales);
    }

    @Test
    void dayJustOutsideBoundary_invalidDay_returnsZero() throws Exception {
        when(connection.createStatement()).thenReturn(statement);

        int sales = controller.getSalesOfSpecificDay("Sund", "animals_sales");

        assertEquals(0, sales);
        verify(statement, never()).executeQuery(anyString()); // default branch => no query
    }

    @Test
    void dayEmptyString_returnsZero() throws Exception {
        when(connection.createStatement()).thenReturn(statement);

        int sales = controller.getSalesOfSpecificDay("", "animals_sales");

        assertEquals(0, sales);
        verify(statement, never()).executeQuery(anyString());
    }

    @Test
    void dayNull_throwsNullPointerException_currentBehavior() throws Exception {
        when(connection.createStatement()).thenReturn(statement);

        assertThrows(NullPointerException.class,
                () -> controller.getSalesOfSpecificDay(null, "animals_sales"));
    }

    // -------- Boundary tests for "table" --------

    @Test
    void tableValid_animalsSales_queryRuns() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(startsWith("SELECT COUNT(*) FROM animals_sales"))).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(3);

        int sales = controller.getSalesOfSpecificDay("Mon", "animals_sales");

        assertEquals(3, sales);
    }

    @Test
    void tableEmptyString_causesSQLExceptionAndReturnsZero() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenThrow(new SQLException("bad table"));

        int sales = controller.getSalesOfSpecificDay("Tue", "");

        assertEquals(0, sales);
    }

    @Test
    void tableNull_causesSQLExceptionAndReturnsZero() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenThrow(new SQLException("null table"));

        int sales = controller.getSalesOfSpecificDay("Wed", null);

        assertEquals(0, sales);
    }

    @Test
    void tableInjectionLikeString_isPassedIntoQuery_currentBehavior() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenThrow(new SQLException("syntax error"));

        int sales = controller.getSalesOfSpecificDay("Thu", "animals_sales; DROP TABLE employees;--");

        assertEquals(0, sales);

        verify(statement).executeQuery(contains("animals_sales; DROP TABLE employees;--"));
    }

    // -------- Boundary: resultset has no row / null-like --------

    @Test
    void resultSetNextFalse_returnsZero() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        int sales = controller.getSalesOfSpecificDay("Fri", "animals_sales");

        assertEquals(0, sales);
    }

    // -------- Boundary: statement creation fails --------

    @Test
    void createStatementThrowsSQLException_currentBehaviorMayThrowNPE() throws Exception {
        when(connection.createStatement()).thenThrow(new SQLException("db down"));

        try (MockedStatic<?> helper = mockStatic(Class.forName("com.dfms.dairy_farm_management_system.helpers.Helper"))) {
            helper.when(() -> displayAlert(anyString(), anyString(), any(Alert.AlertType.class))).thenAnswer(inv -> null);

            assertThrows(NullPointerException.class,
                    () -> controller.getSalesOfSpecificDay("Sun", "animals_sales"));
        }
    }

    // -------- Reflection helper --------
    private static void inject(Object target, String fieldName, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }
}
