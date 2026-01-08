package com.dfms.dairy_farm_management_system.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class DashboardControllerInitDashboardCoverageTest {

    private DashboardController controller;

    private Text totalEmployees, totalSuppliers, totalProducts, totalClients;
    private Text totalCows, totalCalfs, totalBulls;
    private Text todaySales, todayEarnings;

    @BeforeEach
    void setUp() throws Exception {
        controller = new DashboardController();

        totalEmployees = new Text();
        totalSuppliers = new Text();
        totalProducts = new Text();
        totalClients = new Text();
        totalCows = new Text();
        totalCalfs = new Text();
        totalBulls = new Text();
        todaySales = new Text();
        todayEarnings = new Text();

        inject(controller, "total_employees", totalEmployees);
        inject(controller, "total_suppliers", totalSuppliers);
        inject(controller, "total_products", totalProducts);
        inject(controller, "total_clients", totalClients);
        inject(controller, "total_cows", totalCows);
        inject(controller, "total_calfs", totalCalfs);
        inject(controller, "total_bulls", totalBulls);
        inject(controller, "today_sales", todaySales);
        inject(controller, "today_earnings", todayEarnings);
    }

    // -----------------------------
    // Test 1: Statement coverage baseline
    // All next() true, non-null strings for sales/earnings
    // -----------------------------
    @Test
    void initDashboard_allNextTrue_nonNullSalesAndEarnings_statementCoverage() throws Exception {
        Map<String, ResultSet> rsBySql = new HashMap<>();

        rsBySql.put("SELECT COUNT(*) FROM employees", rsNextTrueString("7"));
        rsBySql.put("SELECT COUNT(*) FROM suppliers", rsNextTrueString("3"));
        rsBySql.put("SELECT COUNT(*) FROM stocks", rsNextTrueString("11"));
        rsBySql.put("SELECT COUNT(*) FROM clients", rsNextTrueString("9"));
        rsBySql.put("SELECT COUNT(*) FROM animals WHERE type = 'cow'", rsNextTrueString("4"));
        rsBySql.put("SELECT COUNT(*) FROM animals WHERE type = 'calf'", rsNextTrueString("2"));
        rsBySql.put("SELECT COUNT(*) FROM animals WHERE type = 'bull'", rsNextTrueString("1"));
        rsBySql.put("SELECT COUNT(*) FROM animals_sales WHERE sale_date = CURDATE()", rsNextTrueString("5"));
        rsBySql.put("SELECT SUM(price) FROM animals_sales WHERE sale_date = CURDATE()", rsNextTrueString("120"));

        try (MockedStatic<DBConfig> db = mockStatic(DBConfig.class)) {
            db.when(() -> DBConfig.executeQuery(anyString())).thenAnswer(inv -> rsBySql.get(inv.getArgument(0)));

            controller.initDashboard();
        }

        assertEquals("7", totalEmployees.getText());
        assertEquals("3", totalSuppliers.getText());
        assertEquals("11", totalProducts.getText());
        assertEquals("9", totalClients.getText());
        assertEquals("4", totalCows.getText());
        assertEquals("2", totalCalfs.getText());
        assertEquals("1", totalBulls.getText());

        assertEquals("5", todaySales.getText());
        assertEquals("$120", todayEarnings.getText());
    }

    // -----------------------------
    // Test 2: Branch/Condition/MC-DC for resultSet.next()
    // Make ONE safe query return next() = false (no extra getString() after it)
    // -----------------------------
    @Test
    void initDashboard_employeesNextFalse_branchConditionMcdc_forNext() throws Exception {
        Map<String, ResultSet> rsBySql = new HashMap<>();

        // next() false => body not executed => total_employees stays default ""
        rsBySql.put("SELECT COUNT(*) FROM employees", rsNextFalse());

        // keep others true (especially today_sales and today_earnings must be true,
        // because code calls getString(1) after their if-block too)
        rsBySql.put("SELECT COUNT(*) FROM suppliers", rsNextTrueString("3"));
        rsBySql.put("SELECT COUNT(*) FROM stocks", rsNextTrueString("11"));
        rsBySql.put("SELECT COUNT(*) FROM clients", rsNextTrueString("9"));
        rsBySql.put("SELECT COUNT(*) FROM animals WHERE type = 'cow'", rsNextTrueString("4"));
        rsBySql.put("SELECT COUNT(*) FROM animals WHERE type = 'calf'", rsNextTrueString("2"));
        rsBySql.put("SELECT COUNT(*) FROM animals WHERE type = 'bull'", rsNextTrueString("1"));
        rsBySql.put("SELECT COUNT(*) FROM animals_sales WHERE sale_date = CURDATE()", rsNextTrueString("5"));
        rsBySql.put("SELECT SUM(price) FROM animals_sales WHERE sale_date = CURDATE()", rsNextTrueString("120"));

        try (MockedStatic<DBConfig> db = mockStatic(DBConfig.class)) {
            db.when(() -> DBConfig.executeQuery(anyString())).thenAnswer(inv -> rsBySql.get(inv.getArgument(0)));

            controller.initDashboard();
        }

        assertEquals("", totalEmployees.getText()); // proves next()==false branch taken for this if
        assertEquals("3", totalSuppliers.getText()); // other ifs still take true branch
    }

    // -----------------------------
    // Test 3: Branch/Condition/MC-DC for (today_sales == null)
    // -----------------------------
    @Test
    void initDashboard_todaySalesNull_setsZero() throws Exception {
        Map<String, ResultSet> rsBySql = new HashMap<>();

        rsBySql.put("SELECT COUNT(*) FROM employees", rsNextTrueString("7"));
        rsBySql.put("SELECT COUNT(*) FROM suppliers", rsNextTrueString("3"));
        rsBySql.put("SELECT COUNT(*) FROM stocks", rsNextTrueString("11"));
        rsBySql.put("SELECT COUNT(*) FROM clients", rsNextTrueString("9"));
        rsBySql.put("SELECT COUNT(*) FROM animals WHERE type = 'cow'", rsNextTrueString("4"));
        rsBySql.put("SELECT COUNT(*) FROM animals WHERE type = 'calf'", rsNextTrueString("2"));
        rsBySql.put("SELECT COUNT(*) FROM animals WHERE type = 'bull'", rsNextTrueString("1"));

        // Null branch for today_sales (getString called twice; both should return null)
        rsBySql.put("SELECT COUNT(*) FROM animals_sales WHERE sale_date = CURDATE()", rsNextTrueString(null));

        // keep earnings normal
        rsBySql.put("SELECT SUM(price) FROM animals_sales WHERE sale_date = CURDATE()", rsNextTrueString("120"));

        try (MockedStatic<DBConfig> db = mockStatic(DBConfig.class)) {
            db.when(() -> DBConfig.executeQuery(anyString())).thenAnswer(inv -> rsBySql.get(inv.getArgument(0)));

            controller.initDashboard();
        }

        assertEquals("0", todaySales.getText()); // proves condition (getString==null) true branch
        assertEquals("$120", todayEarnings.getText());
    }

    // -----------------------------
    // Test 4: Branch/Condition/MC-DC for (today_earnings == null)
    // -----------------------------
    @Test
    void initDashboard_todayEarningsNull_setsDollarZero() throws Exception {
        Map<String, ResultSet> rsBySql = new HashMap<>();

        rsBySql.put("SELECT COUNT(*) FROM employees", rsNextTrueString("7"));
        rsBySql.put("SELECT COUNT(*) FROM suppliers", rsNextTrueString("3"));
        rsBySql.put("SELECT COUNT(*) FROM stocks", rsNextTrueString("11"));
        rsBySql.put("SELECT COUNT(*) FROM clients", rsNextTrueString("9"));
        rsBySql.put("SELECT COUNT(*) FROM animals WHERE type = 'cow'", rsNextTrueString("4"));
        rsBySql.put("SELECT COUNT(*) FROM animals WHERE type = 'calf'", rsNextTrueString("2"));
        rsBySql.put("SELECT COUNT(*) FROM animals WHERE type = 'bull'", rsNextTrueString("1"));

        // today_sales normal
        rsBySql.put("SELECT COUNT(*) FROM animals_sales WHERE sale_date = CURDATE()", rsNextTrueString("5"));

        // Null branch for earnings
        rsBySql.put("SELECT SUM(price) FROM animals_sales WHERE sale_date = CURDATE()", rsNextTrueString(null));

        try (MockedStatic<DBConfig> db = mockStatic(DBConfig.class)) {
            db.when(() -> DBConfig.executeQuery(anyString())).thenAnswer(inv -> rsBySql.get(inv.getArgument(0)));

            controller.initDashboard();
        }

        assertEquals("$0", todayEarnings.getText()); // proves condition true branch
        assertEquals("5", todaySales.getText());
    }

    // -----------------------------
    // Helpers
    // -----------------------------

    private static ResultSet rsNextTrueString(String value) throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true);

        // code may call getString(1) multiple times; keep consistent
        when(rs.getString(1)).thenReturn(value);
        return rs;
    }

    private static ResultSet rsNextFalse() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(false);
        return rs;
    }

    private static void inject(Object target, String fieldName, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }
}
