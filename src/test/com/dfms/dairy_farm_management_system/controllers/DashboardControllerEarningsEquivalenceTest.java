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

class DashboardControllerEarningsEquivalenceTest {

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

    // -----------------------
    // EC1: valid day codes
    // -----------------------
    @Test
    void validDay_Sun_returnsFirstRowSum_currentBehavior() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(contains("Sunday"))).thenReturn(resultSet);

        // UNION returns multiple rows, but code reads only the first one
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(100);

        int earnings = controller.getEarningsOfSpecificDay("Sun");
        assertEquals(100, earnings);

        verify(statement).executeQuery(contains("UNION"));
    }

    @Test
    void validDay_Mon_returnsValue() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(contains("Monday"))).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(55);

        int earnings = controller.getEarningsOfSpecificDay("Mon");
        assertEquals(55, earnings);
    }

    // -----------------------
    // EC2: invalid day codes
    // -----------------------
    @Test
    void invalidDay_returnsZero_andDoesNotQuery() throws Exception {
        when(connection.createStatement()).thenReturn(statement);

        int earnings = controller.getEarningsOfSpecificDay("Sund");
        assertEquals(0, earnings);

        verify(statement, never()).executeQuery(anyString());
    }

    // -----------------------
    // EC3: empty string
    // -----------------------
    @Test
    void emptyDay_returnsZero_andDoesNotQuery() throws Exception {
        when(connection.createStatement()).thenReturn(statement);

        int earnings = controller.getEarningsOfSpecificDay("");
        assertEquals(0, earnings);

        verify(statement, never()).executeQuery(anyString());
    }

    // -----------------------
    // EC4: null day
    // -----------------------
    @Test
    void nullDay_throwsNullPointerException_currentBehavior() throws Exception {
        when(connection.createStatement()).thenReturn(statement);

        assertThrows(NullPointerException.class,
                () -> controller.getEarningsOfSpecificDay(null));
    }

    // -----------------------
    // EC5: ResultSet has no rows
    // -----------------------
    @Test
    void validDay_resultSetNextFalse_returnsZero() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(contains("Tuesday"))).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        int earnings = controller.getEarningsOfSpecificDay("Tue");
        assertEquals(0, earnings);
    }

    // -----------------------
    // EC6: query throws SQLException
    // -----------------------
    @Test
    void validDay_executeQueryThrowsSQLException_returnsZero_currentBehaviorPrintsStackTrace() throws Exception {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenThrow(new SQLException("db error"));

        int earnings = controller.getEarningsOfSpecificDay("Wed");
        assertEquals(0, earnings);
    }

    // -----------------------
    // EC7: createStatement throws SQLException
    // -----------------------
    @Test
    void createStatementThrowsSQLException_currentBehaviorMayThrowNPE() throws Exception {
        when(connection.createStatement()).thenThrow(new SQLException("db down"));

        try (MockedStatic<?> helper = mockStatic(Class.forName("com.dfms.dairy_farm_management_system.helpers.Helper"))) {
            helper.when(() -> displayAlert(anyString(), anyString(), any(Alert.AlertType.class))).thenAnswer(inv -> null);

            assertThrows(NullPointerException.class,
                    () -> controller.getEarningsOfSpecificDay("Thu"));
        }
    }

    // -------- Reflection helper --------
    private static void inject(Object target, String fieldName, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }
}
