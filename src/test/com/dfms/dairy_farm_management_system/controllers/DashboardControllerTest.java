package com.dfms.dairy_farm_management_system.controllers;

import junit.framework.TestCase;

public class DashboardControllerTest extends TestCase {

    //Branch Test comment
    public void testGetSalesOfSpecificDay() {
        DashboardController dashboardController = new DashboardController();
        int actual = dashboardController.getSalesOfSpecificDay("Fri", "animals_sales");
        int expected = 3;
        assertEquals(expected, actual);
    }

    public void testGetEarningsOfSpecificDay() {
        DashboardController dashboardController = new DashboardController();
        int actual = dashboardController.getEarningsOfSpecificDay("Fri");
        int expected = 500 + 689;
        assertEquals(expected, actual);
    }
}